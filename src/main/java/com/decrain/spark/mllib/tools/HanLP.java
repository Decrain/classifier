/*
 * <summary></summary>
 * <author>He Han</author>
 * <email>hankcs.cn@gmail.com</email>
 * <create-date>2014/10/17 19:02</create-date>
 *
 * <copyright file="HanLP.java" company="上海林原信息科技有限公司">
 * Copyright (c) 2003-2014, 上海林原信息科技有限公司. All Right Reserved, http://www.linrunsoft.com/
 * This source is subject to the LinrunSpace License. Please contact 上海林原信息科技有限公司 to get more information.
 * </copyright>
 */
package com.decrain.spark.mllib.tools;

import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.io.IIOAdapter;
import com.hankcs.hanlp.dependency.nnparser.NeuralNetworkDependencyParser;
import com.hankcs.hanlp.dictionary.ts.*;
import com.hankcs.hanlp.phrase.IPhraseExtractor;
import com.hankcs.hanlp.phrase.MutualInformationEntropyPhraseExtractor;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.Viterbi.ViterbiSegment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.summary.TextRankKeyword;
import com.hankcs.hanlp.summary.TextRankSentence;
import com.hankcs.hanlp.tokenizer.StandardTokenizer;
import com.hankcs.hanlp.utility.Predefine;
import com.hankcs.hanlp.utility.TextUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import static com.hankcs.hanlp.utility.Predefine.logger;

/**
 * HanLP: Han Language Processing <br>
 * 汉语言处理包 <br>
 * 常用接口工具类
 *
 * @author hankcs
 */
public class HanLP
{
    /**
     * 库的全局配置，既可以用代码修改，也可以通过hanlp.properties配置（按照 变量名=值 的形式）
     */
    public static final class Config
    {
        /**
         * 开发模式
         */
        public static boolean DEBUG = false;
        public static final  String PATH = "/Users/decrain/IdeaProjects/HanLP-master/";
       // public static final String CLASS_PATH = FileUtils.getClassPath();

         // 核心词典路径

        public static String CoreDictionaryPath = PATH+"data/dictionary/CoreNatureDictionary.txt";
        /**
         * 核心词典词性转移矩阵路径
         */
        public static String CoreDictionaryTransformMatrixDictionaryPath = PATH+"data/dictionary/CoreNatureDictionary.tr.txt";
        /**
         * 用户自定义词典路径
         */
        public static String CustomDictionaryPath[] = new String[]{PATH+"data/dictionary/custom/CustomDictionary.txt"};
        /**
         * 2元语法词典路径
         */
        public static String BiGramDictionaryPath = PATH+"data/dictionary/CoreNatureDictionary.ngram.txt";

        /**
         * 停用词词典路径
         */
        public static String CoreStopWordDictionaryPath = PATH+"data/dictionary/stopwords.txt";
        /**
         * 同义词词典路径
         */
        public static String CoreSynonymDictionaryDictionaryPath = PATH+"data/dictionary/synonym/CoreSynonym.txt";
        /**
         * 人名词典路径
         */
        public static String PersonDictionaryPath = PATH+"data/dictionary/person/nr.txt";
        /**
         * 人名词典转移矩阵路径
         */
        public static String PersonDictionaryTrPath = PATH+"data/dictionary/person/nr.tr.txt";
        /**
         * 地名词典路径
         */
        public static String PlaceDictionaryPath = PATH+"data/dictionary/place/ns.txt";
        /**
         * 地名词典转移矩阵路径
         */
        public static String PlaceDictionaryTrPath = PATH+"data/dictionary/place/ns.tr.txt";
        /**
         * 地名词典路径
         */
        public static String OrganizationDictionaryPath = PATH+"data/dictionary/organization/nt.txt";
        /**
         * 地名词典转移矩阵路径
         */
        public static String OrganizationDictionaryTrPath = PATH+"data/dictionary/organization/nt.tr.txt";
        /**
         * 简繁转换词典根目录
         */
        public static String tcDictionaryRoot = PATH+"data/dictionary/tc/";
        /**
         * 声母韵母语调词典
         */
        public static String SYTDictionaryPath = PATH+"data/dictionary/pinyin/SYTDictionary.txt";

