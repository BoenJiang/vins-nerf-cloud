package com.vins_nerf.util.pojo;

import com.vins_nerf.core.utils.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class DyOSSBucket implements Serializable {
    private static final long serialVersionUID = 2089065545470651615L;

    /**
     * Bucket的ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限
     */
    private Integer auth;

    /**
     * 站点简称
     */
    private String endpoint;

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

    /**
     * 内网站点
     */
    public String getIntraEndpoint(){
        return StringUtil.isNullOrEmpty(endpoint) ? null : String.format("oss-%s-internal.aliyuncs.com", endpoint);
    }

    /**
     * 外网站点
     */
    public String getOuterEndpoint() {
        return StringUtil.isNullOrEmpty(endpoint) ? null : String.format("oss-%s.aliyuncs.com", endpoint);
    }

    /**
     * 内网域名
     */
    public String getIntraDomain(){
        String intraEndpoint = getIntraEndpoint();
        return StringUtil.haveNullOrEmpty(name, intraEndpoint) ? null : String.format("%s.%s", name, intraEndpoint);
    }

    /**
     * 外网域名
     */
    public String getOuterDomain() {
        String outerEndpoint = getOuterEndpoint();
        return StringUtil.haveNullOrEmpty(name, outerEndpoint) ? null : String.format("%s.%s", name, outerEndpoint);
    }
}
