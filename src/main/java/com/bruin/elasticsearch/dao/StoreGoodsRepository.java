package com.bruin.elasticsearch.dao;

import com.bruin.elasticsearch.entity.StoreGoods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StoreGoodsRepository extends ElasticsearchRepository<StoreGoods,Long> {

    @Query("")
    Page<StoreGoods> findByGoodsName(String goodsName, Pageable pageable);
}
