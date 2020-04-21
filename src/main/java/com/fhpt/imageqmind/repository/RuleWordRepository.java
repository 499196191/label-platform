package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.RuleWordEntity;


import com.fhpt.imageqmind.domain.TagLabelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Master on 2020/3/18.
 */
public interface RuleWordRepository extends JpaRepository<RuleWordEntity, Long> {

    @Query("select r from RuleWordEntity r where r.lWord like :word order by r.createTime desc ")
    List<RuleWordEntity> getLeftByWordName(@Param("word") String word, Pageable pageable);
    @Query("select r from RuleWordEntity r where r.rWord like :word order by r.createTime desc ")
    List<RuleWordEntity> getRightByWordName(@Param("word") String word, Pageable pageable);
}
