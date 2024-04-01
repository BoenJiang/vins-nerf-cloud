package com.vins_nerf.user.service.impl;


import com.vins_nerf.core.enums.SysGender;
import com.vins_nerf.core.http.ResponseCode;
import com.vins_nerf.core.http.RestProject;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.dao.SysUserInfoMapper;
import com.vins_nerf.user.dao.SysUserMapper;
import com.vins_nerf.user.memdao.UserRedisDao;
import com.vins_nerf.user.pojo.SysUser;
import com.vins_nerf.user.pojo.SysUserInfo;
import com.vins_nerf.user.service.SysTokenAuthService;
import com.vins_nerf.user.service.SysUserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DubboService(version = "1.0.0")
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysTokenAuthService sysTokenAuthService;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    @Resource
    private UserRedisDao userRedisDao;

    @Override
    public SysUser getByUid(long uid) {
        SysUser sysUser = new SysUser();
        sysUser.setId(uid);
        return sysUserMapper.query(sysUser);
    }

    @Override
    public SysUser getByProjectAndUsername(RestProject project, String username) {
        SysUser sysUser = new SysUser();
        if (StringUtil.isPhone(username)) {
            sysUser.setPhone(username);
        } else if (StringUtil.isEmail(username)) {
            sysUser.setEmail(username);
        } else {
            sysUser.setAccesskey(username);
        }
        sysUser.setProject(project.getName());
        return sysUserMapper.query(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse register(String uri, RestProject project, SysUser sysUser) {
        //Param phone and email check
        SysUser queryUser = getByProjectAndUsername(project, sysUser.getPhone());
        if (queryUser != null) return RestResponse.fail(uri, ResponseCode.PHONE_IS_EXIST);
        queryUser = getByProjectAndUsername(project, sysUser.getEmail());
        if (queryUser != null) return RestResponse.fail(uri, ResponseCode.EMAIL_IS_EXIST);

        sysUser.setAccesskey(StringUtil.getNonce(64));
        sysUser.setPassword(sysTokenAuthService.defaultDecrypt(sysUser.getPassword()));
        sysUser.setProject(project.getName());
        if (sysUserMapper.insert(sysUser) <= 0) {
            return RestResponse.fail(uri, ResponseCode.INTERNAL_SERVER_ERROR, "Fail to insert into SysUser.");
        }

        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(sysUser.getId());
        sysUserInfo.setNickname(String.format("%s_%s", project.getName(), StringUtil.getNonce(6)));
        sysUserInfo.setGender(SysGender.UNKNOWN.getState());
        if (sysUserInfoMapper.insert(sysUserInfo) > 0) return RestResponse.success(sysUser);
        return RestResponse.fail(uri, ResponseCode.INTERNAL_SERVER_ERROR, "Fail to insert into SysUserInfo.");
    }

    @Override
    public boolean update(SysUser sysUser) {
        return sysUserMapper.update(sysUser) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestResponse updatePassword(String uri, RestProject project, Long userid, String encPassword) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userid);
        sysUser.setPassword(sysTokenAuthService.defaultDecrypt(encPassword));
        if (!update(sysUser)) {
            //如果更新SysUser失败，则返回错误信息【500】
            return RestResponse.fail(uri, ResponseCode.INTERNAL_SERVER_ERROR, "Fail to update SysUser.");
        }
        return RestResponse.success();
    }

    @Override
    public boolean nonceIsLegal(String nonce) {
        if (StringUtil.isNullOrEmpty(nonce)) return false;
        return StringUtil.isNullOrEmpty(userRedisDao.nonceIsAbsent(nonce));
    }

}
