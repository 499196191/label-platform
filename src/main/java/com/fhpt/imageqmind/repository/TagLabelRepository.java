package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.TagLabelEntity;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagLabelRepository extends JpaRepository<TagLabelEntity, Long> {

    @Query("select count(t.id) from TagLabelEntity t where t.type = :type and t.isDelete = 0")
    long getCountInfoByType(@Param("type")int type);

    @Query("select count(t.id) from TagLabelEntity t where t.name = :name or t.englishName = :englishName")
    long getCountByNameOrEnglishName(@Param("name")String name, @Param("englishName")String englishName);

    @Query("select t from TagLabelEntity t where t.englishName = :englishName and t.isDelete = 0")
    TagLabelEntity getByEnglishName(@Param("englishName") String englishName);

    @Query("select t from TagLabelEntity t where t.type = :type and t.isDelete = 0")
    Page<TagLabelEntity> getListByType(@Param("type")int type, Pageable pageable);

    @Query("select t from TagLabelEntity t where t.isDelete = 0")
    Page<TagLabelEntity> getList(Pageable pageable);

    @Query("select count(t.id) from TagLabelEntity t where t.name = :name and t.type = :type")
    long getCountByName(@Param("name") String name, @Param("type")int type);

    @Query("select count(t.id) from TagLabelEntity t where t.name = :name and t.isDelete = 1 and t.type = :type")
    long getCountByNameAndDeleted(@Param("name") String name, @Param("type")int type);

    @Query("select count(t.id) from TagLabelEntity t where t.englishName = :englishName and t.type = :type")
    long getCountByEnglishName(@Param("englishName") String englishName, @Param("type")int type);

    @Query("select count(t.id) from TagLabelEntity t where t.englishName = :englishName and t.isDelete = 1 and t.type = :type")
    long getCountByEnglishNameAndDeleted(@Param("englishName") String englishName, @Param("type")int type);

}
