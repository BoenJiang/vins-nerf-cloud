package com.vins_nerf.jni.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VinsLine {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 线路名称
     */
    private String name;

    /**
     * 场景编号
     */
    private Long viewId;

    /**
     * 相机编号
     */
    private Long cameraId;

    /**
     * 在OSS中的路径(IMU文件地址)
     */
    private String ossPath;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
