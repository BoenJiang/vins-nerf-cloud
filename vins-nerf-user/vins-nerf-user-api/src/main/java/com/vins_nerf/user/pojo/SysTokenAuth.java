package com.vins_nerf.user.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class
SysTokenAuth implements Serializable {
    private static final long serialVersionUID = 2089065545470651615L;

    /**
     * 临时Token的accesskey
     */
    private String accesskey;

    /**
     * 临时Token的secretkey
     */
    private String secretkey;

    /**
     * 临时Token的iv
     */
    private String iv;
}
