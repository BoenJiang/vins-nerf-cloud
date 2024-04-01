package com.vins_nerf.jni.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VinsImage {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 对应的线路ID（VinsLine）
     */
    private Long lineId;

    /**
     * 在OSS中的路径(图片文件地址)
     */
    private String ossPath;

    /**
     * 图片特征描述（ORB）
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
