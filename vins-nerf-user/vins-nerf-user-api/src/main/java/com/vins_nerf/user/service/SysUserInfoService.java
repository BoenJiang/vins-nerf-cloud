package com.vins_nerf.user.service;

import com.vins_nerf.user.pojo.SysUserInfo;

public interface SysUserInfoService {
    /**
     * 通过userId，获取SysUserInfo的Nickname
     *
     * @param userId 用户的id
     * @return SysUserInfo.nickname
     */
    String getNicknameByUid(long userId);

    /**
     * 通过sysUserInfo，获取SysUserInfo
     *
     * @param sysUserInfo 用户信息
     * @return SysUserInfo
     */
    SysUserInfo query(SysUserInfo sysUserInfo);

    /**
     * 通过userId，获取SysUserInfo
     *
     * @param userId 用户的id
     * @return SysUserInfo
     */
    SysUserInfo getByUid(long userId);

    /**
     * 把 SysUser 插入数据库
     *
     * @param sysUserInfo 用户信息
     * @return update result
     */
    boolean update(SysUserInfo sysUserInfo);
}
