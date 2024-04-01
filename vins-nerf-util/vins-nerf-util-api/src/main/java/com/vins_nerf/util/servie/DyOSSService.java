package com.vins_nerf.util.servie;

import com.aliyun.oss.OSS;
import com.vins_nerf.core.enums.DyOSSAction;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.util.pojo.DyOSSBucket;

public interface DyOSSService {
    /**
     * 获取OSS连接。
     *
     * @param endpoint Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
     *                 String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
     * @return
     */
    OSS getOssClient(String endpoint);

    /**
     * 创建存储空间。
     *
     * @param endpoint   Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
     *                   String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
     * @param bucketName 分区的名称；
     * @return
     */
    RestResponse createBucket(String endpoint, String bucketName);

    /**
     * 上传文件
     *
     * @param endpoint   Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
     *                   String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
     * @param bucketName 分区的名称；
     * @param objectName 文件的名称；
     * @return
     */
    RestResponse upload(String endpoint, String bucketName, String objectName);

    /**
     * 下载文件
     *
     * @param endpoint   Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
     *                   String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
     * @param bucketName 分区的名称；
     * @param objectName 文件的名称；
     * @return
     */
    RestResponse download(String endpoint, String bucketName, String objectName);

    /**
     * 列举文件
     *
     * @param endpoint   Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
     *                   String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
     * @param bucketName 分区的名称；
     * @return
     */
    RestResponse list(String endpoint, String bucketName);

    /**
     * 删除文件
     *
     * @param endpoint   Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
     *                   String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
     * @param bucketName 分区的名称；
     * @param objectName 文件的名称；
     * @return
     */
    RestResponse delete(String endpoint, String bucketName, String objectName);

    /**
     * 获取Bucket
     *
     * @param bucketName bucket的名字
     * @return DyOSSBucketName
     */
    DyOSSBucket getBucket(String bucketName);

    /**
     * 创建一个新的live流
     *
     * @param endpoint   Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
     *                   String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
     * @param bucketName 分区的名称；
     * @param objectName 文件的名称；
     * @return
     */
    RestResponse createLiveChannel(String endpoint, String bucketName, String objectName);

    /**
     * 获取操作OSS的临时身份凭证
     *
     * @param dyOSSAction 获取OSS临时身份凭证的动作类型
     * @param dyOSSBucket 获取OSS临时身份凭证的存储区域
     * @param sessionName 获取OSS临时身份凭证的请求标识
     * @param bucketPath  获取OSS临时身份凭证的请求路径
     * @return
     */
    RestResponse assumeOSSRole(DyOSSAction dyOSSAction, DyOSSBucket dyOSSBucket, String sessionName, String bucketPath);
}
