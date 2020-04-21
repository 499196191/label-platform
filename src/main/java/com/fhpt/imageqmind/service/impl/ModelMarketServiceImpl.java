package com.fhpt.imageqmind.service.impl;

import com.fhpt.imageqmind.domain.ModelMarketEntity;
import com.fhpt.imageqmind.objects.PageInfo;
import com.fhpt.imageqmind.objects.vo.ModelMarketVo;
import com.fhpt.imageqmind.repository.ModelMarketRepository;
import com.fhpt.imageqmind.service.ModelMarketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * 模型市场业务实现类
 * @author xie
 */
@Service
public class ModelMarketServiceImpl implements ModelMarketService {

    @Autowired
    private ModelMarketRepository modelMarketRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void insert(ModelMarketVo modelMarketVo) {
        ModelMarketEntity modelMarketEntity = new ModelMarketEntity();
        modelMarketEntity.setModelName(modelMarketVo.getModelName());
        modelMarketEntity.setModelId(modelMarketVo.getModelId());
        modelMarketEntity.setDeployStatus(modelMarketVo.getDeployStatus());
        Timestamp now = Timestamp.from(Instant.now());
        modelMarketEntity.setCreateTime(now);
        modelMarketEntity.setUpdateTime(now);
        modelMarketRepository.save(modelMarketEntity);
        //BeanUtils
    }

    @Override
    public PageInfo<ModelMarketVo> query(Integer deployStatus, Integer page, Integer pageSize) {
        ModelMarketEntity modelMarketEntity = new ModelMarketEntity();
        modelMarketEntity.setDeployStatus(deployStatus);
        Example<ModelMarketEntity> example = Example.of(modelMarketEntity);
        Page<ModelMarketEntity> list = modelMarketRepository.findAll(example, PageRequest.of(page - 1, pageSize));
        PageInfo result = new PageInfo();
        result.setTotal(list.getTotalElements());
        List<ModelMarketVo> modelMarketVos = new ArrayList<>();
        convertTagLabelInfo(list.getContent(), modelMarketVos);
        result.setList(modelMarketVos);
        return result;
    }


    @Transactional(rollbackOn = Exception.class)
    @Override
    public void delete(long id) {
        modelMarketRepository.deleteById(id);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public void update(ModelMarketVo modelMarketVo) {
        Optional<ModelMarketEntity> modelMarketEntityOptional = modelMarketRepository.findById(modelMarketVo.getId());
        if (modelMarketEntityOptional.isPresent()) {
            ModelMarketEntity modelMarketEntity = modelMarketEntityOptional.get();
            modelMarketEntity.setUpdateTime(Timestamp.from(Instant.now()));
            modelMarketEntity.setDeployStatus(modelMarketVo.getDeployStatus());
            modelMarketRepository.save(modelMarketEntity);
        }
    }

    private void convertTagLabelInfo(List<ModelMarketEntity> source, List<ModelMarketVo> target){
        source.forEach(tagLabelEntity -> {
            ModelMarketVo modelMarketVo = new ModelMarketVo();
            BeanUtils.copyProperties(tagLabelEntity, modelMarketVo);
            modelMarketVo.setCreateTime(tagLabelEntity.getCreateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            modelMarketVo.setUpdateTime(tagLabelEntity.getUpdateTime().toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            target.add(modelMarketVo);
        });
    }
}
