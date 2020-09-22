package com.bruin.elasticsearch.service;

import com.bruin.elasticsearch.entity.Store;
import com.bruin.elasticsearch.entity.StoreSearchRequest;

import java.util.List;

public interface StoreService {

    /**
     * 首页查询
     * @param storeSearchRequest
     * @return
     */
    List<Store> listStore(StoreSearchRequest storeSearchRequest);

    /**
     * 保存或者更新，如果存在就更新
     * @param store
     */
    Boolean saveOrUpdate(Store store);

    /**
     * 单条删除
     * @param store
     */
    Boolean remove(Store store);

    /**
     * 批量保存
     * @param listStore
     */
    Boolean savetBatch(List<Store> listStore);

    /**
     * 批量删除
     * @param listStore
     */
    Boolean removeBatch(List<Store> listStore);

}
