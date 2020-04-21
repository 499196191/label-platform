package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;

public enum DeleteStatus {

    DELETE(1, "已删除"), NOT_DELETE(0, "未删除");

    private int type;

    private String name;

    DeleteStatus(int type, String name) {
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
        for (DeleteStatus dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (DeleteStatus dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static DeleteStatus getByType(int type) {
        DeleteStatus[] types = values();
        for (DeleteStatus addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
