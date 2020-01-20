package com.fhpt.imageqmind.objects;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果
 * @author Marty
 */
@Data
@NoArgsConstructor
public class PageInfo<T> {
    private long total;
    private List<T> list;
}
