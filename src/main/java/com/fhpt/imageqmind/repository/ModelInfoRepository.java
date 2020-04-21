package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.DataRowEntity;
import com.fhpt.imageqmind.domain.ModelInfoEntity;

import com.fhpt.imageqmind.domain.TrainingInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 模型信息
 * @author Marty
 */
public interface ModelInfoRepository extends JpaRepository<ModelInfoEntity, Long> {

    @Query(value = "select d from ModelInfoEntity d where d.trainingInfo.id = :trainingId")
    ModelInfoEntity findByTrainingId(@Param("trainingId") Long trainingId);
    @Query(value = "select d from DataRowEntity d where d.dataSet.id = :dataSetId")
    Page<DataRowEntity> getDataRows(@Param("dataSetId") Long dataSetId, Pageable pageable);
}
