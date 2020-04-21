package com.fhpt.imageqmind.utils;

import com.fhpt.imageqmind.domain.LabelResultEntity;

import com.fhpt.imageqmind.objects.vo.LabelResultCount;



import java.util.List;
import java.util.stream.Collectors;

/**
 * 对象转换类
 * @author Marty
 */
public class ObjectConvertUtil {
    public static void labelResultConvertToLabelCount(List<LabelResultEntity> labelResultEntityList, List<LabelResultCount> countList) {
        if (labelResultEntityList != null) {
            List<String> contents = labelResultEntityList.stream().map(labelResultEntity -> labelResultEntity.getContent()).distinct().collect(Collectors.toList());
            contents.forEach(content -> {
                LabelResultCount labelResultCount = new LabelResultCount();
                labelResultCount.setContent(content);
                labelResultCount.setCount(labelResultEntityList.stream().filter(labelResultEntity -> labelResultEntity.getContent().equals(content)).count());
                countList.add(labelResultCount);
            });
        }
    }
}
