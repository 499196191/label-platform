package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.RuleTagEntity;

import com.fhpt.imageqmind.objects.vo.RuleTagVo;
import com.fhpt.imageqmind.repository.RuleRepository;
import com.fhpt.imageqmind.repository.RuleTagRepository;
import com.fhpt.imageqmind.service.RuleTagService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 规则详情业务
 * @author Marty
 */
@Service
public class RuleTagServiceImpl implements RuleTagService {

    @Autowired
    private RuleTagRepository ruleTagRepository;
    @Autowired
    private RuleRepository ruleRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void add(String name) {
        RuleTagEntity ruleTagEntity = new RuleTagEntity();
        ruleTagEntity.setName(name);
        Timestamp now = Timestamp.from(Instant.now());
        ruleTagEntity.setCreateTime(now);
        ruleTagEntity.setUpdateTime(now);
        ruleTagRepository.save(ruleTagEntity);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean update(Long id, String name) {
        Optional<RuleTagEntity> ruleTagEntityOptional = ruleTagRepository.findById(id);
        if(!ruleTagEntityOptional.isPresent()){
            return false;
        }
        RuleTagEntity ruleTagEntity = ruleTagEntityOptional.get();
        ruleTagEntity.setName(name);
        Timestamp now = Timestamp.from(Instant.now());
        ruleTagEntity.setUpdateTime(now);
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean delete(Long id) {
        Optional<RuleTagEntity> ruleTagEntityOptional = ruleTagRepository.findById(id);
        if (ruleTagEntityOptional.isPresent()) {
            ruleTagRepository.delete(ruleTagEntityOptional.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<RuleTagVo> list() {
        List<RuleTagVo> list = new ArrayList<>();
        ruleTagRepository.findAll().forEach(ruleTagEntity -> {
            RuleTagVo ruleTagVo = new RuleTagVo();
            BeanUtils.copyProperties(ruleTagEntity, ruleTagVo);
            //统计标签下面任务的总数
            ruleTagVo.setSize(ruleRepository.getCountByTag(ruleTagEntity.getId()));
            list.add(ruleTagVo);
        });
        return list;
    }
}
