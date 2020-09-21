package com.bruin.elasticsearch.dao;

import com.bruin.elasticsearch.entity.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface GoodsRepository extends ElasticsearchRepository<Goods,String> {
}
