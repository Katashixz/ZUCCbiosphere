package com.example.zuccbiosphere.service;

import com.example.zuccbiosphere.configuration.JwtUtils;
import com.example.zuccbiosphere.dto.UserDto;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息接口
 */
@Service
public class UserService {
	
	private static final String encryptSalt = "F12839WhsnnEV$#23b";

	@Autowired
	private StringRedisTemplate redisTemplate;

    /**
     * 保存user登录信息，返回token
     */
    public String generateJwtToken(String username) {
    	String salt = JwtUtils.generateSalt();
    	/**
    	 * @todo 将salt保存到数据库或者缓存中
    	 * redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
    	 */
		redisTemplate.opsForValue().set("token:"+username, salt, 3600, TimeUnit.SECONDS);
    	return JwtUtils.sign(username, salt, 3600); //生成jwt token，设置过期时间为1小时
    }
    
    /**
     * 获取上次token生成时的salt值和登录用户信息
     * @param username
     * @return
     */
    public UserDto getJwtTokenInfo(String username) {
    	String salt = "";
    	/**
    	 * @todo 从数据库或者缓存中取出jwt token生成时用的salt
    	 * salt = redisTemplate.opsForValue().get("token:"+username);
    	 */
		salt = redisTemplate.opsForValue().get("token:"+username);
//    	UserDto user = getUserInfo(username);
		UserDto user = new UserDto();
    	user.setSalt(salt);
    	return user;
    }

    /**
     * 清除token信息
     * @param username 登录用户名
     */
    public void deleteLoginInfo(String username) {
    	/**
    	 * @todo 删除数据库或者缓存中保存的salt
    	 * redisTemplate.delete("token:"+username);
    	 */
		redisTemplate.delete("token:"+username);
    }
    
    /**
     * 获取数据库中保存的用户信息，主要是加密后的密码
     * @param userName
     * @return
     */
    public UserDto getUserInfo(String userName) {
		UserDto user = new UserDto();
//		String sql="select * from user where username="+"\""+userName+"\"";
//		System.out.println(sql);
//		List<Map<String,Object>>maps=jdbcTemplate.queryForList(sql);
//		System.out.println(maps);
//
//		if(maps.size()==1){
//			Map<String,Object>usr=maps.get(0);
//			String name=usr.get("username").toString();
//			Long id=Long.valueOf(usr.get("id").toString());
//			String pwd=usr.get("password").toString();
//
//			user.setUserId(id);
//			user.setUsername(name);
//			user.setEncryptPwd(new Sha256Hash(pwd, encryptSalt).toHex());
//		}
		user.setUsername(userName);
		user.setEncryptPwd(new Sha256Hash("123456",encryptSalt).toHex());

		return user;
    }
    
    /**
     * 获取用户角色列表，强烈建议从缓存中获取
     * @param userId
     * @return
     */
    public List<String> getUserRoles(Long userId){
    	return Arrays.asList("admin");
    }

}
