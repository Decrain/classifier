package com.decrain.spark.mllib.tools;
/**
 * Created by Decrain on 2017/8/14.
 */
public class isEnglish {


    public static boolean isEnglish(String text){

        /*if (null != text && !"".equals(text)) {
            if (text.matches("^[A-Za-z0-9]*+$")) {
                return true;
            }
        }*/
        char[] c = text.toCharArray();
        int num = 0;
        for(int i=0;i<text.length();i++){
            if((c[i]>'A'&&c[i]<='Z')){
                num += 1;
            }
            if(c[i]>'a'&& c[i]<='z')
                num += 1;

        }
        if(num>=text.length()*0.6){
            return true;
        }

        return false;
    }
}
