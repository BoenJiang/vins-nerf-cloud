package com.vins_nerf.core.http;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class RestResponseList<T> {
    /**
     * list
     */
    private List<T> list;

    /**
     * list.size()
     */
    private int size;

    private RestResponseList(List<T> list) {
        this.list = list;
        this.size = list == null ? 0 : list.size();
    }

    public static <T> RestResponseList<T> create(List<T> list) {
        return new RestResponseList<>(list);
    }
}
