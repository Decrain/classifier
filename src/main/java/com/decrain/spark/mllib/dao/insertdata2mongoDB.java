package com.decrain.spark.mllib.dao;

import com.decrain.spark.mllib.dto.data;
import com.decrain.spark.mllib.utils.FileUtils;
import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Decrain on 2017/8/18.
 */
public class insertdata2mongoDB {
    public static void main(String args[]) {
        String log4jConfPath = "/Users/decrain/IdeaProjects/Classifier/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("TextClassifier");
        DBCollection collection = db.getCollection("data");
        DBCollection collection1 = db.getCollection("cur");

       /*
        * 字符串读取
        *
        */


       /* data data = new data();
        for (int i = 0; i < 15; i++) {
            data.setText(temp[i]);
            Gson gson = new Gson();
            //转换成json字符串，再转换成DBObject对象
            DBObject dbObject = (DBObject) JSON.parse(gson.toJson(data));
            //插入数据库
            collection.insert(dbObject);
        }
*/

        /*、
        *
        * txt文件存入mongodb数据库
        * */
        DBCursor cur1 = collection1.find();  //获取cur数据库中的ID值
        int t=0;
        while (cur1.hasNext()) {
            t = Integer.parseInt(cur1.next().get("cur").toString());
        }
        for(int i=0;i<50;i++){

        //t += 1;
        String[] dirNames = new File("/Users/decrain/IdeaProjects/Classifier/target/classes/data/NewsData").list();
        //System.out.println("路径： "+CLASS_PATH);
        if(null==dirNames || dirNames.length==0){
            new Exception("data is null").printStackTrace();
            return;
        }

        //DBCollection collection1 = db.getCollection("cur");



        for(String dirName:dirNames) {
            File f = null;
            f = new File("/Users/decrain/IdeaProjects/Classifier/target/classes/data/NewsData"+'/'+dirName);
            System.out.println(dirName);
            File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。
            List<File> list = new ArrayList<File>();
            for (File file : files) {
                list.add(file);
            }
            data data = new data();
            for (File file : files) {
                //System.out.println("文件名字： " + file.getName());
                String text = FileUtils.readFile(file.getAbsolutePath());
                text = text.replaceAll("\t","");
                text = text.replaceAll("\n","");
                text = text.replaceAll("\r","");
                text = text.replaceAll(" ","");
                text = text.replaceAll("","");
                text = text.replaceAll("\\u0000","");
               // System.out.println("文件内容： " + text);
                //System.out.println("yuyan： " + isEnglish.isEnglish(text));
                data.setText(text.toString());
                data.setID(t);
                System.out.println("ID: "+t);
                t += 1;

                Gson gson = new Gson();
                //转换成json字符串，再转换成DBObject对象
                DBObject dbObject = (DBObject) JSON.parse(gson.toJson(data));
                //插入数据库
                collection.insert(dbObject);
            }
        }
        }

    }
}
