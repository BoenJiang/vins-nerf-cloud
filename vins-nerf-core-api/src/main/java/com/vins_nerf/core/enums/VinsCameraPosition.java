package com.vins_nerf.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public enum VinsCameraPosition {
    UNKNOWN(0),

    /// Front facing camera (a user looking at the screen is seen by the camera).
    FRONT(1),

    /// Back facing camera (a user looking at the screen is not seen by the camera).
    BACK(2),

    /// External camera which may not be mounted to the device.
    EXTERNAL(3),;

    private final int state;

    public static VinsCameraPosition getInstance(int state) {
        for (VinsCameraPosition vinsCameraPosition : VinsCameraPosition.values()) {
            if (vinsCameraPosition.state == state) {
                return vinsCameraPosition;
            }
        }

        return VinsCameraPosition.UNKNOWN;
    }

}
