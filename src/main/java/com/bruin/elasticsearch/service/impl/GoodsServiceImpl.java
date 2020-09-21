package com.bruin.elasticsearch.service.impl;

import com.bruin.elasticsearch.dao.GoodsRepository;
import com.bruin.elasticsearch.entity.Goods;
import com.bruin.elasticsearch.service.GoodsService;
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
 * @Description: 商品操作
 * @Author: Bruin
 * @Date: 2020/9/17
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    RestHighLevelClient restHighLevelClient;

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    @Override
    public Boolean saveOrUpdate(Goods goods) {
        ObjectMapper mapper=new ObjectMapper();
        IndexRequest indexRequest = new IndexRequest("ni_haoda_products");
        try {
            indexRequest.id(goods.getGoodsCode()).source(mapper.writeValueAsString(goods), XContentType.JSON).routing(goods.getRelation().getParent());
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED){
                return true;
            }
            //IndexQuery indexQuery = new IndexQueryBuilder().withId(goods.getGoodsCode()).withObject(goods).build();
            //IndexCoordinates indexCoordinates = IndexCoordinates.of("ni_haoda_products");
            //elasticsearchRestTemplate.index(indexQuery,indexCoordinates);
        }catch (Exception e){
            logger.error(e.toString());
        }
        return false;
    }

    @Override
    public Boolean remove(Goods goods) {
        DeleteRequest deleteRequest = new DeleteRequest("ni_haoda_products");
        try {
            deleteRequest.id(goods.getGoodsCode()).routing(goods.getRelation().getParent());
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
    public Boolean savetBatch(List<Goods> listGoods) {
        ObjectMapper mapper=new ObjectMapper();
        BulkRequest request = new BulkRequest();
        listGoods.forEach(item -> {
            try {
                request.add(new IndexRequest("ni_haoda_products").id(item.getGoodsCode()).routing(item.getRelation().getParent())
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
    public Boolean removeBatch(List<Goods> listGoods) {
        BulkRequest request = new BulkRequest();
        listGoods.forEach(item -> request.add(new DeleteRequest("ni_haoda_products", item.getGoodsCode()).routing(item.getRelation().getParent())));
        try {
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
