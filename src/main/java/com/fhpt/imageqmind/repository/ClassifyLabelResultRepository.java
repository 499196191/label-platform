package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.ClassifyLabelResultEntity;
import com.fhpt.imageqmind.domain.LabelResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *
 * @author xie
 * @date 2020/2/9
 */
public interface ClassifyLabelResultRepository extends JpaRepository<ClassifyLabelResultEntity, Long> {
    @Query("select count(t.id) from ClassifyLabelResultEntity t where t.dataRow.id = :rowId and t.tagLabel.id=:tagId")
    long count(@Param("rowId")long rowId,@Param("tagId")long tagId);

    @Query("select l from ClassifyLabelResultEntity l where l.dataRow.id = :dataRowId")
    List<ClassifyLabelResultEntity> getAllByRowId(@Param("dataRowId") long dataRowId);
}
