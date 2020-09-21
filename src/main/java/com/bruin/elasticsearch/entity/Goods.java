package com.bruin.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/17
 */
@Document(indexName = "ni_haoda_products", createIndex = false)
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Field(name = "goods.goods_code")
    @JsonProperty("goods.goods_code")
    private String goodsCode;

    @Field(name = "goods.goods_name")
    @JsonProperty("goods.goods_name")
    private String goodsName;

    @JsonProperty("relation")
    @Field(name = "relation")
    private Relation relation;

    public Goods(String goodsCode, String goodsName, Relation relation) {
        this.goodsCode = goodsCode;
        this.goodsName = goodsName;
        this.relation = relation;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }
}
