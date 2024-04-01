package com.vins_nerf.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum DyOSSBucketAuth {
    PRIVATE(0),//私有
    PUBLIC_READ_ONLY(1),//公共读
    PUBLIC_READ_WRITE(2);//公共读写

    private final Integer value;
}
