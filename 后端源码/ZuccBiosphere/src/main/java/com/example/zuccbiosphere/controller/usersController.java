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

import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户模块")
@RestController
@RequestMapping("/api/user")
public class usersController {
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

    @ApiOperation(value = "添加用户足迹", notes = "需要传入JSON,其中包含:\n" +
            "openid:***\n" +
            "cardId:***\n" +
            "latitude:***\n" +
            "longitude:***")
    @ApiResponses({
            @ApiResponse(code = 200, message = "得到add foot success!表示添加成功\n" +
                    "得到distance not enough!表示用户距离打卡点超过1公里\n" +
                    "得到Unknown Error!表示更新失败，可能是网络问题或者传入参数有误"),
    })
    @PostMapping("/addUserFoot")
    public String addCollect(@RequestBody JSONObject req){
        String openid=req.getString("openid");
        String cardId=req.getString("cardId");
        Double latitude=req.getDouble("latitude");
        Double longitude=req.getDouble("longitude");

        try {
            String cardKey="cards-info:"+cardId;
            Map<Object, Object> map=redisTemplate.opsForHash().entries(cardKey);
            JSONObject card = JSONObject.parseObject(map.get("key").toString());
            Double cardLatitude=card.getDouble("latitude");
            Double cardLongitude=card.getDouble("longitude");
            Double dis= DistanceUtil.getDistance(longitude,latitude,cardLongitude,cardLatitude);

            if(dis<=1000){
                //为用户添加足迹
                String userKey="users:"+openid;
                Map<Object, Object> userMap=redisTemplate.opsForHash().entries(userKey);
                JSONObject user = JSONObject.parseObject(userMap.get("key").toString());
                String myFoot = user.getString("myFoot");

                //检验是否重复
                String[] tmp=myFoot.split("\\*");
                for(int i=0;i<tmp.length;i++){
                    if(tmp[i].length()==0)continue;

                    if(tmp[i].equals(cardKey)){
                        return "repeated!";
                    }
                }

                myFoot+=cardKey+"*";
                user.replace("myFoot",myFoot);
                Map<String, Object> newUserMap = new HashMap<>();
                newUserMap.put("key", JSON.toJSONString(user));
                redisTemplate.opsForHash().putAll(userKey, newUserMap);
                return "add foot success!";
            }else{
                return "distance not enough!";
            }
        }catch (Exception e){
            logger.info(e.toString());
            return "Unknown Error!";
        }
    }


    @ApiOperation(value = "添加用户收藏", notes = "需要传入JSON,其中包含:\n" +
            "openid:***\n" +
            "cardId:***\n")
    @ApiResponses({
            @ApiResponse(code = 200, message = "得到add collect success!表示添加成功\n" +
                    "得到add collect failed!可能网络不畅或者传入参数有误"),
    })
    @PostMapping("/addUserCollect")
    public String addFoot(@RequestBody JSONObject req){
        String openid=req.getString("openid");
        String cardId=req.getString("cardId");

        String[] check=cardId.split("&");
        if(check.length!=3){
            return "PE";
        }
        if(check[0].equals("undefined")||check[1].equals("undefined")||check[2].equals("undefined")){
            return "PE";
        }

        try {
            //为用户添加收藏
            String cardKey="cards-info:"+cardId;
            String userKey="users:"+openid;
            Map<Object, Object> userMap=redisTemplate.opsForHash().entries(userKey);
            JSONObject user = JSONObject.parseObject(userMap.get("key").toString());
            String myCollect = user.getString("myCollect");

            //检验是否重复
            String[] tmp=myCollect.split("\\*");
            for(int i=0;i<tmp.length;i++){
                if(tmp[i].length()==0)continue;

                if(tmp[i].equals(cardKey)){
                    return "repeated!";
                }
            }

            myCollect+=cardKey+"*";
            user.replace("myCollect",myCollect);
            Map<String, Object> newUserMap = new HashMap<>();
            newUserMap.put("key", JSON.toJSONString(user));
            redisTemplate.opsForHash().putAll(userKey, newUserMap);
            return "add collect success!";
        }catch (Exception e){
            logger.info(e.toString());
            return "add collect failed!";
        }
    }


    @ApiOperation(value = "得到用户收藏/足迹列表", notes = "需要传入JSON,其中包含:\n" +
            "cards:***\n" +
            "注:只需把user信息中的collect传上来即可")
    @ApiResponses({
            @ApiResponse(code = 200, message = "得到Map格式的卡片列表"),
    })
    @PostMapping("/getUserCollectOrFoot")
    public Map<Object, Object> getUserCollect(@RequestBody JSONObject req){
        String tmp=req.getString("cards");
        String[] cards=tmp.split("\\*");

        Map<Object, Object> res= new HashMap<>();
        int cnt=1;
        for(int i=1;i<=cards.length;i++){
            if(cards[i-1].length()==0)continue;

            Map<Object, Object> map=redisTemplate.opsForHash().entries(cards[i-1]);
            if(map!=null&&!map.isEmpty()){
                res.put("key"+cnt,map);
                cnt++;
            }
        }

        return res;
    }

    @ApiOperation(value = "删除用户收藏", notes = "需要传入JSON,其中包含:\n" +
            "openid:***\n" +
            "cardId:***\n")
    @ApiResponses({
            @ApiResponse(code = 200, message = "得到delete collect success!表示添加成功\n" +
                    "得到delete collect failed!可能网络不畅或者传入参数有误"),
    })
    @PostMapping("deleteCollect")
    public String deleteCollect(@RequestBody JSONObject req){
        String openid=req.getString("openid");
        String cardId=req.getString("cardId");

        String[] check=cardId.split("&");
        if(check.length!=3){
            return "PE";
        }
        if(check[0].equals("undefined")||check[1].equals("undefined")||check[2].equals("undefined")){
            return "PE";
        }

        try {
            //为用户删除收藏
            String cardKey="cards-info:"+cardId;
            String userKey="users:"+openid;
            Map<Object, Object> userMap=redisTemplate.opsForHash().entries(userKey);
            JSONObject user = JSONObject.parseObject(userMap.get("key").toString());
            String myCollect = user.getString("myCollect");

            String[] list=myCollect.split("\\*");
            String res="";
            for(int i=0;i<list.length;i++){
                if(list[i].length()!=0 && !list[i].equals(cardKey)){
                    res+=list[i]+"*";
                }
            }

            user.replace("myCollect",res);
            Map<String, Object> newUserMap = new HashMap<>();
            newUserMap.put("key", JSON.toJSONString(user));
            redisTemplate.opsForHash().putAll(userKey, newUserMap);
            return "delete collect success!";
        }catch (Exception e){
            logger.info(e.toString());
            return "delete collect failed!";
        }
    }
}
