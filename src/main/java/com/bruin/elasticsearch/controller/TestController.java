package com.bruin.elasticsearch.controller;

import com.bruin.elasticsearch.entity.Goods;
import com.bruin.elasticsearch.entity.Store;
import com.bruin.elasticsearch.service.GoodsService;
import com.bruin.elasticsearch.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void saveOrUpdateStore(@RequestBody Store store){
         storeService.saveOrUpdate(store);
    }

    @PostMapping("/storelist")
    public void saveStoreList(@RequestBody List<Store> listStore){
        storeService.savetBatch(listStore);
    }

    @DeleteMapping("/store")
    public void removeStore(@RequestBody Store store){
        storeService.remove(store);
    }

    @DeleteMapping("/storelist")
    public void removeStoreList(@RequestBody List<Store> listStore){
        storeService.removeBatch(listStore);
    }

    @PostMapping("/goods")
    public void saveOrUpdateGoods(@RequestBody Goods goods){
        goodsService.saveOrUpdate(goods);
    }

    @PostMapping("/goodslist")
    public void saveGoodsList(@RequestBody List<Goods> listGoods){
        goodsService.savetBatch(listGoods);
    }

    @DeleteMapping("/goods")
    public void removeGoods(@RequestBody Goods goods){
        goodsService.remove(goods);
    }

    @DeleteMapping("/goodsList")
    public void removeGoodsList(@RequestBody List<Goods> listGoods){
        goodsService.removeBatch(listGoods);
    }
}
