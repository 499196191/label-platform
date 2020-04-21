package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "api_info", schema = "imageq-mind", catalog = "")
public class ApiInfoEntity {
    private long id;
    private String apiUrl;
    private String method;
    private String paramJson;
    private String returnFormat;
    private String createdBy;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "api_url")
    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Basic
    @Column(name = "method")
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "param_json")
    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    @Basic
    @Column(name = "return_format")
    public String getReturnFormat() {
        return returnFormat;
    }

    public void setReturnFormat(String returnFormat) {
        this.returnFormat = returnFormat;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiInfoEntity that = (ApiInfoEntity) o;
        return id == that.id &&
                Objects.equals(apiUrl, that.apiUrl) &&
                Objects.equals(method, that.method) &&
                Objects.equals(paramJson, that.paramJson) &&
                Objects.equals(returnFormat, that.returnFormat) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiUrl, method, paramJson, returnFormat, createdBy, createTime, updateTime);
    }
}
