package com.xupt.ttms.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.Bucket;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.UUID;

@ConfigurationProperties(prefix = "cos")
@Component
@Data
public class COSUtil {
    private static String SECRET_ID = "AKID0yqkeeU8aqvmOsCLTceeYFj03tYCjBUc";
    private static String SECRET_KEY = "8ZowueSL5cw2BGCwWxLmCXpysjRwDIBt";
    private static String BUCKET = "ttms-1314223856";
    private static String IMG_PATH = "img/";
    private static String USER_PATH = "user/";

    public static Bucket getBucket() {
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        Region region = new Region("COS_REGION");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
        }
        return buckets.get(0);
    }

    /**
     * @param file      上传的文件
     * @param webPath  网页路径：封面：/img/ 预告片封面：/video/img/ 预告片：/video/video/ 用户头像：/user/
     */
    public static String uploadFile(File file, String webPath) {
// 指定文件将要存放的存储桶
        String bucketName = BUCKET;
        String FileName = UUID.randomUUID().toString().replaceAll("-", "");
        String fileExtension = file.getName().substring(file.getName().lastIndexOf("."));
// 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
        String key = webPath + FileName + fileExtension;
        COSCredentials cred = new BasicCOSCredentials(SECRET_ID, SECRET_KEY);
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String url = "https://ttms-1314223856.cos.ap-nanjing.myqcloud.com" + key;
        return url;
    }
}
