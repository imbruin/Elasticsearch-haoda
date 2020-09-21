package com.bruin.elasticsearch.service.impl;

import com.bruin.elasticsearch.dao.StoreGoodsRepository;
import com.bruin.elasticsearch.entity.StoreGoods;
import com.bruin.elasticsearch.service.StoreGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/15
 */
@Service
public class StoreGoodsServiceImpl implements StoreGoodsService {

    @Autowired
    private StoreGoodsRepository storeGoodsRepository;

    @Override
    public Page<StoreGoods> findByGoodsName(String goodsName) {
        Pageable pageable = PageRequest.of(0,10);
        return storeGoodsRepository.findByGoodsName(goodsName,pageable);
    }

    @Override
    public StoreGoods saveGoods(StoreGoods storeGoods) {
        return storeGoodsRepository.save(storeGoods);
    }
}
