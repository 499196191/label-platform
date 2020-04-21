package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * 规则详情（强绑定副本）
 */
@Entity
@Table(name = "rule_rel", schema = "imageq-mind")
public class RuleRelEntity {
    private long id;
    private String name;
    private int typeId;
    private String result;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String createdBy;
    private Integer sortNo;
    private long modelId;

    private List<RuleWordRelEntity> ruleWords;

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
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "type_id", nullable = false)
    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Basic
    @Column(name = "result", nullable = true, length = 255)
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
    @Column(name = "created_by", nullable = true, length = 255)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "sort_no", nullable = true)
    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Basic
    @Column(name = "model_id", nullable = false)
    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    public List<RuleWordRelEntity> getRuleWords() {
        return ruleWords;
    }

    public void setRuleWords(List<RuleWordRelEntity> ruleWords) {
        this.ruleWords = ruleWords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RuleRelEntity that = (RuleRelEntity) o;

        if (id != that.id)
            return false;
        if (typeId != that.typeId)
            return false;
        if (modelId != that.modelId)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (result != null ? !result.equals(that.result) : that.result != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null)
            return false;
        if (sortNo != null ? !sortNo.equals(that.sortNo) : that.sortNo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = (int)id;
        result1 = 31 * result1 + (name != null ? name.hashCode() : 0);
        result1 = 31 * result1 + typeId;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (createTime != null ? createTime.hashCode() : 0);
        result1 = 31 * result1 + (updateTime != null ? updateTime.hashCode() : 0);
        result1 = 31 * result1 + (createdBy != null ? createdBy.hashCode() : 0);
        result1 = 31 * result1 + (sortNo != null ? sortNo.hashCode() : 0);
        result1 = 31 * result1 + (int)modelId;
        return result1;
    }
}
