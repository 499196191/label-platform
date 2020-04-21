package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tag_data_row", schema = "imageq-mind", catalog = "")
public class TagDataRowEntity {
    private long id;
    private Integer dataSetId;
    private Integer dataRowId;
    private String tagLabelId;
    private String tagLabelName;
    private String createdBy;
    private Timestamp updateTime;
    private Timestamp createTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "data_set_id")
    public Integer getDataSetId() {
        return dataSetId;
    }

    public void setDataSetId(Integer dataSetId) {
        this.dataSetId = dataSetId;
    }

    @Basic
    @Column(name = "data_row_id")
    public Integer getDataRowId() {
        return dataRowId;
    }

    public void setDataRowId(Integer dataRowId) {
        this.dataRowId = dataRowId;
    }

    @Basic
    @Column(name = "tag_label_id")
    public String getTagLabelId() {
        return tagLabelId;
    }

    public void setTagLabelId(String tagLabelId) {
        this.tagLabelId = tagLabelId;
    }

    @Basic
    @Column(name = "tag_label_name")
    public String getTagLabelName() {
        return tagLabelName;
    }

    public void setTagLabelName(String tagLabelName) {
        this.tagLabelName = tagLabelName;
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
    @Column(name = "update_time")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDataRowEntity that = (TagDataRowEntity) o;
        return id == that.id &&
                Objects.equals(dataSetId, that.dataSetId) &&
                Objects.equals(dataRowId, that.dataRowId) &&
                Objects.equals(tagLabelId, that.tagLabelId) &&
                Objects.equals(tagLabelName, that.tagLabelName) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataSetId, dataRowId, tagLabelId, tagLabelName, createdBy, updateTime, createTime);
    }
}
