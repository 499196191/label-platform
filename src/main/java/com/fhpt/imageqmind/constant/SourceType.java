package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;

/**
 * 数据源类型
 * @author Marty
 */
public enum SourceType {

    ORACLE(1, "oracle数据源"), MYSQL(2, "mysql数据源"), Excel(3, "excel文件"), CSV(4, "csv文件");

    private int type;

    private String name;

    SourceType(int type, String name) {
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
        for (SourceType dataType : values()) {
            if (dataType.type == type.intValue()) {
                name = dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (SourceType dataType : values()) {
            if (dataType.name.equals(name)) {
                type = dataType.type;
            }
        }
        return type;
    }

    public static SourceType getByType(int type) {
        SourceType[] types = values();
        for (SourceType addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
