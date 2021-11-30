package com.example.zuccbiosphere.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.zuccbiosphere.dto.UserDto;
import com.example.zuccbiosphere.dto.userDataDto;
import com.example.zuccbiosphere.dto.userInfoDto;
import com.example.zuccbiosphere.service.UserService;
import com.example.zuccbiosphere.utils.HttpMethodUtil;
import com.example.zuccbiosphere.utils.encryptUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "登录模块")
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private StringRedisTemplate redisTemplate;

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户第一次登录
     * @return token
     */
    @ApiOperation(value = "用户登录", notes = "wx.login上传加密字符串即可")
    @ResponseBody
    @RequestMapping(value="/getUserInfo",method = RequestMethod.GET)
    public ResponseEntity<Void> login(String encryptedData,String iv,String code, HttpServletResponse response){
        Map map = new HashMap();

        //获取用户openid
        if(code == null || code.length() == 0){
            map.put("status",0);
            map.put("msg","code不能为空");
            return null;
        }

        String wxspAppid = "wxbe4d121aff0adbb7";
        String wxspSecret = "0dfe287f7d1088088217498b693ec061";
        String grant_type = "authorization_code";

        //获取openid
        String params = "appid="+wxspAppid+"&secret="+wxspSecret+"&js_code="+code+"&grant_type="+grant_type;
        String str= HttpMethodUtil.sendGet("https://api.weixin.qq.com/sns/jscode2session",params);

        JSONObject json=JSONObject.parseObject(str);
        String openid=json.get("openid").toString();
        logger.info(json.toJSONString());

        //获取access_toekn
//        String tokenUrl="https://api.weixin.qq.com/cgi-bin/token";
//        String tokenParam="grant_type=client_credential&appid="+wxspAppid+"&secret="+wxspSecret;
//        String tokenStr=HttpMethodUtil.sendGet(tokenUrl,tokenParam);
//        logger.info(tokenStr);
//        JSONObject tokenJson=JSONObject.parseObject(tokenStr);
//        String access_token=tokenJson.getString("access_token");



//        String requestUrl="https://api.weixin.qq.com/cgi-bin/user/info";
//        String param="access_token="+access_token+"&openid="+openid+"&lang=zh_CN";
//        String res=HttpMethodUtil.sendGet(requestUrl,param);
//
//        logger.info(res);
//
//        JSONObject userInfo=JSONObject.parseObject(res);
//        String headerUrl=userInfo.getString("headimgurl");
//        String nickName=userInfo.getString("nickname");
        //为用户创建信息
//        Map<Object, Object> newUserMap=redisTemplate.opsForHash().entries("users:"+ encryptUtils.MD5EncryptMethod(openid));
        /*if(newUserMap.isEmpty()){
            userInfoDto uData=new userInfoDto();
            uData.setOpenid(encryptUtils.MD5EncryptMethod(openid));
            uData.setOpenid("");
            uData.setUserName("");
            uData.setUserPhone("");
            uData.setUserGender("");
            Map<String, Object> newMap = new HashMap<String, Object>();
            saveOpenId(uData);
            newMap.put("key", JSON.toJSONString(uData));
            redisTemplate.opsForHash().putAll("users:"+encryptUtils.MD5EncryptMethod(openid), newMap);
            System.out.println("new user create success!");
        }else{
            System.out.println("user has existed!");
        }*/
        //存储用户信息到数据库
        userInfoDto uData=new userInfoDto();
        uData.setOpenid(encryptUtils.MD5EncryptMethod(openid));
        uData.setUserName("");
        uData.setUserPhone("");
        uData.setUserGender("");
        uData.setUserPassword("");
        saveOpenId(uData);
        //为用户生成token
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(encryptUtils.MD5EncryptMethod(openid), "123456");
            subject.login(token);

            UserDto user = (UserDto) subject.getPrincipal();
            String newToken = userService.generateJwtToken(user.getUsername());
            response.setHeader("x-auth-token", newToken);
            response.setHeader("openid", encryptUtils.MD5EncryptMethod(openid));
            logger.info("User {} login success!",openid);
            return ResponseEntity.ok().build();
        } catch (AuthenticationException e) {
            logger.error("User {} login fail, Reason:{}",openid, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public void saveOpenId(userInfoDto uDto){
        String sql = "select * from user where user_ID = ?";
        String sql2 = "insert into user(user_ID) value (?)";
        if(jdbcTemplate.queryForList(sql,uDto.getOpenid()).isEmpty()){
            jdbcTemplate.update(sql2,uDto.getOpenid());
        }
    }
}
