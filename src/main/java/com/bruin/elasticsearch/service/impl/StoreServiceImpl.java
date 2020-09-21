package com.bruin.elasticsearch.service.impl;

import com.bruin.elasticsearch.dao.StoreRepository;
import com.bruin.elasticsearch.entity.Store;
import com.bruin.elasticsearch.service.StoreService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/17
 */
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Override
    public Store save(Store store) {
        try {
            return storeRepository.save(store);
        }catch (Exception e){
            logger.error(e.toString());
        }
        return null;
    }

    @Override
    public void savetBatch(List<Store> listStore) {
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
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        }catch (Exception e){
            logger.error(e.toString());
        }
    }
}
