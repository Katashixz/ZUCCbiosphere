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

            //取出活动信息
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

    @ApiOperation(value = "从数据库中调取此账号参加过的活动", notes = "需要传入JSON,其中包含:\n" +
            "userId: ***\n")
    @PostMapping("/loadMyActivities")
    public JSONArray loadMyActivities(@RequestBody JSONObject req){
        JSONArray result = new JSONArray();
        String userID = req.getString("userId");
        List<Long> idList = new ArrayList<>();

        try{
            Connection conn = dataSource.getConnection();

            //取出这个用户参加过的所有活动编号
            String sql = "select activity_ID from acfcrecord where user_ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,userID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                idList.add(rs.getLong(1));
            }

            //根据活动id找出要显示的信息
            //此步骤设计数据库操作过多，需要优化，最好是一次性查出所有再筛选以减小压力
            for(Long id:idList){
                sql = "select activity_Name,activity_Location,activity_StartDate,activity_Type " +
                        "from animalcityfriendsclub " +
                        "where activity_ID = ?";
                pst = conn.prepareStatement(sql);
                pst.setLong(1,id);
                rs = pst.executeQuery();
                if (rs.next()) {
                    JSONObject activityData = new JSONObject();
                    activityData.put("activityId",id);
                    activityData.put("activityPersonId",userID);
                    activityData.put("activityLocation",rs.getString("activity_Location"));
                    activityData.put("activityStartDate",rs.getString("activity_StartDate"));
                    activityData.put("activityContent",rs.getString("activity_Name"));
                    activityData.put("activityType",rs.getString("activity_Type"));
                    result.add(activityData);
                }
            }

            pst.close();
            conn.close();

        }catch (SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @ApiOperation(value = "从数据库中调取此活动的详细信息", notes = "需要传入JSON,其中包含:\n" +
            "activityId: ***\n")
    @PostMapping("/loadActivityDetail")
    public JSONObject loadActivityDetail(@RequestBody JSONObject req){
        JSONObject result = new JSONObject();
        Long activityId = req.getLong("activityId");

        //活动信息实体数组存储取出的帖子信息
        activityDto data = new activityDto();

        //用于存储openid和用户名之间的对应关系
        Map<String,String> userMap = new HashMap<>();

        try{
            Connection conn = dataSource.getConnection();
            String sql = "select * from animalcityfriendsclub where activity_ID = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setLong(1,activityId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                data.setActivityId(rs.getLong("activity_ID"));
                data.setAdminId(rs.getString("admin_ID"));
                data.setUserId(rs.getString("user_ID"));
                data.setName(rs.getString("activity_Name"));
                data.setContent(rs.getString("activity_Content"));
                data.setType(rs.getString("activity_Type"));
                data.setCurrentParticipantsNum(rs.getLong("activity_CurrentParticipantsNum"));
                data.setTotalParticipantsNum(rs.getLong("activity_TotalParticipantsNum"));
                data.setLocation(rs.getString("activity_Location"));
                data.setStartDate(rs.getDate("activity_StartDate") + " " + rs.getTime("activity_StartDate"));
                data.setReleaseDate(rs.getDate("activity_ReleaseDate") + " " + rs.getTime("activity_ReleaseDate"));
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

        result.put("activityId",data.getActivityId());
        result.put("activityPersonId",userMap.get(data.getUserId()));
        result.put("activityType",data.getType());
        result.put("activityParticipantsNum",data.getCurrentParticipantsNum());
        result.put("activityTotolParticipantsNum",data.getTotalParticipantsNum());
        result.put("activityTitle",data.getName());
        result.put("activityContent",data.getContent());
        result.put("activityLocation",data.getLocation());
        result.put("activityStartDate",data.getStartDate());
        result.put("activityDate",data.getReleaseDate());



        return result;
    }


}
