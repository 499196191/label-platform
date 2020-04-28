package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.RuleEntity;
import com.fhpt.imageqmind.domain.TrainingInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 规则查询相关
 */
public interface RuleRepository extends JpaRepository<RuleEntity, Long> {
    @Query("select count(r.id) from RuleTagMapEntity r where r.tagId = :tagId")
    Long getCountByTag(@Param("tagId")long tagId);

    @Query("select r from RuleEntity r left join RuleTagMapEntity rtm on r.id = rtm.ruleId where rtm.tagId =:tagId")
    List<RuleEntity> getAllByTagId(@Param("tagId")long tagId);
}
