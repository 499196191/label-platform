package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.TagLabelEntity;
import com.fhpt.imageqmind.domain.TrainingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * 训练任务
 * @author Marty
 */
public interface TrainingInfoRepository extends JpaRepository<TrainingInfoEntity, Long> {

    @Query("select t from TrainingInfoEntity t where t.id in (select m.trainingId from TrainingTagMapEntity m where m.tagId = :tagId) and t.isDelete = 0")
    List<TrainingInfoEntity> query(@Param("tagId") long tagId);

    @Query("select t from TrainingInfoEntity t where t.trainingStatus = :status and t.isDelete = 0")
    List<TrainingInfoEntity> getAllByTrainingStatus(@Param("status")int status);

}
