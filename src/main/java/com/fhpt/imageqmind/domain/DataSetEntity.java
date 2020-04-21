package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * 数据集实体
 */
@Entity
@Table(name = "data_set", schema = "imageq-mind")
public class DataSetEntity {
    private long id;
    private String name;
    private int sourceType;
    private String typeNames;
    private Integer size;
    private Integer maxSize;
    private String createdBy;
    private String description;
    private String columnName;
    private Integer isDelete;
    private Timestamp updateTime;
    private Timestamp createTime;
    /**
     * 数据源信息
     */
    private DbInfoEntity dbInfo;
    /**
     * 文件信息
     */
    private FileInfoEntity fileInfo;
    private List<DataRowEntity> dataRowEntities;

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
    @Column(name = "source_type", nullable = false)
    public int getSourceType() {
        return sourceType;
    }

    public void setSourceType(int type) {
        this.sourceType = type;
    }

    @Basic
    @Column(name = "type_names")
    public String getTypeNames() {
        return typeNames;
    }

    public void setTypeNames(String type_names) {
        this.typeNames = type_names;
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
    @Column(name = "max_size", nullable = true)
    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
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
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "column_name", nullable = true, length = 255)
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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
    @Column(name = "update_time", nullable = false)
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
    @JoinColumn(name = "db_id", referencedColumnName = "id")
    public DbInfoEntity getDbInfo() {
        return dbInfo;
    }

    public void setDbInfo(DbInfoEntity dbInfo) {
        this.dbInfo = dbInfo;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    public FileInfoEntity getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(FileInfoEntity fileInfo) {
        this.fileInfo = fileInfo;
    }

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "data_set_id", referencedColumnName = "id")
    public List<DataRowEntity> getDataRowEntities() {
        return dataRowEntities;
    }

    public void setDataRowEntities(List<DataRowEntity> dataRowEntities) {
        this.dataRowEntities = dataRowEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DataSetEntity that = (DataSetEntity) o;

        if (id != that.id)
            return false;
        if (sourceType != that.sourceType)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (size != null ? !size.equals(that.size) : that.size != null)
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
        int result = (int)id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + sourceType;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (updateTime != null ? updateTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
