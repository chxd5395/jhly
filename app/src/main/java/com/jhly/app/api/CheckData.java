package com.jhly.app.api;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by r on 2017/5/21.
 */

public class CheckData {
    public static boolean isPhone(String str) {
        String reg = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    public static boolean isVehicle(String str){
        String reg = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isName(String str){
        String reg = "(([\\u4E00-\\u9FA5]{2,7})|([a-zA-Z]{3,10}))";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
