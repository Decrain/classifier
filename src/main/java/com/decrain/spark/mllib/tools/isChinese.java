package com.decrain.spark.mllib.tools;

import com.google.common.base.Strings;
import static java.lang.Character.UnicodeBlock.*;

/**
 * Created by Decrain on 2017/8/1.
 */
public class isChinese {
    /*public static boolean isChinese(char c) {
        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
    }
*/
    // 判断一个字符串是否含有中文
    private static final String CHINESE_REGEX = "[\\u4e00-\\u9fa5]";

    public static boolean isChinese(String checkStr) {
        /*if (str == null) return false;
        double num = 0;
        for (char c : str.toCharArray()) {
            if(isChinese(c)){
                num +=0;
            }

        }
        if(num>str.length()*0.4){
            //System.out.println("chn");
            return true;
        }
        return false;*/
        double num = 0;
        if (null != checkStr && !"".equals(checkStr)){
            if(!Strings.isNullOrEmpty(checkStr)){
                char[] checkChars = checkStr.toCharArray();
                for(int i = 0; i < checkChars.length; i++){
                    char checkChar = checkChars[i];
                    if(checkCharContainChinese(checkChar)){
                        num ++;
                    }
                }
            }
        }

        if(num>=checkStr.length()*0.5){
            /*System.out.println(num);
            System.out.println(checkStr.length());*/
            return true;

        }
        return false;
    }
    private static boolean checkCharContainChinese(char checkChar){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(checkChar);
        if(CJK_UNIFIED_IDEOGRAPHS == ub || CJK_COMPATIBILITY_IDEOGRAPHS == ub || CJK_COMPATIBILITY_FORMS == ub ||
                CJK_RADICALS_SUPPLEMENT == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A == ub || CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B == ub){
            return true;
        }
        return false;
    }
    public static boolean containsChinese(String str) {
        double num = 0;
        for (int i = 0, len = str.length(); i < len; i++) {
            if (isChinese(str.charAt(i))) {
                num ++;
            }
            System.out.println(num);
        }
        if(num>=str.length()*0.8){
            return true;

        }
        return false;
    }
    public static boolean isChinese(char c) {
        return '〇' == c || String.valueOf(c).matches(CHINESE_REGEX);
    }
}
