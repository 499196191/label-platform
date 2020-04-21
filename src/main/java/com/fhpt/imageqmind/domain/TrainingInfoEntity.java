package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 训练任务模型
 * @author Marty
 */
@Entity
@Table(name = "training_info", schema = "imageq-mind")
public class TrainingInfoEntity {
    private Long id;
    private String name;
    private Integer type;
    private Integer modelType;
    private BigDecimal trainingPercent;
    private BigDecimal validatePercent;
    private BigDecimal testPercent;
    private Integer iterationTimes;
    private Integer batchSize;
    private BigDecimal learningRate;
    private Integer trainingStatus;
    private String trainingResult;
    private String failMsg;
    private Integer isDelete;
    private String createdBy;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Timestamp finishTime;
    private String taskId;
    private String dataSetDir;
    private String modelDir;

    private List<TaskInfoEntity> taskInfo;
    private List<TagLabelEntity> tagLabels;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type", nullable = true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "model_type", nullable = true)
    public Integer getModelType() {
        return modelType;
    }

    public void setModelType(Integer modelType) {
        this.modelType = modelType;
    }

    @Basic
    @Column(name = "training_percent", nullable = true, precision = 4)
    public BigDecimal getTrainingPercent() {
        return trainingPercent;
    }

    public void setTrainingPercent(BigDecimal trainingPercent) {
        this.trainingPercent = trainingPercent;
    }

    @Basic
    @Column(name = "validate_percent", nullable = true, precision = 4)
    public BigDecimal getValidatePercent() {
        return validatePercent;
    }

    public void setValidatePercent(BigDecimal validatePercent) {
        this.validatePercent = validatePercent;
    }
    @Basic
    @Column(name = "test_percent", nullable = true, precision = 4)
    public BigDecimal getTestPercent() {
        return testPercent;
    }

    public void setTestPercent(BigDecimal testPercent) {
        this.testPercent = testPercent;
    }

    @Basic
    @Column(name = "iteration_times", nullable = true)
    public Integer getIterationTimes() {
        return iterationTimes;
    }

    public void setIterationTimes(Integer iterationTimes) {
        this.iterationTimes = iterationTimes;
    }

    @Basic
    @Column(name = "batch_size", nullable = true)
    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    @Basic
    @Column(name = "learning_rate", nullable = true, precision = 6)
    public BigDecimal getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(BigDecimal learningRate) {
        this.learningRate = learningRate;
    }

    @Basic
    @Column(name = "training_status", nullable = true)
    public Integer getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingStatus(Integer trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    @Basic
    @Column(name = "training_result", nullable = true)
    public String getTrainingResult() {
        return trainingResult;
    }

    public void setTrainingResult(String trainingResult) {
        this.trainingResult = trainingResult;
    }

    @Basic
    @Column(name = "fail_msg", nullable = true)
    public String getFailMsg() {
        return failMsg;
    }

    public void setFailMsg(String failMsg) {
        this.failMsg = failMsg;
    }

    @Basic
    @Column(name = "is_delete", nullable = false)
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
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
    @Column(name = "finish_time", nullable = true)
    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    @Basic
    @Column(name = "task_id", nullable = true)
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "dataset_dir", nullable = false, length = 255)
    public String getDataSetDir() {
        return dataSetDir;
    }

    public void setDataSetDir(String dataSetDir) {
        this.dataSetDir = dataSetDir;
    }
    @Basic
    @Column(name = "model_dir", nullable = false, length = 255)
    public String getModelDir() {
        return modelDir;
    }

    public void setModelDir(String modelDir) {
        this.modelDir = modelDir;
    }

    @ManyToMany
    @JoinTable(name = "training_task_map",
            joinColumns = {@JoinColumn(name = "training_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "task_id", referencedColumnName = "id")})
    public List<TaskInfoEntity> getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(List<TaskInfoEntity> taskInfo) {
        this.taskInfo = taskInfo;
    }

    @ManyToMany
    @JoinTable(name = "training_tag_map",
            joinColumns = {@JoinColumn(name = "training_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    public List<TagLabelEntity> getTagLabels() {
        return tagLabels;
    }

    public void setTagLabels(List<TagLabelEntity> tagLabels) {
        this.tagLabels = tagLabels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TrainingInfoEntity that = (TrainingInfoEntity) o;

        if (id != that.id)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (modelType != null ? !modelType.equals(that.modelType) : that.modelType != null)
            return false;
        if (trainingPercent != null ? !trainingPercent.equals(that.trainingPercent) : that.trainingPercent != null)
            return false;
        if (iterationTimes != null ? !iterationTimes.equals(that.iterationTimes) : that.iterationTimes != null)
            return false;
        if (batchSize != null ? !batchSize.equals(that.batchSize) : that.batchSize != null)
            return false;
        if (learningRate != null ? !learningRate.equals(that.learningRate) : that.learningRate != null)
            return false;
        if (trainingStatus != null ? !trainingStatus.equals(that.trainingStatus) : that.trainingStatus != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.intValue();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (modelType != null ? modelType.hashCode() : 0);
        result = 31 * result + (trainingPercent != null ? trainingPercent.hashCode() : 0);
        result = 31 * result + (iterationTimes != null ? iterationTimes.hashCode() : 0);
        result = 31 * result + (batchSize != null ? batchSize.hashCode() : 0);
        result = 31 * result + (learningRate != null ? learningRate.hashCode() : 0);
        result = 31 * result + (trainingStatus != null ? trainingStatus.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
