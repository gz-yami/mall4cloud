package com.mall4j.cloud.search.listener;

import cn.hutool.core.util.StrUtil;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import com.mall4j.cloud.api.product.bo.EsProductBO;
import com.mall4j.cloud.api.product.feign.ProductFeignClient;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.search.bo.BrandBO;
import com.mall4j.cloud.search.manager.ProductUpdateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author YXF
 * @date 2021/01/21
 */
@Component
public class BrandCanalListener extends BaseCanalBinlogEventProcessor<BrandBO> {

    private static final Logger log = LoggerFactory.getLogger(BrandCanalListener.class);

    @Autowired
    private ProductUpdateManager productUpdateManager;
    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 新增品牌
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<BrandBO> brandResult) {

    }

    /**
     * 更新品牌
     * @param result
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<BrandBO> result) {
        BrandBO beforeData = result.getBeforeData();
        if (Objects.isNull(beforeData.getName()) && StrUtil.isBlank(beforeData.getImgUrl())) {
            return;
        }
        BrandBO afterData = result.getAfterData();
        EsProductBO esProductBO = new EsProductBO();
        if (StrUtil.isNotBlank(beforeData.getName())) {
            esProductBO.setBrandName(afterData.getName());
        }
        if (Objects.nonNull(beforeData.getImgUrl())) {
            esProductBO.setBrandImg(afterData.getImgUrl());
        }
        ServerResponseEntity<List<Long>> responseData = productFeignClient.getSpuIdsByBrandId(afterData.getBrandId());
        productUpdateManager.esUpdateSpuBySpuIds(responseData.getData(), esProductBO);
    }
}
