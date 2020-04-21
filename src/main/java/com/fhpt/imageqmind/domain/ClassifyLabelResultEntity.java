package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "classify_label_result", schema = "imageq-mind")
public class ClassifyLabelResultEntity {
    private long id;
    private long taskId;
    private String createdBy;
    private Timestamp createTime;
    private Timestamp updateTime;

    //标签信息
    private TagLabelEntity tagLabel;
    //数据行
    private DataRowEntity dataRow;


    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task_id")
    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    public TagLabelEntity getTagLabel() {
        return tagLabel;
    }

    public void setTagLabel(TagLabelEntity tagLabel) {
        this.tagLabel = tagLabel;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "row_id", referencedColumnName = "id")
    public DataRowEntity getDataRow() {
        return dataRow;
    }

    public void setDataRow(DataRowEntity dataRow) {
        this.dataRow = dataRow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassifyLabelResultEntity that = (ClassifyLabelResultEntity) o;
        return id == that.id &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, createdBy, createTime, updateTime);
    }
}
