package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;


/**
 * 数据源类型
 * @author Marty
 */
public enum TaskStatus {

    NOT_START(0, "未开始"), STARTING(1, "进行中"), FINISHED(2, "已完成");

    private int type;

    private String name;

    TaskStatus(int type, String name) {
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
        for (TaskStatus dataType : values()) {
            if (dataType.type == type.intValue()) {
                name = dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (TaskStatus dataType : values()) {
            if (dataType.name.equals(name)) {
                type = dataType.type;
            }
        }
        return type;
    }

    public static TaskStatus getByType(int type) {
        TaskStatus[] types = values();
        for (TaskStatus addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
