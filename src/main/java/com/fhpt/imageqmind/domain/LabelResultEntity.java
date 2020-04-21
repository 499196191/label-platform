package com.fhpt.imageqmind.domain;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 标注信息
 * @author Marty
 */
@Entity
@Table(name = "label_result", schema = "imageq-mind")
public class LabelResultEntity {
    private long id;
    private Integer start;
    private Integer end;
    private Integer colorIndex;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;
    private String createdBy;
    //数据行
    private DataRowEntity dataRow;
    //任务信息
    private TaskInfoEntity taskInfo;
    //标签信息
    private TagLabelEntity tagLabel;

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
    @Column(name = "start", nullable = true)
    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    @Basic
    @Column(name = "end", nullable = true)
    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Basic
    @Column(name = "color_index", nullable = true)
    public Integer getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(Integer colorIndex) {
        this.colorIndex = colorIndex;
    }

    @Basic
    @Column(name = "content", nullable = true)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "row_id", referencedColumnName = "id")
    public DataRowEntity getDataRow() {
        return dataRow;
    }

    public void setDataRow(DataRowEntity dataRow) {
        this.dataRow = dataRow;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    public TaskInfoEntity getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfoEntity taskInfo) {
        this.taskInfo = taskInfo;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    public TagLabelEntity getTagLabel() {
        return tagLabel;
    }

    public void setTagLabel(TagLabelEntity tagLabel) {
        this.tagLabel = tagLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        LabelResultEntity that = (LabelResultEntity) o;

        if (id != that.id)
            return false;
        if (start != null ? !start.equals(that.start) : that.start != null)
            return false;
        if (end != null ? !end.equals(that.end) : that.end != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int)id;
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }
}
