package com.example.zuccbiosphere.dto;

public class cardDto {
    public String owner;
    public String classify="";
    public String name="";
    public Double latitude;
    public Double longitude;
    public String picUrl="";
    public String introduce="";
    public String picName="";

    public void setOwner(String openid){this.owner=openid;}

    public void setPicName(String name) {this.picName = name; }

    public void setClassify(String cls){
        this.classify=cls;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setLatitude(double a){
        this.latitude=a;
    }

    public void setLongitude(double a){
        this.longitude=a;
    }

    public void setPicUrl(String url){
        this.picUrl=url;
    }

    public void setIntroduce(String intro){
        this.introduce=intro;
    }
}
