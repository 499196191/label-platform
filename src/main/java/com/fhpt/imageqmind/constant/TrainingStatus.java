package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;

/**
 * 训练任务状态
 *
 * @author Marty
 */
public enum TrainingStatus {
    NOT_START(0, "未开始"), TRAINING(1, "进行中"), FINISHED(2, "已完成"), FAILED(3, "失败"), STOPPED(4, "已停止");

    private int type;

    private String name;

    TrainingStatus(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String getTypeName(Integer type) {
        Assert.notNull(type);
        String name = "";
        for (TrainingStatus dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (TrainingStatus dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static TrainingStatus getByType(int type) {
        TrainingStatus[] types = values();
        for (TrainingStatus addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
