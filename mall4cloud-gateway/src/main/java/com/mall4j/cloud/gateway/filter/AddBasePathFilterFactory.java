package com.mall4j.cloud.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Iterator;

@Component
public class AddBasePathFilterFactory extends AbstractGatewayFilterFactory<AddBasePathFilterFactory.Config> {

    private final ObjectMapper objectMapper;
    private final ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    public AddBasePathFilterFactory(ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory) {
        super(Config.class);
        this.modifyResponseBodyGatewayFilterFactory = modifyResponseBodyGatewayFilterFactory;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public GatewayFilter apply(Config config) {
        ModifyResponseBodyGatewayFilterFactory.Config cf = new ModifyResponseBodyGatewayFilterFactory.Config()
                .setRewriteFunction(JsonNode.class, JsonNode.class,
                        (e, jsonNode) -> Mono.justOrEmpty(addBasePath(e, jsonNode)));
        return modifyResponseBodyGatewayFilterFactory.apply(cf);
    }

    @Override
    public String name() {
        return "AddBasePath";
    }

    public static class Config {
    }

    private JsonNode addBasePath(ServerWebExchange exchange, JsonNode jsonNode) {
        if (jsonNode.isObject()) {
            String basePath = exchange.getRequest().getPath().subPath(4).value();
            ObjectNode jsonInfo = (ObjectNode) jsonNode;
            ObjectNode paths = (ObjectNode) jsonNode.get("paths");
            ObjectNode newUrlInfos = objectMapper.createObjectNode();

            Iterator<String> pathIterator = paths.fieldNames();
            while (pathIterator.hasNext()) {
                String path = pathIterator.next();
                JsonNode urlInfo = paths.get(path);
                String newPath = basePath.replace("-", "_") + path;
                newUrlInfos.set(newPath, urlInfo);
            }
            jsonInfo.replace("paths", newUrlInfos);
            return jsonInfo;
        }
        return jsonNode;
    }
}
