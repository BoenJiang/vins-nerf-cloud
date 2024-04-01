package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsPhone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsPhoneMapper {
    /**
     * 通过多条件，获取VinsPhone
     *
     * @param vinsPhone
     * @return
     */
    List<VinsPhone> query(VinsPhone vinsPhone);

    /**
     * 插入VinsLine
     *
     * @param vinsPhone
     * @return
     */
    int insert(VinsPhone vinsPhone);

    /**
     * 删除VinsLine
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
