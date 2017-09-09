package decrain;

//import cmu.arktweetnlp.Twokenize;

import com.decrain.spark.mllib.dao.Statistics;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by Decrain on 2017/8/4.
 */
public class test4 {
    public static void main(String agrs[]){
        String log4jConfPath = "/Users/decrain/IdeaProjects/Classifier/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
//String sent ="현행 민법에서 자식은 아버지의 성과 본을 따르도록 되어 있으며(제781조), 성이 오칭(誤稱)되거나 하는 특별한 경우 이외에는 성의 변경은 허용되지 않는다. 한편 2003년 1월 통계청이 발표한 '2000년 인구주택 총조사 성씨 및 본관 집계 결과'를 보면, 2000년 11월 현재 우리나라 성씨는 모두 286개였으며, 본관은 4179개였다. 이 가운데 김(金)씨가 전체 인구의 21.6%인 992만6천명으로 가장 많았다. ";
String sent = "asfasfl asfg 124190 fsdjofsdoagsdarfk[]sgdjghsdghds[]%69809352sfdsdssfsa";
String text="windows平台，开发工具IDEA，语言scala，使用的是ansj v5.0.3，将ansj_library.properties放到resource root下面去，可以识别出library的路径，直接执行可以分词成功，但使用maven打包后，发现使用无法加载default.dic，ambiguity.dic, synonyms.dic等三个文件，导致后面的jar包始终无法正常运行，是我放的路径的问题";
/*List<String> tokenList = Twokenize.tokenizeRawTweetText(sent);
        for (int i=0;i<tokenList.toString().length();i++){
            System.out.println(tokenList.get(i));
        }*/
      /*  Result result = NlpAnalysis.parse(text);
        List<Term> terms = result.getTerms();
        for (int i = 0; i < terms.size(); i++) {
           *//* Term term = terms.get(i);
            String word = term.getName();
            if(i!=0){
                wordText.append(" ");
            }
            wordText.append(word);*//*
            Term tempterm = terms.get(i);
            String tempstr = tempterm.toString();
            String word = tempterm.getName();
            System.out.println(word);
        }

        System.out.println(isKorean.isKorean(sent));
        System.out.println(isEnglish.isEnglish(sent));*/

       Statistics statistics = new Statistics();
        statistics.StatisticsUtil();

       // CHNdataResult chNdataResult = new CHNdataResult();
        //chNdataResult.CHNdataResultUtil();

        //JPAdataResult jpAdataResult = new JPAdataResult();
        //jpAdataResult.JPAdataResultUtil();

        //KoreandataResult koreandataResult = new KoreandataResult();
        //koreandataResult.KoreandataResultUtil();

     // TWdataResult tWdataResult = new TWdataResult();
       //tWdataResult.TWdataResultUtil();

       // USAdataResult usAdataResult = new USAdataResult();
        //usAdataResult.USAdataResultUtil();



       //System.out.println("测试： "+statistics.StatisticsUtil1().getCHNQuantity());
    }
}
