package com.decrain.spark.mllib.dao;

import com.mongodb.*;

import java.text.DecimalFormat;

/**
 * Created by Decrain on 2017/8/16.
 */
public class JPAdataResult {
    public static void JPAdataResultUtil() {

        // StatisticsElement statistics = null;
        //连接数据库

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("TextClassifier");
        //DB db =  mongoClient.getDatabase("TextClassifier");

        DBCollection collection = db.getCollection("result");

        DBCollection collection1 = db.getCollection("JPAdataResult");


        //long AreaQuantity = 0;

        long LabelQuantity = 0;

        long ITQuantity = 0;

        long MilitaryQuantity = 0;

        long GameQuantity = 0;

        long CarQuantity = 0;

        long PEQuantity = 0;

        long CultureQuantity = 0;

        long EconomyQuantity = 0;

        long EducationQuantity = 0;

        long Otherlabel = 0;

        long CHNQuantity = 0;

        long TWQuantity = 0;

        long USAQuantity = 0;

        long KoreanQuantity = 0;

        long JPAQuantity = 0;

        long OtherArea = 0;

        long count = 1;

        DBCursor cur = collection.find();
       // count = collection.count() - 1;



        /*、
        * 取值
        * */

        while (cur.hasNext()) {

            String area = null;
            String label = null;
            DBObject temp = null;
            temp = cur.next();
            area = temp.get("area").toString();
            label = temp.get("label").toString();

            if (area.equals("日本")) {
                count += 1;
                if (label.equals("IT类")) {
                    ITQuantity += 1;
                } else if (label.equals("文化类")) {
                    CultureQuantity += 1;
                    //System.out.println(CultureQuantity);
                } else if (label.equals("体育类")) {
                    PEQuantity += 1;
                } else if (label.equals("军事类")) {
                    MilitaryQuantity += 1;
                } else if (label.equals("汽车类")) {
                    CarQuantity += 1;
                } else if (label.equals("财经类")) {
                    EconomyQuantity += 1;
                } else if (label.equals("教育类")) {
                    EducationQuantity += 1;
                } else if (label.equals("游戏类")) {
                    GameQuantity += 1;
                } else {
                    Otherlabel += 1;
                }
            }
            //System.out.println(label);
            /*
            * 可以放到上面if后面统计地区的标签总数
            * */

        }


       /*
       *
       * 更新数据
       * */

        BasicDBObject doc = new BasicDBObject();
        BasicDBObject res = new BasicDBObject();

        res.put("count", count);
        res.put("IT", ITQuantity);
        res.put("Military", MilitaryQuantity);
        res.put("Game", GameQuantity);
        res.put("Car", CarQuantity);
        res.put("PE", PEQuantity);
        res.put("Culture", CultureQuantity);
        res.put("Economy", EconomyQuantity);
        res.put("Education", EducationQuantity);
        //res.put("USA", USAQuantity);

        //res.put("JPA", JPAQuantity);
        //res.put("Korean", KoreanQuantity);
        //res.put("TW", TWQuantity);
        // res.put("OtherArea", OtherArea);
        res.put("OtherLabel", Otherlabel);

        doc.put("$set", res);
        collection1.update(new BasicDBObject(), doc, false, true);

       /*
       *
       * 数据结果输出
       * */
        DecimalFormat df = new DecimalFormat("0.0000");
        if(count ==0){
            count += 1;
        }
        System.out.println("==========================日本地区统计结果输出=========================");
        System.out.println("总数： "+count);
        System.out.println("==========================日本类别数统计结果输出=========================");
        System.out.println("军事类数据所占数量及比例： " + "数量: " + MilitaryQuantity + "  比例： " + df.format((double) 100 * MilitaryQuantity / count) + "%");
        System.out.println("IT类数据所占数量及比例： " + "数量: " + ITQuantity + "    比例：" + df.format((double) 100 * ITQuantity / count) + "%");
        System.out.println("教育类数据所占数量及比例： " + "数量: " + EducationQuantity + "   比例： " + df.format((double) 100 * EducationQuantity / count) + "%");
        System.out.println("游戏类数据所占数量及比例： " + "数量: " + GameQuantity + "  比例： " + df.format((double) 100 * GameQuantity / count) + "%");
        System.out.println("汽车类数据所占数量及比例： " + "数量: " + CarQuantity + "   比例： " + df.format((double) 100 * CarQuantity / count) + "%");
        System.out.println("体育类数据所占数量及比例： " + "数量: " + PEQuantity + "    比例： " + df.format((double) 100 * PEQuantity / count) + "%");
        System.out.println("财经类数据所占数量及比例： " + "数量: " + EconomyQuantity + "   比例： " + df.format((double) 100 * EconomyQuantity / count) + "%");
        System.out.println("文化类数据所占数量及比例： " + "数量: " + CultureQuantity + "   比例： " + df.format((double) 100 * CultureQuantity / count) + "%");
        System.out.println("其他类数据所占数量及比例： " + "数量: " + Otherlabel + "   比例： " + df.format((double) 100 * Otherlabel / count) + "%");


































/*
      StatisticsElement t = new StatisticsElement();
       t.setArea("日本");
       t.setCount(count);
        t.setCar(CarQuantity);
        t.setCulture(CultureQuantity);
        t.setEconomy(EconomyQuantity);
        t.setEducation(EducationQuantity);
        t.setGame(GameQuantity);
        t.setIT(ITQuantity);
        t.setMilitary(MilitaryQuantity);
        t.setPE(PEQuantity);
        t.setOtherLabel(Otherlabel);
        //t.setJPA(JPAQuantity);
        //t.setUSA(USAQuantity);
       // t.setKorean(KoreanQuantity);
       // t.setTW(TWQuantity);



       // BasicDBObject doc = new BasicDBObject();
        Gson gson=new Gson();
        //转换成json字符串，再转换成DBObject对象
       DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(t));
        //插入数据库
       collection1.insert(dbObject);*/

    }
}
