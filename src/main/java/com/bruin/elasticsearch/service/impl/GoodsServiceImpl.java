package com.bruin.elasticsearch.service.impl;

import com.bruin.elasticsearch.dao.GoodsRepository;
import com.bruin.elasticsearch.entity.Goods;
import com.bruin.elasticsearch.service.GoodsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequest;
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
 * @Description: TODO
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
    public Goods save(Goods goods) {
        try {
            String indexName = "ni_haoda_products";
            ObjectMapper mapper=new ObjectMapper();
            String json = mapper.writeValueAsString(goods);
            IndexRequest indexRequest = new IndexRequest(indexName).id(goods.getGoodsCode()).source(json, XContentType.JSON);
            indexRequest.routing(goods.getRelation().getParent());
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            //IndexQuery indexQuery = new IndexQueryBuilder().withId(goods.getGoodsCode()).withObject(goods).build();
            //IndexCoordinates indexCoordinates = IndexCoordinates.of("ni_haoda_products");
            //elasticsearchRestTemplate.index(indexQuery,indexCoordinates);
        }catch (Exception e){
            logger.error(e.toString());
        }
        return null;
    }

    @Override
    public void savetBatch(List<Goods> listGoods) {
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
            restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        }catch (Exception e){
            logger.error(e.toString());
        }
    }
}
