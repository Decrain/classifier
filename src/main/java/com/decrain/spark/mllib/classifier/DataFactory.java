package com.decrain.spark.mllib.classifier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.decrain.spark.mllib.tools.*;
import com.decrain.spark.mllib.utils.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 1、first step
 * data format
 *
 */
public class DataFactory {

    public static final String CLASS_PATH = FileUtils.getClassPath();

    public static final String NEWS_DATA_PATH = CLASS_PATH+"/data/NewsData";

    public static final String DATA_TRAIN_PATH = CLASS_PATH+"/data/data-train.txt";

    public static final String DATA_TEST_PATH = CLASS_PATH+"/data/data-test.txt";

    public static final String MODELS = CLASS_PATH+"/data/models";

    public static final String MODEL_PATH = CLASS_PATH+"/data/models/category-4";

    public static final String LABEL_PATH = CLASS_PATH+"/data/models/labels.txt";

    public static final String TF_PATH = CLASS_PATH+"/data/models/tf";

    public static final String IDF_PATH = CLASS_PATH+"/data/models/idf";

    public static final String DS_PATH = CLASS_PATH+"/data/NewsData/.DS_Store";




    public static void main(String[] args) throws IOException {
        File file = new File(MODELS);
        if(!file.exists()){
            file.mkdirs();
        }
        FileUtils.deleteFile(DATA_TRAIN_PATH);
        FileUtils.deleteFile(DATA_TEST_PATH);
        FileUtils.deleteFile(LABEL_PATH);
        FileUtils.deleteFile(DS_PATH);

        Double spiltRate = 0.9;//solit rate

        Map<Integer,String> labels = new HashMap<>();
        String[] dirNames = new File(NEWS_DATA_PATH).list();
        if(null==dirNames || dirNames.length==0){
            new Exception("data is null").printStackTrace();
            return;
        }
        Integer dirIndex = 0;
        for(String dirName:dirNames){
            dirIndex++;
            labels.put(dirIndex,dirName);

            String fileDirPath = String.format("%s/%s",NEWS_DATA_PATH,dirName);
            System.out.println("文件路径： "+fileDirPath);
            String[] fileNames = new File(fileDirPath).list();
            //System.out.println("文件名称： "+fileNames);
            //按照文件数量来分割的-splilt

            for(int i=0;i<fileNames.length;i++){
                String fileName = fileNames[i];
                String text = FileUtils.readFile(String.format("%s/%s",fileDirPath,fileName));
               // System.out.println("文件名称： "+text);
                text = text.replaceAll("\n","");
                text = text.replaceAll("\t","");
                text = text.replaceAll(" ","");
                text = text.replaceAll("。","");
                text = text.replaceAll("\\u0000","");

                if(StringUtils.isBlank(text)){
                    continue;
                }
                JSONObject data = new JSONObject();
                if(isTranditionalChinese.isTraditionalChinese(text)){
                    data.put("text", AnsjUtils.TraditionalChineseParticipleText(text)
                    );
                    //System.out.println(isTranditionalChinese.isTraditionalChinese(text));
                }
                else if(isChinese.isChinese(text)){
                    data.put("text", AnsjUtils.participleText(text)
                    );
                }
                else if(isKorean.isKorean(text)){
                data.put("text", AnsjUtils.KoreanParticipleText(text));
                //System.out.println(isKorean.isKorean(text));
                }
                else if(isJapanese.isJapanese(text)){
                    data.put("text", AnsjUtils.JapaneseParticipleText(text));
                    // System.out.println(isJapanese.isJapanese(text));
                }


                else if(isEnglish.isEnglish(text)){
                    data.put("text", AnsjUtils.EnglishParticipleText(text)
                    );
                }
                 else {
                    data.put("text", AnsjUtils.participleText(text)
                    );
                }



                data.put("category",Double.valueOf(dirIndex));
                int spilt = Double.valueOf(fileNames.length*spiltRate).intValue();
                if(i>spilt){
                    //test data
                    FileUtils.appendText(DATA_TEST_PATH,data.toJSONString()+"\n");
                }else{
                    //train data
                    FileUtils.appendText(DATA_TRAIN_PATH,data.toJSONString()+"\n");
                }
               //文档中80%为训练文本，20%为测试文本
               /*int spilt = (int) (text.length()*spiltRate);
                String text0 = text.substring(0,spilt);
                String text1 = text.substring(spilt);
                JSONObject data0 = new JSONObject();
                JSONObject data1 = new JSONObject();
                data0.put("text", AnsjUtils.participleText(text0));
                data0.put("category",Double.valueOf(dirIndex));

                data1.put("text", AnsjUtils.participleText(text1));
                data1.put("category",Double.valueOf(dirIndex));
               //test data
                FileUtils.appendText(DATA_TEST_PATH,data0.toJSONString()+"\n");
                //train data
                FileUtils.appendText(DATA_TRAIN_PATH,data1.toJSONString()+"\n");*/


            }

        }

        FileUtils.writer(LABEL_PATH, JSON.toJSONString(labels));//data labels

        System.out.println("Data processing successfully !");
        System.out.println("=======================================================");
        System.out.println("trainData:"+DATA_TRAIN_PATH);
        System.out.println("testData:"+DATA_TEST_PATH);
        System.out.println("labes:"+LABEL_PATH);
        System.out.println("=======================================================");

    }

}
