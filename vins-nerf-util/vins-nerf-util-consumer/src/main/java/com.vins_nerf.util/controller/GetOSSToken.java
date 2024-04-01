package com.vins_nerf.util.controller;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.vins_nerf.core.auth.AuthLevel;
import com.vins_nerf.core.enums.DyOSSAction;
import com.vins_nerf.core.enums.DyOSSBucketName;
import com.vins_nerf.core.http.*;
import com.vins_nerf.util.param.GetOSSTokenParam;
import com.vins_nerf.util.pojo.DyOSSBucket;
import com.vins_nerf.util.result.GetOSSTokenResult;
import com.vins_nerf.util.servie.DyOSSService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class GetOSSToken {
    private static final String URI = "/util/get-oss-token";

    @DubboReference(version = "1.0.0")
    private DyOSSService dyOSSService;

    private DyOSSBucket getBucket(RestProject project, DyOSSAction action) {
        if (project == null || action == null) return null;

        DyOSSBucketName DyOSSBucketName = null;
        switch (project) {
            case myjy:
                switch (action) {
                    case ADD_HEAD_URL:
                    case RESET_HEAD_URL:
                    case UPLOAD_COLLECTOR_PICTURE:
                        DyOSSBucketName = DyOSSBucketName.AIMED_PUBLIC_READ;
                        break;
                }
        }

        if (DyOSSBucketName == null) return null;
        return dyOSSService.getBucket(DyOSSBucketName.getName());
    }

    /**
     * 获取OSS的临时权限
     *
     * @param param         转化为请求主体的参数
     * @param bindingResult 请求主体参数的格式校验结果
     * @param accesskey     请求用户的accesskey
     * @param restProject   请求的来源项目
     * @return RestResponse
     */
    @RestfulAPI(path = URI, auth = AuthLevel.TOKEN_AUTH)
    public RestResponse getOSSToken(@RequestAttribute(RestConstants.ACCESSKEY) String accesskey,
                                    @RequestAttribute(RestConstants.PROJECT) RestProject restProject,
                                    @RequestBody @Valid GetOSSTokenParam param, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (!fieldErrors.isEmpty()) {
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, fieldErrors.get(0).getDefaultMessage());
        }

        DyOSSAction dyOSSAction = DyOSSAction.parse(param.getAction());
        DyOSSBucket dyOSSBucket = getBucket(restProject, dyOSSAction);
        if (dyOSSBucket == null) {
            String message = String.format("Fail to get Bucket by GetOSSTokenParam[%s].", JSON.toJSONString(param));
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, message);
        }

        String bucketPath = dyOSSAction.getBucketPath(param.getFilePath(), accesskey);
        if (bucketPath == null) {//如果返回null认为是非法的；但是返回""，认为是合法的；
            String message = String.format("Fail to get BucketName by GetOSSTokenParam[%s].", JSON.toJSONString(param));
            return RestResponse.fail(URI, ResponseCode.BAD_REQUEST, message);
        }

        RestResponse response = dyOSSService.assumeOSSRole(dyOSSAction, dyOSSBucket, accesskey, bucketPath);
        if (RestResponse.isFail(response)) return response;//失败，返回错误信息；
        AssumeRoleResponse aliResponse = (AssumeRoleResponse) response.getData();

        //请求成功，则返回成功结果
        GetOSSTokenResult result = new GetOSSTokenResult();
        result.setBucketPath(bucketPath);
        result.setBucketName(dyOSSBucket.getName());
        result.setEndpoint(dyOSSBucket.getEndpoint());
        result.setAccessKeyId(aliResponse.getCredentials().getAccessKeyId());
        result.setAccessKeySecret(aliResponse.getCredentials().getAccessKeySecret());
        result.setSecurityToken(aliResponse.getCredentials().getSecurityToken());
        result.setExpiration(aliResponse.getCredentials().getExpiration());
        return RestResponse.success(result);
    }
}
