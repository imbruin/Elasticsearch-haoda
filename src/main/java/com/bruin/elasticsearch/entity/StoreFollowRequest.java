package com.bruin.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/29
 */
public class StoreFollowRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("store.store_no")
    private String[] storeNo;
    @JsonProperty("store.location")
    private Location location;
    @JsonProperty("search_after")
    private String[] searchAfter;

    public StoreFollowRequest(String[] storeNo, Location location, String[] searchAfter) {
        this.storeNo = storeNo;
        this.location = location;
        this.searchAfter = searchAfter;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String[] getStoreNo() {
        return storeNo;
    }

    public Location getLocation() {
        return location;
    }

    public String[] getSearchAfter() {
        return searchAfter;
    }

    public void setStoreNo(String[] storeNo) {
        this.storeNo = storeNo;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSearchAfter(String[] searchAfter) {
        this.searchAfter = searchAfter;
    }
}
