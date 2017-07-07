package com.jhly.app.api;

/**
 * Created by r on 2017/7/1.
 */

public class Result {
    public int id;
    public String time;
    public Ticket lv;
    public Plan pv;
    public Info cv;

    public Result(int id, String time, Ticket lv, Plan pv, Info cv) {
        this.id = id;
        this.time = time;
        this.lv = lv;
        this.pv = pv;
        this.cv = cv;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Ticket getLv() {
        return lv;
    }

    public void setLv(Ticket lv) {
        this.lv = lv;
    }

    public Plan getPv() {
        return pv;
    }

    public void setPv(Plan pv) {
        this.pv = pv;
    }

    public Info getCv() {
        return cv;
    }

    public void setCv(Info cv) {
        this.cv = cv;
    }


    public class Ticket {
        public int id;
        public String code;
        public String vehicle;
        public double weight;
        public String aim;
        public String scode;
        public String addr;

        public Ticket(int id, String code, String vehicle, double weight, String aim, String scode, String addr) {
            this.id = id;
            this.code = code;
            this.vehicle = vehicle;
            this.weight = weight;
            this.aim = aim;
            this.scode = scode;
            this.addr = addr;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getVehicle() {
            return vehicle;
        }

        public void setVehicle(String vehicle) {
            this.vehicle = vehicle;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public String getAim() {
            return aim;
        }

        public void setAim(String aim) {
            this.aim = aim;
        }

        public String getScode() {
            return scode;
        }

        public void setScode(String scode) {
            this.scode = scode;
        }

        public String getAddr() {
            return addr;
        }

        public void setAddr(String addr) {
            this.addr = addr;
        }
    }
    public class Plan{
        public int id;
        public String src;
        public String protein;
        public String beginTime;
        public String endTime;
        public double total;
        public double used;

        public Plan(int id, String src, String protein, String beginTime, String endTime, double total, double used) {
            this.id = id;
            this.src = src;
            this.protein = protein;
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

        public String getProtein() {
            return protein;
        }

        public void setProtein(String protein) {
            this.protein = protein;
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

    public class Info{
        public int id;
        public String name;
        public String phone;

        public Info(int id, String name, String phone) {
            this.id = id;
            this.name = name;
            this.phone = phone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

}
