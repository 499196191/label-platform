package com.fhpt.imageqmind.domain;

import javax.persistence.*;



/**
 * Created by admin on 2020/1/13.
 */

@Entity


@Table(name = "task_tag_map", schema = "imageq-mind")
public class TaskTagMapEntity {
    private int id;

    private TaskInfoEntity taskInfo;
    private TagLabelEntity tagLabel;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    public TaskInfoEntity getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfoEntity taskInfo) {
        this.taskInfo = taskInfo;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "tag_id")
    public TagLabelEntity getTagLabel() {
        return tagLabel;
    }

    public void setTagLabel(TagLabelEntity tagLabel) {
        this.tagLabel = tagLabel;
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
