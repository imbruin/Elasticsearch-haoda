package com.bruin.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/21
 */
public class StoreSearchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("search_text")
    private String searchText;

    @JsonProperty("store.location")
    private Location location;

    @JsonProperty("search_after")
    private String searchAfter;

    public StoreSearchRequest(String searchText, Location location, String searchAfter) {
        this.searchText = searchText;
        this.location = location;
        this.searchAfter = searchAfter;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSearchText() {
        return searchText;
    }

    public Location getLocation() {
        return location;
    }

    public String getSearchAfter() {
        return searchAfter;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setSearchAfter(String searchAfter) {
        this.searchAfter = searchAfter;
    }
}
