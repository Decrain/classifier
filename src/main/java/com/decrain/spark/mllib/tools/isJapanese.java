package com.decrain.spark.mllib.tools;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Decrain on 2017/8/1.
 */
public class isJapanese {

    public static boolean isJapanese(String str) throws UnsupportedEncodingException {
        Set<Character.UnicodeBlock> japaneseUnicodeBlocks = new HashSet<Character.UnicodeBlock>() {{
            add(Character.UnicodeBlock.HIRAGANA);
            add(Character.UnicodeBlock.KATAKANA);
            add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
        }};

        String mixed = "ラドクリフ、マラソン五輪代表に1万m出場にも含み";
        int num = 0;
        if (null != str && !"".equals(str)){
            if (str.getBytes("shift-jis").length >= (2 * str.length())) {
                return true;
            }
        }

      /*  for (char c : str.toCharArray()) {
            if (japaneseUnicodeBlocks.contains(Character.UnicodeBlock.of(c))) {
                num ++;
            }


        }
        if(num>=str.length()/2){
            System.out.println(num);
            System.out.println(str.length());
            return true;
        }*/

        return false;

    }
}
