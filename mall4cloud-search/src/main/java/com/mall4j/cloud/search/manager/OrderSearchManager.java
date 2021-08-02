package com.mall4j.cloud.search.manager;

import cn.hutool.core.util.StrUtil;
import com.mall4j.cloud.api.dto.EsPageDTO;
import com.mall4j.cloud.api.vo.EsPageVO;
import com.mall4j.cloud.api.vo.search.EsOrderVO;
import com.mall4j.cloud.common.dto.OrderSearchDTO;
import com.mall4j.cloud.common.exception.Mall4cloudException;
import com.mall4j.cloud.common.util.BooleanUtil;
import com.mall4j.cloud.common.util.Json;
import com.mall4j.cloud.search.constant.EsIndexEnum;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author FrozenWatermelon
 * @date 2020/11/16
 */
@Component
public class OrderSearchManager {

    private static final Logger log = LoggerFactory.getLogger(OrderSearchManager.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 通过搜索信息分页搜索es数据的信息
     *
     * @param pageDTO        分页数据
     * @param orderSearchDTO 订单搜索条件
     * @return 搜索结果
     */
    public EsPageVO<EsOrderVO> pageSearchResult(EsPageDTO pageDTO, OrderSearchDTO orderSearchDTO) {
        //1、动态构建出查询需要的DSL语句
        EsPageVO<EsOrderVO> result;

        //1、准备检索请求
        SearchRequest searchRequest = buildSearchRequest(pageDTO, orderSearchDTO);

        try {
            //2、执行检索请求
            SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            log.debug("搜索返回结果：" + response.toString());

            //3、分析响应数据，封装成我们需要的格式
            result = buildSearchResult(pageDTO, response);
        } catch (IOException e) {
            log.error(e.toString());
            throw new Mall4cloudException("搜索服务出了点小差，请稍后再试", e);
        }
        return result;
    }

    /**
     * 构建结果数据
     */
    private EsPageVO<EsOrderVO> buildSearchResult(EsPageDTO pageDTO, SearchResponse response) {
        EsPageVO<EsOrderVO> esPageVO = new EsPageVO<>();

        //1、返回的所有查询到的商品
        SearchHits hits = response.getHits();
        List<EsOrderVO> productSearchs = getEsOrderBOList(response);
        esPageVO.setList(productSearchs);


        //===============分页信息====================//
        //总记录数
        long total = hits.getTotalHits().value;
        esPageVO.setTotal(total);
        // 总页码
        int totalPages = (int) total % pageDTO.getPageSize() == 0 ?
                (int) total / pageDTO.getPageSize() : ((int) total / pageDTO.getPageSize() + 1);
        esPageVO.setPages(totalPages);
        return esPageVO;
    }

    private List<EsOrderVO> getEsOrderBOList(SearchResponse response) {

        return getOrderListByResponse(response.getHits().getHits());
    }

    /**
     * 从es返回的数据中获取spu列表
     *
     * @param hits es返回的数据
     * @return
     */
    private List<EsOrderVO> getOrderListByResponse(SearchHit[] hits) {
        List<EsOrderVO> esOrders = new ArrayList<>();
        for (SearchHit hit : hits) {
            EsOrderVO esOrder = Json.parseObject(hit.getSourceAsString(), EsOrderVO.class);
            esOrders.add(esOrder);
        }
        return esOrders;
    }


    /**
     * 准备检索请求
     *
     * @param pageDTO 分页参数
     * @param param   搜索参数
     * @return
     */
    private SearchRequest buildSearchRequest(EsPageDTO pageDTO, OrderSearchDTO param) {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建bool-query
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // 过滤
        filterQueryIfNecessary(param, boolQueryBuilder);

        // 关键字搜索
        keywordSearch(param, boolQueryBuilder);

        // 排序
        sort(searchSourceBuilder, boolQueryBuilder);

        //分页
        if (Objects.nonNull(pageDTO)) {
            searchSourceBuilder.from((pageDTO.getPageNum() - 1) * pageDTO.getPageSize());
            searchSourceBuilder.size(pageDTO.getPageSize());
        }

        log.debug("构建的DSL语句 {}", searchSourceBuilder.toString());

        return new SearchRequest(new String[]{EsIndexEnum.ORDER.value()}, searchSourceBuilder);
    }


    /**
     * 关键字搜索
     */
    private void keywordSearch(OrderSearchDTO param, BoolQueryBuilder boolQueryBuilder) {

        // 创建查询语句 ES中must和should不能同时使用 同时使用should失效 嵌套多个must 将should条件拼接在一个must中即可


        BoolQueryBuilder keywordShouldQuery = QueryBuilders.boolQuery();

        // 订单id
        if (param.getOrderId() != null) {
            keywordShouldQuery.should(QueryBuilders.matchQuery("orderId", param.getOrderId()));
        }

        // 店铺名称
        if (StrUtil.isNotBlank(param.getShopName())) {
            keywordShouldQuery.should(QueryBuilders.matchQuery("shopName", param.getShopName()));
        }

        if (StrUtil.isNotBlank(param.getSpuName())) {
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            // 订单项商品名称搜索
            if (StrUtil.isNotBlank(param.getSpuName())) {
                boolQuery.must(QueryBuilders.matchQuery("orderItems.spuName", param.getSpuName()));
            }
            NestedQueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("orderItems", boolQuery, ScoreMode.None);
            keywordShouldQuery.should(nestedQueryBuilder);
        }


        //收货人姓名
        if (StrUtil.isNotBlank(param.getConsignee())) {
            keywordShouldQuery.should(QueryBuilders.matchQuery("consignee", param.getConsignee()));
        }

        //收货人手机号
        if (StrUtil.isNotBlank(param.getMobile())) {
            keywordShouldQuery.should(QueryBuilders.matchQuery("mobile", param.getMobile()));
        }


        boolQueryBuilder.must(keywordShouldQuery);
    }

    /**
     * 进行排序
     */
    private void sort(SearchSourceBuilder searchSourceBuilder, BoolQueryBuilder boolQueryBuilder) {
        searchSourceBuilder.sort("createTime", SortOrder.DESC);
        searchSourceBuilder.query(boolQueryBuilder);
    }

    /**
     * 过滤查询条件，如果有必要的话
     *
     * @param param            查询条件
     * @param boolQueryBuilder 组合进boolQueryBuilder
     */
    private void filterQueryIfNecessary(OrderSearchDTO param, BoolQueryBuilder boolQueryBuilder) {
        // 店铺id
        if (Objects.nonNull(param.getShopId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("shopId", param.getShopId()));
        }

        // 用户id
        if (Objects.nonNull(param.getUserId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("userId", param.getUserId()));
        }

        // 订单状态 参考OrderStatus
        if (Objects.nonNull(param.getStatus()) && !Objects.equals(param.getStatus(), 0)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("status", param.getStatus()));
        }

        // 是否已经支付，1：已经支付过，0：，没有支付过
        if (Objects.nonNull(param.getIsPayed())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("isPayed", param.getIsPayed()));
        }

        // 开始时间 - 结束时间
        if (param.getStartTime() != null || param.getEndTime() != null) {
            // 销售价
            String createTime = "createTime";
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(createTime);
            if (param.getStartTime() != null) {
                rangeQueryBuilder.gte(param.getStartTime());
            }
            if (param.getEndTime() != null) {
                rangeQueryBuilder.lte(param.getEndTime());
            }
            boolQueryBuilder.filter(rangeQueryBuilder);
        }

        // 物流类型 3：无需快递
        if (BooleanUtil.isTrue(param.getDeliveryType())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("deliveryType", param.getDeliveryType()));
        }

    }

}
