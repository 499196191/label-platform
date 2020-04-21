package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;

/**
 * 部署状态
 * @author Marty
 */
public enum DeployStatus {

    DEPLOYED(1, "已部署"), NOT_DEPLOYED(0, "未部署");

    private int type;

    private String name;

    DeployStatus(int type, String name) {
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
        for (DeployStatus dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (DeployStatus dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static DeployStatus getByType(int type) {
        DeployStatus[] types = values();
        for (DeployStatus addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
