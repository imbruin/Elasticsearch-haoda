package com.bruin.elasticsearch.entity;

import java.io.Serializable;

/**
 * @version 1.0.0
 * @Description: TODO
 * @Author: Bruin
 * @Date: 2020/9/17
 */
public class Relation implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String parent;

    public Relation(String name, String parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public String getParent() {
        return parent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
