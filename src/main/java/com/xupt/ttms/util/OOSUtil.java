package com.xupt.ttms.util;

import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@ConfigurationProperties(prefix = "oos")
@Component
@Data
public class OOSUtil implements InitializingBean {
    private  String  endpoint;
    private  String  accessKeyId;
    private  String  accessKeySecret;
    private  String  bucketName;

    //公开的变量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        ACCESS_KEY_ID=accessKeyId;
        ACCESS_KEY_SECRET=accessKeySecret;
        BUCKET_NAME=bucketName;
    }
    public static String upload(MultipartFile dropzFile, String type) {
        String result = "";

        //文件名
        String fileName = dropzFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newName =type+ UUID.randomUUID() + "." + suffix;
        OSS client = new OSSClientBuilder().build(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try {
            client.putObject(new PutObjectRequest(BUCKET_NAME, newName, new ByteArrayInputStream(dropzFile.getBytes())));
            // 上传文件路径 = http://BUCKET_NAME.ENDPOINT/自定义路径/fileName
            String filePath = "http://" + BUCKET_NAME + "." + END_POINT + "/"+ newName;
            result+=filePath;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.shutdown();
        }
        return result;
    }
}
