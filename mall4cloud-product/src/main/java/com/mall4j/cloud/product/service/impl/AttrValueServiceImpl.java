package com.mall4j.cloud.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mall4j.cloud.product.constant.AttrType;
import com.mall4j.cloud.product.mapper.AttrMapper;
import com.mall4j.cloud.product.model.Attr;
import com.mall4j.cloud.product.model.AttrValue;
import com.mall4j.cloud.product.mapper.AttrValueMapper;
import com.mall4j.cloud.product.model.SpuAttrValue;
import com.mall4j.cloud.product.service.AttrValueService;
import com.mall4j.cloud.api.product.vo.AttrVO;
import com.mall4j.cloud.api.product.vo.AttrValueVO;
import com.mall4j.cloud.product.service.SpuAttrValueService;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 属性值信息
 *
 * @author FrozenWatermelon
 * @date 2020-10-28 15:27:24
 */
@Service
public class AttrValueServiceImpl implements AttrValueService {

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private AttrMapper attrMapper;

    @Autowired
    private SpuAttrValueService spuAttrValueService;

    @Override
    public void saveByAttrValuesAndAttrId(List<AttrValue> attrValues, Long attrId) {
        if (CollUtil.isEmpty(attrValues)){
            return;
        }
        for (AttrValue attrValue : attrValues) {
            attrValue.setAttrId(attrId);
        }
        attrValueMapper.saveBatch(attrValues);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Attr attr, AttrVO dbAttr) {
        List<AttrValue> addAttrValue = new ArrayList<>();
        List<AttrValue> updateAttrValue = new ArrayList<>();
        List<Long> deleteAttrValue = dbAttr.getAttrValues().stream().map(AttrValueVO::getAttrValueId).collect(Collectors.toList());
        // 分别筛选出增删改的数据
        attr.getAttrValues().forEach(attrValue -> {
            if (Objects.isNull(attrValue.getAttrValueId())) {
                addAttrValue.add(attrValue);
            } else {
                updateAttrValue.add(attrValue);
                deleteAttrValue.remove(attrValue.getAttrValueId());
            }
        });
        if (CollUtil.isNotEmpty(deleteAttrValue)) {
            if (Objects.equals(attr.getAttrType(), AttrType.BASIC.value())) {
                spuAttrValueService.deleteByAttIdAndCategoryIds(attr.getAttrId(), deleteAttrValue, null);
            }
            attrValueMapper.deleteBatch(deleteAttrValue);
        }

        // 新增属性值数据
        saveByAttrValuesAndAttrId(addAttrValue, attr.getAttrId());
        if (CollUtil.isNotEmpty(updateAttrValue)) {
            attrValueMapper.updateBatch(updateAttrValue);
        }
        if (Objects.equals(attr.getAttrType(), AttrType.BASIC.value())) {
            updateAttrAndAttrValueOfSpuOrSku(updateAttrValue, attr, dbAttr);
        }
    }

    /**
     * 更新属性数据时，更新商品/sku中的属性数据
     * 若不需要同步更新商品/sku中的属性数据，在更新属性数据时，不调用该方法即可，不影响其他流程
     * @param updateAttrValue
     * @param dbAttr
     */
    private void updateAttrAndAttrValueOfSpuOrSku(List<AttrValue> updateAttrValue, Attr attr, AttrVO dbAttr) {
        if (CollUtil.isEmpty(updateAttrValue)) {
            return;
        }
        Map<Long, String> dbAttrValueMap = dbAttr.getAttrValues().stream().collect(Collectors.toMap(AttrValueVO::getAttrValueId, AttrValueVO::getValue));
        boolean attrNotChange = Objects.equals(dbAttr.getName(), dbAttrValueMap.get(attr.getName()))
                && Objects.equals(dbAttr.getDesc(), dbAttrValueMap.get(attr.getDesc()));
        List<SpuAttrValue> spuAttrValues = new ArrayList<>(updateAttrValue.size());
        for (AttrValue attrValue : updateAttrValue) {
            boolean valueNotChange = Objects.equals(attrValue.getValue(), dbAttrValueMap.get(attrValue.getAttrValueId()));
            // 数据没有改变或者仅改变了描述，则跳过本次循环
            if (attrNotChange && valueNotChange) {
                continue;
            } else if (Objects.isNull(attr.getName()) && valueNotChange) {
                continue;
            }

            SpuAttrValue spuAttrValue = new SpuAttrValue();
            spuAttrValue.setAttrName(attr.getName());
            spuAttrValue.setAttrDesc(attr.getDesc());
            spuAttrValue.setAttrValueName(attrValue.getValue());
            spuAttrValue.setAttrId(attr.getAttrId());
            spuAttrValue.setAttrValueId(attrValue.getAttrValueId());
            spuAttrValues.add(spuAttrValue);
        }
        // 更新数据
        if(CollUtil.isNotEmpty(spuAttrValues)) {
            spuAttrValueService.batchUpdateSpuAttrValue(spuAttrValues);
        }
    }

}
