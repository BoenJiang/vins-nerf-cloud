package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsPoint;
import com.vins_nerf.jni.pojo.VinsView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsViewMapper {
    /**
     * 通过多条件，获取VinsView
     *
     * @param vinsView
     * @return
     */
    List<VinsPoint> query(VinsView vinsView);

    /**
     * 插入VinsView
     *
     * @param vinsView
     * @return
     */
    int insert(VinsView vinsView);

    /**
     * 更新VinsView
     *
     * @param vinsView
     * @return
     */
    int update(VinsView vinsView);

    /**
     * 删除VinsView
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
