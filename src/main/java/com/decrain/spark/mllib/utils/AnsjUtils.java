package com.decrain.spark.mllib.utils;
/**
 * Created by Decrain on 2017/8/16.
 */

//import cmu.arktweetnlp.Twokenize;
import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import com.decrain.spark.mllib.tools.isEnglish;
import com.decrain.spark.mllib.tools.isKorean;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.tokenizer.TraditionalChineseTokenizer;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Tokenizer
 * Created by Coder-Ma on 2017/6/26.
 */
public class AnsjUtils {

    public static void main(String[] args) throws UnsupportedEncodingException {
     String text = "你好abasjhfahsf";
        System.out.println(JapaneseParticipleText(text));
        System.out.println("是韩语： "+ isKorean.isKorean(text));
        System.out.println("是英语： "+ isEnglish.isEnglish(text));
      //String text = "This is a test.";
        //String text = "半年後に銀行融資を受けたいのだが,，今から何を準備すればいいのか？";
        /*List<String> wordList = participle(text);
        for (String word : wordList) {
            System.out.println(word);

        }*/
       /* List<String> datat = participleText(text);
        for (String word : datat) {
            System.out.println(word);
        }*/

       //
        //System.out.println(isTranditionalChinese.isTraditionalChinese(text));
        //System.out.println(isChinese.isChinese(text));
        //System.out.println(TraditionalChineseParticipleText(text));
        /*if(isKorean.isKorean(text)){
            System.out.println(KoreanParticipleText(text));
        }
        else if(isJapanese.isJapanese(text)){
            System.out.println(isJapanese.isJapanese(text));
        }
        else if(isTranditionalChinese.isTraditionalChinese(text)){
            System.out.println(isTranditionalChinese.isTraditionalChinese(text));
        }
        else if(isChinese.isChinese(text)){
            System.out.println(isChinese.isChinese(text));
        }
        else {
            System.out.println(EnglishParticipleText(text));
        }*/

        //System.out.println(EnglishParticipleText(text));
        /*List<String> data = participleText(text);

        for (String word : data) {
            System.out.println(word);
        }*/
        /*if(isChinese.isChinese(text)){
            List<String> data9 = participleText(text);
           // System.out.println("是中文");
            for (String word : data9) {
                System.out.println(word);
            }
        }
        else if(isJapanese.isJapanese(text)){
            System.out.println("分割线============================1");
            List<String> data1 = JapaneseParticipleText(text);
            for (String word : data1) {
                System.out.println(word);
            }
        }
        else {
            System.out.println("分割线============================2");
            List<String> data0 = EnglishParticipleText(text);
            for (String word : data0) {
                System.out.println(word);
            }
        }*/


    }



    /*public static List<String> participle(String text) {
        List<String> wordList = new ArrayList<String>();
        Result result = NlpAnalysis.parse(text);
        List<Term> terms = result.getTerms();
        StopRecognition filter = new StopRecognition();
        for (Term term : terms) {
            String name = term.getName();
            wordList.add(name);
        }
        return wordList;
    }*/

