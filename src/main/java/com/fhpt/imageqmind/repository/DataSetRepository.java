package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.DataSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DataSetRepository extends JpaRepository<DataSetEntity, Long> {

    @Query("select count(d.id) from DataSetEntity d where d.name = :name and d.isDelete = 0")
    long getCountByName(@Param("name") String name);

    @Query("select count(d.id) from DataSetEntity d where d.name = :name and d.isDelete = 1")
    long getCountByNameDeleted(@Param("name") String name);

}
