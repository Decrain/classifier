package decrain;


import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.tokenizer.TraditionalChineseTokenizer;
import com.decrain.spark.mllib.utils.PinyinResource;

import java.util.List;
import java.util.Map;

/**
 * Created by Decrain on 2017/8/2.
 */
public class test3 {
    private static final Map<String, String> CHINESE_MAP = PinyinResource.getChineseResource();
    public static void main(String agrs[]){
        testDemo();
        System.out.println(isTraditionalChinese("辣妹合唱團成員維多利亞·碧咸，亦由於他擁有"));

    }



    public static void testDemo() {
       /* JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences =
                new String[] {"这是一个伸手不见五指的黑夜。我叫孙悟空，我爱北京，我爱Python和C++。", "我不喜欢日本和服。", "雷猴回归人间。",
                        "工信处女干事每月经过下属科室都要亲口交代24口交换机等技术性器件的安装工作", "结果婚的和尚未结过婚的"};
        for (String sentence : sentences) {
            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.INDEX).toString());
        }*/
        HanLP.Config.ShowTermNature = false;
        List<Term> termList = TraditionalChineseTokenizer.segment("大衛貝克漢不僅僅是名著名球員，球場以外，其妻為前" +
                "辣妹合唱團成員維多利亞·碧咸，亦由於他擁有" +
                "突出外表、百變髮型及正面的形象，以至自己" +
                "品牌的男士香水等商品，及長期擔任運動品牌" +
                "Adidas的代言人，因此對大眾傳播媒介和時尚界" +
                "等方面都具很大的影響力，在足球圈外所獲得的" +
                "認受程度可謂前所未見。");

        System.out.println(termList);
    }

    public static boolean isTraditionalChinese(String text) {
        double num = 0;
        char[] c = text.toCharArray();
        for(int i=0;i<text.length();i++){
            if(CHINESE_MAP.containsKey(String.valueOf(c[i]))){
                num +=1;
            }
        }

       if(num>=text.length()*0.2){return true;}

       return false;

    }
}