    public static List<String> participleText(String text) {
        //StringBuffer wordText = new StringBuffer();
        List<String> wordList = new ArrayList<String>();
        Result result = NlpAnalysis.parse(text);
        List<Term> terms = result.getTerms();


        for (int i = 0; i < terms.size(); i++) {
           /* Term term = terms.get(i);
            String word = term.getName();
            if(i!=0){
                wordText.append(" ");
            }
            wordText.append(word);*/
            Term tempterm = terms.get(i);
            String tempstr = tempterm.toString();
            String word = tempterm.getName();
            if (tempstr.contains("/w")) {
                continue;
            }//去掉标点符号

            //wordList.add(word);
           if (tempstr.indexOf("/") != -1) {
               // String segword = tempstr.split("/");
                if (i != 0) {
                    //去停用词
                    if (!StopDict.StopDictUtil.isStopWord(word)) {
                        wordList.add(word);
                    }
                }
                else
                {continue;}
            }
            else{
               continue;
           }


        }

        return wordList;
    }
    public static  List<String> EnglishParticipleText(String text){
        /**
         * 创建一个StanfordCoreNLP object
         * tokenize(分词)、ssplit(断句)、 pos(词性标注)、lemma(词形还原)、
         * ner(命名实体识别)、parse(语法解析)、指代消解？同义词分辨？
         */
        text = text.replaceAll("\n","");
        text = text.replaceAll("\t","");
        text = text.replaceAll(" ","");
        text = text.replaceAll("。","");
        text = text.replaceAll("}","");
        text = text.replaceAll("]","");
        text = text.replaceAll(" ","");
        text = text.replaceAll("。","");

        List<String> wordList = new ArrayList<String>();
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit");    // 七种Annotators
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // 依次处理

       // String text = "This is a test.";               // 输入文本

        Annotation document = new Annotation(text);    // 利用text创建一个空的Annotation
        pipeline.annotate(document);                   // 对text执行所有的Annotators（七种）

        // 下面的sentences 中包含了所有分析结果，遍历即可获知结果。
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                String word = token.get(CoreAnnotations.TextAnnotation.class);// 获取分词

                if (!StopDict.StopDictUtil.isStopWord(word)) {
                    wordList.add(word);
                }

            }

        }
        return wordList;
    }
    public static  List<String> JapaneseParticipleText(String text){

        List<String> wordList = new ArrayList<String>();
        Tokenizer tokenizer = new Tokenizer() ;
        List<Token> tokens = tokenizer.tokenize(text);
        for (Token token : tokens) {
            if (!JapanStopDict.JapanStopUtil.isStopWord(token.getSurface())) {
                wordList.add(token.getSurface());
            }

        }
       return wordList;
    }

    public static List<String> TraditionalChineseParticipleText(String text){

        List<String> wordList = new ArrayList<>();
        HanLP.Config.ShowTermNature = false;
        List<com.hankcs.hanlp.seg.common.Term> termList  = TraditionalChineseTokenizer.segment(text);

        for(com.hankcs.hanlp.seg.common.Term term : termList){
            if(!TCStopDict.TCStopUtil.isStopWord(term.toString())){
                wordList.add(String.valueOf(term));
            }

        }

        return wordList;
    }
    /*public static List<String> KoreanParticipleText(String text){
        List<String> wordList = new ArrayList<String>();
        List<String> tokenList = Twokenize.tokenizeRawTweetText(text);
        for (String word:tokenList){
            if(!Koreanstop.KoreanstopUtil.isStopWord(word)){
                wordList.add(word);
            }
        }
        return wordList;
    }*/
   /* public static String participleText1(String text) {
        StringBuffer wordText = new StringBuffer();
        Result result = NlpAnalysis.parse(text);
        List<Term> terms = result.getTerms();

        for (int i = 0; i < terms.size(); i++) {
            Term term = terms.get(i);
            String word = term.getName();
            if(i!=0){
                wordText.append(" ");
            }
            wordText.append(word);
        }

        return wordText.toString();
    }*/
    public static  List<String> KoreanParticipleText(String text){
        /**
         * 创建一个StanfordCoreNLP object
         * tokenize(分词)、ssplit(断句)、 pos(词性标注)、lemma(词形还原)、
         * ner(命名实体识别)、parse(语法解析)、指代消解？同义词分辨？
         */

        List<String> wordList = new ArrayList<String>();
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit");    // 七种Annotators
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // 依次处理

        // String text = "This is a test.";               // 输入文本

        Annotation document = new Annotation(text);    // 利用text创建一个空的Annotation
        pipeline.annotate(document);                   // 对text执行所有的Annotators（七种）

        // 下面的sentences 中包含了所有分析结果，遍历即可获知结果。
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                String word = token.get(CoreAnnotations.TextAnnotation.class);// 获取分词

                if (!Koreanstop.KoreanstopUtil.isStopWord(word)) {
                    wordList.add(word);
                }

            }

        }
        return wordList;
    }
}