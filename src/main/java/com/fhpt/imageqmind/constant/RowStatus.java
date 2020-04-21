package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;

/**
 * 文章状态信息
 * @author Marty
 */
public enum RowStatus {

    UNLABELED(1, "未标注"), LABELING(2, "标注中"), IGNORED(3, "已忽略"), LABELED(4, "已标注");

    private int type;

    private String name;

    RowStatus(int type, String name) {
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
        for (RowStatus dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (RowStatus dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static RowStatus getByType(int type) {
        RowStatus[] types = values();
        for (RowStatus addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
