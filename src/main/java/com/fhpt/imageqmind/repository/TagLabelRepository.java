package com.fhpt.imageqmind.repository;

import com.fhpt.imageqmind.domain.TagLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagLabelRepository extends JpaRepository<TagLabelEntity, Long> {

    @Query("select count(t.id) from TagLabelEntity t where t.type = :type")
    long getCountInfoByType(int type);

    @Query("select count(t.id) from TagLabelEntity t where t.name = :name or t.englishName = :englishName")
    long getCountByNameOrEnglishName(@Param("name")String name, @Param("englishName")String englishName);


}
