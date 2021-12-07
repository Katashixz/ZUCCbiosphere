package com.example.zuccbiosphere.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.dto.commentDto;
import com.example.zuccbiosphere.dto.postInfoDto;
import com.example.zuccbiosphere.dto.userInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 论坛功能模块
 */
@Api(tags = "论坛模块")
@RestController
@RequestMapping("/api/postBar")
public class postBarController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @ApiOperation(value = "发帖功能", notes = "需要传入JSON,其中包含:\n" +
            "postTheme:***\n" +
            "postContent:***\n" +
            "userID:***")
    @PostMapping("/releasePost")
    public String releasePost(@RequestBody JSONObject req){
        postInfoDto postData = new postInfoDto();
        postData.postTheme = req.getString("postTheme");
        postData.postContent = req.getString("postContent");
        postData.userID =req.getString("userID");
        postData.postDate = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        postData.postUserAvatarUrl = req.getString("userAvatarUrl");
        postData.postLikeNum = 0;
        postData.postReportNum = 0;
        postData.postCommentNum  = 0;
        postData.postIsEssential = false;
        postData.postIsTop = false;
        System.out.println(postData.postTheme);

        try{
            String sql = "insert into post(user_ID,post_Theme,post_Content," +
                    "post_Date,post_LikeNum,post_CommentNum,post_ReportNum," +
                    "post_isTop,post_isEssential,post_UserAvatarUrl) value (?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,postData.userID,postData.postTheme,postData.postContent,
                    postData.postDate,postData.postLikeNum,postData.postCommentNum,
                    postData.postReportNum,postData.postIsTop,postData.postIsEssential,
                    postData.postUserAvatarUrl);

            return "Post release success";
        }catch (Exception e){
            e.printStackTrace();
            return "Post release failed";
        }

    }

    @Autowired
    private DataSource dataSource;

    @ApiOperation(value = "发送帖子信息到前端页面显示", notes = "不需要传入参数")

    @PostMapping("/loadPost")
    public JSONArray loadPost(){

        //用于存储从数据库中获取的所有帖子
        List<postInfoDto> postInfoList = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();


        try{
            //数据库连接
            Connection conn = dataSource.getConnection();
            String sql = "select * from post ORDER BY post_Date DESC";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            while(rs.next()){
                postInfoDto post = new postInfoDto();
                post.setPostID(rs.getLong(1));
                post.setUserID(rs.getString(2));
                post.setPostTheme(rs.getString(3));
                post.setPostContent(rs.getString(4));
                post.setPostDate(rs.getDate(5) + " " + rs.getTime(5));
                post.setPostLikeNum(rs.getInt(6));
                post.setPostCommentNum(rs.getInt(7));
                post.setPostUserAvatarUrl(rs.getString(8));
                post.setPostReportNum(rs.getInt(9));
                post.setPostIsTop(rs.getBoolean(10));
                post.setPostIsEssential(rs.getBoolean(11));
                post.setPostImage(rs.getString(12));
                postInfoList.add(post);
            }

            sql = "select user_ID,user_Name from user";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            //建立openid和用户名之间的映射关系
            while(rs.next()){
                userMap.put(rs.getString(1),rs.getString(2));
            }
            pst.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        //返回给前端的json数据包
        JSONArray arr = new JSONArray();

        for(postInfoDto post: postInfoList){
            JSONObject res = new JSONObject();
            res.put("post_ID", post.postID);
            res.put("user_ID", userMap.get(post.userID));
            res.put("post_Theme", post.postTheme);
            res.put("post_Content", post.postContent);
            res.put("post_Date", post.postDate);
            res.put("post_likeNum", post.postLikeNum);
            res.put("post_commentNum", post.postCommentNum);
            res.put("user_head", post.postUserAvatarUrl);
            res.put("post_isTop", post.postIsTop);
            res.put("post_isEssential", post.postIsEssential);
            res.put("post_Image",post.postImage);
            arr.add(res);
        }

        return arr;


    }

    @ApiOperation(value = "从数据库中调取此帖子的详细信息", notes = "需要传入帖子的id")
    @PostMapping("/loadPostDetail")
    public JSONObject loadPostDetail(@RequestBody JSONObject req){

        JSONObject result = new JSONObject();
        Long postId = req.getLong("post_ID");

        //帖子信息实体数组存储取出的帖子信息
        postInfoDto data = new postInfoDto();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();

        try{
            Connection conn = dataSource.getConnection();
            String sql = "select * from post where post_ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setLong(1,postId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                data.setPostID(postId);
                data.setUserID(rs.getString("user_ID"));
                data.setPostTheme(rs.getString("post_Theme"));
                data.setPostContent(rs.getString("post_Content"));
                data.setPostDate(rs.getDate("post_Date") + " " + rs.getTime("post_Date"));
                data.setPostLikeNum(rs.getInt("post_LikeNum"));
                data.setPostCommentNum(rs.getInt("post_CommentNum"));
                data.setPostUserAvatarUrl(rs.getString("post_UserAvatarUrl"));
                data.setPostIsTop(rs.getBoolean("post_isTop"));
                data.setPostIsEssential(rs.getBoolean("post_isEssential"));
            }



            sql = "select user_ID,user_Name from user";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while(rs.next()){
                userMap.put(rs.getString("user_ID"),rs.getString("user_Name"));
            }
            pst.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        result.put("user_ID",userMap.get(data.getUserID()));
        result.put("user_icon",data.getPostUserAvatarUrl());
        result.put("user_content",data.getPostContent());
        result.put("post_Date",data.getPostDate());
        result.put("post_likeNum",data.getPostLikeNum());
        result.put("post_isLiked",data.postIsTop);
        result.put("post_isEssential",data.postIsEssential);

        return result;
    }



    @ApiOperation(value = "从数据库中调取此帖子的评论", notes = "需要传入帖子的id")
    @PostMapping("/loadPostComment")
    public JSONArray loadPostComment(@RequestBody JSONObject req){

        JSONArray result = new JSONArray();
        Long postId = req.getLong("post_ID");

        //评论信息实体数组存储数据库中取出的评论信息
        List<commentDto> commentDtoList = new ArrayList<>();

        //用于存储openid和用户名、用户头像之间的对应关系
        Map<String,List<String>> userMap = new HashMap<>();

        try{
            Connection conn = dataSource.getConnection();
            String sql = "select * from commentrecord where post_ID = ? order by Comment_Date";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setLong(1,postId);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                commentDto temp = new commentDto();
                temp.setUserId(rs.getString("user_ID"));
                temp.setPostId(rs.getLong("post_ID"));
                temp.setCommentContent(rs.getString("Comment_Con"));
                temp.setCommentWho(rs.getString("Comment_Acced"));
                temp.setCommentDate(rs.getDate("Comment_Date") + " " + rs.getTime("Comment_Date"));
                commentDtoList.add(temp);
            }

            sql = "select user_ID,user_Name,user_AvatarUrl from user";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            //建立openid和用户名之间的映射关系
            while(rs.next()){
                List<String> list = new ArrayList<>();
                list.add(0,rs.getString("user_Name"));
                list.add(1,rs.getString("user_AvatarUrl"));
                userMap.put(rs.getString("user_ID"),list);
            }
            pst.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        for (commentDto data: commentDtoList) {
            JSONObject temp = new JSONObject();
            List<String> userInfoList = userMap.get(data.getUserId());
            temp.put("user_ID",userInfoList.get(0));
            temp.put("user_icon",userInfoList.get(1));
            temp.put("user_Content",data.getCommentContent());
            temp.put("post_Date",data.getCommentDate());
            result.add(temp);
        }


        return result;
    }


}
