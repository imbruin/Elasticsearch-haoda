package com.bruin.elasticsearch.entity;

import lombok.Data;
import org.elasticsearch.geometry.Point;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/15
 */
@Data
@Document(indexName = "ni_haoda_products", shards = 5,replicas = 1)
public class StoreGoods implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private long id;
    @Field(name = "store.store_no", type = FieldType.Keyword)
    private String storeNo;
    @Field(name = "store.store_name", type = FieldType.Text)
    private String storeName;
    @Field(name = "store.live_status", type = FieldType.Keyword)
    private String liveStatus;
    @Field(name = "store.location")
    @GeoPointField
    private Point location;
    @Field(name = "store.store_img", type = FieldType.Text)
    private String storeImg;
    @Field(name = "store.delivery_config", type = FieldType.Text)
    private String deliveryConfig;
    @Field(name = "goods.goods_code", type = FieldType.Text)
    private String goodsCode;
    @Field(name = "goods.goods_name", type = FieldType.Text)
    private String goodsName;

}
