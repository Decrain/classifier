package com.decrain.spark.mllib.tools;

/**
 * Created by Decrain on 2017/8/4.
 */
public class isKorean {

    public  static boolean isKorean(String text){
        if (null != text && !"".equals(text)){
            if(text.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")){
                return true;
            }
        }
       /* final Pattern p= Pattern.compile("[(\\x3130-\\x318F|\\xAC00-\\xD7A3)]");
        Matcher m=p.matcher(text);
        if(m.find()){
            return true;
        }*/

        return  false;
    }
}
