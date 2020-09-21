package com.bruin.elasticsearch.service;

import com.bruin.elasticsearch.entity.Store;

import java.util.List;

public interface StoreService {
    Store save(Store store);
    void savetBatch(List<Store> listStore);
}
