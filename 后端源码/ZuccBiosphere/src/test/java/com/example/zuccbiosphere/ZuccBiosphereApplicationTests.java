package com.example.zuccbiosphere;

import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.controller.UserInfoController;
import com.example.zuccbiosphere.dto.userInfoDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootTest
@RunWith(SpringRunner.class)
class ZuccBiosphereApplicationTests {

    /*@Autowired
    public JSONObject req;

    @Before
    public void setUp(){
        req = new JSONObject();

    }*/

    private UserInfoController test = new UserInfoController();
    @Test
    void contextLoads() {
        JSONObject req = new JSONObject();
        req.put("userName","屑黄宝");
        req.put("openId","test123");
        req.put("userAvatarUrl","hahaha.com");
        Assert.assertEquals("Save done!", test.saveUserInfo(req));
    }

}
