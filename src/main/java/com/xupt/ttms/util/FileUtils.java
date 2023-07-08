package com.xupt.ttms.util;

import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.*;
import java.util.UUID;

public class FileUtils {
    public static long ReadVideoTime(File source) {
        long ls = new Long(0);
        String length = "";
        try {
            MultimediaObject instance = new MultimediaObject(source);
            MultimediaInfo result = instance.getInfo();
            ls = result.getDuration() / 1000;
            Integer hour = (int) (ls / 3600);
            Integer minute = (int) (ls % 3600) / 60;
            Integer second = (int) (ls - hour * 3600 - minute * 60);
            String hr = hour.toString();
            String mi = minute.toString();
            String se = second.toString();
            if (hr.length() < 2) {
                hr = "0" + hr;
            }
            if (mi.length() < 2) {
                mi = "0" + mi;
            }
            if (se.length() < 2) {
                se = "0" + se;
            }
            length = hr + ":" + mi + ":" + se;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ls;
    }

    public static File multipartFileToFile(MultipartFile file) {
        File toFile = null;
        InputStream ins = null;
        OutputStream os = null;
        try {
            ins = file.getInputStream();
            String fileName = file.getOriginalFilename();
            String prefix = UUID.randomUUID().toString().replaceAll("-","");
            String suffix = "."+getExtensionName(fileName);
            toFile = File.createTempFile(prefix,suffix);
            os = new FileOutputStream(toFile);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                os.close();
                ins.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return toFile;
    }

    private static String getExtensionName(String fileName) {
        if ((fileName != null) && (fileName.length() > 0)) {
            int dot = fileName.lastIndexOf('.');
            if ((dot >-1) && (dot < (fileName.length() - 1))) {
                return fileName.substring(dot + 1);
            }
        }
        return fileName;
    }

    private static File inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
