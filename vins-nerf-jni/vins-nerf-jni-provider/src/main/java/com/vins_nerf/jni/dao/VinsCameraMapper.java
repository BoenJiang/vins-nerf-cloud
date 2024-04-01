package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsCamera;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsCameraMapper {
    /**
     * 通过多条件，获取VinsCamera
     *
     * @param vinsCamera
     * @return
     */
    List<VinsCamera> query(VinsCamera vinsCamera);

    /**
     * 插入VinsCamera
     *
     * @param vinsCamera
     * @return
     */
    int insert(VinsCamera vinsCamera);

    /**
     * 删除VinsCamera
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
