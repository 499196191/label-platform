package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.LabelResultEntity;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import javax.transaction.Transactional;
import java.util.List;

/**
 * 标注信息查询
 */
public interface LabelResultRepository extends JpaRepository<LabelResultEntity, Long> {

    @Query("select l from LabelResultEntity l where l.dataRow.id = :dataRowId and l.taskInfo.id = :taskId")
    List<LabelResultEntity> query(@Param("dataRowId") long dataRowId, @Param("taskId") long taskId);

    @Query("select count(l.id) from LabelResultEntity l where l.taskInfo.id = :taskId group by l.dataRow.id")
    long getFinishedRowCount(@Param("taskId") long taskId);

    @Query("select l from LabelResultEntity l where l.taskInfo.id in :taskIds and l.tagLabel.id in :tagIds order by l.dataRow.id, l.start")
    List<LabelResultEntity> getAll(List<Long> taskIds, List<Long> tagIds);

    @Query("select count(l.id) from LabelResultEntity l where l.tagLabel.id = :tagId and l.taskInfo.id = :taskId")
    long getLabelResultSize(@Param("tagId") long tagId, @Param("taskId") long taskId);

    @Query("select count(l.id) from LabelResultEntity l where l.tagLabel.id = :tagId and l.taskInfo.id = :taskId and l.dataRow.id = :rowId")
    long getLabelResultSize(@Param("tagId") long tagId, @Param("taskId") long taskId, @Param("rowId") long rowId);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("delete from LabelResultEntity l where l.dataRow.id = :dataRowId and l.taskInfo.id = :taskId")
    void deleteAllCondition(@Param("dataRowId") long dataRowId, @Param("taskId") long taskId);

    @Query("select l from LabelResultEntity l where l.taskInfo.id = :taskId and l.tagLabel.id = :tagId order by l.createTime desc")
    List<LabelResultEntity> getAll(@Param("taskId")Long taskId, @Param("tagId")Long tagId);

    @Query("select l from LabelResultEntity l where l.taskInfo.id = :taskId and l.tagLabel.id = :tagId and l.dataRow.id = :rowId order by l.createTime desc")
    List<LabelResultEntity> getAll(@Param("taskId")Long taskId, @Param("tagId")Long tagId, @Param("rowId") long rowId);

    @Query("select l from LabelResultEntity l where l.taskInfo.id = :taskId order by l.createTime desc")
    List<LabelResultEntity> getAll(@Param("taskId")Long taskId);

    @Query("select l from LabelResultEntity l where l.dataRow.id = :dataRowId")
    List<LabelResultEntity> getAllByRowId(@Param("dataRowId") long dataRowId);
}
