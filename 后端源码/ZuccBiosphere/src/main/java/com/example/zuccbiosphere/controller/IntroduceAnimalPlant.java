package com.example.zuccbiosphere.controller;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.dto.activityDto;
import com.example.zuccbiosphere.filter.getHoursFilter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "动植物科普模块")
@RestController
@RequestMapping("/api/intro")
public class IntroduceAnimalPlant {
    /**
     * 从数据库中读取动植物信息返回给前端
     */
    @Autowired
    private DataSource dataSource;
    @ApiOperation(value = "读取动植物信息", notes = "需要前端发送请求，不需要传数据")
    @PostMapping("/loadAllIntroduce")
    public JSONArray loadAllIntroduce(){
        //返回给前端的数据包
        JSONArray animals = new JSONArray();
        JSONArray plants = new JSONArray();

        //数组存储活动数据
        List<activityDto> activityData = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();


        try{
            Connection conn = dataSource.getConnection();

            //取出活动信息
            String sql = "SELECT * FROM popularizationofscience";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                JSONObject tmp = new JSONObject();
                tmp.put("ID", rs.getString(1));
                tmp.put("type", rs.getString("pos_Type"));
                tmp.put("name", rs.getString("pos_NickName"));
                tmp.put("scientificName", rs.getString("pos_Name"));
                tmp.put("situation", rs.getString("pos_Condition"));
                tmp.put("appearance", rs.getString("pos_Appearance"));
                tmp.put("image", rs.getString("pos_Image"));
                tmp.put("character", rs.getString("pos_Character"));
                if (tmp.get("type").equals("动物")){
                    animals.add(tmp);
                }
                else{
                    plants.add(tmp);
                }
            }

            //取出用户信息作用户名和openid之间的对应关系
            sql = "select user_ID,user_Name from user";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            //建立openid和用户名之间的映射关系
            while (rs.next()) {
                userMap.put(rs.getString(1),rs.getString(2));
            }
            pst.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }


        //打包数据

        JSONArray res = new JSONArray();
        res.add(animals);
        res.add(plants);
        return res;
    }






}
