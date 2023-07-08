package com.xupt.ttms.util;/*
 * @author ck
 * @date 2023/4/20
 * @apiNote
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    /**
     * 判断字符串是否匹配正则表达式
     *
     * @param regex 正则表达式
     * @param str   待匹配的字符串
     * @return 是否匹配
     */
    public static boolean isMatch(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 判断字符串是否为邮箱
     *
     * @param email 待匹配的字符串
     * @return 是否匹配
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        return email.matches(regex);
    }

    /**
     * 判断字符串是否为手机号
     *
     * @param phoneNumber 待匹配的字符串
     * @return 是否匹配
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }
        String regex = "^1\\d{10}$";
        return phoneNumber.matches(regex);
    }

    /**
     * 判断字符串是否为合法的密码
     *
     * @param password 待匹配的字符串
     * @return 是否匹配
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        // 必须同时包含大小写字母和数字且长度在6到12个字符之间
        String regex = "^(?=.*[a-z])(?=.*\\d)\\S{6,16}";
        return password.matches(regex);
    }
    /**
     * 判断字符串是否为合法的金钱
     *
     * @param money 待匹配的字符串
     * @return 是否匹配
     */
    public static boolean isValidMoney(String money) {
        if (money == null || money.isEmpty()) {
            return false;
        }
        String regex = "^(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$";
        return money.matches(regex);
    }



    /**
     * 判断字符串是否为合法的用户名
     *
     * @param username 待匹配的字符串
     * @return 是否匹配
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        // 必须同时包含字母和数字,且长度在5到8个字符之间
        String regex = "^[a-zA-Z0-9_-]{4,8}$";
        return username.matches(regex);
    }

    /**
     * 判断字符串是否为合法的中文姓名
     *
     * @param name 待匹配的字符串
     * @return 是否匹配
     */
    public static boolean isValidChineseName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        // 匹配 2~4 个汉字
        String regex = "^[\\u4E00-\\u9FA5]{2,4}$";
        return name.matches(regex);
    }

    /**
     * 查找字符串中匹配正则表达式的部分
     *
     * @param regex 正则表达式
     * @param str   待查找的字符串
     * @return 匹配的部分
     */
    public static String find(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return null;
        }
    }


    /**
     * 查找字符串中匹配正则表达式的所有部分
     *
     * @param regex 正则表达式
     * @param str   待查找的字符串
     * @return 匹配的所有部分
     */
    public static String[] findAll(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            sb.append(matcher.group()).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString().split(",");
        } else {
            return null;
        }
    }

    /**
     * 替换字符串中匹配正则表达式的部分
     *
     * @param regex       正则表达式
     * @param str         待替换的字符串
     * @param replacement 替换后的字符串
     * @return 替换后的字符串
     */
    public static String replace(String regex, String str, String replacement) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll(replacement);
    }
}
