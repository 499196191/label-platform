package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.TaskRowMapEntity;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import java.util.List;

/**
 * Created by admin on 2020/1/14.
 */
public interface TaskRowMapRepository extends JpaRepository<TaskRowMapEntity, Long> {

    @Query(value = "select t from TaskRowMapEntity t where t.rowId = :dataRowId and t.taskId = :taskId")
    TaskRowMapEntity getOne(@Param("dataRowId") Long dataRowId, @Param("taskId") Long taskId);

    @Query(value = "select t from TaskRowMapEntity t where t.taskId = :taskId and (t.status = 1 or t.status = 2)")
    List<TaskRowMapEntity> getNext(@Param("taskId") Long taskId, Pageable pageable);

    @Query(value = "select t from TaskRowMapEntity t where t.taskId = :taskId and t.status = :status")
    List<TaskRowMapEntity> getAllByTaskIdAndStatus(@Param("taskId") Long taskId, @Param("status") int status);
}
