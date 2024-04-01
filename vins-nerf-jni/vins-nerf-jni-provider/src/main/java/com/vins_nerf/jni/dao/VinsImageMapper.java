package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsImageMapper {
    /**
     * 通过多条件，获取VinsImage
     *
     * @param vinsImage
     * @return
     */
    List<VinsImage> query(VinsImage vinsImage);

    /**
     * 插入VinsImage
     *
     * @param vinsImage
     * @return
     */
    int insert(VinsImage vinsImage);

    /**
     * 删除VinsImage
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
