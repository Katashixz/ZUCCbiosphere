package com.example.zuccbiosphere.Controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录模块
 */
@RestController
@RequestMapping("/api/login")
public class UserInfoController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @PostMapping("/saveUserInfo")
    public String saveUserInfo(@RequestBody JSONObject req){
        String userName = req.getString("userName");
        String openId = req.getString("openId");
        String avatarUrl = req.getString("userAvatarUrl");
        try{
            String sql = "update user set user_Name = ?, user_AvatarUrl = ? where user_ID = ?";
            jdbcTemplate.update(sql,userName,avatarUrl,openId);
            return "Save done!";
        }catch (Exception e){
            e.printStackTrace();
            return "Save failed";
        }

    }
}
