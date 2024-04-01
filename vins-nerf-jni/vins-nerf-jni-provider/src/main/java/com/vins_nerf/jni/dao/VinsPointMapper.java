package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsPointMapper {
    /**
     * 通过多条件，获取VinsPoint
     *
     * @param vinsPoint
     * @return
     */
    List<VinsPoint> query(VinsPoint vinsPoint);

    /**
     * 插入VinsPoint
     *
     * @param vinsPoint
     * @return
     */
    int insertOrUpdate(List<VinsPoint> vinsPoint);

    /**
     * 删除VinsPoint
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
