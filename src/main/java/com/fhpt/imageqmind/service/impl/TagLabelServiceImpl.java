package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.constant.TagType;


import com.fhpt.imageqmind.domain.DataSetEntity;
import com.fhpt.imageqmind.domain.TagLabelEntity;



import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.TagLabelCount;
import com.fhpt.imageqmind.objects.vo.TagLabelVo;
import com.fhpt.imageqmind.repository.TagLabelRepository;
import com.fhpt.imageqmind.service.TagLabelService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;



import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;


import java.util.Optional;


/**
 * 标签业务实现类
 * @author Marty
 */
@Service
public class TagLabelServiceImpl implements TagLabelService {

    @Autowired
    private TagLabelRepository tagLabelRepository;

    @Override
    public boolean checkName(TagLabelVo tagLabel) {
        return false;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public TagLabelVo add(TagLabelVo tagLabel) {
        //名称检查
        long count = tagLabelRepository.getCountByNameOrEnglishName(tagLabel.getName(), tagLabel.getEnglishName());
        if (count > 0) {
            return tagLabel;
        }
        TagLabelEntity tagLabelEntity = new TagLabelEntity();
        tagLabelEntity.setName(tagLabel.getName());
        tagLabelEntity.setEnglishName(tagLabel.getEnglishName());
        tagLabelEntity.setType(tagLabel.getType());
        Timestamp now = Timestamp.from(Instant.now());
        tagLabelEntity.setCreateTime(now);
        tagLabelEntity.setUpdateTime(now);
        tagLabelRepository.save(tagLabelEntity);
        tagLabel.setId(tagLabelEntity.getId());
        return tagLabel;
    }

    @Override
    public TagLabelCount getCountInfo() {
        TagLabelCount tagLabelCount = new TagLabelCount();
        tagLabelCount.setNoDesignedCount(tagLabelRepository.getCountInfoByType(TagType.NOT_SIGNED.getType()));
        tagLabelCount.setEntityTagCount(tagLabelRepository.getCountInfoByType(TagType.ENTITY_TAG.getType()));
        tagLabelCount.setTypeTagCount(tagLabelRepository.getCountInfoByType(TagType.TYPE_TAG.getType()));
        tagLabelCount.setRelationTagCount(tagLabelRepository.getCountInfoByType(TagType.RELATION_TAG.getType()));
        return tagLabelCount;
    }

    @Override
    public PageInfo<TagLabelVo> query(Integer type, Integer page, Integer pageSize) {
        TagLabelEntity tagLabelExample = new TagLabelEntity();
        tagLabelExample.setType(type);
        Example<TagLabelEntity> example = Example.of(tagLabelExample);
        Page<TagLabelEntity> list = tagLabelRepository.findAll(example, PageRequest.of(page - 1, pageSize));
        PageInfo result = new PageInfo();
        result.setTotal(list.getTotalElements());
        List<TagLabelVo> tagLabelVos = new ArrayList<>();
        convertTagLabelInfo(list.getContent(), tagLabelVos);
        result.setList(tagLabelVos);
        return result;
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public void delete(Long id) {
        tagLabelRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean update(TagLabelVo tagLabel) {
        Optional<TagLabelEntity> tagLabelEntityOptional = tagLabelRepository.findById(tagLabel.getId());
        if (tagLabelEntityOptional.isPresent()) {
            TagLabelEntity tagLabelEntity = tagLabelEntityOptional.get();
            tagLabelEntity.setUpdateTime(Timestamp.from(Instant.now()));
            tagLabelEntity.setType(tagLabel.getType());
            tagLabelEntity.setName(tagLabel.getName());
            tagLabelEntity.setEnglishName(tagLabel.getEnglishName());
            tagLabelRepository.save(tagLabelEntity);
            return true;
        }
        return false;
    }

    @Override
    public void changeType(List<Long> ids, int type) {
        List<TagLabelEntity> list = tagLabelRepository.findAllById(ids);
        list.forEach(tagLabelEntity -> tagLabelEntity.setType(type));
        tagLabelRepository.saveAll(list);
    }

    private void convertTagLabelInfo(List<TagLabelEntity> source, List<TagLabelVo> target){
        source.forEach(tagLabelEntity -> {
            TagLabelVo tagLabelVo = new TagLabelVo();
            BeanUtils.copyProperties(tagLabelEntity, tagLabelVo);
            tagLabelVo.setCreateTime(tagLabelEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            tagLabelVo.setUpdateTime(tagLabelEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            target.add(tagLabelVo);
        });
    }


}
