package com.example.zuccbiosphere.Controller;

import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.dto.postInfoDto;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 论坛功能模块
 */

@RestController
@RequestMapping("/api/postBar")
public class postBarController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostMapping("/releasePost")
    public String releasePost(@RequestBody JSONObject req){
        postInfoDto postData = new postInfoDto();
        postData.postTheme = req.getString("postTheme");
        postData.postContent = req.getString("postContent");
        postData.userID =req.getString("userID");
        postData.postDate = new DateTime().toString("yyyy-MM-dd HH:mm:ss");
        postData.postLikeNum = 0;
        postData.postReportNum = 0;
        postData.postCommentNum  = 0;
        postData.postIsEssential = false;
        postData.postIsTop = false;
        System.out.println(postData.postTheme);

        try{
            String sql = "insert into post(user_ID,post_Theme,post_Content," +
                    "post_Date,post_LikeNum,post_CommentNum,post_ReportNum," +
                    "post_isTop,post_isEssential) value (?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(sql,postData.userID,postData.postTheme,postData.postContent,
                    postData.postDate,postData.postLikeNum,postData.postCommentNum,
                    postData.postReportNum,postData.postIsTop,postData.postIsEssential);
            return "Post release success";
        }catch (Exception e){
            e.printStackTrace();
            return "Post release failed";
        }

    }

}
