package com.vins_nerf.user.service.impl;

import com.vins_nerf.user.dao.SysUserReferenceMapper;
import com.vins_nerf.user.service.SysUserReferenceService;
import jakarta.annotation.Resource;
import org.apache.dubbo.config.annotation.DubboService;


@DubboService(version = "1.0.0")
public class SysUserReferenceServiceImpl implements SysUserReferenceService {
    @Resource
    private SysUserReferenceMapper sysUserReferenceMapper;
}
