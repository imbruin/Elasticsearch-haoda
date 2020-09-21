package com.bruin.elasticsearch.service.impl;

import com.bruin.elasticsearch.entity.Store;
import com.bruin.elasticsearch.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    RestHighLevelClient restHighLevelClient;

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

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
