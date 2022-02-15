package com.wangxt.easy.publish.hexo.blog.util;

public class StringUtil {

    public static boolean isBlank(String cs){
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String cs){
        return !isBlank(cs);
    }
}
