package com.mall4j.cloud.search.listener;

import cn.throwx.canal.gule.CanalGlue;
import com.alibaba.fastjson2.JSON;
import com.mall4j.cloud.common.rocketmq.config.RocketMqConstant;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author FrozenWatermelon
 * @date 2021/02/03
 */
@Component
@RocketMQMessageListener(topic = RocketMqConstant.CANAL_TOPIC,consumerGroup = RocketMqConstant.CANAL_TOPIC)
public class CanalListener implements RocketMQListener<String> {

    private static final Logger log = LoggerFactory.getLogger(CanalListener.class);

    @Autowired
    private CanalGlue canalGlue;
    @Override
    public void onMessage(String message) {
        Map<?, ?> map = JSON.parseObject(message, Map.class);
        if (map == null || map.get("table") == null || map.get("database") == null) {
            log.warn("ignore invalid canal message: {}", message);
            return;
        }
        log.info("canal-database: {}, table: {}, mq message:{}", map.get("database"), map.get("table"), message);
        canalGlue.process(message);
    }
}
