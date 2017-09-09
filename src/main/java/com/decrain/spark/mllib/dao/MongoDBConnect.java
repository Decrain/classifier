package com.decrain.spark.mllib.dao;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import org.apache.log4j.PropertyConfigurator;
import com.decrain.spark.mllib.dto.data;

/**
 * Created by Decrain on 2017/8/15.
 */
public class MongoDBConnect {

    public static void main(String args[]) {
        String log4jConfPath = "/Users/decrain/IdeaProjects/Classifier/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("TextClassifier");
        DBCollection collection = db.getCollection("data");
        DBCollection collection1 = db.getCollection("cur");
        String[] temp = new String[100];
        temp[0] = "自2008年首款 Andriod 系统推出以来，Google 一直提倡与App 开发者，设备制造商，及广大用户共享 Android 平台的预览版，并期望收到技术方面的反馈。今日， Google 发布首个 Android O 开发者预览版。 虽然作为早期预览版来说，很多新特性未正式加入其中，在稳定性与性能方面也需要更多改进，但这仅仅是一个开端在接下来的几个月中，Google 将发布开发者预览版的更新，更多详情将在今年 5 月的 Google I/O 大会上揭晓。同时，Google 也期待收到开发者关于新特性的反馈，也希望有更多开发者在这新的操作系统中对 App 进行测试。";         //temp = "晴朗的天空，我们常看到喷气式飞机在高空飞行时，机身后边会出现一条或数条长长的“白烟”。其实，这不是喷气式飞机喷出来的烟，而是飞机排出来的废气与周围环境空气混合后，水汽凝结而成的特殊云系，航空飞行界和航空气象学上称之为飞机尾迹";
        temp[1] = "2017年7月19日，东风雪铁龙旗下全新紧凑型SUV天逸 C5 AIRCROSS在神龙汽车成都工厂正式下线，搜狐汽车抢先与东风雪铁龙的车总和饶总进行了产品设计方面的沟通";
        temp[2] = "海军部队将在青岛至连云港以东海域组织大型军事活动。为确保安全，请各船舶特别注意：2017年7月27日0800至7月29日1800，不得进入下图所示临时禁航区，含91-95、101-105、111-115号、120-123号渔区，并听从外围警戒舰艇指挥，以策安全。——中国人民解放军91208部队";
        temp[3] = "sdgfklasdngksdngosdgn";
        temp[4] = "美联社说，美国与澳大利亚刚刚完成大型联合军事演习，双方派出36艘战舰、220架飞机及3.3万名士兵，期间遭到中国海军情报船监视。";
        temp[5] = "据考试院介绍，在刚刚结束的本科二批次录取中，372所院校在京参加二批录取，计划招生14351人，实际录取15124人，较计划增加773人。其中文史类招生计划4435人，实际录取4525人，较计划增加了90人；理工类招生计划9916人，实际录取10599人，较计划增加了683人";
        temp[6] = "按照全国考办的统一部署，2006年度全国会计专业技术资格考试定于5月20日、21日举行。准考证现已制作完毕，请县市区考生持身份证";
        temp[7] = "《魔兽世界》一直是喜欢怀旧们的老铁心目中最值得回忆的游戏之一，而有一些外国技术宅也致力于通过私服的方式还原老版本的《魔兽世界》。这不，有一群外国粉丝进行了一个菲米丝计划（Felmyst project）：一个打算让玩家重温“燃烧的远征”资料片的私服。该项目酝酿了四年之久，却在正式开服5小时之后，被暴雪无情关闭。";
        temp[8] = "意甲本周末拉齐奥与帕尔马之战为收官阶段表现较为突出的两支球队之间的较量，两队在最";
        temp[9] = "Can we make this quick? Roxanne Korrine and Andrew Barrett are having an incredibly horrendous public break- up on the quad. Again.";
        temp[10] = "半年後に銀行融資を受けたいのだが、今から何を準備すればいいのか？";
        temp[11] = "代わりと言っては何ですが、???私が、いささか、ご指南いたしましょう。退職を契機に茶道を始めた。滞りのない、優雅な仕草でグラスに水を注ぎ込んだ体壊したらどうするんだ。アンバランスな食事は、万病の元だぞ。";
        temp[12] = "本報訊 蘇崇琦、吳春輝報道：11月上旬，筆者在第20集團軍某團「戰神杯」千人百崗練兵比武場上看到：汽車連駕駛員李鵬在無千斤頂的情況下快速更換輪胎，用時不到5分鐘。教練班長沙仕萬和他所帶的徒弟謝家勝，包攬了瞄準手專業冠亞軍。該團團長王洪輝説，這是他們開展千人百崗大練兵帶來的可喜變化。";
        temp[13] = "表示的測試總時長爲1180秒，總里程:11.007km，圖中有Part1和PArt2兩部分";
        temp[14] = "買車時的售價雖然很重要，但之後養車的費用更是令許多人頭痛！養車成本主要可從擁車 3 年與 10 年兩個期間來看，大部分車款3年後都已過原廠保固，而 10 年則是一般車輛長期保養成本的分水嶺，也是民眾考慮換車的時候。美國《消費者報告 Consumer Reports》近日公布一項調查統計，比較平均保養成本。";
        temp[15] = "美國相當具權威的《消費者報告 Consumer Reports》調查 32 家車廠，同時針對車主所提供的保養、維修費用進行統計，分別列出 3 年車與 10 年車的每年平均養車成本，數據顯示為 2014 年與 2007 年，其中部分品牌因為停產緣故，沒有 3 年養車成本數據，但仍可以比較 10 年養車成本。";



       /*
        * 字符串读取
        *
        */


        data data = new data();
        for (int i = 0; i < 15; i++) {
            data.setText(temp[i]);
            Gson gson = new Gson();
            //转换成json字符串，再转换成DBObject对象
            DBObject dbObject = (DBObject) JSON.parse(gson.toJson(data));
            //插入数据库
            collection.insert(dbObject);
        }


        /*、
        *
        * txt文件存入mongodb数据库
        * */


      /* String[] dirNames = new File("/Users/decrain/IdeaProjects/Classifier/target/classes/data/ceshi").list();
        //System.out.println("路径： "+CLASS_PATH);
        if(null==dirNames || dirNames.length==0){
            new Exception("data is null").printStackTrace();
            return;
        }

        //DBCollection collection1 = db.getCollection("cur");

        DBCursor cur1 = collection1.find();  //获取cur数据库中的ID值
        int t=0;
        while (cur1.hasNext()) {
            t = Integer.parseInt(cur1.next().get("cur").toString());
        }

        for(String dirName:dirNames) {
            File f = null;
            f = new File("/Users/decrain/IdeaProjects/Classifier/target/classes/data/ceshi"+'/'+dirName);
            System.out.println(dirName);
            File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。
            List<File> list = new ArrayList<File>();
            for (File file : files) {
                list.add(file);
            }
            data data = new data();
            for (File file : files) {
                System.out.println("文件名字： " + file.getName());
                String text = FileUtils.readFile(file.getAbsolutePath());

                text = text.replaceAll("\t","");
                text = text.replaceAll("\n","");
                text = text.replaceAll("\r","");
                text = text.replaceAll("\\u0000","");
                data.setText(text.toString());
                data.setID(t);
                t += 1;
                Gson gson = new Gson();
                //转换成json字符串，再转换成DBObject对象
                DBObject dbObject = (DBObject) JSON.parse(gson.toJson(data));
                //插入数据库
                collection.insert(dbObject);
            }
        }*/
        /*
        * 字符串读取
        * */
        /*data data = new data();
        for (int i = 0; i < 3; i++) {
            data.setText(temp[i]);
            Gson gson = new Gson();
            //转换成json字符串，再转换成DBObject对象
            DBObject dbObject = (DBObject) JSON.parse(gson.toJson(data));
            //插入数据库
            collection.insert(dbObject);
        }*/



        /*DBCursor cur = collection1.find();
        while (cur.hasNext()) {
            System.out.println(cur.next().get("cur"));*/
       /*cur cur = new cur();
        cur.setCur(0);
        Gson gson=new Gson();
        //转换成json字符串，再转换成DBObject对象
        DBObject dbObject = (DBObject) JSON.parse(gson.toJson(cur));
        //插入数据库
        collection1.insert(dbObject);*/

            //db游标

/*

        DBCursor cur1 = collection1.find();
        while (cur1.hasNext()){
            DBCursor cur = collection.find(new BasicDBObject("_id", new BasicDBObject("$gte", cur1.next().get("cur"))));
            while (cur.hasNext()) {
                System.out.println(cur.next().get("text").toString()+'\n');
                System.out.println(cur.next().get("_id"));
                Object t = cur.next().get("_id");
                BasicDBObject doc = new BasicDBObject();
                BasicDBObject res = new BasicDBObject();
                res.put("cur",t);
                doc.put("$set",res);
                collection1.update(new BasicDBObject(),doc,false,true);


            }
        }
*/

            //更新数据
           /* BasicDBObject doc = new BasicDBObject();
            BasicDBObject res = new BasicDBObject();
            res.put("cur",t);
            doc.put("$set",res);
            collection1.update(new BasicDBObject(),doc,false,true);*/


            // data.setText();
       /* Gson gson=new Gson();
        //转换成json字符串，再转换成DBObject对象
        DBObject dbObject = (DBObject) JSON.parse(gson.toJson(data));
        //插入数据库
        collection.insert(dbObject);
        BasicDBObject document = new BasicDBObject();
        document.put("id", 1001);
        document.put("msg", "hello world mongoDB in Java");
        //将新建立的document保存到collection中去
        collection.insert(document);
        // 创建要查询的document
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", 1001);
        // 使用collection的find方法查找document
        DBCursor cursor = collection.find(searchQuery);
        //循环输出结果
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        System.out.println("Done");*/
        }
}




