package com.bruin.elasticsearch.service;

import com.bruin.elasticsearch.entity.Store;

import java.util.List;

public interface StoreService {
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
