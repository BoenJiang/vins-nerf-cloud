package com.vins_nerf.jni.service;

import com.vins_nerf.jni.pojo.VinsPoint;

public interface VinsPointService {
    /**
     * 获取dBoWPoint点。
     *
     * @param vinsPoint 要查询的目标点。
     * @return
     */
    VinsPoint get(VinsPoint vinsPoint);

    /**
     * 获取dBoWPoint点。
     *
     * @param id 要查询的目标点id。
     * @return
     */
    VinsPoint getByID(long id);


}
