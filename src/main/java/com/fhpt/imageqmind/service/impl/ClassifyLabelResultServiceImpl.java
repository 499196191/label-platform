package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.ClassifyLabelResultEntity;
import com.fhpt.imageqmind.objects.vo.ClassifyLabelResultVo;
import com.fhpt.imageqmind.repository.ClassifyLabelResultRepository;
import com.fhpt.imageqmind.repository.DataRowRepository;
import com.fhpt.imageqmind.repository.TagLabelRepository;
import com.fhpt.imageqmind.service.ClassifyLabelResultService;
import com.fhpt.imageqmind.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
@Service
public class ClassifyLabelResultServiceImpl implements ClassifyLabelResultService {
    @Autowired
    ClassifyLabelResultRepository classifyLabelResultRepository;
    @Autowired
    private TagLabelRepository tagLabelRepository;
    @Autowired
    private DataRowRepository dataRowRepository;
    @Autowired
    private LoginService loginService;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean insert(ClassifyLabelResultVo classifyLabelResultVo) {
        long rowId =classifyLabelResultVo.getDataRowId();
        long tagId =classifyLabelResultVo.getTagId();
        long count =classifyLabelResultRepository.count(rowId,tagId);
        //如果标注存在,返回已标注
        if(count>0){
            return true;
        }
        ClassifyLabelResultEntity entity =new ClassifyLabelResultEntity();
        entity.setTagLabel(tagLabelRepository.findById(classifyLabelResultVo.getTagId()).orElse(null));
        entity.setTaskId(classifyLabelResultVo.getTaskId());
        entity.setDataRow(dataRowRepository.findById(classifyLabelResultVo.getDataRowId()).orElse(null));
        Timestamp now = Timestamp.from(Instant.now());
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        entity.setCreatedBy(loginService.getLoginUserName());
        classifyLabelResultRepository.save(entity);
        return true;
    }

    @Override
    public void delete(long id) {
        classifyLabelResultRepository.deleteById(id);
    }
}
