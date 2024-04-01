package com.vins_nerf.jni.service.impl;

import com.vins_nerf.jni.dao.VinsPointMapper;
import com.vins_nerf.jni.pojo.VinsPoint;
import com.vins_nerf.jni.service.VinsPointService;
import jakarta.annotation.Resource;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("dBoWPointService")
public class VinsPointServiceImpl implements VinsPointService {
    @Resource
    private VinsPointMapper vinsPointMapper;

    @Override
    public VinsPoint get(@NonNull VinsPoint vinsPoint) {
        List<VinsPoint> result = vinsPointMapper.query(vinsPoint);
        return result == null || result.isEmpty() ? null : result.get(0);
    }

    @Override
    public VinsPoint getByID(long id) {
        VinsPoint vinsPoint = new VinsPoint();
        vinsPoint.setId(id);
        return get(vinsPoint);
    }


}
