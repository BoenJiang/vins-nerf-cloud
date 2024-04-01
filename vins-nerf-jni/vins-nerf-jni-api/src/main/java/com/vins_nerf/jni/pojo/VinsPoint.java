package com.vins_nerf.jni.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VinsPoint {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 图片ID
     */
    private Long imageId;

    /**
     * 图片在X轴上的坐标
     */
    private Double x;

    /**
     * 图片在Y轴上的坐标
     */
    private Double y;

    /**
     * BRIEF特征描述（2^256）
     */
    private byte[] orbDescription;

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
