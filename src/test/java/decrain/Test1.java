package decrain;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

// import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;

public class Test1 {
    public static void main(String[] args) {
        /**
         * 创建一个StanfordCoreNLP object
         * tokenize(分词)、ssplit(断句)、 pos(词性标注)、lemma(词形还原)、
         * ner(命名实体识别)、parse(语法解析)、指代消解？同义词分辨？
         */

        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit");    // 七种Annotators
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);    // 依次处理

        String text = "This is a test.";               // 输入文本

        Annotation document = new Annotation(text);    // 利用text创建一个空的Annotation
        pipeline.annotate(document);                   // 对text执行所有的Annotators（七种）

        // 下面的sentences 中包含了所有分析结果，遍历即可获知结果。
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {

                String word = token.get(TextAnnotation.class);            // 获取分词

                System.out.println(word);
            }

        }

    }
}