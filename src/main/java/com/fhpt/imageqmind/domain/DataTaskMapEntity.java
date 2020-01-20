package com.fhpt.imageqmind.domain;

import javax.persistence.*;

/**
 * Created by admin on 2020/1/13.
 */
@Entity
@Table(name = "data_task_map", schema = "imageq-mind")
public class DataTaskMapEntity {

    private long id;

    private DataSetEntity dataSet;
    private TaskInfoEntity taskInfo;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "data_set_id")
    public DataSetEntity getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSetEntity dataSet) {
        this.dataSet = dataSet;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_info_id")
    public TaskInfoEntity getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfoEntity taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        DataTaskMapEntity that = (DataTaskMapEntity) o;

        if (id != that.id)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        return result;
    }
}
