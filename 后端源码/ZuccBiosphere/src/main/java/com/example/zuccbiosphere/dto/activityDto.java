package com.example.zuccbiosphere.dto;

public class activityDto {
    public Long activityId;
    public String adminId;
    public String userId;
    public String name;
    public String content;
    public String type;
    public Long currentParticipantsNum;
    public Long totalParticipantsNum;
    public String location;
    public String startDate;
    public String releaseDate;
    public String Image;

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCurrentParticipantsNum() {
        return currentParticipantsNum;
    }

    public void setCurrentParticipantsNum(Long currentParticipantsNum) {
        this.currentParticipantsNum = currentParticipantsNum;
    }

    public Long getTotalParticipantsNum() {
        return totalParticipantsNum;
    }

    public void setTotalParticipantsNum(Long totalParticipantsNum) {
        this.totalParticipantsNum = totalParticipantsNum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
