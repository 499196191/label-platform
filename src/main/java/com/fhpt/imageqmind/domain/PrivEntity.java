package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "priv", schema = "imageq-mind", catalog = "")
public class PrivEntity {
    private int id;
    private String privName;
    private String privDesc;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "priv_name")
    public String getPrivName() {
        return privName;
    }

    public void setPrivName(String privName) {
        this.privName = privName;
    }

    @Basic
    @Column(name = "priv_desc")
    public String getPrivDesc() {
        return privDesc;
    }

    public void setPrivDesc(String privDesc) {
        this.privDesc = privDesc;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrivEntity that = (PrivEntity) o;
        return id == that.id &&
                Objects.equals(privName, that.privName) &&
                Objects.equals(privDesc, that.privDesc) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, privName, privDesc, createTime, updateTime);
    }
}
