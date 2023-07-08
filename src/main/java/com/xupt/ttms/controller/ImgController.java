package com.xupt.ttms.controller;/*
 * @author ck
 * @date 2023/1/13
 * @apiNote
 */

import com.xupt.ttms.util.QRCodeUtil;
import ij.ImagePlus;
import ij.process.ImageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;


@Controller
@Slf4j
public class ImgController {
    @GetMapping("/qrcode")
    public void getQRCode(HttpServletResponse response) throws Exception {

        try {
            /*
             * 调用工具类生成二维码并输出到输出流中
             */
            QRCodeUtil.createCodeToOutputStream("", response.getOutputStream());
            log.info("成功生成二维码!");
        } catch (IOException e) {
            log.error("发生错误， 错误信息是：{}！", e.getMessage());
        }
    }
//    @GetMapping(value = "/resizeImage")
//    public ResponseEntity<byte[]> resizeImage(@RequestParam String imageUrl, @RequestParam("width") int width,@RequestParam("height") int height) throws IOException {
//        log.info(imageUrl);
//        URL url = new URL(imageUrl);
//        BufferedImage originalImage = ImageIO.read(url);
//        log.info(String.valueOf(originalImage));
//        BufferedImage bufferedImage = Thumbnails.of(originalImage).forceSize(375, 165).asBufferedImage();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(bufferedImage,"jpg",outputStream);
//        byte[] byteArray = outputStream.toByteArray();
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(byteArray);
//    }
//    @GetMapping("/resizeImage")
    @GetMapping("/resizeImage")
    public ResponseEntity<ByteArrayResource> getImage(@RequestParam String imageUrl, @RequestParam("width") int width, @RequestParam("height") int height) throws Exception {
        // 获取图片
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();
        Path tempFile = Files.createTempFile("image", ".jpg");
        Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        in.close();

        // 使用ImageJ调整图像大小
        ImagePlus imagePlus = new ImagePlus(tempFile.toUri().toString());
        ImageProcessor processor = imagePlus.getProcessor();
        ImageProcessor resizedProcessor = processor.resize(width, height);
        ImagePlus resizedImage = new ImagePlus("Resized Image", resizedProcessor);

        // 将ImagePlus对象转换为BufferedImage对象
        BufferedImage bufferedImage = resizedImage.getBufferedImage();

        // 将BufferedImage对象转换为byte[]
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.close();

        // 创建ResponseEntity并返回
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        ByteArrayResource imgResource = new ByteArrayResource(byteArrayOutputStream.toByteArray());
        return ResponseEntity.ok().headers(headers).body(imgResource);
    }

}
