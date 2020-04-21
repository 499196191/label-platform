package com.fhpt.imageqmind.job;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.fhpt.imageqmind.config.SystemProperties;
import com.fhpt.imageqmind.constant.TrainingStatus;
import com.fhpt.imageqmind.domain.TrainingInfoEntity;
import com.fhpt.imageqmind.repository.TrainingInfoRepository;
import com.fhpt.imageqmind.service.TrainingInfoService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 训练任务进度同步
 * @author Marty
 */
@JobHandler(value = "asyncTrainingStatusHandler")
@Component
@Slf4j
public class AsyncTrainingStatusHandler extends IJobHandler {

    @Autowired
    private TrainingInfoService trainingInfoService;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private TrainingInfoRepository trainingInfoRepository;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        List<TrainingInfoEntity> list = trainingInfoService.getTrainingList(TrainingStatus.TRAINING);
        for (TrainingInfoEntity trainingInfoEntity : list) {
            Map<String, Object> params = new HashMap<>(1);
            params.put("task_id", trainingInfoEntity.getTaskId());
            try {
                String result = HttpUtil.post(systemProperties.getTrainingAddress() + "/query_model_status", params);
                JSONObject json = JSONObject.parseObject(result);
                Timestamp now = Timestamp.from(Instant.now());
                if (json.getBooleanValue("success")) {
                    if (0 == json.getIntValue("status")) {
                        trainingInfoEntity.setTrainingStatus(TrainingStatus.FINISHED.getType());
                        trainingInfoEntity.setTrainingResult(json.getJSONObject("result").toJSONString());
                        trainingInfoEntity.setFinishTime(now);
                        trainingInfoRepository.save(trainingInfoEntity);
                        log.info("训练任务[id][{}]状态更新成功，更新为已完成", trainingInfoEntity.getId());
                    } else if (-1 == json.getIntValue("status") || -2 == json.getIntValue("status")) {
                        trainingInfoEntity.setTrainingStatus(TrainingStatus.FAILED.getType());
                        trainingInfoEntity.setFinishTime(now);
                        trainingInfoEntity.setFailMsg(json.getString("result"));
                        trainingInfoRepository.save(trainingInfoEntity);
                        log.info("训练任务[id][{}]状态更新成功，更新为失败", trainingInfoEntity.getId());
                    }
                } else {
                    trainingInfoEntity.setTrainingStatus(TrainingStatus.FAILED.getType());
                    trainingInfoEntity.setFinishTime(now);
                    trainingInfoEntity.setFailMsg(json.getString("result"));
                    trainingInfoRepository.save(trainingInfoEntity);
                    log.info("训练任务[id][{}]状态更新成功，更新为失败", trainingInfoEntity.getId());
                }
            } catch (Exception e) {
                log.error("训练任务[id][{}]状态更新失败", trainingInfoEntity.getId());
                e.printStackTrace();
            }
        }
        return ReturnT.SUCCESS;
    }
}
