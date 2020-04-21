package com.fhpt.imageqmind.constant;

import cn.hutool.core.lang.Assert;


/**
 * 标签类型
 * @author Marty
 */
public enum TagType {

    NOT_SIGNED(0, "未分类"), ENTITY_TAG(1, "实体标签"), TYPE_TAG(2, "分类标签"), RELATION_TAG(3, "关系标签");

    private int type;

    private String name;

    TagType(int type, String name) {
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
        for (TagType dataType : values()) {
            if (dataType.type == type.intValue()) {
                return dataType.name;
            }
        }
        return name;
    }

    public static int getTypeByName(String name) {
        Assert.notNull(name);
        int type = 0;
        for (TagType dataType : values()) {
            if (dataType.name.equals(name)) {
                return dataType.type;
            }
        }
        return type;
    }

    public static TagType getByType(int type) {
        TagType[] types = values();
        for (TagType addType : types) {
            if (addType.getType() == type) {
                return addType;
            }
        }
        return null;
    }
}
