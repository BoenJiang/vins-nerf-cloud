package com.vins_nerf.jni.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VinsCamera {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 手机编号（与表vins_phone对应）
     */
    private Long phoneId;

    /**
     * 相机类型ID（与表vins_camera_type对应）
     */
    private Long cameraTypeId;

    /**
     * 相机的值
     */
    private String value;

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
