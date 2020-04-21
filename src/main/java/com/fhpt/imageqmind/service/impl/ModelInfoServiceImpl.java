package com.fhpt.imageqmind.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhpt.imageqmind.constant.DeployStatus;
import com.fhpt.imageqmind.constant.ModelSource;
import com.fhpt.imageqmind.domain.*;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.AddModelVo;
import com.fhpt.imageqmind.objects.vo.AddRuleModelVo;
import com.fhpt.imageqmind.objects.vo.ModelInfoVo;
import com.fhpt.imageqmind.objects.vo.SortedRule;
import com.fhpt.imageqmind.repository.ModelInfoRepository;
import com.fhpt.imageqmind.repository.RuleRelRepository;
import com.fhpt.imageqmind.repository.RuleRepository;
import com.fhpt.imageqmind.repository.TrainingInfoRepository;
import com.fhpt.imageqmind.service.ModelInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 模型业务实现类
 */
@Service
@Slf4j
public class ModelInfoServiceImpl implements ModelInfoService {

    @Autowired
    private TrainingInfoRepository trainingInfoRepository;
    @Autowired
    private ModelInfoRepository modelInfoRepository;
    @Autowired
    private RuleRepository ruleRepository;
    @Autowired
    private RuleRelRepository ruleRelRepository;
    @Autowired
    private RestTemplate restTemplate;
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean add(AddModelVo addModelVo) {
        Optional<TrainingInfoEntity> trainingInfoEntityOptional = trainingInfoRepository.findById(addModelVo.getTrainingId());
        if (trainingInfoEntityOptional.isPresent()) {
            ModelInfoEntity modelInfoEntity = new ModelInfoEntity();
            modelInfoEntity.setModelName(addModelVo.getModelName());
            modelInfoEntity.setModelType(addModelVo.getType());
            modelInfoEntity.setSourceType(addModelVo.getSourceType());
            modelInfoEntity.setTypesName(addModelVo.getTypesName());
            modelInfoEntity.setDeployStatus(DeployStatus.NOT_DEPLOYED.getType());
            modelInfoEntity.setTrainingInfo(trainingInfoEntityOptional.get());
            Timestamp now = Timestamp.from(Instant.now());
            modelInfoEntity.setCreateTime(now);
            modelInfoEntity.setUpdateTime(now);
            ApiInfoEntity api =new ApiInfoEntity();
            if (modelInfoEntity.getModelType() == 1) {
                api.setId(1);
                log.info("设置类型1");
            } else {
                api.setId(2);
                log.info("设置类型2");
            }
            modelInfoEntity.setApiInfoEntity(api);
            modelInfoRepository.save(modelInfoEntity);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean add(AddRuleModelVo addRuleModelVo) {
        List<SortedRule> sortedRules = addRuleModelVo.getSortedRules();
        ModelInfoEntity modelInfoEntity = new ModelInfoEntity();
        modelInfoEntity.setModelName(addRuleModelVo.getModelName());
        modelInfoEntity.setModelType(addRuleModelVo.getType());
        modelInfoEntity.setTypesName(addRuleModelVo.getTypesName());
        modelInfoEntity.setVersion(addRuleModelVo.getVersion());
        modelInfoEntity.setBrief(addRuleModelVo.getBrief());
        modelInfoEntity.setSourceType(ModelSource.RULE_SETTING.getType());
        modelInfoEntity.setDeployStatus(DeployStatus.DEPLOYED.getType());
        modelInfoEntity.setResultType(addRuleModelVo.getResultType());
        Timestamp now = Timestamp.from(Instant.now());
        modelInfoEntity.setCreateTime(now);
        modelInfoEntity.setUpdateTime(now);
        List<RuleRelEntity> ruleRelEntities = new ArrayList<>();
        if (sortedRules != null) {
            sortedRules.forEach(sortedRule -> {
                Optional<RuleEntity> ruleEntityOptional = ruleRepository.findById(sortedRule.getRuleId());
                if (ruleEntityOptional.isPresent()) {
                    RuleRelEntity ruleRelEntity = new RuleRelEntity();
                    RuleEntity ruleEntity = ruleEntityOptional.get();
                    //词复制
                    List<RuleWordEntity> ruleWordEntities = ruleEntity.getRuleWords();
                    List<RuleWordRelEntity> ruleWordRelEntities = new ArrayList<>();
                    if (ruleWordEntities != null) {
                        ruleWordEntities.forEach(ruleWordEntity -> {
                            RuleWordRelEntity ruleWordRelEntity = new RuleWordRelEntity();
                            BeanUtils.copyProperties(ruleWordEntity, ruleWordRelEntity);
                            ruleWordRelEntity.setId(0);
                            ruleWordRelEntities.add(ruleWordRelEntity);
                        });
                    }
                    BeanUtils.copyProperties(ruleEntity, ruleRelEntity);
                    ruleRelEntity.setRuleWords(ruleWordRelEntities);
                    ruleRelEntity.setId(0);
                    ruleRelEntity.setSortNo(sortedRule.getSortNo());
//                    ruleRelEntity.setModelId(modelInfoEntity.getId());
                    //规则复制
                    ruleRelEntities.add(ruleRelEntity);
                } else {
                    try {
                        throw new NullPointerException("未找到此规则，复制失败！");
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
            });
        } else {
            return false;
        }
        modelInfoEntity.setRules(ruleRelEntities);
        modelInfoRepository.save(modelInfoEntity);
        return true;
    }

    @Override
    public PageInfo<ModelInfoVo> query(String modelName, Integer modelType, Integer sourceType,String typesName,Integer deployStatus, Integer page, Integer pageSize) {
        PageInfo<ModelInfoVo> pageInfo = new PageInfo<>();
        StringBuilder sql = new StringBuilder("select t from ModelInfoEntity t where 1=1 ");
        StringBuilder sqlCount = new StringBuilder("select count(t.id) from ModelInfoEntity t where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(modelName)) {
            sql.append(" and t.modelName like :modelName");
            sqlCount.append(" and t.modelName like :modelName");
            params.put("modelName", "%" + modelName + "%");
        }
        if (StringUtils.hasText(typesName)) {
            sql.append(" and t.typesName = :typesName");
            sqlCount.append(" and t.modelName = :typesName");
            params.put("typesName", typesName);
        }
        if (modelType!=null) {
            sql.append(" and t.modelType = :modelType");
            sqlCount.append(" and t.modelType = :modelType");
            params.put("modelType", modelType);
        }
        if (sourceType!=null) {
            sql.append(" and t.sourceType = :sourceType");
            sqlCount.append(" and t.sourceType = :sourceType");
            params.put("sourceType", sourceType);
        }
        if (deployStatus!=null) {
            sql.append(" and t.deployStatus = :deployStatus");
            sqlCount.append(" and t.deployStatus = :deployStatus");
            params.put("deployStatus", deployStatus);
        }
        Query query = em.createQuery(sql.toString());
        Query queryCount = em.createQuery(sqlCount.toString());
        params.forEach((k, v) -> {
            query.setParameter(k, v);
            queryCount.setParameter(k, v);
        });
        List<ModelInfoEntity> list = (List<ModelInfoEntity>) query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList();
        List<ModelInfoVo> listvo = new ArrayList<>();
        convertModelInfo(list,listvo);
        long count = (Long)queryCount.getSingleResult();
        pageInfo.setTotal(count);
        pageInfo.setList(listvo);
        return pageInfo;
    }

    @Override
    public boolean update (long id,Integer deployStatus,String modelName,String typesName, String version,String brief) {
        boolean flag=true;
        Optional<ModelInfoEntity> modelInfoEntityOptional = modelInfoRepository.findById(id);
        if (modelInfoEntityOptional.isPresent()) {
            ModelInfoEntity modelInfoEntity = modelInfoEntityOptional.get();
            modelInfoEntity.setUpdateTime(Timestamp.from(Instant.now()));

            if(StringUtils.hasText(modelName)){
                modelInfoEntity.setModelName(modelName);
            }
            if(StringUtils.hasText(typesName)){
                modelInfoEntity.setTypesName(typesName);
            }
            if(StringUtils.hasText(version)){
                modelInfoEntity.setVersion(version);
            }
            if(StringUtils.hasText(brief)){
                modelInfoEntity.setBrief(brief);
            }

            if(deployStatus!=null){
                modelInfoEntity.setDeployStatus(deployStatus);
                if(1==modelInfoEntity.getSourceType()&&deployStatus==1){
                    flag=callApi(modelInfoEntity);

                }
                if(flag){
                    log.info("部署成功");
                    modelInfoRepository.save(modelInfoEntity);
                }else{
                    log.info("部署失败");
                }
            }else{
                modelInfoRepository.save(modelInfoEntity);
            }

        }
        return flag;
    }

    private  boolean callApi(ModelInfoEntity modelInfoEntity){
        JSONArray ja = JSONObject.parseArray(modelInfoEntity.getApiInfoEntity().getParamJson());
        JSONObject jo =(JSONObject) ja.get(0);
        String paramName =jo.getString("param");
        MultiValueMap<String,String> param= new LinkedMultiValueMap<>();
        log.info("部署算法接口地址: "+modelInfoEntity.getApiInfoEntity().getApiUrl());
        log.info("请求参数值:"+modelInfoEntity.getTrainingInfo().getModelDir());
        param.set(paramName,modelInfoEntity.getTrainingInfo().getModelDir());
        String resp = "";
        try {
            resp = restTemplate.postForObject(modelInfoEntity.getApiInfoEntity().getApiUrl(),param,String.class);
        }catch (Exception e) {
            resp = "{\"result\": \"程序异常\", \"success\": \"false\",\"msg\":\"请求接口地址超时 :"+modelInfoEntity.getApiInfoEntity().getApiUrl()+"\"}";
        }
        JSONObject respJo = JSONObject.parseObject(resp);
        String flag =respJo.getString("success");
        log.info("模型部署接口响应: "+resp);
        return "true".equals(flag);
    }
    @Override
    public ModelInfoEntity view(long id) {
        Optional<ModelInfoEntity> modelInfoEntityOptional = modelInfoRepository.findById(id);
        return  modelInfoEntityOptional.orElse(null);
    }

    @Override
    public boolean delete(long id) {
        String url ="http://10.95.130.115:8015/alg_middle_platform/api/model_delete";
        Optional<ModelInfoEntity> modelInfoEntityOptional = modelInfoRepository.findById(id);
        if (modelInfoEntityOptional.isPresent()) {
            ModelInfoEntity modelInfoEntity = modelInfoEntityOptional.get();
            modelInfoEntity.getApiInfoEntity().setApiUrl(url);
            boolean flag= callApi(modelInfoEntity);
            update(id,0,null,null,null,null);
            return flag;
        }
        return false;
    }

    @Override
    public boolean del(long id){
        log.info("要删除的id:"+id);
        modelInfoRepository.deleteById(id);
        return true;
    }

    private void convertModelInfo(List<ModelInfoEntity> source, List<ModelInfoVo> target){
        source.forEach(modelInfoEntity -> {
            ModelInfoVo modelInfoVo =getModelInfoVo(modelInfoEntity);
            target.add(modelInfoVo);
        });
    }

    private ModelInfoVo getModelInfoVo(ModelInfoEntity modelInfoEntity) {
        ModelInfoVo modelInfoVo = new ModelInfoVo();
        BeanUtils.copyProperties(modelInfoEntity, modelInfoVo);
        if(1==modelInfoEntity.getSourceType()){
            modelInfoVo.setTrainingId(modelInfoEntity.getTrainingInfo()==null?null:modelInfoEntity.getTrainingInfo().getId());
        }
        modelInfoVo.setCreateTime(modelInfoEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        modelInfoVo.setUpdateTime(modelInfoEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return modelInfoVo;
    }
}