        /**
         * 拼音词典路径
         */
        public static String PinyinDictionaryPath = PATH+"data/dictionary/pinyin/pinyin.txt";

        /**
         * 音译人名词典
         */
        public static String TranslatedPersonDictionaryPath = PATH+"data/dictionary/person/nrf.txt";

        /**
         * 日本人名词典路径
         */
        public static String JapanesePersonDictionaryPath = PATH+"data/dictionary/person/nrj.txt";

        /**
         * 字符类型对应表
         */
        public static String CharTypePath = PATH+"data/dictionary/other/CharType.bin";

        /**
         * 字符正规化表（全角转半角，繁体转简体）
         */
        public static String CharTablePath = PATH+"data/dictionary/other/CharTable.txt";

        /**
         * 词-词性-依存关系模型
         */
        public static String WordNatureModelPath = PATH+"data/model/dependency/WordNature.txt";

        /**
         * 最大熵-依存关系模型
         */
        public static String MaxEntModelPath = PATH+"data/model/dependency/MaxEntModel.txt";
        /**
         * 神经网络依存模型路径
         */
        public static String NNParserModelPath = PATH+"data/model/dependency/NNParserModel.txt";
        /**
         * CRF分词模型
         */
        public static String CRFSegmentModelPath = PATH+"data/model/segment/CRFSegmentModel.txt";
        /**
         * HMM分词模型
         */
        public static String HMMSegmentModelPath = PATH+"data/model/segment/HMMSegmentModel.bin";
        /**
         * CRF依存模型
         */
        public static String CRFDependencyModelPath = PATH+"data/model/dependency/CRFDependencyModelMini.txt";
        /**
         * 分词结果是否展示词性
         */
        public static boolean ShowTermNature = true;
        /**
         * 是否执行字符正规化（繁体->简体，全角->半角，大写->小写），切换配置后必须删CustomDictionary.txt.bin缓存
         */
        public static boolean Normalization = false;
        /**
         * IO适配器（默认null，表示从本地文件系统读取），实现com.hankcs.hanlp.corpus.io.IIOAdapter接口
         * 以在不同的平台（Hadoop、Redis等）上运行HanLP
         */
        public static IIOAdapter IOAdapter;

