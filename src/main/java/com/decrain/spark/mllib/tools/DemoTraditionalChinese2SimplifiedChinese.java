
package com.decrain.spark.mllib.tools;

import com.decrain.spark.mllib.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

;

/**
 * 将简繁转换做到极致
 *
 * @author hankcs
 */
public class DemoTraditionalChinese2SimplifiedChinese
{
    public static final String CLASS_PATH = FileUtils.getClassPath();
    public static final String NEWS_DATA_PATH = CLASS_PATH+"/s2t/Simplified";
    public static final String OUT_PUT = CLASS_PATH+"/s2t/output";
    public static void main(String[] args) throws IOException {


        System.out.println("路径： "+CLASS_PATH);

        String[] dirNames = new File(NEWS_DATA_PATH).list();

        if(null==dirNames || dirNames.length==0){

            new Exception("data is null").printStackTrace();
            return;
        }

        for(String dirName:dirNames) {

            File f = null;
            f = new File(NEWS_DATA_PATH+'/'+dirName);

            System.out.println(dirName);
            File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。
            List<File> list = new ArrayList<File>();
            for (File file : files) {
                list.add(file);
            }
            String dirName1 = "台湾" + dirName;
            //File fileT = new File(file.getName());
            FileUtils.createDir(OUT_PUT+'/'+dirName);
            for (File file : files) {

                System.out.println("文件名字： " + file.getName());

                String text = FileUtils.readFile(file.getAbsolutePath());


                text = text.replaceAll("\t","");
                text = text.replaceAll(" ","");
                text = text.replaceAll("\\u0000","");


                //System.out.println(HanLP.convertToTraditionalChinese(text));
                String fileName = dirName1 + '/' + file.getName();
                //System.out.println("文件名字1： " + fileName);
               // System.out.println("内容： " + text);
                FileUtils.createFile(fileName);

                FileUtils.writer(fileName, HanLP.convertToTraditionalChinese(text));
            }
        }
        /*for (File file :fileNames) {
                //System.out.println("文件名字： "+fileNames);
                //System.out.println("文件路径："+fileNames.get(i).getName());

                String text = FileUtils.readFile(file.getAbsolutePath());
                System.out.println(text);
                System.out.println(HanLP.convertToTraditionalChinese(text));
            }*/

       // System.out.println(HanLP.convertToTraditionalChinese("“以后等你当上皇后，就能买草莓庆祝了”。发现一根白头发"));
        //System.out.println(HanLP.convertToSimplifiedChinese("憑藉筆記簿型電腦寫程式HanLP"));
        // 简体转台湾繁体
        System.out.println(HanLP.s2tw("程序员"));

    }
}
