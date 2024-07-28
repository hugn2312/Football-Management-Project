package com.example.doanmh.model;

public class FavCLubs {
    String name;
    String surl;
    public  FavCLubs(){}

    public FavCLubs(String name, String surl) {
        this.name = name;
        this.surl = surl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}
