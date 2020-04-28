package com.fhpt.imageqmind.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fhpt.imageqmind.config.SystemProperties;
import com.fhpt.imageqmind.constant.DeleteStatus;
import com.fhpt.imageqmind.constant.TrainingStatus;
import com.fhpt.imageqmind.domain.*;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.*;
import com.fhpt.imageqmind.repository.*;

import com.fhpt.imageqmind.service.LoginService;
import com.fhpt.imageqmind.service.TrainingInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 训练任务业务实现类
 * @author Marty
 */
@Slf4j
@Service
public class TrainingInfoServiceImpl implements TrainingInfoService {

    @Autowired
    private TrainingInfoRepository trainingInfoRepository;
    @Autowired
    private TaskInfoRepository taskInfoRepository;
    @Autowired
    private TagLabelRepository tagLabelRepository;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private LabelResultRepository labelResultRepository;
    @Autowired
    private ModelInfoRepository modelInfoRepository;
    @Autowired
    private LoginService loginService;
    @PersistenceContext
    private EntityManager em;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void save(AddTrainingVo addTrainingVo) {
        TrainingInfoEntity trainingInfoEntity = new TrainingInfoEntity();
        Long id = addTrainingVo.getId();
        if (id != null) {
            Optional<TrainingInfoEntity> trainingInfoEntityOptional = trainingInfoRepository.findById(id);
            if (trainingInfoEntityOptional.isPresent()) {
                trainingInfoEntity = trainingInfoEntityOptional.get();
            }
        }
        trainingInfoEntity.setName(addTrainingVo.getName());
        trainingInfoEntity.setType(addTrainingVo.getType());
        trainingInfoEntity.setModelType(addTrainingVo.getModelType());
        Timestamp now = Timestamp.from(Instant.now());
        if (trainingInfoEntity.getCreateTime() == null) {
            trainingInfoEntity.setCreateTime(now);
        }
        trainingInfoEntity.setUpdateTime(now);
        trainingInfoEntity.setCreatedBy(loginService.getLoginUserName());
        trainingInfoEntity.setIsDelete(DeleteStatus.NOT_DELETE.getType());
        trainingInfoEntity.setBatchSize(addTrainingVo.getBatchSize());
        trainingInfoEntity.setIterationTimes(addTrainingVo.getIterationTimes());
        trainingInfoEntity.setLearningRate(BigDecimal.valueOf(addTrainingVo.getLearningRate()));
        trainingInfoEntity.setTrainingPercent(BigDecimal.valueOf(addTrainingVo.getTrainingPercent()));
        trainingInfoEntity.setValidatePercent(BigDecimal.valueOf(addTrainingVo.getValidatePercent()));
        trainingInfoEntity.setTestPercent(BigDecimal.valueOf(addTrainingVo.getTestPercent()));
        //找到此次训练任务的所有标注信息
        List<LabelResultEntity> labelResultEntityList = labelResultRepository.getAll(addTrainingVo.getTaskIds(), addTrainingVo.getTagIds());
        //生成文件内容
        StringBuilder stringBuilder = generateFileString(labelResultEntityList);
        String datetime =DateUtil.format(new Date(),"YYYYMMDDHHmmss");
        String lastDir="/zhangsan/"+datetime;
        String dataSetFile = systemProperties.getDataSetDir() + "/taskId" + trainingInfoEntity.getId() + ".dev";
        trainingInfoEntity.setDataSetDir(dataSetFile);
        String modelDir = systemProperties.getModelDir() + lastDir;
        trainingInfoEntity.setModelDir(modelDir);
        //写入文件
        fileWrite(stringBuilder.toString(), dataSetFile);
        //初始化任务状态为训练中
        trainingInfoEntity.setTrainingStatus(TrainingStatus.NOT_START.getType());
        List<TaskInfoEntity> taskInfoEntityList = taskInfoRepository.findAllById(addTrainingVo.getTaskIds());
        trainingInfoEntity.setTaskInfo(taskInfoEntityList);
        List<TagLabelEntity> tagLabelEntityList = tagLabelRepository.findAllById(addTrainingVo.getTagIds());
        trainingInfoEntity.setTagLabels(tagLabelEntityList);
        trainingInfoRepository.save(trainingInfoEntity);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void saveAndStart(AddTrainingVo addTrainingVo) {
        TrainingInfoEntity trainingInfoEntity = new TrainingInfoEntity();
        Long id = addTrainingVo.getId();
        if (id != null) {
            Optional<TrainingInfoEntity> trainingInfoEntityOptional = trainingInfoRepository.findById(id);
            if (trainingInfoEntityOptional.isPresent()) {
                trainingInfoEntity = trainingInfoEntityOptional.get();
            }
        }
        trainingInfoEntity.setName(addTrainingVo.getName());
        Timestamp now = Timestamp.from(Instant.now());
        if (trainingInfoEntity.getCreateTime() == null) {
            trainingInfoEntity.setCreateTime(now);
        }
        trainingInfoEntity.setUpdateTime(now);
        trainingInfoEntity.setCreatedBy(loginService.getLoginUserName());
        trainingInfoEntity.setType(addTrainingVo.getType());
        trainingInfoEntity.setModelType(addTrainingVo.getModelType());
        trainingInfoEntity.setIsDelete(DeleteStatus.NOT_DELETE.getType());
        trainingInfoEntity.setBatchSize(addTrainingVo.getBatchSize());
        trainingInfoEntity.setIterationTimes(addTrainingVo.getIterationTimes());
        trainingInfoEntity.setLearningRate(BigDecimal.valueOf(addTrainingVo.getLearningRate()));
        trainingInfoEntity.setTrainingPercent(BigDecimal.valueOf(addTrainingVo.getTrainingPercent()));
        trainingInfoEntity.setValidatePercent(BigDecimal.valueOf(addTrainingVo.getValidatePercent()));
        trainingInfoEntity.setTestPercent(BigDecimal.valueOf(addTrainingVo.getTestPercent()));
        List<TaskInfoEntity> taskInfoEntityList = taskInfoRepository.findAllById(addTrainingVo.getTaskIds());
        trainingInfoEntity.setTaskInfo(taskInfoEntityList);
        List<TagLabelEntity> tagLabelEntityList = tagLabelRepository.findAllById(addTrainingVo.getTagIds());
        trainingInfoEntity.setTagLabels(tagLabelEntityList);
        trainingInfoRepository.save(trainingInfoEntity);
        //找到此次训练任务的所有标注信息
        List<LabelResultEntity> labelResultEntityList = labelResultRepository.getAll(addTrainingVo.getTaskIds(), addTrainingVo.getTagIds());
        //生成文件内容
        StringBuilder stringBuilder = generateFileString(labelResultEntityList);
        String datetime =DateUtil.format(new Date(),"YYYYMMDDHHmmss");
        String lastDir="/zhangsan/"+datetime;
        String dataSetFile = systemProperties.getDataSetDir() + "/taskId" + trainingInfoEntity.getId() + ".dev";
        trainingInfoEntity.setDataSetDir(dataSetFile);
        String modelDir = systemProperties.getModelDir() + lastDir;
        trainingInfoEntity.setModelDir(modelDir);
        //写入文件
        fileWrite(stringBuilder.toString(), dataSetFile);
        //发送训练任务请求
        String res = sendTrainRequest(dataSetFile, modelDir, addTrainingVo);
        JSONObject json = JSONObject.parseObject(res);
        if (json.getBooleanValue("success")) {
            //初始化任务状态为训练中
            trainingInfoEntity.setTrainingStatus(TrainingStatus.TRAINING.getType());
            trainingInfoEntity.setTaskId(json.getString("result"));
        } else {
            //初始化任务状态为训练中
            trainingInfoEntity.setTrainingStatus(TrainingStatus.FAILED.getType());
            trainingInfoEntity.setFinishTime(Timestamp.from(Instant.now()));
            trainingInfoEntity.setFailMsg(json.getString("result"));
        }
        trainingInfoRepository.save(trainingInfoEntity);
    }

    private StringBuilder generateFileString(List<LabelResultEntity> labelResultEntityList) {
        //生成数据集文件
        StringBuilder stringBuilder = new StringBuilder();
        //1.先找出所有行，并去重（这里从已有标注里面选，未有标注的行不生成数据集文件）
        List<DataRowEntity> dataRowEntities = labelResultEntityList.stream().map(labelResultEntity -> labelResultEntity.getDataRow()).distinct().collect(Collectors.toList());
        TagLabelEntity currentTagLabel = null;
        char[] currentContent = null;
        //2.遍历行，一行一行扫描生成数据集文件
        for (DataRowEntity dataRowEntity : dataRowEntities) {
            currentContent = dataRowEntity.getContent().toCharArray();
            //记录当前扫描的游标
            int curIndex = 0;
            for (LabelResultEntity labelResultEntity : labelResultEntityList) {
                if (labelResultEntity.getDataRow() == dataRowEntity) {
                    currentTagLabel = labelResultEntity.getTagLabel();
                } else {
                    continue;
                }
                //从上次结束游标开始遍历，在标签end结束
                for (int i = curIndex; i <= labelResultEntity.getEnd(); i++) {
                    char temp = currentContent[i];
                    if (i >= labelResultEntity.getStart() && i <= labelResultEntity.getEnd()) {
                        stringBuilder.append(temp);
                        stringBuilder.append(" ");
                        if (i == labelResultEntity.getStart()) {
                            stringBuilder.append("B-" + currentTagLabel.getEnglishName());
                        } else {
                            stringBuilder.append("I-" + currentTagLabel.getEnglishName());
                        }
                    } else {
                        stringBuilder.append(temp);
                        stringBuilder.append(" ");
                        stringBuilder.append("O");
                    }
                    stringBuilder.append("\n");
                    if (temp == '。' || temp == '！') {
                        stringBuilder.append("\n");
                    }
                    curIndex = i;
                }
            }
            //完成文章末尾部分的拼接
            for (int i = curIndex; i < currentContent.length; i++) {
                char temp = currentContent[i];
                stringBuilder.append(temp);
                stringBuilder.append(" ");
                stringBuilder.append("O");
                stringBuilder.append("\n");
                if (temp == '。' || temp == '！') {
                    stringBuilder.append("\n");
                }
            }
        }
        return stringBuilder;
    }

    private void fileWrite(String content, String filePath) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(filePath);
            writer.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private String sendTrainRequest(String dataSetFile, String modelDir, AddTrainingVo addTrainingVo) {
        String trainingAddress = systemProperties.getTrainingAddress();
        Map<String, Object> params = new HashMap<>(10);
        params.put("dataset", dataSetFile);
        params.put("model_dir", modelDir);
        params.put("train_ratio", addTrainingVo.getTrainingPercent());
        params.put("dev_ratio", addTrainingVo.getValidatePercent());
        params.put("test_ratio", addTrainingVo.getTestPercent());
        params.put("batch_size", addTrainingVo.getBatchSize());
        params.put("lr", addTrainingVo.getLearningRate());
        params.put("epochs", addTrainingVo.getIterationTimes());
        log.info("请求训练接口{},参数:{}", trainingAddress + "/ner_model_train", params.toString());
        return HttpUtil.post(trainingAddress + "/ner_model_train", params);
    }

    @Override
    public PageInfo<TrainingInfoVo> query(int page, int pageSize, int type, int trainingStatus, String tags, String name) {
        PageInfo<TrainingInfoVo> pageInfo = new PageInfo<>();
        StringBuilder sql = new StringBuilder("select t from TrainingInfoEntity t where 1=1 ");
        StringBuilder sqlCount = new StringBuilder("select count(t.id) from TrainingInfoEntity t where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.hasText(name)) {
            sql.append(" and t.name like :name");
            sqlCount.append(" and t.name like :name");
            params.put("name", "%" + name + "%");
        }
        if (type != -1) {
            sql.append(" and t.type = :type");
            sqlCount.append(" and t.type = :type");
            params.put("type", type);
        }
        if (trainingStatus != -1) {
            sql.append(" and t.trainingStatus = :trainingStatus");
            sqlCount.append(" and t.trainingStatus = :trainingStatus");
            params.put("trainingStatus", trainingStatus);
        }
        if (StringUtils.hasText(tags)) {
            List<Long> tagIds = Arrays.stream(tags.split(",")).map(t -> Long.parseLong(t)).collect(Collectors.toList());
            sql.append(" and t.id in (select m.trainingId from TrainingTagMapEntity m where m.tagId in :tagId)");
            sqlCount.append(" and t.id in (select m.trainingId from TrainingTagMapEntity m where m.tagId in :tagId)");
            params.put("tagId", tagIds);
        }
        sql.append(" and t.isDelete = 0 order by t.createTime desc");
        sqlCount.append(" and t.isDelete = 0 order by t.createTime desc");
        Query query = em.createQuery(sql.toString());
        Query queryCount = em.createQuery(sqlCount.toString());
        params.forEach((k, v) -> {
            query.setParameter(k, v);
            queryCount.setParameter(k, v);
        });
        List<TrainingInfoEntity> list = (List<TrainingInfoEntity>) query.setFirstResult((page - 1) * pageSize).setMaxResults(pageSize).getResultList();
        long count = (Long)queryCount.getSingleResult();
        pageInfo.setTotal(count);
        List<TrainingInfoVo> trainingInfoVos = new ArrayList<>();
        list.forEach(trainingInfoEntity -> {
            TrainingInfoVo trainingInfoVo = new TrainingInfoVo();
            BeanUtils.copyProperties(trainingInfoEntity, trainingInfoVo);
            trainingInfoVo.setTrainingPercent(trainingInfoEntity.getTrainingPercent().doubleValue());
            trainingInfoVo.setValidatePercent(trainingInfoEntity.getValidatePercent().doubleValue());
            trainingInfoVo.setTestPercent(trainingInfoEntity.getTestPercent().doubleValue());
            trainingInfoVo.setLearningRate(trainingInfoEntity.getLearningRate().doubleValue());
            trainingInfoVo.setCreateTime(trainingInfoEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            if (trainingInfoEntity.getTrainingStatus() == TrainingStatus.FINISHED.getType() || trainingInfoEntity.getTrainingStatus() == TrainingStatus.FAILED.getType() || trainingInfoEntity.getTrainingStatus() == TrainingStatus.STOPPED.getType()) {
                Duration duration = Duration.between(trainingInfoEntity.getCreateTime().toLocalDateTime(), trainingInfoEntity.getFinishTime().toLocalDateTime());
                String constTime = String.format("%d小时%d分钟%d秒", duration.toHours(), duration.toMinutes() % 60, duration.getSeconds() % 60);
                trainingInfoVo.setCostTime(constTime);
            } else if (trainingInfoEntity.getTrainingStatus() == TrainingStatus.NOT_START.getType()) {
                trainingInfoVo.setCostTime("");
            } else {
                Duration duration = Duration.between(trainingInfoEntity.getCreateTime().toLocalDateTime(), LocalDateTime.now());
                String constTime = String.format("%d小时%d分钟%d秒", duration.toHours(), duration.toMinutes() % 60, duration.getSeconds() % 60);
                trainingInfoVo.setCostTime(constTime);
            }
            trainingInfoVos.add(trainingInfoVo);
        });
        pageInfo.setList(trainingInfoVos);
        return pageInfo;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean delete(long id) {
        //        ModelInfoEntity modelInfoEntity = modelInfoRepository.findByTrainingId(id);
        //        if (modelInfoEntity != null) {
        //            modelInfoRepository.delete(modelInfoEntity);
        //        }
        //        trainingInfoRepository.deleteById(id);
        //这里使用伪删除
        Optional<TrainingInfoEntity> optionalTrainingInfoEntity = trainingInfoRepository.findById(id);
        if (optionalTrainingInfoEntity.isPresent()) {
            TrainingInfoEntity trainingInfoEntity = optionalTrainingInfoEntity.get();
            trainingInfoEntity.setIsDelete(DeleteStatus.DELETE.getType());
            trainingInfoRepository.save(trainingInfoEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<TrainingInfoEntity> getTrainingList(TrainingStatus trainingStatus) {
        return trainingInfoRepository.getAllByTrainingStatus(trainingStatus.getType());
    }

    @Override
    public TrainingDetailVo get(long id) {
        TrainingDetailVo trainingDetailVo = new TrainingDetailVo();
        Optional<TrainingInfoEntity> trainingInfoEntityOptional = trainingInfoRepository.findById(id);
        if (trainingInfoEntityOptional.isPresent()) {
            TrainingInfoEntity trainingInfoEntity = trainingInfoEntityOptional.get();
            trainingDetailVo.setId(trainingInfoEntity.getId());
            trainingDetailVo.setType(trainingInfoEntity.getType());
            trainingDetailVo.setName(trainingInfoEntity.getName());
            trainingDetailVo.setModelType(trainingInfoEntity.getModelType());
            trainingDetailVo.setTrainingStatus(trainingInfoEntity.getTrainingStatus());
            trainingDetailVo.setTaskId(trainingInfoEntity.getTaskId());
            trainingDetailVo.setBatchSize(trainingInfoEntity.getBatchSize());
            trainingDetailVo.setLearningRate(trainingInfoEntity.getLearningRate().doubleValue());
            trainingDetailVo.setIterationTimes(trainingInfoEntity.getIterationTimes());
            trainingDetailVo.setTrainingPercent(trainingInfoEntity.getTrainingPercent().doubleValue());
            trainingDetailVo.setFailMsg(trainingInfoEntity.getFailMsg());
            trainingDetailVo.setTestPercent(trainingInfoEntity.getTestPercent().doubleValue());
            trainingDetailVo.setValidatePercent(trainingInfoEntity.getValidatePercent().doubleValue());
            trainingDetailVo.setCreateTime(trainingInfoEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            if (trainingInfoEntity.getTrainingStatus() == TrainingStatus.FINISHED.getType() || trainingInfoEntity.getTrainingStatus() == TrainingStatus.FAILED.getType() || trainingInfoEntity.getTrainingStatus() == TrainingStatus.STOPPED.getType()) {
                Duration duration = Duration.between(trainingInfoEntity.getCreateTime().toLocalDateTime(), trainingInfoEntity.getFinishTime().toLocalDateTime());
                String constTime = String.format("%d小时%d分钟%d秒", duration.toHours(), duration.toMinutes() % 60, duration.getSeconds() % 60);
                trainingDetailVo.setCostTime(constTime);
            } else {
                Duration duration = Duration.between(trainingInfoEntity.getCreateTime().toLocalDateTime(), LocalDateTime.now());
                String constTime = String.format("%d小时%d分钟%d秒", duration.toHours(), duration.toMinutes() % 60, duration.getSeconds() % 60);
                trainingDetailVo.setCostTime(constTime);
            }
            if (trainingInfoEntity.getTrainingResult() != null) {
                JSONObject resultObj = JSONObject.parseObject(trainingInfoEntity.getTrainingResult());
                JSONArray losses = resultObj.getJSONArray("loss");
                trainingDetailVo.setLoss(new BigDecimal(losses.getDouble(losses.size() - 1)).setScale(6, BigDecimal.ROUND_HALF_UP).doubleValue());
                trainingDetailVo.setAllSampleSize(resultObj.getJSONObject("samples_num").getLongValue("total"));
                //处理训练结果信息
                List<TrainingTag> trainingTags = new ArrayList<>();
                JSONObject trainInfo = resultObj.getJSONObject("train");
                trainInfo.keySet().stream().forEach(key -> {
                    if (!"overall".equals(key)) {
                        JSONObject tagTrainInfo = trainInfo.getJSONObject(key);
                        TrainingTag trainingTag = new TrainingTag();
                        TagLabelEntity tagLabelEntity = tagLabelRepository.getByEnglishName(key);
                        trainingTag.setTagName(tagLabelEntity != null ? tagLabelEntity.getName() : key);
                        trainingTag.setPrecision(tagTrainInfo.getDouble("precision"));
                        trainingTag.setRecall(tagTrainInfo.getDouble("recall"));
                        trainingTag.setF(tagTrainInfo.getDouble("FB1"));
                        trainingTags.add(trainingTag);
                    }
                });
                trainingDetailVo.setTrainingTags(trainingTags);
            }
            //处理标记任务信息
            List<TaskInfoVo> taskInfoVos = new ArrayList<>();
            trainingInfoEntity.getTaskInfo().stream().forEach(taskInfoEntity -> {
                TaskInfoVo taskInfoVo = new TaskInfoVo();
                BeanUtils.copyProperties(taskInfoEntity, taskInfoVo);
                String tagNames = taskInfoEntity.getTagLabelEntities().stream().map(t -> t.getName()).collect(Collectors.joining(","));
                taskInfoVo.setTagNames(tagNames);
                //处理标签信息
                List<TagLabelVo> tagLabels = new ArrayList<>();
                List<TagLabelEntity> tagLabelEntityList = taskInfoEntity.getTagLabelEntities();
                tagLabelEntityList.stream().forEach(tagLabelEntity -> {
                    TagLabelVo tagLabelVo = new TagLabelVo();
                    BeanUtils.copyProperties(tagLabelEntity, tagLabelVo);
                    tagLabels.add(tagLabelVo);
                });
                taskInfoVo.setTagLabels(tagLabels);
                taskInfoVos.add(taskInfoVo);
                trainingDetailVo.setAllTaskSize(trainingDetailVo.getAllTaskSize() + taskInfoEntity.getSize());
            });
            trainingDetailVo.setTaskInfoVos(taskInfoVos);
            //处理标记任务信息
            List<SampleDataVo> sampleDataVos = new ArrayList<>();
            //1.查找标签所有类别
            List<Long> taskIds = trainingInfoEntity.getTaskInfo().stream().map(TaskInfoEntity::getId).collect(Collectors.toList());
            List<TagLabelEntity> tagLabelEntities = trainingInfoEntity.getTagLabels();
            //2.分别查找标签下对应的标注信息
            for (TagLabelEntity tagLabelEntity : tagLabelEntities) {
                SampleDataVo sampleDataVo = new SampleDataVo();
                sampleDataVo.setTagName(tagLabelEntity.getName());
                long size =taskIds.size()==0?0: labelResultRepository.getCountByTagIdAndTaskIds(taskIds, tagLabelEntity.getId());
                BigDecimal allSize = new BigDecimal(size);
                sampleDataVo.setTotalNum(allSize.intValue());
                sampleDataVo.setTrainNum(trainingInfoEntity.getTrainingPercent().multiply(allSize).intValue());
                sampleDataVo.setDevNum(trainingInfoEntity.getValidatePercent().multiply(allSize).intValue());
                sampleDataVo.setTestNum(trainingInfoEntity.getTestPercent().multiply(allSize).intValue());
                sampleDataVos.add(sampleDataVo);
            }
            trainingDetailVo.setSampleDataVos(sampleDataVos);
            //处理标记任务信息
            if (trainingInfoEntity.getTrainingResult() != null) {
                List<SampleDataVo> sampleDataVoList = new ArrayList<>();
                String trainingResult =trainingInfoEntity.getTrainingResult();
                JSONObject jo =JSONObject.parseObject(trainingResult);
                JSONObject samplesNumJo =jo.getJSONObject("samples_num");
                JSONObject labelsNumJo =jo.getJSONObject("labels_num");
                JSONObject totalJo =labelsNumJo.getJSONObject("total");
                JSONObject trainJo =labelsNumJo.getJSONObject("train");
                JSONObject devJo =labelsNumJo.getJSONObject("dev");
                JSONObject testJo =labelsNumJo.getJSONObject("test");

                Map<String, Object> totalMap = JSONObject.parseObject(totalJo.toJSONString());
                SampleDataVo sampleTotalVo=new SampleDataVo();
                sampleTotalVo.setTagName("总计");
                sampleTotalVo.setTestNum(samplesNumJo.getIntValue("test"));
                sampleTotalVo.setDevNum(samplesNumJo.getIntValue("dev"));
                sampleTotalVo.setTrainNum(samplesNumJo.getIntValue("train"));
                sampleTotalVo.setTotalNum(samplesNumJo.getIntValue("total"));
                sampleDataVoList.add(sampleTotalVo);
                for (String key:totalMap.keySet()){
                    if("sum".equals(key)){
                        continue;
                    }
                    SampleDataVo sampleDataVo=new SampleDataVo();
                    sampleDataVo.setTagName(key);
                    sampleDataVo.setDevNum(devJo.getIntValue(key));
                    sampleDataVo.setTrainNum(trainJo.getIntValue(key));
                    sampleDataVo.setTestNum(testJo.getIntValue(key));
                    sampleDataVo.setTotalNum(totalJo.getIntValue(key));
                    sampleDataVoList.add(sampleDataVo);
                }
                trainingDetailVo.setSampleDataVos(sampleDataVoList);
            }
        }
        return trainingDetailVo;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public boolean suspend(long id) {
        Optional<TrainingInfoEntity> trainingInfoEntityOptional = trainingInfoRepository.findById(id);
        if (!trainingInfoEntityOptional.isPresent()) {
            return false;
        }
        TrainingInfoEntity trainingInfoEntity = trainingInfoEntityOptional.get();
        //算法挂起任务接口 （暂时只能停止掉任务）
        Map<String, Object> params = new HashMap<>(10);
        params.put("task_id", trainingInfoEntity.getTaskId());
        try {
            String res = HttpUtil.post(systemProperties.getTrainingAddress() + "/stop_model_train", params);
            JSONObject json = JSONObject.parseObject(res);
            if (json.getBoolean("success")) {
                trainingInfoEntity.setTrainingStatus(TrainingStatus.STOPPED.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Timestamp now = Timestamp.from(Instant.now());
        trainingInfoEntity.setFinishTime(now);
        trainingInfoRepository.save(trainingInfoEntity);
        return true;
    }

    @Override
    public boolean restart(long id) {
        Optional<TrainingInfoEntity> trainingInfoEntityOptional = trainingInfoRepository.findById(id);
        if (!trainingInfoEntityOptional.isPresent()) {
            return false;
        }
        TrainingInfoEntity trainingInfoEntity = trainingInfoEntityOptional.get();
        trainingInfoEntity.setTrainingStatus(TrainingStatus.TRAINING.getType());
        //算法重启任务接口 (目前只能重新开始任务，不能再原来基础上继续)
        //发送训练任务请求
        String trainingAddress = systemProperties.getTrainingAddress();
        Map<String, Object> params = new HashMap<>(10);
        params.put("dataset", trainingInfoEntity.getDataSetDir());
        params.put("model_dir", trainingInfoEntity.getModelDir());
        params.put("train_ratio", trainingInfoEntity.getTrainingPercent());
        params.put("dev_ratio", trainingInfoEntity.getValidatePercent());
        params.put("test_ratio", trainingInfoEntity.getTestPercent());
        params.put("batch_size", trainingInfoEntity.getBatchSize());
        params.put("lr", trainingInfoEntity.getLearningRate());
        params.put("epochs", trainingInfoEntity.getIterationTimes());
        try {
            String res = HttpUtil.post(trainingAddress + "/ner_model_train", params);
            JSONObject json = JSONObject.parseObject(res);
            trainingInfoEntity.setTaskId(json.getString("result"));
            if (json.getBooleanValue("success")) {
                //初始化任务状态为训练中
                trainingInfoEntity.setTrainingStatus(TrainingStatus.TRAINING.getType());
            } else {
                //初始化任务状态为训练中
                trainingInfoEntity.setTrainingStatus(TrainingStatus.FAILED.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        Timestamp now = Timestamp.from(Instant.now());
        trainingInfoEntity.setCreateTime(now);
        trainingInfoRepository.save(trainingInfoEntity);
        return true;
    }
}
