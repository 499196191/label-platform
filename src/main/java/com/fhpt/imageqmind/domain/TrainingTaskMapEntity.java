package com.fhpt.imageqmind.domain;

import javax.persistence.*;

/**
 * Created by Master on 2020/2/13.
 */
@Entity
@Table(name = "training_task_map", schema = "imageq-mind", catalog = "")
public class TrainingTaskMapEntity {
    private long id;
    private Long trainingId;
    private Long taskId;

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
    @Column(name = "training_id", nullable = true)
    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    @Basic
    @Column(name = "task_id", nullable = true)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        TrainingTaskMapEntity that = (TrainingTaskMapEntity) o;

        if (id != that.id)
            return false;
        if (trainingId != null ? !trainingId.equals(that.trainingId) : that.trainingId != null)
            return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (trainingId != null ? trainingId.hashCode() : 0);
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        return result;
    }
}
