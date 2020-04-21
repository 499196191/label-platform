package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;

/**
 * 训练类型
 * @author Marty
 */
public enum TaskType {

    ENTITY(1, "实体识别"), TYPE(2, "文本分类"), RELATION(3, "关系抽取"), NOTSIGNED(4, "其它");

    private int type;

    private String name;

    TaskType(int type, String name) {
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
        for (TaskType dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (TaskType dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static TaskType getByType(int type) {
        TaskType[] types = values();
        for (TaskType addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