        static
        {
            // 自动读取配置
            Properties p = new Properties();
            try
            {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                if (loader == null)
                {  // IKVM (v.0.44.0.5) doesn't set context classloader
                    loader = com.hankcs.hanlp.HanLP.Config.class.getClassLoader();
                }
                p.load(new InputStreamReader(Predefine.HANLP_PROPERTIES_PATH == null ?
                        loader.getResourceAsStream("hanlp.properties") :
                        new FileInputStream(Predefine.HANLP_PROPERTIES_PATH)
                        , "UTF-8"));
                String root = p.getProperty("root", "").replaceAll("\\\\", "/");
                if (root.length() > 0 && !root.endsWith("/")) root += "/";
                CoreDictionaryPath = root + p.getProperty("CoreDictionaryPath", CoreDictionaryPath);
                CoreDictionaryTransformMatrixDictionaryPath = root + p.getProperty("CoreDictionaryTransformMatrixDictionaryPath", CoreDictionaryTransformMatrixDictionaryPath);
                BiGramDictionaryPath = root + p.getProperty("BiGramDictionaryPath", BiGramDictionaryPath);
                CoreStopWordDictionaryPath = root + p.getProperty("CoreStopWordDictionaryPath", CoreStopWordDictionaryPath);
                CoreSynonymDictionaryDictionaryPath = root + p.getProperty("CoreSynonymDictionaryDictionaryPath", CoreSynonymDictionaryDictionaryPath);
                PersonDictionaryPath = root + p.getProperty("PersonDictionaryPath", PersonDictionaryPath);
                PersonDictionaryTrPath = root + p.getProperty("PersonDictionaryTrPath", PersonDictionaryTrPath);
                String[] pathArray = p.getProperty("CustomDictionaryPath", "data/dictionary/custom/CustomDictionary.txt").split(";");
                String prePath = root;
                for (int i = 0; i < pathArray.length; ++i)
                {
                    if (pathArray[i].startsWith(" "))
                    {
                        pathArray[i] = prePath + pathArray[i].trim();
                    }
                    else
                    {
                        pathArray[i] = root + pathArray[i];
                        int lastSplash = pathArray[i].lastIndexOf('/');
                        if (lastSplash != -1)
                        {
                            prePath = pathArray[i].substring(0, lastSplash + 1);
                        }
                    }
                }
                CustomDictionaryPath = pathArray;
                tcDictionaryRoot = root + p.getProperty("tcDictionaryRoot", tcDictionaryRoot);
                if (!tcDictionaryRoot.endsWith("/")) tcDictionaryRoot += '/';
                SYTDictionaryPath = root + p.getProperty("SYTDictionaryPath", SYTDictionaryPath);
                PinyinDictionaryPath = root + p.getProperty("PinyinDictionaryPath", PinyinDictionaryPath);
                TranslatedPersonDictionaryPath = root + p.getProperty("TranslatedPersonDictionaryPath", TranslatedPersonDictionaryPath);
                JapanesePersonDictionaryPath = root + p.getProperty("JapanesePersonDictionaryPath", JapanesePersonDictionaryPath);
                PlaceDictionaryPath = root + p.getProperty("PlaceDictionaryPath", PlaceDictionaryPath);
                PlaceDictionaryTrPath = root + p.getProperty("PlaceDictionaryTrPath", PlaceDictionaryTrPath);
                OrganizationDictionaryPath = root + p.getProperty("OrganizationDictionaryPath", OrganizationDictionaryPath);
                OrganizationDictionaryTrPath = root + p.getProperty("OrganizationDictionaryTrPath", OrganizationDictionaryTrPath);
                CharTypePath = root + p.getProperty("CharTypePath", CharTypePath);
                CharTablePath = root + p.getProperty("CharTablePath", CharTablePath);
                WordNatureModelPath = root + p.getProperty("WordNatureModelPath", WordNatureModelPath);
                MaxEntModelPath = root + p.getProperty("MaxEntModelPath", MaxEntModelPath);
                NNParserModelPath = root + p.getProperty("NNParserModelPath", NNParserModelPath);
                CRFSegmentModelPath = root + p.getProperty("CRFSegmentModelPath", CRFSegmentModelPath);
                CRFDependencyModelPath = root + p.getProperty("CRFDependencyModelPath", CRFDependencyModelPath);
                HMMSegmentModelPath = root + p.getProperty("HMMSegmentModelPath", HMMSegmentModelPath);
                ShowTermNature = "true".equals(p.getProperty("ShowTermNature", "true"));
                Normalization = "true".equals(p.getProperty("Normalization", "false"));
                String ioAdapterClassName = p.getProperty("IOAdapter");
                if (ioAdapterClassName != null)
                {
                    try
                    {
                        Class<?> clazz = Class.forName(ioAdapterClassName);
                        Constructor<?> ctor = clazz.getConstructor();
                        Object instance  = ctor.newInstance();
                        if (instance != null) IOAdapter = (IIOAdapter) instance;
                    }
                    catch (ClassNotFoundException e)
                    {
                        logger.warning(String.format("找不到IO适配器类： %s ，请检查第三方插件jar包", ioAdapterClassName));
                    }
                    catch (NoSuchMethodException e)
                    {
                        logger.warning(String.format("工厂类[%s]没有默认构造方法，不符合要求", ioAdapterClassName));
                    }
                    catch (SecurityException e)
                    {
                        logger.warning(String.format("工厂类[%s]默认构造方法无法访问，不符合要求", ioAdapterClassName));
                    }
                    catch (Exception e)
                    {
                        logger.warning(String.format("工厂类[%s]构造失败：%s\n", ioAdapterClassName, TextUtility.exceptionToString(e)));
                    }
                }
            }
            catch (Exception e)
            {
                StringBuilder sbInfo = new StringBuilder("========Tips========\n请将hanlp.properties放在下列目录：\n"); // 打印一些友好的tips
                String classPath = (String) System.getProperties().get("java.class.path");
                if (classPath != null)
                {
                    for (String path : classPath.split(File.pathSeparator))
                    {
                        if (new File(path).isDirectory())
                        {
                            sbInfo.append(path).append('\n');
                        }
                    }
                }
                sbInfo.append("Web项目则请放到下列目录：\n" +
                                      "Webapp/WEB-INF/lib\n" +
                                      "Webapp/WEB-INF/classes\n" +
                                      "Appserver/lib\n" +
                                      "JRE/lib\n");
                sbInfo.append("并且编辑root=PARENT/path/to/your/data\n");
                sbInfo.append("现在HanLP将尝试从").append(System.getProperties().get("user.dir")).append("读取data……");
                logger.severe("没有找到hanlp.properties，可能会导致找不到data\n" + sbInfo);
            }
        }

