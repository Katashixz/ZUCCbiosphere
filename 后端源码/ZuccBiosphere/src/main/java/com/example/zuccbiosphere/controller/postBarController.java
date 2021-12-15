package com.example.zuccbiosphere.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.dto.commentDto;
import com.example.zuccbiosphere.dto.postInfoDto;
import com.example.zuccbiosphere.dto.userInfoDto;
import com.example.zuccbiosphere.utils.TencentCosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
        List<String> postImage = new ArrayList<>();
        List<String> postImageName = new ArrayList<>();
        List<String> postImageType = new ArrayList<>();
        //图片base64

//        if(req.getString("picture1")!=null){
        postImage.add(req.getString("picture1"));
        postImageName.add(req.getString("picName1"));
        postImageType.add(req.getString("picType1"));
//        }
//        if(req.getString("picture2")!=null){
        postImage.add(req.getString("picture2"));
        postImageName.add(req.getString("picName2"));
        postImageType.add(req.getString("picType2"));
//        }
//        if(req.getString("picture3")!=null){
        postImage.add(req.getString("picture3"));
        postImageName.add(req.getString("picName3"));
        postImageType.add(req.getString("picType3"));
//        }

        System.out.println(req.getString("picName2"));
        System.out.println(req.getString("picName3"));


        //url存储
        List<String> urlList = new ArrayList<>();
        //上传图片到腾讯云cos得到url
        try {
            for(String image:postImage){
                System.out.println(image);
                if(image != null){
                    int i = postImage.indexOf(image);
                    System.out.println(postImageType.get(i));
                    System.out.println(postImageName.get(i));
                    byte[] decodeBase64 = Base64.decodeBase64(image);
                    File file = File.createTempFile(String.valueOf(System.currentTimeMillis()),postImageType.get(i));
                    FileUtils.writeByteArrayToFile(file, decodeBase64);
                    String picUrl = TencentCosUtil.uploadfile(file,postImageName.get(i));
                    urlList.add(picUrl);
                }
                else{
                    urlList.add(null);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(urlList.get(0));
        System.out.println(urlList.get(1));
        System.out.println(urlList.get(2));
        try{
            String sql = "insert into post(user_ID,post_Theme,post_Content," +
                    "post_Date,post_LikeNum,post_CommentNum,post_ReportNum," +
                    "post_isTop,post_isEssential,post_UserAvatarUrl,post_isHot,post_Image1,post_Image2,post_Image3) value (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,postData.userID,postData.postTheme,postData.postContent,
                    postData.postDate,postData.postLikeNum,postData.postCommentNum,
                    postData.postReportNum,postData.postIsTop,postData.postIsEssential,
                    postData.postUserAvatarUrl,0,urlList.get(0),urlList.get(1),urlList.get(2));
            //每次发帖后发帖数量加一
            Connection conn = dataSource.getConnection();
            sql="update user set user_PushPostPoint = user_PushPostPoint+1 WHERE user_ID=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, postData.userID);
            pst.executeUpdate();

            /*String value = "";
            if(!urlList.isEmpty()){
                sql = "insert into post(";
                value = "value (";
                int i = 1;
                for(String url:urlList){
                    sql = sql + "post_Image" + i;
                    value = value + ",?"
                }

            }*/




            return "Post release success";
        }catch (Exception e){
            e.printStackTrace();
            return "Post release failed";
        }

    }

    @Autowired
    private DataSource dataSource;

    @ApiOperation(value = "发送帖子信息到前端页面显示", notes = "传入用户ID")
    @PostMapping("/loadPost")
    public JSONArray loadPost(@RequestBody JSONObject req){


        //用于存储从数据库中获取的所有帖子
        List<postInfoDto> postInfoList = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();
        String userID = req.getString("user_ID");
        Set<Integer> isLike = userID.equals("")?new HashSet<>(): getAllIsLike(req);


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
                post.setPostID(rs.getLong("post_ID"));
                post.setUserID(rs.getString("user_ID"));
                post.setPostTheme(rs.getString("post_Theme"));
                post.setPostContent(rs.getString("post_Content"));
                post.setPostDate(rs.getDate("post_Date") + " " + rs.getTime("post_Date"));
                post.setPostLikeNum(rs.getInt("post_LikeNum"));
                post.setPostCommentNum(rs.getInt("post_CommentNum"));
                post.setPostUserAvatarUrl(rs.getString("post_UserAvatarUrl"));
//                post.setPostReportNum(rs.getInt("post_ReportNum"));
                post.setPostIsTop(rs.getBoolean("post_isTop"));
                post.setPostIsEssential(rs.getBoolean("post_isEssential"));
                post.setPostIsHot(rs.getBoolean("post_isHot"));
                post.urlList.add(rs.getString("post_Image1"));
                post.urlList.add(rs.getString("post_Image2"));
                post.urlList.add(rs.getString("post_Image3"));
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
            JSONArray urlArr = new JSONArray();
            for (String url:post.urlList){
                if (url == null)break;
                JSONObject urlObj = new JSONObject();
                urlObj.put("imageurl",url);
                urlArr.add(urlObj);
            }
            res.put("post_Image",urlArr);
            res.put("post_isLiked", isLike.contains(post.postID.intValue()));
            res.put("user_openID", post.userID);
//            res.put("post_Image1",post.postImage1);
            arr.add(res);
        }

        return arr;


    }


    @ApiOperation(value = "发送我的贴子到前端页面显示", notes = "传入用户ID")
    @PostMapping("/loadMyPost")
    public JSONArray loadMyPost(@RequestBody JSONObject req){

        //用于存储从数据库中获取的所有帖子
        List<postInfoDto> postInfoList = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();
        String userID = req.getString("user_ID");
        Set<Integer> isLike = userID.equals("")?new HashSet<>(): getAllIsLike(req);

        try{
            //数据库连接
            Connection conn = dataSource.getConnection();
            String sql = "select * from post where user_ID = ? ORDER BY post_Date DESC";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            java.sql.ResultSet rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            while(rs.next()){
                postInfoDto post = new postInfoDto();
                post.setPostID(rs.getLong("post_ID"));
                post.setUserID(rs.getString("user_ID"));
                post.setPostTheme(rs.getString("post_Theme"));
                post.setPostContent(rs.getString("post_Content"));
                post.setPostDate(rs.getDate("post_Date") + " " + rs.getTime("post_Date"));
                post.setPostLikeNum(rs.getInt("post_LikeNum"));
                post.setPostCommentNum(rs.getInt("post_CommentNum"));
                post.setPostUserAvatarUrl(rs.getString("post_UserAvatarUrl"));
//                post.setPostReportNum(rs.getInt("post_ReportNum"));
                post.setPostIsTop(rs.getBoolean("post_isTop"));
                post.setPostIsEssential(rs.getBoolean("post_isEssential"));
                post.setPostIsHot(rs.getBoolean("post_isHot"));
                post.urlList.add(rs.getString("post_Image1"));
                post.urlList.add(rs.getString("post_Image2"));
                post.urlList.add(rs.getString("post_Image3"));

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
            JSONArray urlArr = new JSONArray();
            for (String url:post.urlList){
                if (url == null)break;
                JSONObject urlObj = new JSONObject();
                urlObj.put("imageurl",url);
                urlArr.add(urlObj);
            }
            res.put("post_Image",urlArr);
            res.put("post_isLiked", isLike.contains(post.postID.intValue()));
            arr.add(res);
        }

        return arr;

    }

    @ApiOperation(value = "发送我的评论到前端页面显示", notes = "传入用户ID")
    @PostMapping("/loadMyComment")
    public List<JSONObject> loadMyComment(@RequestBody JSONObject req){

        //用于存储从数据库中获取的所有帖子
        List<JSONObject> commentList = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();
        String userID = req.getString("user_ID");


        try{
            //数据库连接
            Connection conn = dataSource.getConnection();
            String sql = "select user_ID,user_Name from user";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            //建立openid和用户名之间的映射关系
            while(rs.next()){
                userMap.put(rs.getString(1),rs.getString(2));
            }

            sql = "select * from commentrecord a , post  b where a.post_ID=b.post_ID and a.user_ID=? ORDER BY a.Comment_Date DESC";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //关于帖子

            while(rs.next()){
                JSONObject temp = new JSONObject();
                temp.put("comment_content",rs.getString("Comment_Con"));
                temp.put("comment_date",rs.getDate("Comment_Date")+" "+rs.getTime("Comment_Date"));
                temp.put("commented_Name",userMap.get(rs.getString("b.user_ID")));
                temp.put("post_content",rs.getString("post_Content"));
                temp.put("post_ID",rs.getString("post_ID"));
                commentList.add(temp);
            }

            pst.close();
            conn.close();


        }catch (SQLException e){
            e.printStackTrace();
        }

        return commentList;

    }

    @ApiOperation(value = "发送我的赞赏记录到前端页面显示", notes = "传入用户ID")
    @PostMapping("/loadMyRewardRecord")
    public List<JSONObject> loadMyRewardRecord(@RequestBody JSONObject req){

        //用于存储从数据库中获取的所有帖子
        List<JSONObject> commentList = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();
        String userID = req.getString("user_ID");


        try{
            //数据库连接
            Connection conn = dataSource.getConnection();
            String sql = "select user_ID,user_Name from user";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            //建立openid和用户名之间的映射关系
            while(rs.next()){
                userMap.put(rs.getString(1),rs.getString(2));
            }

            sql = "SELECT * from rpRecord WHERE user_ID =? ORDER BY getDate DESC";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //关于帖子
            while(rs.next()){
                JSONObject temp = new JSONObject();
                temp.put("rewarded_Name",userMap.get(rs.getString("recUser_ID")));
                temp.put("RPCount_type",rs.getString("getType"));
                temp.put("rewarded_date",rs.getDate("getDate")+" "+rs.getTime("getDate"));
                temp.put("rewarded_count",rs.getInt("Point"));
                commentList.add(temp);
            }

            pst.close();
            conn.close();


        }catch (SQLException e){
            e.printStackTrace();
        }

        return commentList;

    }

    @ApiOperation(value = "发送我的人品记录到前端页面显示", notes = "传入用户ID")
    @PostMapping("/loadMyRPRecord")
    public List<JSONObject> loadMyRPRecord(@RequestBody JSONObject req){

        //用于存储从数据库中获取的所有帖子
        List<JSONObject> commentList = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();
        String userID = req.getString("user_ID");


        try{
            //数据库连接
            Connection conn = dataSource.getConnection();
            String sql = "select user_ID,user_Name from user";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            //建立openid和用户名之间的映射关系
            while(rs.next()){
                userMap.put(rs.getString(1),rs.getString(2));
            }

            sql = "SELECT * from rpRecord WHERE user_ID =? ORDER BY getDate DESC";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //关于帖子

            while(rs.next()){
                JSONObject temp = new JSONObject();
                temp.put("RPCount_type",rs.getString("getType"));
                temp.put("record_date",rs.getDate("getDate")+" "+rs.getTime("getDate"));
                temp.put("RP_number",rs.getInt("Point"));
                commentList.add(temp);
            }

            pst.close();
            conn.close();


        }catch (SQLException e){
            e.printStackTrace();
        }

        return commentList;

    }


    @ApiOperation(value = "发送十大热帖信息到前端页面显示", notes = "不需要传入参数")
    @PostMapping("/loadHotPost")
    public JSONArray loadHotPost(){

        //用于存储从数据库中获取的所有帖子
        List<postInfoDto> postInfoList = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();

        try{
            //数据库连接
            Connection conn = dataSource.getConnection();
            String sql = "select * from post where post_isHot = 1 ORDER BY post_Date DESC";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            while(rs.next()){
                postInfoDto post = new postInfoDto();
                post.setPostID(rs.getLong("post_ID"));
                post.setUserID(rs.getString("user_ID"));
                post.setPostTheme(rs.getString("post_Theme"));
                post.setPostContent(rs.getString("post_Content"));
                post.setPostDate(rs.getDate("post_Date") + " " + rs.getTime("post_Date"));
                post.setPostLikeNum(rs.getInt("post_LikeNum"));
                post.setPostCommentNum(rs.getInt("post_CommentNum"));
                post.setPostUserAvatarUrl(rs.getString("post_UserAvatarUrl"));
//                post.setPostReportNum(rs.getInt("post_ReportNum"));
                post.setPostIsTop(rs.getBoolean("post_isTop"));
                post.setPostIsEssential(rs.getBoolean("post_isEssential"));
                post.setPostIsHot(rs.getBoolean("post_isHot"));

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
        String userID = req.getString("user_ID");
        Set<Integer> isLike = userID==null?new HashSet<>(): getAllIsLike(req);

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
                data.urlList.add(rs.getString("post_Image1"));
                data.urlList.add(rs.getString("post_Image2"));
                data.urlList.add(rs.getString("post_Image3"));
            }


            int commentNum = 0;
            sql = "SELECT COUNT(0) FROM commentrecord WHERE post_ID = ?";
            pst = conn.prepareStatement(sql);
            pst.setLong(1,postId);
            rs = pst.executeQuery();
            if(rs.next()){
                commentNum = rs.getInt(1);
                result.put("post_commentNum", commentNum);
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
        result.put("post_isEssential",data.postIsEssential);
        result.put("post_isLiked", isLike.contains(postId.intValue()));
        JSONArray urlArr = new JSONArray();
        for (String url:data.urlList){
            if (url == null)break;
            JSONObject urlObj = new JSONObject();
            urlObj.put("url",url);
            urlArr.add(urlObj);
        }
        result.put("post_Image",urlArr);
        result.put("user_openID", data.getUserID());
        return result;
    }



    @ApiOperation(value = "从数据库中调取此帖子的评论", notes = "需要传入帖子的id")
    @PostMapping("/loadPostComment")
    public JSONArray loadPostComment(@RequestBody JSONObject req){

        JSONArray result = new JSONArray();
        Long postId = req.getLong("post_ID");

        System.out.println(postId);

        //评论信息实体数组存储数据库中取出的评论信息
        List<commentDto> commentDtoList = new ArrayList<>();

        //用于存储openid和用户名、用户头像之间的对应关系
        Map<String,List<String>> userMap = new HashMap<>();

        try{
            Connection conn = dataSource.getConnection();

            String sql = "select user_ID,user_Name,user_AvatarUrl from user";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            //建立openid和用户名之间的映射关系
            while(rs.next()){
                List<String> list = new ArrayList<>();
                list.add(0,rs.getString("user_Name"));
                list.add(1,rs.getString("user_AvatarUrl"));
                userMap.put(rs.getString("user_ID"),list);
            }


            sql = "select * from commentrecord where post_ID = ? order by Comment_Date";
            pst = conn.prepareStatement(sql);
            pst.setLong(1,postId);
            rs = pst.executeQuery();
            while(rs.next()){
                JSONObject temp = new JSONObject();
                temp.put("user_ID", userMap.get(rs.getString(1)).get(0));
                temp.put("user_icon", userMap.get(rs.getString(1)).get(1));
                temp.put("post_ID", rs.getLong(2));
                temp.put("post_Date",rs.getDate("Comment_Date") + " " + rs.getTime("Comment_Date"));
                temp.put("user_Content", rs.getString(4));
                result.add(temp);
            }

            pst.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }



        return result;
    }

    @ApiOperation(value = "点赞或取消点赞贴子", notes = "需要传入帖子的id，用户id，用户是否喜欢")
    @PostMapping("/modifyLike")
    public JSONObject modifyLike(@RequestBody JSONObject req){

        JSONObject result = new JSONObject();
        Long postId = req.getLong("post_ID");
        String userID = req.getString("user_ID");
        boolean post_isLiked = req.getBoolean("post_isLiked");

//        System.out.println(postId + "  " + userID + "  " + post_isLiked);
        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();

        try{
            Connection conn = dataSource.getConnection();
            String sql;
            if (post_isLiked){
                sql = "DELETE FROM favoriterecord WHERE user_ID like ? and post_ID = ?";
            }
            else{
                sql = "INSERT INTO favoriterecord VALUES(?, ?, NOW())";
            }

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            pst.setLong(2,postId);
            pst.executeUpdate();
            ResultSet rs;

            int likeNum = 5;
            sql = "SELECT count(0) FROM favoriterecord WHERE post_ID = ?";
            pst = conn.prepareStatement(sql);
            pst.setLong(1,postId);
            rs = pst.executeQuery();
            if(rs.next()){
                likeNum = rs.getInt(1);
                result.put("post_likeNum", likeNum);
            }


            sql = "UPDATE post SET post_LikeNum = ? where post_ID = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1,likeNum);
            pst.setLong(2,postId);
            pst.executeUpdate();


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


        return result;
    }

    @ApiOperation(value = "回复贴子", notes = "需要传入帖子的id，用户id，贴主id")
    @PostMapping("/pushComment")
    public JSONObject pushComment(@RequestBody JSONObject req){

        JSONObject result = new JSONObject();
        Long postId = req.getLong("post_ID");
        String userID = req.getString("user_ID");
        String acceptID = req.getString("acceptID");
        String comment = req.getString("comment");

        System.out.println(postId);

//        System.out.println(postId + "  " + userID + "  " + post_isLiked);
        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();

        try{
            Connection conn = dataSource.getConnection();
            String sql;
            sql = "INSERT INTO commentrecord VALUES(?, ?, NOW(), ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            pst.setLong(2,postId);
            pst.setString(3, comment);
            pst.setString(4, acceptID);
            pst.executeUpdate();
            ResultSet rs;

            int commentNum = 5;
            sql = "SELECT COUNT(0) FROM commentrecord WHERE post_ID = ?";
            pst = conn.prepareStatement(sql);
            pst.setLong(1,postId);
            rs = pst.executeQuery();
            if(rs.next()){
                commentNum = rs.getInt(1);
                result.put("commentNum", commentNum);
            }

//更新用户评论数
            sql="update user set user_CommentPoint = user_CommentPoint+1 WHERE user_ID=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            pst.executeUpdate();

            sql = "UPDATE post SET post_CommentNum = ? WHERE post_ID = ?";
            pst = conn.prepareStatement(sql);
            pst.setLong(1,commentNum);
            pst.setLong(2,postId);
            pst.executeUpdate();

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


        return result;
    }



    public Set<Integer> getAllIsLike(@RequestBody JSONObject req){
        Set<Integer> res = new HashSet<>();
        String userID = req.getString("user_ID");

        try{
            Connection conn = dataSource.getConnection();
            String sql = "SELECT * FROM favoriterecord WHERE user_ID = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userID);
            ResultSet rs = pst.executeQuery();


            while(rs.next()){
                res.add(rs.getInt(2));
            }
            pst.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return res;
    }


}
