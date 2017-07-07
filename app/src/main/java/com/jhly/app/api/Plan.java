package com.jhly.app.api;

/**
 * Created by r on 2017/4/23.
 */

public class Plan {
    public int id;
    public String src;
    public String beginTime;
    public String endTime;
    public double total;
    public double used;

    public Plan(int id, String src, String beginTime, String endTime, double total, double used) {
        this.id = id;
        this.src = src;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.total = total;
        this.used = used;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
    }
}
