package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.ModelMarketVo;

/**
 * Created by xie on 2020/2/27.
 */
public interface ModelMarketService {

    void insert(ModelMarketVo modelMarketVo);

    PageInfo<ModelMarketVo> query(Integer deployStatus, Integer page, Integer pageSize);

    void update(ModelMarketVo modelMarketVo);

    void delete(long id);
}
