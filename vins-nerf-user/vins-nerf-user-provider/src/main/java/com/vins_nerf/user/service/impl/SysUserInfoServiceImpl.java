package com.vins_nerf.user.service.impl;

import com.vins_nerf.core.utils.StringUtil;
import com.vins_nerf.user.dao.SysUserInfoMapper;
import com.vins_nerf.user.pojo.SysUserInfo;
import com.vins_nerf.user.service.SysUserInfoService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.List;

@DubboService(version = "1.0.0")
public class SysUserInfoServiceImpl implements SysUserInfoService {
    private static final String UNKNOWN_UID_FORMAT = "UNKNOWN_UID_%d";

    @Resource
    private SysUserInfoMapper sysUserInfoMapper;

    private static String getUnknownUidNickname(long uid) {
        return String.format(UNKNOWN_UID_FORMAT, uid);
    }

    @Override
    public String getNicknameByUid(long userId) {
        SysUserInfo sysUserInfo = this.getByUid(userId);
        return sysUserInfo == null || StringUtil.isNullOrEmpty(sysUserInfo.getNickname()) ?
                getUnknownUidNickname(userId) : sysUserInfo.getNickname();
    }

    @Override
    public SysUserInfo query(SysUserInfo sysUserInfo) {
        List<SysUserInfo> sysUserInfoList = sysUserInfoMapper.query(sysUserInfo);
        return sysUserInfoList == null || sysUserInfoList.isEmpty() ? null : sysUserInfoList.get(0);
    }

    @Override
    public SysUserInfo getByUid(long userId) {
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserId(userId);
        return this.query(sysUserInfo);
    }

    @Override
    public boolean update(SysUserInfo sysUserInfo) {
        return sysUserInfoMapper.update(sysUserInfo) > 0;
    }
}
