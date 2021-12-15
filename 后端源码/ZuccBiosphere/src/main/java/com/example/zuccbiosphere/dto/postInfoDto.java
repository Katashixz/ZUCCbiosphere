package com.example.zuccbiosphere.dto;

import io.swagger.models.auth.In;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class postInfoDto {
    public String userID;
    public String postContent;
    public String postDate;
    public Integer postLikeNum;
    public Integer postCommentNum;
    public String postUserAvatarUrl;
    public Integer postReportNum;
    public boolean postIsTop;
    public boolean postIsEssential;
    public String postTheme;
    public boolean postIsHot;
    public Long postID;
    public List<String> urlList = new ArrayList<>();

    public boolean isPostIsHot() {
        return postIsHot;
    }

    public void setPostIsHot(boolean postIsHot) {
        this.postIsHot = postIsHot;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }


    public String getPostUserAvatarUrl() {
        return postUserAvatarUrl;
    }

    public void setPostUserAvatarUrl(String postUserAvatarUrl) {
        this.postUserAvatarUrl = postUserAvatarUrl;
    }

    public String getPostTheme() {
        return postTheme;
    }

    public void setPostTheme(String postTheme) {
        this.postTheme = postTheme;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public Integer getPostLikeNum() {
        return postLikeNum;
    }

    public void setPostLikeNum(Integer postLikeNum) {
        this.postLikeNum = postLikeNum;
    }

    public Integer getPostCommentNum() {
        return postCommentNum;
    }

    public void setPostCommentNum(Integer postCommentNum) {
        this.postCommentNum = postCommentNum;
    }

    public Integer getPostReportNum() {
        return postReportNum;
    }

    public void setPostReportNum(Integer postReportNum) {
        this.postReportNum = postReportNum;
    }

    public boolean isPostIsTop() {
        return postIsTop;
    }

    public void setPostIsTop(boolean postIsTop) {
        this.postIsTop = postIsTop;
    }

    public boolean isPostIsEssential() {
        return postIsEssential;
    }

    public void setPostIsEssential(boolean postIsEssential) {
        this.postIsEssential = postIsEssential;
    }
}
