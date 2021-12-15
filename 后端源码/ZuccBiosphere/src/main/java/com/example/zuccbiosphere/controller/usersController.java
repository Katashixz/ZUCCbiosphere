package com.example.zuccbiosphere.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.utils.DistanceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user")
public class usersController {
    @Autowired
    private DataSource dataSource;


    @Autowired
    private StringRedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(usersController.class);

    @ApiOperation(value = "获取用户信息", notes = "需要传入JSON,其中包含:\n" +
            "openid:***")
    @ApiResponses({
            @ApiResponse(code = 200, message = "得到Map格式用户数据\n" +
                    "得到null则openid有误"),
    })
    @PostMapping("/getUserData")
    public Map<Object, Object> getUser(@RequestBody JSONObject req) {
        String openid=req.getString("openid");

        String key = "users:"+openid;
        try{
            return redisTemplate.opsForHash().entries(key);
        }catch (Exception e){
            logger.info(e.toString());
        }
        return null;
    }


    @ApiOperation(value = "打赏人品值", notes = "需要传入JSON,其中包含:\n" +
            "openid:***\n" +
            "cardId:***\n")
    @ApiResponses({
            @ApiResponse(code = 200, message = "得到delete collect success!表示添加成功\n" +
                    "得到delete collect failed!可能网络不畅或者传入参数有误"),
    })
    @PostMapping("/rpSend")
    public JSONObject rpSend(@RequestBody JSONObject req){
        JSONObject res = new JSONObject();
        String openid=req.getString("openid");
        String toUserID=req.getString("posterID");
        int point = req.getInteger("point");

        if (point == 0) point = 10;
        else if (point == 1) point = 20;
        else point = 50;


        try {
            Connection conn = dataSource.getConnection();

            String sql = "select user_rpPoint from user where user_ID = ?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, openid);
            java.sql.ResultSet rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            int user_rp = -1, toUser_rp;
            if (rs.next()){
                user_rp = rs.getInt(1);
            }
            if (user_rp < point){
                res.put("result", "点数不够！");
            }
            else{
                res.put("result", "成功打赏" + point + "人品值");
                sql = "UPDATE `user` SET user_rpPoint = user_rpPoint + ? where user_ID = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, point);
                pst.setString(2, toUserID);
                pst.executeUpdate();

                sql = "UPDATE `user` SET user_rpPoint = user_rpPoint - ? where user_ID = ?";
                pst = conn.prepareStatement(sql);
                pst.setInt(1, point);
                pst.setString(2, openid);
                pst.executeUpdate();


                sql = "INSERT INTO rpRecord VALUES(RPrecordID, ?, ?, NOW(), 1, ?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, openid);
                pst.setString(2, toUserID);
                pst.setInt(3, point);
                pst.executeUpdate();

                //打赏次数记录+1___12/14 22:01
                sql="update user set user_RewardPoint = user_RewardPoint+1 WHERE user_ID=?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, openid);
                pst.executeUpdate();

            }


        }catch (Exception e){
            e.printStackTrace();
        }


        return res;
    }
    @ApiOperation(value = "用户信息加载显示", notes = "需要传入JSON,其中包含:\n" +
            "openid:***\n" +
            "cardId:***\n")

    @PostMapping("/loadUserInfo")
    public JSONObject loadUserInfo(@RequestBody JSONObject req){
        JSONObject res = new JSONObject();
        String openid=req.getString("openid");
        try {
            Connection conn = dataSource.getConnection();

            String sql = "SELECT user_rpPoint,user_PushPostPoint,user_CommentPoint,user_RewardPoint from `user` where user_ID=?";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, openid);
            java.sql.ResultSet rs = pst.executeQuery();
            //时间格式转换
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(rs.next()){
                res.put("myrp",rs.getInt(1));
                res.put("myPush",rs.getInt(2));
                res.put("myComment",rs.getInt(3));
                res.put("myRewardRecord",rs.getInt(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        return res;
    }

}
