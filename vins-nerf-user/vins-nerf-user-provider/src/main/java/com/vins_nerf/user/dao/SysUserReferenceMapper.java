package com.vins_nerf.user.dao;

import com.vins_nerf.user.pojo.SysUserReference;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SysUserReferenceMapper {
    /**
     * 通过多条件，获取SysUserReference
     *
     * @param sysUserReference
     * @return sysUserReference
     */
    List<SysUserReference> query(SysUserReference sysUserReference);

    /**
     * 插入SysUserReference
     *
     * @param sysUserReference
     * @return
     */
    int insert(SysUserReference sysUserReference);
}
