package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.TagLabelEntity;

import com.fhpt.imageqmind.domain.TaskInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import java.util.List;

public interface TaskInfoRepository extends JpaRepository<TaskInfoEntity, Long> {

    @Query("select t from TaskInfoEntity t where t.taskType.id = :type")
    Page<TaskInfoEntity> getListByType(@Param("type")Long type, Pageable pageable);

    @Query("select t from TaskInfoEntity t left join DataTaskMapEntity dtm on t.id = dtm.taskInfo.id where dtm.dataSet.id = :dataSetId")
    List<TaskInfoEntity> getAll(@Param("dataSetId")Long dataSetId);
}
