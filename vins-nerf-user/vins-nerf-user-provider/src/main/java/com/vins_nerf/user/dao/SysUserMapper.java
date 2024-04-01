package com.vins_nerf.user.dao;

import com.vins_nerf.user.pojo.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysUserMapper {
    /**
     * 通过多条件，获取SysUser
     *
     * @param sysUser
     * @return SysUser
     */
    SysUser query(SysUser sysUser);

    /**
     * 插入SysUser
     *
     * @param sysUser
     * @return
     */
    int insert(SysUser sysUser);

    /**
     * 更新SysUser
     *
     * @param sysUser
     * @return
     */
    int update(SysUser sysUser);
}
