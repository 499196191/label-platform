package com.fhpt.imageqmind.domain;

import javax.persistence.*;



/**
 * Created by Master on 2020/2/18.
 */

@Entity


@Table(name = "training_tag_map", schema = "imageq-mind")
public class TrainingTagMapEntity {
    private long id;
    private Long trainingId;
    private Long tagId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "training_id", nullable = true)
    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    @Basic
    @Column(name = "tag_id", nullable = true)
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TrainingTagMapEntity that = (TrainingTagMapEntity) o;

        if (id != that.id)
            return false;
        if (trainingId != null ? !trainingId.equals(that.trainingId) : that.trainingId != null)
            return false;
        if (tagId != null ? !tagId.equals(that.tagId) : that.tagId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (trainingId != null ? trainingId.hashCode() : 0);
        result = 31 * result + (tagId != null ? tagId.hashCode() : 0);
        return result;
    }
}
