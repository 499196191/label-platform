package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.RuleTagEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 规则标签
 * @author Marty
 */
public interface RuleTagRepository extends JpaRepository<RuleTagEntity, Long> {

    @Query("select count(r.id) from RuleTagEntity r where r.name = :name")
    long getCountByTagName(String name);

}
