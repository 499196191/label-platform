package com.fhpt.imageqmind.domain;

import javax.persistence.*;


import java.sql.Timestamp;


import java.util.List;

/**
 * 规则详情
 */
@Entity
@Table(name = "rule", schema = "imageq-mind")
public class RuleEntity {
    private long id;
    private String name;
    private Integer typeId;
    private String result;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String createdBy;

    private List<RuleTagEntity> ruleTags;
    private List<RuleWordEntity> ruleWords;

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
    @Column(name = "type_id", nullable = true)
    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
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

    @ManyToMany
    @JoinTable(name = "rule_tag_map",
            joinColumns = {@JoinColumn(name = "rule_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id", referencedColumnName = "id")})
    public List<RuleTagEntity> getRuleTags() {
        return ruleTags;
    }

    public void setRuleTags(List<RuleTagEntity> ruleTags) {
        this.ruleTags = ruleTags;
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "rule_id", referencedColumnName = "id")
    public List<RuleWordEntity> getRuleWords() {
        return ruleWords;
    }

    public void setRuleWords(List<RuleWordEntity> ruleWords) {
        this.ruleWords = ruleWords;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RuleEntity that = (RuleEntity) o;

        if (id != that.id)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (typeId != null ? !typeId.equals(that.typeId) : that.typeId != null)
            return false;
        if (result != null ? !result.equals(that.result) : that.result != null)
            return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null)
            return false;
        if (updateTime != null ? !updateTime.equals(that.updateTime) : that.updateTime != null)
            return false;
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = (int)id;
        result1 = 31 * result1 + (name != null ? name.hashCode() : 0);
        result1 = 31 * result1 + (typeId != null ? typeId.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (createTime != null ? createTime.hashCode() : 0);
        result1 = 31 * result1 + (updateTime != null ? updateTime.hashCode() : 0);
        result1 = 31 * result1 + (createdBy != null ? createdBy.hashCode() : 0);
        return result1;
    }
}
