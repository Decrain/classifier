package com.decrain.spark.mllib.dao;

import com.mongodb.*;

import java.text.DecimalFormat;

/**
 * Created by Decrain on 2017/8/16.
 */
public class  Statistics  {

    public static void StatisticsUtil() {

       // StatisticsElement statistics = null;
        //连接数据库

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("TextClassifier");

        DBCollection collection = db.getCollection("result");

        DBCollection collection1 = db.getCollection("cur");


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

        long count = 0;

        DBCursor cur = collection.find();
        count = collection.count();



        /*、
        * 取值
        * */

        while (cur.hasNext()) {
            String area =null;
            String label = null;
            DBObject temp = null;
            temp = cur.next();
             area =temp.get("area").toString();
            label = temp.get("label").toString();

            if (area.equals("大陆")) {
                CHNQuantity += 1;
            } else if (area.equals("台湾")) {
                TWQuantity += 1;
            } else if (area.equals("美国")) {
                USAQuantity += 1;
            } else if (area.equals("日本")) {
                JPAQuantity += 1;
            } else if (area.equals("韩国")) {
                KoreanQuantity += 1;
            } else {
                OtherArea += 1;
            }
            //System.out.println(label);
            /*
            * 可以放到上面if后面统计地区的标签总数
            * */
            if(label.equals("IT类")){
                ITQuantity += 1;
            }
            else if(label.equals("文化类")){
                CultureQuantity += 1;
                //System.out.println(CultureQuantity);
            }
            else if(label.equals("体育类")){
                PEQuantity += 1;
            }
            else if(label.equals("军事类")){
                MilitaryQuantity += 1;
            }
            else if(label.equals("汽车类")){
                CarQuantity += 1;
            }
            else if(label.equals("财经类")){
                EconomyQuantity += 1;
            }
            else  if(label.equals("教育类")){
                EducationQuantity += 1;
            }
            else if(label.equals("游戏类")){
                GameQuantity += 1;
            }
            else{
                Otherlabel += 1;
            }
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
        res.put("USA", USAQuantity);
        res.put("CHN", CHNQuantity);
        res.put("JPA", JPAQuantity);
        res.put("Korean", KoreanQuantity);
        res.put("TW", TWQuantity);
        res.put("OtherArea", OtherArea);
        res.put("OtherLabel", Otherlabel);

        doc.put("$set", res);
       collection1.update(new BasicDBObject(), doc, false, true);

       /*
       *
       * 数据结果输出
       * */
        DecimalFormat df = new DecimalFormat("0.0000");
        System.out.println("总数： "+count);
        System.out.println("==========================地区统计结果输出=========================");

        System.out.println("大陆地区数据所占数量及比例： "+"数量： "+CHNQuantity+"   比例： "+df.format((double) 100*CHNQuantity/count)+"%");
        System.out.println("美国地区数据所占数量及比例： "+"数量： "+USAQuantity+"   比例： "+df.format((double) 100*USAQuantity/count)+"%");
        System.out.println("台湾地区数据所占数量及比例： "+"数量： "+TWQuantity+"    比例： "+df.format((double) 100*TWQuantity/count)+"%");
        System.out.println("日本地区数据所占数量及比例： "+"数量： "+JPAQuantity+"   比例： "+ df.format((double) 100*JPAQuantity/count)+"%");
        System.out.println("韩国地区数据所占数量及比例： "+"数量： "+KoreanQuantity+"    比例： "+df.format((double) 100*KoreanQuantity/count)+"%");
        System.out.println("其他地区数据所占数量及比例： "+"数量： "+OtherArea+"    比例： "+df.format((double) 100*OtherArea/count)+"%");
        System.out.println("\n");
        System.out.println("==========================类别数统计结果输出=========================");
        System.out.println("军事类所占数量及比例： "+"数量: " +MilitaryQuantity+"  比例： "+df.format((double) 100*MilitaryQuantity/count)+"%");
        System.out.println("IT类所占数量及比例： "+"数量: " +ITQuantity+"    比例："+df.format((double) 100*ITQuantity/count)+"%");
        System.out.println("教育类所占数量及比例： "+"数量: " +EducationQuantity+"   比例： "+df.format((double) 100*EducationQuantity/count)+"%");
        System.out.println("游戏类所占数量及比例： "+"数量: " +GameQuantity+"  比例： "+df.format((double) 100*GameQuantity/count)+"%");
        System.out.println("汽车类所占数量及比例： "+"数量: " +CarQuantity+"   比例： "+df.format((double) 100*CarQuantity/count)+"%");
        System.out.println("体育类所占数量及比例： "+"数量: " +PEQuantity+"    比例： "+df.format((double) 100*PEQuantity/count)+"%");
        System.out.println("财经类所占数量及比例： "+"数量: " +EconomyQuantity+"   比例： "+df.format((double) 100*EconomyQuantity/count)+"%");
        System.out.println("文化类所占数量及比例： "+"数量: " +CultureQuantity+"   比例： "+df.format((double) 100*CultureQuantity/count)+"%");

        CHNdataResult chNdataResult = new CHNdataResult();
        chNdataResult.CHNdataResultUtil();

        JPAdataResult jpAdataResult = new JPAdataResult();
        jpAdataResult.JPAdataResultUtil();

        KoreandataResult koreandataResult = new KoreandataResult();
        koreandataResult.KoreandataResultUtil();

        TWdataResult tWdataResult = new TWdataResult();
        tWdataResult.TWdataResultUtil();

         USAdataResult usAdataResult = new USAdataResult();
        usAdataResult.USAdataResultUtil();





























/*
       cur t = new cur();
        t.setCar(CarQuantity);
        t.setCulture(CultureQuantity);
        t.setEconomy(EconomyQuantity);
        t.setEducation(EducationQuantity);
        t.setGame(GameQuantity);
        t.setIT(ITQuantity);
        t.setMilitary(MilitaryQuantity);
        t.setPE(PEQuantity);
        t.setOtherLabel(Otherlabel);
        t.setJPA(JPAQuantity);
        t.setUSA(USAQuantity);
        t.setKorean(KoreanQuantity);
        t.setTW(TWQuantity);
        t.setCHN(CHNQuantity);
        t.setOtherArea(OtherArea);
        t.setCount(count);


       // BasicDBObject doc = new BasicDBObject();
        Gson gson=new Gson();
        //转换成json字符串，再转换成DBObject对象
       DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(t));
        //插入数据库
       collection1.insert(dbObject);
       /*




            //System.out.println(CultrueQuantity);
       /* *//*、
        * 地区统计
        * *//*
             System.out.println("测试： "+CHNQuantity);
            statistics.setTWQuantity(TWQuantity);
            statistics.setCHNQuantity(CHNQuantity);
            statistics.setJPAQuantity(JPAQuantity);
            statistics.setUSAQuantity(USAQuantity);
            statistics.setOtherArea(otherArea);

        *//*、
        * 类别统计
        * */

        /*
            statistics.setCarQuantity(CarQuantity);
            statistics.setCultrueQuantity(CultrueQuantity);
            statistics.setEconomyQuantity(EconomyQuantity);
            statistics.setEducationQuantity(EducationQuantity);
            statistics.setGameQuantity(GameQuantity);
            statistics.setLabelQuantity(Otherlabel);
            statistics.setMilitaryQuantity(MilitaryQuantity);
            statistics.setPEQuantity(PEQuantity);
            statistics.setITQuantity(ITQuantity);


            return statistics;*/
        }



}
