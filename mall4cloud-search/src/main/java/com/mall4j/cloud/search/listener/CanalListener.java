package com.mall4j.cloud.search.listener;

import cn.throwx.canal.gule.CanalGlue;
import com.mall4j.cloud.common.rocketmq.config.RocketMqConstant;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author FrozenWatermelon
 * @date 2021/02/03
 */
@Component
@RocketMQMessageListener(topic = RocketMqConstant.CANAL_TOPIC,consumerGroup = RocketMqConstant.CANAL_TOPIC)
public class CanalListener implements RocketMQListener<String> {

    @Autowired
    private CanalGlue canalGlue;

    @Override
    public void onMessage(String message) {
        canalGlue.process(message);
    }
}