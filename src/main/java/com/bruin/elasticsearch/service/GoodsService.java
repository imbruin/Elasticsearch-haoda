package com.bruin.elasticsearch.service;

import com.bruin.elasticsearch.entity.Goods;

import java.util.List;

public interface GoodsService {
    Goods save(Goods goods);
    void savetBatch(List<Goods> listGoods);
}
