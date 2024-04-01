package com.vins_nerf.util.dao;

import com.vins_nerf.util.pojo.DyOSSBucket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DyOSSBucketMapper {
    /**
     * 通过多条件，获取DyOSSBucket
     *
     * @param dyOSSBucket
     * @return DyOSSBucket
     */
    DyOSSBucket query(DyOSSBucket dyOSSBucket);

    /**
     * 插入DyOSSBucket
     *
     * @param dyOSSBucket
     * @return
     */
    int insert(DyOSSBucket dyOSSBucket);

    /**
     * 更新DyOSSBucket
     *
     * @param dyOSSBucket
     * @return
     */
    int update(DyOSSBucket dyOSSBucket);
}
