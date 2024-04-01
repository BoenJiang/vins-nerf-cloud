package com.vins_nerf.user.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class SysUser implements Serializable {
    private static final long serialVersionUID = 2089065545470651615L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 系统颁布的AK
     */
    private String accesskey;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机
     */
    private String phone;

    /**
     * 项目
     */
    private String project;

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
