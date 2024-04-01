package com.vins_nerf.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class SysUserReference implements Serializable {
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
     * 第三方平台（0:Unkown；1:微信）
     */
    private Integer type;

    /**
     * 平台昵称
     */
    private String unionId;

    /**
     * 平台昵称
     */
    private String nickname;

    /**
     * 平台头像地址
     */
    private String headUrl;

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
