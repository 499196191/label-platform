package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;


/**
 * 数据源类型
 * @author Marty
 */
public enum ModelSource {

    TRAINING(1, "模型训练"), RULE_SETTING(2, "规则配置"), OUT_IMPORT(3, "外部导入");

    private int type;

    private String name;

    ModelSource(int type, String name) {
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
        for (ModelSource dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (ModelSource dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static ModelSource getByType(int type) {
        ModelSource[] types = values();
        for (ModelSource addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
