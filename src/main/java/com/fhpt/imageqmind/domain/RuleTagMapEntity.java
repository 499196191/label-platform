package com.fhpt.imageqmind.domain;

import javax.persistence.*;



/**
 * Created by Master on 2020/3/17.
 */
@Entity
@Table(name = "rule_tag_map", schema = "imageq-mind")
public class RuleTagMapEntity {
    private int id;
    private long ruleId;
    private long tagId;

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
    @Column(name = "rule_id", nullable = false)
    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    @Basic
    @Column(name = "tag_id", nullable = false)
    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        RuleTagMapEntity that = (RuleTagMapEntity) o;

        if (id != that.id)
            return false;
        if (ruleId != that.ruleId)
            return false;
        if (tagId != that.tagId)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int)ruleId;
        result = 31 * result + (int)tagId;
        return result;
    }
}
