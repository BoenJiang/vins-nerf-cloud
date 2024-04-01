package com.vins_nerf.jni.dao;

import com.vins_nerf.jni.pojo.VinsLine;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VinsLineMapper {
    /**
     * 通过多条件，获取VinsLine
     *
     * @param vinsLine
     * @return
     */
    List<VinsLine> query(VinsLine vinsLine);

    /**
     * 插入VinsLine
     *
     * @param vinsLine
     * @return
     */
    int insert(VinsLine vinsLine);

    /**
     * 删除VinsLine
     *
     * @param id
     * @return
     */
    int delete(@Param("id") long id);
}
