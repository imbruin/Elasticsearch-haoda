package com.bruin.elasticsearch.dao;

import com.bruin.elasticsearch.entity.Store;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StoreRepository extends ElasticsearchRepository<Store,String> {

}
