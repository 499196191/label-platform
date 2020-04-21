package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;

/**
 * 模型类型
 * @author Marty
 */
public enum ModelType {

    CHARACTER_BASED(1, "基于字符的BiLSTM+CRF模型"), WORD_SEGMENTATION(2, "基于分词的BiLSTM+CRF模型");

    private int type;

    private String name;

    ModelType(int type, String name) {
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
        for (ModelType dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (ModelType dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static ModelType getByType(int type) {
        ModelType[] types = values();
        for (ModelType addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
