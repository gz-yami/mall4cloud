package com.mall4j.cloud.search.canal;

import cn.hutool.core.collection.CollectionUtil;
import cn.throwx.canal.gule.CanalGlue;
import cn.throwx.canal.gule.model.CanalBinLogEvent;
import cn.throwx.canal.gule.model.ModelTable;
import cn.throwx.canal.gule.support.adapter.SourceAdapterFacade;
import cn.throwx.canal.gule.support.processor.BaseCanalBinlogEventProcessor;
import cn.throwx.canal.gule.support.processor.CanalBinlogEventProcessorFactory;

import java.util.List;

/**
 * @author FrozenWatermelon
 */
public class Mall4cloudCanalGlue implements CanalGlue {

    private final CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory;

    @Override
    public void process(String content) {
        CanalBinLogEvent event = (CanalBinLogEvent) SourceAdapterFacade.X.adapt(CanalBinLogEvent.class, content);
        ModelTable modelTable = ModelTable.of(event.getDatabase(), event.getTable());
        List<BaseCanalBinlogEventProcessor<?>> baseCanalBinlogEventProcessors = this.canalBinlogEventProcessorFactory.get(modelTable);
        if (CollectionUtil.isEmpty(baseCanalBinlogEventProcessors)) {
            return;
        }
        baseCanalBinlogEventProcessors.forEach((processor) -> {
            processor.process(event);
        });
    }


    private Mall4cloudCanalGlue(CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory) {
        this.canalBinlogEventProcessorFactory = canalBinlogEventProcessorFactory;
    }

    public static Mall4cloudCanalGlue of(CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory) {
        return new Mall4cloudCanalGlue(canalBinlogEventProcessorFactory);
    }
}
