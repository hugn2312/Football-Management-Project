package com.example.doanmh.model;

import java.util.HashMap;
import java.util.Map;

public class Clubs {
    public String stt;
    public String name;
    public String pl;
    public String gd;
    public String pts;
    public String surl;
    public Clubs() {}

    public Clubs( String stt, String name, String pl, String gd, String pts, String surl) {
        this.stt = stt;
        this.name = name;
        this.pl = pl;
        this.gd = gd;
        this.pts = pts;
        this.surl = surl;
    }
    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPl() {
        return pl;
    }

    public void setPl(String pl) {
        this.pl = pl;
    }

    public String getGd() {
        return gd;
    }

    public void setGd(String gd) {
        this.gd = gd;
    }

    public String getPts() {
        return pts;
    }

    public void setPts(String pts) {
        this.pts = pts;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String imageUrl) {
        this.surl = imageUrl;
    }


    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", name);
        result.put("stt",  stt);
        result.put("pl",  pl);
        result.put("gd",  gd);
        result.put("pts",  pts);

        return result ;
    }
}
