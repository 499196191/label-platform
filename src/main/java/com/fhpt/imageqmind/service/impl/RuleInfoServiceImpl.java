package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.RuleEntity;

import com.fhpt.imageqmind.domain.RuleTagEntity;
import com.fhpt.imageqmind.domain.RuleWordEntity;

import com.fhpt.imageqmind.domain.TaskInfoEntity;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.AddRuleVo;
import com.fhpt.imageqmind.objects.vo.RuleInfoVo;


import com.fhpt.imageqmind.objects.vo.RuleWordVo;
import com.fhpt.imageqmind.objects.vo.TaskInfoVo;
import com.fhpt.imageqmind.repository.RuleRepository;
import com.fhpt.imageqmind.repository.RuleTagRepository;
import com.fhpt.imageqmind.repository.RuleWordRepository;
import com.fhpt.imageqmind.service.RuleInfoService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;


import java.util.stream.Collectors;

/**
 * 规则信息业务
 * @author Marty
 */
@Service
public class RuleInfoServiceImpl implements RuleInfoService {

    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private RuleTagRepository ruleTagRepository;
    @Autowired
    private RuleWordRepository ruleWordRepository;
    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void add(AddRuleVo addRuleVo) {
        //保存规则
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setName(addRuleVo.getName());
        ruleEntity.setResult(addRuleVo.getResult());
        ruleEntity.setTypeId(addRuleVo.getTypeId());
        Timestamp now = Timestamp.from(Instant.now());
        ruleEntity.setCreateTime(now);
        ruleEntity.setUpdateTime(now);
        ruleEntity.setRuleTags(ruleTagRepository.findAllById(addRuleVo.getRuleTagIds()));
        ruleRepository.save(ruleEntity);
        //保存配词
        List<RuleWordEntity> ruleWords = new ArrayList<>();
        addRuleVo.getRuleWords().forEach(ruleWordVo -> {
            RuleWordEntity ruleWordEntity = new RuleWordEntity();
            ruleWordEntity.setlWord(ruleWordVo.getLWord());
            ruleWordEntity.setrWord(ruleWordVo.getRWord());
            ruleWordEntity.setWordSpacing(ruleWordVo.getWordSpacing());
            ruleWordEntity.setSortNo(0);
            ruleWordEntity.setCreateTime(now);
            ruleWordEntity.setUpdateTime(now);
            ruleWordEntity.setRuleId(ruleEntity.getId());
            ruleWords.add(ruleWordEntity);
        });
        ruleWordRepository.saveAll(ruleWords);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean update(AddRuleVo addRuleVo) {
        Optional<RuleEntity> ruleEntityOptional = ruleRepository.findById(addRuleVo.getId());
        if (!ruleEntityOptional.isPresent()) {
            return false;
        }
        //重新设置规则
        RuleEntity ruleEntity = ruleEntityOptional.get();
        ruleEntity.setName(addRuleVo.getName());
        ruleEntity.setResult(addRuleVo.getResult());
        ruleEntity.setTypeId(addRuleVo.getTypeId());
        Timestamp now = Timestamp.from(Instant.now());
        ruleEntity.setUpdateTime(now);
        ruleEntity.setRuleTags(ruleTagRepository.findAllById(addRuleVo.getRuleTagIds()));
        ruleRepository.save(ruleEntity);
        //保存配词
        List<RuleWordEntity> ruleWords = new ArrayList<>();
        addRuleVo.getRuleWords().forEach(ruleWordVo -> {
            if (ruleWordVo.getId() != null) {
                Optional<RuleWordEntity> ruleWordEntityOptional = ruleWordRepository.findById(ruleWordVo.getId());
                if (ruleWordEntityOptional.isPresent()) {
                    RuleWordEntity ruleWordEntity = ruleWordEntityOptional.get();
                    ruleWordEntity.setlWord(ruleWordVo.getLWord());
                    ruleWordEntity.setrWord(ruleWordVo.getRWord());
                    ruleWordEntity.setWordSpacing(ruleWordVo.getWordSpacing());
                    ruleWordEntity.setSortNo(ruleWordVo.getSortNo());
                    ruleWordEntity.setUpdateTime(now);
                    ruleWords.add(ruleWordEntity);
                }
            } else {
                RuleWordEntity ruleWordEntity = new RuleWordEntity();
                ruleWordEntity.setlWord(ruleWordVo.getLWord());
                ruleWordEntity.setrWord(ruleWordVo.getRWord());
                ruleWordEntity.setWordSpacing(ruleWordVo.getWordSpacing());
                ruleWordEntity.setSortNo(ruleWordVo.getSortNo());
                ruleWordEntity.setCreateTime(now);
                ruleWordEntity.setUpdateTime(now);
                ruleWordEntity.setRuleId(ruleEntity.getId());
                ruleWords.add(ruleWordEntity);
            }
        });
        ruleWordRepository.saveAll(ruleWords);
        return true;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void delete(String ruleIds) {
        if (StringUtils.hasText(ruleIds)) {
            List<Long> ruleList = Arrays.stream(ruleIds.split(",")).map(Long::parseLong).collect(Collectors.toList());
            ruleList.forEach(id -> ruleRepository.deleteById(id));
        }
    }

    @Override
    public PageInfo<RuleInfoVo> query(String ruleTagIds, int typeId, String name, Integer page, Integer pageSize) {
        PageInfo<RuleInfoVo> pageInfo = new PageInfo<>();
        StringBuilder sql = new StringBuilder("select t from RuleEntity t where 1=1");
        StringBuilder sqlCount = new StringBuilder("select count(t.id) from RuleEntity t where 1=1");
        Map<String, Object> params = new HashMap<>();
        if (typeId != -1) {
            sql.append(" and t.typeId = :typeId");
            sqlCount.append(" and t.typeId = :typeId");
            params.put("typeId", typeId);
        }
        if (StringUtils.hasText(name)) {
            sql.append(" and t.name like :name");
            sqlCount.append(" and t.name like :name");
            params.put("name", "%" + name + "%");

        }
        if (StringUtils.hasText(ruleTagIds)) {
            List<Long> ruleTagIdList = Arrays.stream(ruleTagIds.split(",")).map(ruleTagId -> Long.parseLong(ruleTagId)).collect(Collectors.toList());
            sql.append(" and t.id in (select r.ruleId from RuleTagMapEntity r where r.tagId in (:ruleTagId))");
            sqlCount.append(" and t.id in (select r.ruleId from RuleTagMapEntity r where r.tagId in (:ruleTagId))");
            params.put("ruleTagId", ruleTagIdList);
        }
        Query query = em.createQuery(sql.toString());
        Query queryCount = em.createQuery(sqlCount.toString());
        params.forEach((k, v) -> {
            query.setParameter(k, v);
            queryCount.setParameter(k, v);
        });
        List<RuleEntity> list = (List<RuleEntity>) query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList();
        long count = (Long) queryCount.getSingleResult();
        pageInfo.setTotal(count);
        List<RuleInfoVo> ruleInfoVos = new ArrayList<>();
        list.forEach(ruleEntity -> {
            RuleInfoVo ruleInfoVo = new RuleInfoVo();
            BeanUtils.copyProperties(ruleEntity, ruleInfoVo);
            ruleInfoVo.setCreateTime(ruleEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            ruleInfoVo.setUpdateTime(ruleEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            List<Long> tagIds = ruleEntity.getRuleTags().stream().map(RuleTagEntity::getId).collect(Collectors.toList());
            ruleInfoVo.setRuleTagIds(tagIds);
            List<RuleWordVo> ruleWordVos = new ArrayList<>();
            ruleEntity.getRuleWords().forEach(ruleWordEntity -> {
                RuleWordVo ruleWordVo = new RuleWordVo();
                BeanUtils.copyProperties(ruleWordEntity, ruleWordVo);
                ruleWordVos.add(ruleWordVo);
            });
            ruleInfoVo.setRuleWords(ruleWordVos);
            ruleInfoVos.add(ruleInfoVo);
        });
        pageInfo.setList(ruleInfoVos);
        return pageInfo;
    }

    @Override
    public List<String> getRecentWords(String word) {
        //这里取最近的五条合并
        List<String> words = new ArrayList<>();
        List<RuleWordEntity> lRuleWordEntities = ruleWordRepository.getLeftByWordName("%" + word + "%", PageRequest.of(0, 5));
        List<RuleWordEntity> rRuleWordEntities = ruleWordRepository.getRightByWordName("%" + word + "%", PageRequest.of(0, 5));
        for (RuleWordEntity ruleWordEntity:lRuleWordEntities) {
            if(!words.contains(ruleWordEntity.getlWord())){
                words.add(ruleWordEntity.getlWord());
            }
        }
        for (RuleWordEntity ruleWordEntity:rRuleWordEntities) {
            if(!words.contains(ruleWordEntity.getrWord())){
                words.add(ruleWordEntity.getrWord());
            }
        }
//        int lIndex = 0;
//        int rIndex = 0;
//        for (int i = 0; i < 5; i++) {
//            if (words.size() >= 5) {
//                break;
//            }
//            //两两比较
//            if (lIndex < lRuleWordEntities.size() && rIndex < rRuleWordEntities.size()) {
//                RuleWordEntity lRuleWordEntity = lRuleWordEntities.get(lIndex);
//                RuleWordEntity rRuleWordEntity = rRuleWordEntities.get(rIndex);
//                if (lRuleWordEntity.getCreateTime() == rRuleWordEntity.getCreateTime()) {
//                    //这里判断个数不能大于5
//                    if (!words.contains(lRuleWordEntity.getlWord()) && words.size() < 5) {
//                        words.add(lRuleWordEntity.getlWord());
//                    }
//                    if (!words.contains(rRuleWordEntity.getlWord()) && words.size() < 5) {
//                        words.add(rRuleWordEntity.getlWord());
//                    }
//                    lIndex += 1;
//                    rIndex += 1;
//                } else if (lRuleWordEntity.getCreateTime().after(rRuleWordEntity.getCreateTime())) {
//                    if (!words.contains(lRuleWordEntity.getlWord())) {
//                        words.add(lRuleWordEntity.getlWord());
//                    }
//                    lIndex += 1;
//                } else if (lRuleWordEntity.getCreateTime().before(rRuleWordEntity.getCreateTime())) {
//                    if (!words.contains(rRuleWordEntity.getlWord())) {
//                        words.add(rRuleWordEntity.getlWord());
//                    }
//                    rIndex += 1;
//                }
//                //只取一边
//            }else if(lIndex >= lRuleWordEntities.size()){
//
//            }else if(rIndex >= rRuleWordEntities.size()){
//
//            }
//
//
//        }
        return words;
    }
}
