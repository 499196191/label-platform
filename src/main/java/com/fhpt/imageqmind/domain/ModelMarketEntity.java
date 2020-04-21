package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Master on 2020/2/26.
 */
@Entity
@Table(name = "model_market")
public class ModelMarketEntity {

    private long id;
    private long modelId;
    private String modelName;
    private int deployStatus;
    private String request;
    private String response;
    private int callTime;
    private String createdBy;
    private Timestamp createTime;
    private Timestamp updateTime;

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
    @Column(name = "model_id", nullable = false)
    public long getModelId() {
        return modelId;
    }

    public void setModelId(long modelId) {
        this.modelId = modelId;
    }

    @Basic
    @Column(name = "request", nullable = true, length = 300)
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
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
    @Column(name = "response", nullable = true, length = 50)
    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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

}
