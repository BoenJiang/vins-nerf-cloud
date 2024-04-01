package com.vins_nerf.user.service;

import com.vins_nerf.core.http.RestProject;
import com.vins_nerf.core.http.RestResponse;
import com.vins_nerf.user.pojo.SysUser;


public interface SysUserService {
    /**
     * 通过Id，获取SysUser
     *
     * @param uid 用户的id
     * @return SysUser
     */
    SysUser getByUid(long uid);

    /**
     * 通过多条件，从数据库获取SysUser
     *
     * @param project  用户项目来源
     * @param username 用户账号（手机或邮箱）
     * @return SysUser
     */
    SysUser getByProjectAndUsername(RestProject project, String username);

    /**
     * 把 SysUser、SysUserInfo 插入数据库
     *
     * @param uri     调用请求的URI
     * @param project 用户项目来源
     * @param sysUser 用户信息(其中password是加密的)
     * @return SysUser
     */
    RestResponse register(String uri, RestProject project, SysUser sysUser);

    /**
     * 把 SysUser 插入数据库
     *
     * @param sysUser 用户信息
     * @return SysUser
     */
    boolean update(SysUser sysUser);

    /**
     * 更新 SysUser 的 password
     *
     * @param uri         调用请求的URI
     * @param project     用户项目来源
     * @param userid      用户的id
     * @param encPassword 用户新的密码(加密)
     * @return SysUser
     */
    RestResponse updatePassword(String uri, RestProject project, Long userid, String encPassword);

    /**
     * 确认nonce是否存在
     *
     * @param nonce 请求随机码
     * @return
     */
    boolean nonceIsLegal(String nonce);
}
