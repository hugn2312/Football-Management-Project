package com.example.doanmh.model;

import java.io.Serializable;

public class Team implements Serializable {
    private String surl;
    private String name;
    private String detail;
    private String surl2;
    boolean favourite;


    public Team(){}

    public Team(String surl, String name, String detail, String surl2, boolean favourite) {
        this.surl = surl;
        this.name = name;
        this.detail = detail;
        this.surl2 = surl2;
        this.favourite = favourite;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurl2() {
        return surl2;
    }

    public void setSurl2(String surl2) {
        this.surl2 = surl2;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
