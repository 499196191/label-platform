package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.constant.DeleteStatus;
import com.fhpt.imageqmind.constant.TagType;



import com.fhpt.imageqmind.domain.TagLabelEntity;


import com.fhpt.imageqmind.exceptions.VerifyException;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.TagLabelCount;
import com.fhpt.imageqmind.objects.vo.TagLabelVo;
import com.fhpt.imageqmind.repository.TagLabelRepository;

import com.fhpt.imageqmind.service.LoginService;
import com.fhpt.imageqmind.service.TagLabelService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import org.springframework.util.StringUtils;


import javax.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;



import java.time.format.DateTimeFormatter;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;


import java.util.Optional;
import java.util.stream.Collectors;


/**
 * 标签业务实现类
 * @author Marty
 */
@Service
public class TagLabelServiceImpl implements TagLabelService {

    @Autowired
    private TagLabelRepository tagLabelRepository;
    @Autowired
    private LoginService loginService;

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
        tagLabelEntity.setFontColor(tagLabel.getFontColor());
        tagLabelEntity.setBackgroundColor(tagLabel.getBackgroudColor());
        tagLabelEntity.setCreatedBy(loginService.getLoginUserName());
        tagLabelEntity.setIsDelete(DeleteStatus.NOT_DELETE.getType());
        tagLabelRepository.save(tagLabelEntity);
        tagLabel.setId(tagLabelEntity.getId());
        return tagLabel;
    }

    @Override
    public boolean verify(Integer type, String name, boolean isChinese) throws VerifyException {
        if (isChinese) {
            if (tagLabelRepository.getCountByName(name, type) > 0) {
                throw new VerifyException(String.format("中文名称：'%s'在当前系统中已经使用，请重新命名！", name));
            }
            if (tagLabelRepository.getCountByNameAndDeleted(name, type) > 0) {
                throw new VerifyException(String.format("中文名称：'%s'在回收站中存在，请从回收站中复原！", name));
            }
        } else {
            if (tagLabelRepository.getCountByEnglishName(name, type) > 0) {
                throw new VerifyException(String.format("英文名称：'%s'在当前系统中已经使用，请重新命名！", name));
            }
            if (tagLabelRepository.getCountByEnglishNameAndDeleted(name, type) > 0) {
                throw new VerifyException(String.format("英文名称：'%s'在回收站中存在，请从回收站中复原！", name));
            }
        }
        return true;
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
        Page<TagLabelEntity> list;
        if (type != -1) {
            list = tagLabelRepository.getListByType(type, pageSize.intValue() == -1 ? null : PageRequest.of(page - 1, pageSize));
        } else {
            list = tagLabelRepository.getList(pageSize.intValue() == -1 ? null : PageRequest.of(page - 1, pageSize));
        }
        PageInfo result = new PageInfo();
        result.setTotal(list.getTotalElements());
        List<TagLabelVo> tagLabelVos = new ArrayList<>();
        convertTagLabelInfo(list.getContent(), tagLabelVos);
        result.setList(tagLabelVos);
        return result;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean delete(String ids) {
        if (StringUtils.hasText(ids)) {
            String[] idsStr = ids.split(",");
            //            Arrays.stream(idsStr).forEach(id -> tagLabelRepository.deleteById(Long.parseLong(id)));
            //这里使用伪删除
            List<TagLabelEntity> tagLabelEntities = tagLabelRepository.findAllById(Arrays.stream(idsStr).map(Long::parseLong).collect(Collectors.toList()));
            tagLabelEntities.forEach(tagLabelEntity -> tagLabelEntity.setIsDelete(DeleteStatus.DELETE.getType()));
            tagLabelRepository.saveAll(tagLabelEntities);
            return true;
        } else {
            return false;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean update(TagLabelVo tagLabel) {
        Optional<TagLabelEntity> tagLabelEntityOptional = tagLabelRepository.findById(tagLabel.getId());
        if (tagLabelEntityOptional.isPresent()) {
            TagLabelEntity tagLabelEntity = tagLabelEntityOptional.get();
            tagLabelEntity.setUpdateTime(Timestamp.from(Instant.now()));
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

    @Override
    public boolean isValid(String chineseName, String englishName) {
        if (chineseName.contains(" ") || englishName.contains(" ")) {
            return false;
        }
        if (englishName.startsWith("0") || englishName.startsWith("1") || englishName.startsWith("2") || englishName.startsWith("3") || englishName.startsWith("4") || englishName.startsWith("5") || englishName.startsWith("6") || englishName.startsWith("7") || englishName.startsWith("8") || englishName.startsWith("9")) {
            return false;
        }
        return true;
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
