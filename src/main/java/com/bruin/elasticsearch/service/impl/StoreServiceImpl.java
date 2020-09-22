package com.bruin.elasticsearch.service.impl;

import com.bruin.elasticsearch.entity.Store;
import com.bruin.elasticsearch.config.StoreGoodsIndexConfig;
import com.bruin.elasticsearch.entity.StoreSearchRequest;
import com.bruin.elasticsearch.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.join.query.HasChildQueryBuilder;
import org.elasticsearch.join.query.JoinQueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0.0
 * @Description: 门店操作
 * @Author: Bruin
 * @Date: 2020/9/17
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Override
    public List<Store> listStore(StoreSearchRequest storeSearchRequest) {
        try {
            SearchRequest searchRequest = new SearchRequest(StoreGoodsIndexConfig.INDEX_NAME);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            //查询门店
            boolQueryBuilder.should(QueryBuilders.matchQuery(StoreGoodsIndexConfig.STORE_NAME,storeSearchRequest.getSearchText()));
            //查询商品返回门店
            HasChildQueryBuilder hasChildQueryBuilder = JoinQueryBuilders.hasChildQuery(StoreGoodsIndexConfig.JOIN_TYPE,QueryBuilders.matchQuery(StoreGoodsIndexConfig.GOODS_NAME,storeSearchRequest.getSearchText()), ScoreMode.None);
            boolQueryBuilder.should(hasChildQueryBuilder);
            //地址过滤
            boolQueryBuilder.filter(QueryBuilders.geoDistanceQuery(StoreGoodsIndexConfig.LOCATION).point(storeSearchRequest.getLocation().getLat(), storeSearchRequest.getLocation().getLon()).distance(StoreGoodsIndexConfig.DISTANCE, DistanceUnit.KILOMETERS));
            searchSourceBuilder.query(boolQueryBuilder);
            //page
            searchSourceBuilder.size(StoreGoodsIndexConfig.PAGE_SIZE);
            if (StringUtils.isNotBlank(storeSearchRequest.getSearchAfter())){
                searchSourceBuilder.searchAfter(new Object[]{storeSearchRequest.getSearchAfter()});
            }

            //sort
            GeoDistanceSortBuilder geoDistanceSortBuilder = SortBuilders.geoDistanceSort(StoreGoodsIndexConfig.LOCATION,storeSearchRequest.getLocation().getLat(),storeSearchRequest.getLocation().getLon());
            geoDistanceSortBuilder.unit(DistanceUnit.KILOMETERS);
            geoDistanceSortBuilder.order(SortOrder.ASC);
            geoDistanceSortBuilder.geoDistance(GeoDistance.PLANE);
            SortBuilder sortBuilder = SortBuilders.fieldSort(StoreGoodsIndexConfig.STORE_NO).order(SortOrder.ASC);
            searchSourceBuilder.sort(geoDistanceSortBuilder);
            searchSourceBuilder.sort(sortBuilder);
            searchRequest.source(searchSourceBuilder);
            System.out.println(searchSourceBuilder.toString());
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            List<Store> list = new ArrayList<Store>();
            ObjectMapper mapper=new ObjectMapper();
            Arrays.asList(searchResponse.getHits().getHits()).forEach(item ->{
                try {
                    Store store = mapper.readValue(item.getSourceAsString(),Store.class);
                    store.setSearchAfter(mapper.writeValueAsString(item.getSortValues()));
                    store.setDistance(item.getSortValues()[0].toString());
                    list.add(store);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return list;
        }catch (Exception e){
            logger.error(e.toString());
        }
        return null;
    }

    @Override
    public Boolean saveOrUpdate(Store store) {
        ObjectMapper mapper=new ObjectMapper();
        IndexRequest indexRequest = new IndexRequest("ni_haoda_products");
        try {
            indexRequest.id(store.getStoreNo()).source(mapper.writeValueAsString(store), XContentType.JSON);
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED){
                return true;
            }
        }catch (Exception e){
            logger.error(e.toString());
        }
        return false;
    }

    @Override
    public Boolean remove(Store store) {
        DeleteRequest deleteRequest = new DeleteRequest("ni_haoda_products");
        try {
            deleteRequest.id(store.getStoreNo());
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED){
                return true;
            }
        }catch (Exception e){
            logger.error(e.toString());
        }
        return false;
    }

    @Override
    public Boolean savetBatch(List<Store> listStore) {
        ObjectMapper mapper=new ObjectMapper();
        BulkRequest request = new BulkRequest();
        listStore.forEach(item -> {
            try {
                request.add(new IndexRequest("ni_haoda_products").id(item.getStoreNo())
                        .source(mapper.writeValueAsString(item), XContentType.JSON));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        try {
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            if (!bulkItemResponses.hasFailures()){
                return true;
            }
        }catch (Exception e){
            logger.error(e.toString());
        }
        return false;
    }

    @Override
    public Boolean removeBatch(List<Store> listStore) {
        BulkRequest request = new BulkRequest();
        listStore.forEach(item -> request.add(new DeleteRequest("ni_haoda_products", item.getStoreNo())));
        try {
            BulkResponse bulkItemResponses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            if (!bulkItemResponses.hasFailures()){
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

}
