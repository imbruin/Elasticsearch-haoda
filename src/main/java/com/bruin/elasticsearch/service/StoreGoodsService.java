package com.bruin.elasticsearch.service;

import com.bruin.elasticsearch.entity.StoreGoods;
import org.springframework.data.domain.Page;

public interface StoreGoodsService {
    Page<StoreGoods> findByGoodsName(String goodsName);
    StoreGoods saveGoods(StoreGoods storeGoods);
}
