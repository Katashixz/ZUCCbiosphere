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

@Api(tags = "动物城友会模块")
@RestController
@RequestMapping("/api/acfc")
public class AnimalCityFriendsClubController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 动物城友会发布活动并更新到数据库
     * @param req
     * @return
     */
    @ApiOperation(value = "发起活动", notes = "需要传入JSON，包括\n" +
            "openID:***\n" +
            "ActivityName:***\n" +
            "ActivityType:***\n" +
            "ActivityDescription:***")
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



    /**
     * 从数据库中读取所有活动信息返回给前端
     */
    @Autowired
    private DataSource dataSource;
    @ApiOperation(value = "读取所有活动", notes = "需要前端发送请求，不需要传数据")
    @PostMapping("/loadActivities")
    public JSONArray loadActivities(){
        //返回给前端的数据包
        JSONArray arr = new JSONArray();

        //数组存储活动数据
        List<activityDto> activityData = new ArrayList<>();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();


        try{
            Connection conn = dataSource.getConnection();

            //取出帖子信息
            String sql = "select * from animalcityfriendsclub order by activity_ReleaseDate DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                activityDto temp = new activityDto();
                temp.setActivityId(rs.getLong("activity_ID"));
                temp.setAdminId(rs.getString("admin_ID"));
                temp.setUserId(rs.getString("user_ID"));
                temp.setName(rs.getString("activity_Name"));
                temp.setType(rs.getString("activity_Type"));
                temp.setStartDate(rs.getDate("activity_StartDate") + " " + rs.getTime("activity_StartDate"));
                temp.setReleaseDate(rs.getDate("activity_ReleaseDate") + " " + rs.getTime("activity_ReleaseDate"));
                activityData.add(temp);
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
        for (activityDto data:activityData) {
            JSONObject object = new JSONObject();
            object.put("activityId", data.getActivityId());
            object.put("activityPersonId", data.getUserId());
            object.put("activityType", data.getType());
            object.put("activityContent", data.getName());
            object.put("activityDate", getHoursFilter.getTimeDiff(data.getReleaseDate()));
            object.put("activityStarter", userMap.get(data.getUserId()));
            arr.add(object);
        }



        return arr;


    }

}
