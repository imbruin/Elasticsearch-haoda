package com.bruin.elasticsearch.controller;

import com.bruin.elasticsearch.entity.Goods;
import com.bruin.elasticsearch.entity.Store;
import com.bruin.elasticsearch.service.GoodsService;
import com.bruin.elasticsearch.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/15
 */
@RestController
@RequestMapping("/es")
public class TestController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private GoodsService goodsService;


    @PostMapping("/store")
    public Store saveStore(@RequestBody Store store){
        return storeService.save(store);
    }

    @PostMapping("/storelist")
    public void saveStoreList(@RequestBody List<Store> listStore){
        storeService.savetBatch(listStore);
    }

    @PostMapping("/goods")
    public Goods saveGoods(@RequestBody Goods goods){
        return goodsService.save(goods);
    }

    @PostMapping("/goodslist")
    public void saveGoodsList(@RequestBody List<Goods> listGoods){
        goodsService.savetBatch(listGoods);
    }
}
