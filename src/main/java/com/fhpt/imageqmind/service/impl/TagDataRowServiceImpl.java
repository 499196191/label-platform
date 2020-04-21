package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.TagDataRowEntity;
import com.fhpt.imageqmind.repository.TagDataRowRepository;
import com.fhpt.imageqmind.service.TagDataRowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 数据行业务实现
 * @author Marty
 */
@Service
public class TagDataRowServiceImpl implements TagDataRowService {

    @Autowired
    private TagDataRowRepository tagDataRowRepository;

    @Override
    public void add(TagDataRowEntity tagDataRowEntity) {
        tagDataRowRepository.save(tagDataRowEntity);
    }
}
