package com.vins_nerf.jni.pojo;

import com.vins_nerf.core.enums.VinsFilterType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VinsFilter {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 过滤类型
     */
    private VinsFilterType type;

    /**
     * 特征1
     */
    private Double feature1;

    /**
     * 特征2
     */
    private Double feature2;

    /**
     * 特征3
     */
    private Double feature3;

    /**
     * 特征4
     */
    private Double feature4;

    /**
     * 特征5
     */
    private Double feature5;

    /**
     * 特征6
     */
    private Double feature6;

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
