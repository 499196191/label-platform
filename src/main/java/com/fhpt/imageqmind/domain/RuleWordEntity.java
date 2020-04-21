package com.fhpt.imageqmind.domain;

import javax.persistence.*;


import java.sql.Timestamp;

/**
 * 词规则
 */
@Entity
@Table(name = "rule_word", schema = "imageq-mind")
public class RuleWordEntity {
    private Long id;
    private Long ruleId;
    private String lWord;
    private String rWord;
    private Integer wordSpacing;
    private Integer sortNo;
    private String createdBy;
    private Timestamp createTime;
    private Timestamp updateTime;

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
    @Column(name = "rule_id", nullable = true)
    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @Basic
    @Column(name = "l_word", nullable = true, length = 255)
    public String getlWord() {
        return lWord;
    }

    public void setlWord(String lWord) {
        this.lWord = lWord;
    }

    @Basic
    @Column(name = "r_word", nullable = true, length = 255)
    public String getrWord() {
        return rWord;
    }

    public void setrWord(String rWord) {
        this.rWord = rWord;
    }

    @Basic
    @Column(name = "word_spacing", nullable = true)
    public Integer getWordSpacing() {
        return wordSpacing;
    }

    public void setWordSpacing(Integer wordSpacing) {
        this.wordSpacing = wordSpacing;
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


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RuleWordEntity that = (RuleWordEntity) o;

        if (id != that.id)
            return false;
        if (ruleId != null ? !ruleId.equals(that.ruleId) : that.ruleId != null)
            return false;
        if (lWord != null ? !lWord.equals(that.lWord) : that.lWord != null)
            return false;
        if (rWord != null ? !rWord.equals(that.rWord) : that.rWord != null)
            return false;
        if (wordSpacing != null ? !wordSpacing.equals(that.wordSpacing) : that.wordSpacing != null)
            return false;
        if (sortNo != null ? !sortNo.equals(that.sortNo) : that.sortNo != null)
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
        result = 31 * result + (ruleId != null ? ruleId.hashCode() : 0);
        result = 31 * result + (lWord != null ? lWord.hashCode() : 0);
        result = 31 * result + (rWord != null ? rWord.hashCode() : 0);
        result = 31 * result + (wordSpacing != null ? wordSpacing.hashCode() : 0);
        result = 31 * result + (sortNo != null ? sortNo.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        return result;
    }
}
