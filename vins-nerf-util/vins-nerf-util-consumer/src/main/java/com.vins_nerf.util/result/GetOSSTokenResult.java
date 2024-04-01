package com.vins_nerf.util.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetOSSTokenResult {
    /**
     * 对应的Bucket名字
     */
    private String bucketName;

    /**
     * 在bucket中的路径
     */
    private String bucketPath;

    /**
     * 站点简称
     */
    private String endpoint;

    /**
     * accessKeyId
     */
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    private String accessKeySecret;

    /**
     * securityToken
     */
    private String securityToken;

    /**
     * 过期时间
     */
    private String expiration;
}
