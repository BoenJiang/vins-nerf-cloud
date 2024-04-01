package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsPhoneType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsPhoneTypeMapper {
    /**
     * 通过多条件，获取VinsPhoneType
     *
     * @param vinsPhoneType
     * @return
     */
    List<VinsPhoneType> query(VinsPhoneType vinsPhoneType);

    /**
     * 插入VinsPhoneType
     *
     * @param vinsPhoneType
     * @return
     */
    int insert(VinsPhoneType vinsPhoneType);

    /**
     * 删除VinsPhoneType
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
