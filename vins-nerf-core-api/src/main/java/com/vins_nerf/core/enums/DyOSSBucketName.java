package com.vins_nerf.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum DyOSSBucketName {
    AIMED_PRIVATE("lilylab-aimed-private"),
    AIMED_PUBLIC_READ("lilylab-aimed-public-read"),
    AIMED_PUBLIC_ALL("lilylab-aimed-public");

    private final String name;
}
