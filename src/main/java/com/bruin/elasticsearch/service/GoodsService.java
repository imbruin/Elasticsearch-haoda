package com.bruin.elasticsearch.service;

import com.bruin.elasticsearch.entity.Goods;

import java.util.List;

public interface GoodsService {
    /**
     * 保存或者更新，如果存在就更新
     * @param goods
     */
    Boolean saveOrUpdate(Goods goods);

    /**
     * 单条删除
     * @param goods
     */
    Boolean remove(Goods goods);

    /**
     * 批量保存
     * @param listGoods
     */
    Boolean savetBatch(List<Goods> listGoods);

    /**
     * 批量删除
     * @param listGoods
     */
    Boolean removeBatch(List<Goods> listGoods);
}
