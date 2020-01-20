package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.LabelResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 标注信息查询
 */
public interface LabelResultRepository extends JpaRepository<LabelResultEntity, Long> {

    @Query("select l from LabelResultEntity l where l.dataRow.id = :dataRowId and l.taskInfo.id = :taskId")
    List<LabelResultEntity> query(@Param("dataRowId") long dataRowId, @Param("taskId") long taskId);


}
