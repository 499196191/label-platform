package com.fhpt.imageqmind.domain;

import javax.persistence.*;



/**
 * Created by admin on 2020/1/13.
 */

@Entity
@Table(name = "task_tag_map", schema = "imageq-mind")
public class TaskTagMapEntity {
    private int id;
    private Long taskId;
    private Long tagId;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task_id", nullable = true)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
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

        TaskTagMapEntity that = (TaskTagMapEntity) o;

        if (id != that.id)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        return result;
    }
}
