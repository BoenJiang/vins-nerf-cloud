package com.vins_nerf.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class SysUserInfo implements Serializable {
    private static final long serialVersionUID = 2089065545470651615L;

    /**
     * 自增ID
     */
    private Long id;

    /**
     * SysUser的对应ID
     */
    private Long userId;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像地址
     */
    private String headUrl;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 地址
     */
    private String address;

    /**
     * 身份证号
     */
    private String idNo;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 性别.
     */
    private Integer gender;

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
