package com.example.zuccbiosphere.dto;

import io.swagger.models.auth.In;

public class commentDto {
    public String userId;
    public Long postId;
    public String commentDate;
    public String commentContent;
    public String commentWho;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContext) {
        this.commentContent = commentContext;
    }

    public String getCommentWho() {
        return commentWho;
    }

    public void setCommentWho(String commentWho) {
        this.commentWho = commentWho;
    }
}
