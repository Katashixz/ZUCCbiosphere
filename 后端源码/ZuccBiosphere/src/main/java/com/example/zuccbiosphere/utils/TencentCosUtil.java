package com.example.zuccbiosphere.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Random;

/**
 * @Author: zh
 * @Date: 2020/6/5 16:22
 */

public class TencentCosUtil {
    // 存储桶名称
    private static final String bucketName = "wxcontest-1305913165";
    //secretId 秘钥id
    private static final String SecretId = "AKID5C2buvgnfGadQwBAtMYS5RVJr89tLWP1";
    //SecretKey 秘钥
    private static final String SecretKey = "NSGm20h6E5fy0lEQKyLQEm21i8IwLTO4";
    // 腾讯云 自定义文件夹名称
    private static final String prefix = "cards/";
    // 访问域名
    public static final String URL = "https://wxcontest-1305913165.cos.ap-nanjing.myqcloud.com/";
    // 创建COS 凭证
    private static COSCredentials credentials = new BasicCOSCredentials(SecretId,SecretKey);
    // 配置 COS 区域 就购买时选择的区域 我这里是 广州（guangzhou）
    private static ClientConfig clientConfig = new ClientConfig(new Region("ap-nanjing"));

    public static String uploadfile(MultipartFile file){
        // 创建 COS 客户端连接
        COSClient cosClient = new COSClient(credentials,clientConfig);
        String fileName = file.getOriginalFilename();
        try {
            String substring = fileName.substring(fileName.lastIndexOf("."));
            File localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
            file.transferTo(localFile);
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+substring;
            // 将 文件上传至 COS
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,fileName,localFile);
            cosClient.putObject(objectRequest);
            System.out.println("insert into cos success!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }
        return URL+fileName;
    }

    public static String uploadfile(File file,String fileName){
        // 创建 COS 客户端连接
        COSClient cosClient = new COSClient(credentials,clientConfig);
        try {
            // 将 文件上传至 COS
            Random random = new Random();
            fileName=random.nextInt(10000)+fileName;
            PutObjectRequest objectRequest = new PutObjectRequest(bucketName,prefix+fileName,file);
            cosClient.putObject(objectRequest);
            System.out.println("insert into cos success!");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }

        return URL+prefix+fileName;
    }

    public static void delFile(String filename){
        new Thread(new Runnable() {
            public void run() {
                // 创建 COS 客户端连接
                COSClient cosClient = new COSClient(credentials,clientConfig);
                try{
                    String key=prefix+filename;
                    cosClient.deleteObject(bucketName, key);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    cosClient.shutdown();
                }
            }
        }).start();
    }

}