package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 任务信息
 * @author Marty
 */
@Entity
@Table(name = "task_info", schema = "imageq-mind")
public class TaskInfoEntity {
    private long id;
    private String name;
    private Integer size;
    private Integer status;
    private BigDecimal process;
    private String createdBy;
    private Timestamp updateTime;
    private Timestamp createTime;
    private TaskTypeEntity taskType;

    private List<DataSetEntity> dataSetEntities;
    private List<TagLabelEntity> tagLabelEntities;

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
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "size", nullable = true)
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "process", nullable = true, precision = 0)
    public BigDecimal getProcess() {
        return process;
    }

    public void setProcess(BigDecimal process) {
        this.process = process;
    }

    @Basic
    @Column(name = "created_by", nullable = true, length = 255)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "update_time", nullable = true)
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    public TaskTypeEntity getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskTypeEntity taskType) {
        this.taskType = taskType;
    }

    @ManyToMany
    @JoinTable(name = "data_task_map",
            joinColumns = {@JoinColumn(name = "task_info_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "data_set_id", referencedColumnName = "id")})
    public List<DataSetEntity> getDataSetEntities() {
        return dataSetEntities;
    }

    public void setDataSetEntities(List<DataSetEntity> dataSetEntities) {
        this.dataSetEntities = dataSetEntities;
    }

    //CascadeType.REFRESH 存在就执行更新操作
    //这里不需要配置级联操作，只是起个查询的作用
    @ManyToMany
    @JoinTable(name = "task_tag_map",
            joinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    public List<TagLabelEntity> getTagLabelEntities() {
        return tagLabelEntities;
    }

    public void setTagLabelEntities(List<TagLabelEntity> tagLabelEntities) {
        this.tagLabelEntities = tagLabelEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TaskInfoEntity that = (TaskInfoEntity) o;

        if (id != that.id)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (size != null ? !size.equals(that.size) : that.size != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null)
            return false;
        if (process != null ? !process.equals(that.process) : that.process != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (process != null ? process.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
