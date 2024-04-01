package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsCameraType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsCameraTypeMapper {
    /**
     * 通过多条件，获取VinsCameraType
     *
     * @param vinsCameraType
     * @return
     */
    List<VinsCameraType> query(VinsCameraType vinsCameraType);

    /**
     * 插入VinsCamera
     *
     * @param vinsCameraType
     * @return
     */
    int insert(VinsCameraType vinsCameraType);

    /**
     * 删除VinsCameraType
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
