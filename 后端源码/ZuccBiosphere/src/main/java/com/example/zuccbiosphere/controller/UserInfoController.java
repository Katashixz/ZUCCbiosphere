package com.example.zuccbiosphere.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.dto.userInfoDto;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "保存用户信息到数据库", notes = "需要传入JSON，其中包括\n" +
            "openId:***\n" +
            "userName:***\n" +
            "avatarUrl:***")
    @PostMapping("/saveUserInfo")
    public String saveUserInfo(@RequestBody JSONObject req){
        userInfoDto curUser = new userInfoDto();
        curUser.Name = req.getString("userName");
        curUser.Openid = req.getString("openId");
        curUser.AvatarUrl = req.getString("userAvatarUrl");
        try{
            String sql = "update user set user_Name = ?, user_AvatarUrl = ? where user_ID = ?";
            jdbcTemplate.update(sql,curUser.Name,curUser.AvatarUrl,curUser.Openid);
            return "Save done!";
        }catch (Exception e){
            e.printStackTrace();
            return "Save failed";
        }

    }
}
