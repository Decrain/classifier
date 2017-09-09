package com.decrain.spark.mllib.tools;

import com.decrain.spark.mllib.utils.PinyinResource;

import java.util.Map;

/**
 * Created by Decrain on 2017/8/2.
 */
public class isTranditionalChinese {
    private static final Map<String, String> CHINESE_MAP = PinyinResource.getChineseResource();
    public static boolean isTraditionalChinese(String text) {
        double num = 0;
        char[] c = text.toCharArray();
        if (null != text && !"".equals(text)){
            for(int i=0;i<text.length();i++){
                if(CHINESE_MAP.containsKey(String.valueOf(c[i]))){
                    num += 1;
                }
            }
        }


        if(num>=text.length()*0.2 || num>2){return true;}

        return false;

    }
}
