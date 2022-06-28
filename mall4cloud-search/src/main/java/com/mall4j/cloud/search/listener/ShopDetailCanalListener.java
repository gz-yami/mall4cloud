package com.mall4j.cloud.search.listener;

import cn.hutool.core.util.StrUtil;
import cn.throwx.canal.gule.model.CanalBinLogResult;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import com.mall4j.cloud.api.product.bo.EsProductBO;
import com.mall4j.cloud.api.product.feign.ProductFeignClient;
import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.search.bo.ShopDetailBO;
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
public class ShopDetailCanalListener extends BaseCanalBinlogEventProcessor<ShopDetailBO> {

    private static final Logger log = LoggerFactory.getLogger(ShopDetailCanalListener.class);

    @Autowired
    private ProductUpdateManager productUpdateManager;
    @Autowired
    private ProductFeignClient productFeignClient;

    /**
     * 新增店铺
     */
    @Override
    protected void processInsertInternal(CanalBinLogResult<ShopDetailBO> shopDetailResult) {

    }

    /**
     * 更新店铺
     * @param result
     */
    @Override
    protected void processUpdateInternal(CanalBinLogResult<ShopDetailBO> result) {
        ShopDetailBO beforeData = result.getBeforeData();
        if (Objects.isNull(beforeData.getShopName()) && StrUtil.isBlank(beforeData.getShopLogo()) && !Objects.equals(beforeData.getShopStatus(), StatusEnum.ENABLE.value())) {
            return;
        }
        ShopDetailBO afterData = result.getAfterData();
        EsProductBO esProductBO = new EsProductBO();
        if (StrUtil.isNotBlank(beforeData.getShopName())) {
            esProductBO.setShopName(afterData.getShopName());
        }
        if (Objects.nonNull(beforeData.getShopLogo())) {
            esProductBO.setShopImg(afterData.getShopLogo());
        }
        if (Objects.nonNull(beforeData.getShopStatus()) && Objects.equals(beforeData.getShopId(), StatusEnum.ENABLE.value())) {
            esProductBO.setSpuStatus(StatusEnum.DISABLE.value());
        }
        ServerResponseEntity<List<Long>> responseData = productFeignClient.getSpuIdsByShopId(afterData.getShopId());
        productUpdateManager.esUpdateSpuBySpuIds(responseData.getData(), esProductBO);
    }
}
