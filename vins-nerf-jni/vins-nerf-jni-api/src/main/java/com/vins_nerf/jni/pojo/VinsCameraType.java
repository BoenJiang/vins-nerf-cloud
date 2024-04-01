package com.vins_nerf.jni.pojo;

import com.vins_nerf.core.enums.VinsCameraPosition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VinsCameraType {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 在表vins_phone_type上的编号
     */
    private Long phoneTypeId;

    /**
     * 位置(0:NULL；1:前端；2:后端；3:外置)
     */
    private VinsCameraPosition position;

    /**
     * 在手机上的选择值
     */
    private Integer selection;

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
