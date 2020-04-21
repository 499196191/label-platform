package com.fhpt.imageqmind.domain;

import javax.persistence.*;


import java.sql.Timestamp;


import java.util.List;

/**
 * Created by Master on 2020/2/26.
 */
@Entity
@Table(name = "model_info")
public class ModelInfoEntity {

    private long id;
    private String modelName;
    private int modelType;
    private int sourceType;
    private String typesName;
    private String createdBy;
    private String version;
    private String brief;
    private int deployStatus;
    private int resultType;
    private Timestamp createTime;
    private Timestamp updateTime;

    private TrainingInfoEntity trainingInfo;
    private ApiInfoEntity apiInfoEntity;
    private List<RuleRelEntity> rules;

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
    @Column(name = "model_name", nullable = false, length = 255)
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    @Basic
    @Column(name = "brief", nullable = true, length = 255)
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
    @Basic
    @Column(name = "model_type", nullable = false)
    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    @Basic
    @Column(name = "source_type", nullable = false)
    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int sourceType) {
        this.sourceType = sourceType;
    }

    @Basic
    @Column(name = "types_name", nullable = true, length = 255)
    public String getTypesName() {
        return typesName;
    }

    public void setTypesName(String typesName) {
        this.typesName = typesName;
    }

    @Basic
    @Column(name = "result_type", nullable = false)
    public int getResultType() {
        return resultType;
    }

    public void setResultType(int resultType) {
        this.resultType = resultType;
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
    @Column(name = "version", nullable = true, length = 50)
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Basic
    @Column(name = "deploy_status", nullable = true, length = 2)
    public int getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(int deployStatus) {
        this.deployStatus = deployStatus;
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

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "training_id", referencedColumnName = "id")
    public TrainingInfoEntity getTrainingInfo() {
        return trainingInfo;
    }

    public void setTrainingInfo(TrainingInfoEntity trainingInfo) {
        this.trainingInfo = trainingInfo;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "api_info_id", referencedColumnName = "id")
    public ApiInfoEntity getApiInfoEntity() {
        return apiInfoEntity;
    }

    public void setApiInfoEntity(ApiInfoEntity apiInfoEntity) {
        this.apiInfoEntity = apiInfoEntity;
    }
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    public List<RuleRelEntity> getRules() {
        return rules;
    }

    public void setRules(List<RuleRelEntity> rules) {
        this.rules = rules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ModelInfoEntity that = (ModelInfoEntity) o;

        if (id != that.id)
            return false;
        if (modelType != that.modelType)
            return false;
        if (modelName != null ? !modelName.equals(that.modelName) : that.modelName != null)
            return false;
        if (typesName != null ? !typesName.equals(that.typesName) : that.typesName != null)
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
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + modelType;
        result = 31 * result + (typesName != null ? typesName.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
