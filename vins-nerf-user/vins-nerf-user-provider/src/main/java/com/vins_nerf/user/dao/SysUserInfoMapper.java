package com.vins_nerf.user.dao;

import com.vins_nerf.user.pojo.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysUserInfoMapper {
    /**
     * 通过多条件，获取SysUserInfo
     *
     * @param sysUserInfo
     * @return List<SysUserInfo>
     */
    List<SysUserInfo> query(SysUserInfo sysUserInfo);

    /**
     * 插入SysUserInfo
     *
     * @param sysUserInfo
     * @return
     */
    int insert(SysUserInfo sysUserInfo);

    /**
     * 更新SysUserInfo
     *
     * @param sysUserInfo
     * @return
     */
    int update(SysUserInfo sysUserInfo);
}
