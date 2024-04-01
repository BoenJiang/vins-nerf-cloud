package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsFilter;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsFilterMapper {
    /**
     * 通过多条件，获取vinsFilter
     *
     * @param vinsFilter
     * @return
     */
    List<VinsFilter> query(VinsFilter vinsFilter);

    /**
     * 插入VinsFilter
     *
     * @param vinsFilter
     * @return
     */
    int insert(VinsFilter vinsFilter);

    /**
     * 删除VinsFilter
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
