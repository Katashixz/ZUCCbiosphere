package com.example.zuccbiosphere.dto;

public class userDataDto {
    public String openid = "";
    public String myCollect = "";
    public String myFoot = "";
    public String myCards = "";
    public int myScore = 0;
    public boolean isGuider = false;

    public void setOpenid(String id){
        this.openid = id;
    }

    public void setMyCollect(String collect){
        this.myCollect = collect;
    }

    public  void setMyFoot(String foot){
        this.myFoot = foot;
    }

    public void setMyCards(String cards){
        this.myCards = cards;
    }

    public void setMyScore(int score){
        this.myScore = score;
    }
}