        /**
         * 开启调试模式(会降低性能)
         */
        public static void enableDebug()
        {
            enableDebug(true);
        }

        /**
         * 开启调试模式(会降低性能)
         * @param enable
         */
        public static void enableDebug(boolean enable)
        {
            DEBUG = enable;
            if (DEBUG)
            {
                logger.setLevel(Level.ALL);
            }
            else
            {
                logger.setLevel(Level.OFF);
            }
        }
    }

    /**
     * 工具类，不需要生成实例
     */
    private HanLP() {}



    /**
     * 简转繁
     *
     * @param simplifiedChineseString 简体中文
     * @return 繁体中文
     */
    public static String convertToTraditionalChinese(String simplifiedChineseString)
    {
        return SimplifiedChineseDictionary.convertToTraditionalChinese(simplifiedChineseString.toCharArray());
    }

    /**
     * 简转繁,是{@link com.hankcs.hanlp.HanLP#convertToTraditionalChinese(String)}的简称
     * @param s 简体中文
     * @return 繁体中文(大陆标准)
     */
    public static String s2t(String s)
    {
        return com.hankcs.hanlp.HanLP.convertToTraditionalChinese(s);
    }


    /**
     * 簡體到臺灣正體
     * @param s 簡體
     * @return 臺灣正體
     */
    public static String s2tw(String s)
    {
        return SimplifiedToTaiwanChineseDictionary.convertToTraditionalTaiwanChinese(s);
    }







    /**
     * 分词
     *
     * @param text 文本
     * @return 切分后的单词
     */
    public static List<Term> segment(String text)
    {
        return StandardTokenizer.segment(text.toCharArray());
    }

    /**
     * 创建一个分词器<br>
     * 这是一个工厂方法<br>
     * 与直接new一个分词器相比，使用本方法的好处是，以后HanLP升级了，总能用上最合适的分词器
     * @return 一个分词器
     */
    public static Segment newSegment()
    {
        return new ViterbiSegment();   // Viterbi分词器是目前效率和效果的最佳平衡
    }

    /**
     * 依存文法分析
     * @param sentence 待分析的句子
     * @return CoNLL格式的依存关系树
     */
    public static CoNLLSentence parseDependency(String sentence)
    {
        return NeuralNetworkDependencyParser.compute(sentence);
    }

    /**
     * 提取短语
     * @param text 文本
     * @param size 需要多少个短语
     * @return 一个短语列表，大小 <= size
     */
    public static List<String> extractPhrase(String text, int size)
    {
        IPhraseExtractor extractor = new MutualInformationEntropyPhraseExtractor();
        return extractor.extractPhrase(text, size);
    }

    /**
     * 提取关键词
     * @param document 文档内容
     * @param size 希望提取几个关键词
     * @return 一个列表
     */
    public static List<String> extractKeyword(String document, int size)
    {
        return TextRankKeyword.getKeywordList(document, size);
    }

    /**
     * 自动摘要
     * 分割目标文档时的默认句子分割符为，,。:：“”？?！!；;
     * @param document 目标文档
     * @param size 需要的关键句的个数
     * @return 关键句列表
     */
    public static List<String> extractSummary(String document, int size)
    {
        return TextRankSentence.getTopSentenceList(document, size);
    }

    /**
     * 自动摘要
     * 分割目标文档时的默认句子分割符为，,。:：“”？?！!；;
     * @param document 目标文档
     * @param max_length 需要摘要的长度
     * @return 摘要文本
     */
    public static String getSummary(String document, int max_length)
    {
        // Parameter size in this method refers to the string length of the summary required;
        // The actual length of the summary generated may be short than the required length, but never longer;
        return TextRankSentence.getSummary(document, max_length);
    }




    
}
