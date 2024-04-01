package com.vins_nerf.jni.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VinsPhone {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 手机型号ID（vins_phone_type）
     */
    private Long phoneTypeId;

    /**
     * 手机编号
     */
    private String phoneNo;

    /**
     * 加速度计X轴零偏（NULL表示未赋值）
     */
    private Double axBias;

    /**
     * 加速度计Y轴零偏（NULL表示未赋值）
     */
    private Double ayBias;

    /**
     * 加速度计Z轴零偏（NULL表示未赋值）
     */
    private Double azBias;

    /**
     * 陀螺仪X轴零偏（NULL表示未赋值）
     */
    private Double gxBias;

    /**
     * 陀螺仪Y轴零偏（NULL表示未赋值）
     */
    private Double gyBias;

    /**
     * 陀螺仪Z轴零偏（NULL表示未赋值）
     */
    private Double gzBias;

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
