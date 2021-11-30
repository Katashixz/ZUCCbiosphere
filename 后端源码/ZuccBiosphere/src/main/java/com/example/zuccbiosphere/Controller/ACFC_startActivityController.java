package com.example.zuccbiosphere.Controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sql")
public class ACFC_startActivityController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 动物城友会发布活动并更新到数据库
     * @param req
     * @return
     */
    @PostMapping("/releaseActivity")
    public String ACFC_startActivity(@RequestBody JSONObject req){
        //从前端发送的数据包中根据关键字获取对应信息
        String ActivityName = req.getString("ActivityName");
        String ActivityType = req.getString("ActivityType");
        String ActivityDescription = req.getString("ActivityDescription");
        String userId = req.getString("userId");
//        System.out.println(userId);
        try{
            //上传到数据库
            String sql = "insert into animalcityfriendsclub (user_ID,activity_Name,activity_Content,activity_Type) value (?,?,?,?)";
            jdbcTemplate.update(sql,userId,ActivityName,ActivityDescription,ActivityType);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "failed";
        }

    }


}
