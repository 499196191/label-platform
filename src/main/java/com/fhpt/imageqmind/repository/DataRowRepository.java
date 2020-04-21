package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.DataRowEntity;

import com.fhpt.imageqmind.domain.LabelResultEntity;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by admin on 2020/1/14.
 */
public interface DataRowRepository extends JpaRepository<DataRowEntity, Long> {
    @Query(value = "select d from DataRowEntity d where d.dataSet.id = :dataSetId")
    Page<DataRowEntity> getDataRows(@Param("dataSetId") Long dataSetId, Pageable pageable);

    @Query(value = "select d from DataRowEntity d left join DataTaskMapEntity dtm on d.dataSet.id = dtm.dataSet.id where d.id = :dataRowId and dtm.taskInfo.id = :taskId")
    DataRowEntity getOne(@Param("dataRowId") Long dataRowId, @Param("taskId") Long taskId);
}
