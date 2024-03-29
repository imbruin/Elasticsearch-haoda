package com.bruin.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/16
 */
@Document(indexName = "ni_haoda_products", createIndex = false)
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @JsonProperty("store.store_no")
    private String storeNo;

    @JsonProperty("store.store_name")
    private String storeName;

    @JsonProperty("store.live_status")
    private String liveStatus;

    @JsonProperty("store.location")
    private Location location;

    @JsonProperty("store.store_img")
    private String storeImg;

    @JsonProperty("store.delivery_config")
    private String deliveryConfig;

    @JsonProperty("relation")
    private String relation;

    @JsonProperty("search_after")
    private String searchAfter;

    @JsonProperty("store.distance")
    private String distance;

    public Store(){}

    public Store(String storeNo, String storeName, String liveStatus, Location location, String storeImg, String deliveryConfig, String relation, String searchAfter, String distance) {
        this.storeNo = storeNo;
        this.storeName = storeName;
        this.liveStatus = liveStatus;
        this.location = location;
        this.storeImg = storeImg;
        this.deliveryConfig = deliveryConfig;
        this.relation = relation;
        this.searchAfter = searchAfter;
        this.distance = distance;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public Location getLocation() {
        return location;
    }

    public String getStoreImg() {
        return storeImg;
    }

    public String getDeliveryConfig() {
        return deliveryConfig;
    }

    public String getRelation() {
        return relation;
    }

    public String getSearchAfter() {
        return searchAfter;
    }

    public String getDistance() {
        return distance;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStoreImg(String storeImg) {
        this.storeImg = storeImg;
    }

    public void setDeliveryConfig(String deliveryConfig) {
        this.deliveryConfig = deliveryConfig;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public void setSearchAfter(String searchAfter) {
        this.searchAfter = searchAfter;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
