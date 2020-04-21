package com.fhpt.imageqmind.service;

import com.fhpt.imageqmind.constant.TrainingStatus;

import com.fhpt.imageqmind.domain.TrainingInfoEntity;

import com.fhpt.imageqmind.objects.PageInfo;

import com.fhpt.imageqmind.objects.vo.AddTrainingVo;



import com.fhpt.imageqmind.objects.vo.TrainingDetailVo;
import com.fhpt.imageqmind.objects.vo.TrainingInfoVo;



import java.util.List;

/**
 * 训练任务业务
 * @author Marty
 */
public interface TrainingInfoService {

    void save(AddTrainingVo addTrainingVo);

    void saveAndStart(AddTrainingVo addTrainingVo);

    PageInfo<TrainingInfoVo> query(int page, int pageSize,int type, int trainingStatus, String tags, String name);

    boolean delete(long id);

    List<TrainingInfoEntity> getTrainingList(TrainingStatus trainingStatus);

    TrainingDetailVo get(long id);

    boolean suspend(long id);

    boolean restart(long id);
}
