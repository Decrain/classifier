
package com.decrain.spark.mllib.classifier;

import com.alibaba.fastjson.JSON;
import com.decrain.spark.mllib.dao.Statistics;
import com.decrain.spark.mllib.dto.Result;
import com.decrain.spark.mllib.dto.user;
import com.decrain.spark.mllib.tools.*;
import com.decrain.spark.mllib.utils.AnsjUtils;
import com.decrain.spark.mllib.utils.FileUtils;
import com.google.gson.Gson;
import com.mongodb.*;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.IDFModel;
import org.apache.spark.ml.linalg.SparseVector;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.*;


/**
 * 3、the third step
 *
 */

public class NaiveBayesTest {

    private static HashingTF hashingTF;

    private static IDFModel idfModel;

    private static NaiveBayesModel model;

    private static Map<Integer,String> labels = new HashMap<>();

    private  static String temp;

    static {
        boolean error = false;
        if (!new File(DataFactory.DATA_TEST_PATH).exists()) {
            new Exception(DataFactory.DATA_TEST_PATH + " is not exists").printStackTrace();
            error = true;
        }
        if (!new File(DataFactory.MODEL_PATH).exists()) {
            new Exception(DataFactory.MODEL_PATH + " is not exists").printStackTrace();
            error = true;
        }
        if (!new File(DataFactory.TF_PATH).exists()) {
            new Exception(DataFactory.TF_PATH + " is not exists").printStackTrace();
            error = true;
        }
        if (!new File(DataFactory.IDF_PATH).exists()) {
            new Exception(DataFactory.IDF_PATH + " is not exists").printStackTrace();
            error = true;
        }
        if (!new File(DataFactory.LABEL_PATH).exists()) {
            new Exception(DataFactory.LABEL_PATH + " is not exists").printStackTrace();
            error = true;
        }
        if (error) {
            System.exit(0);
        }

        String labelsData = FileUtils.readFile(DataFactory.LABEL_PATH);

        labels = JSON.parseObject(labelsData,Map.class);


    }

    public static void main(String[] args) throws UnsupportedEncodingException {






        SparkSession spark = SparkSession.builder().appName("NaiveBayes").master("local")
                .config("spark.driver.memory", "1073741824")
                .config("spark.testing.memory", "10073741824")
                .getOrCreate();

        //load tf file
        hashingTF = HashingTF.load(DataFactory.TF_PATH);
        //load idf file
        idfModel = IDFModel.load(DataFactory.IDF_PATH);
        //load model
        model = NaiveBayesModel.load(spark.sparkContext(), DataFactory.MODEL_PATH);

        //batch test
       batchTestModel(spark, DataFactory.DATA_TEST_PATH);

        //test a single
        //temp = "自2008年首款 Andriod 系统推出以来，Google 一直提倡与App 开发者，设备制造商，及广大用户共享 Android 平台的预览版，并期望收到技术方面的反馈。今日， Google 发布首个 Android O 开发者预览版。 虽然作为早期预览版来说，很多新特性未正式加入其中，在稳定性与性能方面也需要更多改进，但这仅仅是一个开端在接下来的几个月中，Google 将发布开发者预览版的更新，更多详情将在今年 5 月的 Google I/O 大会上揭晓。同时，Google 也期待收到开发者关于新特性的反馈，也希望有更多开发者在这新的操作系统中对 App 进行测试。";         //temp = "晴朗的天空，我们常看到喷气式飞机在高空飞行时，机身后边会出现一条或数条长长的“白烟”。其实，这不是喷气式飞机喷出来的烟，而是飞机排出来的废气与周围环境空气混合后，水汽凝结而成的特殊云系，航空飞行界和航空气象学上称之为飞机尾迹";
        // temp = "2017年7月19日，东风雪铁龙旗下全新紧凑型SUV天逸 C5 AIRCROSS在神龙汽车成都工厂正式下线，搜狐汽车抢先与东风雪铁龙的车总和饶总进行了产品设计方面的沟通";
       //temp = "海军部队将在青岛至连云港以东海域组织大型军事活动。为确保安全，请各船舶特别注意：2017年7月27日0800至7月29日1800，不得进入下图所示临时禁航区，含91-95、101-105、111-115号、120-123号渔区，并听从外围警戒舰艇指挥，以策安全。——中国人民解放军91208部队";
        //temp = "sdgfklasdngksdngosdgn";
       //temp = "美联社说，美国与澳大利亚刚刚完成大型联合军事演习，双方派出36艘战舰、220架飞机及3.3万名士兵，期间遭到中国海军情报船监视。";
        //temp ="据考试院介绍，在刚刚结束的本科二批次录取中，372所院校在京参加二批录取，计划招生14351人，实际录取15124人，较计划增加773人。其中文史类招生计划4435人，实际录取4525人，较计划增加了90人；理工类招生计划9916人，实际录取10599人，较计划增加了683人";
        //temp = "按照全国考办的统一部署，2006年度全国会计专业技术资格考试定于5月20日、21日举行。准考证现已制作完毕，请县市区考生持身份证";
       //temp="《魔兽世界》一直是喜欢怀旧们的老铁心目中最值得回忆的游戏之一，而有一些外国技术宅也致力于通过私服的方式还原老版本的《魔兽世界》。这不，有一群外国粉丝进行了一个菲米丝计划（Felmyst project）：一个打算让玩家重温“燃烧的远征”资料片的私服。该项目酝酿了四年之久，却在正式开服5小时之后，被暴雪无情关闭。";
       // temp = "意甲本周末拉齐奥与帕尔马之战为收官阶段表现较为突出的两支球队之间的较量，两队在最";
       //temp = "Can we make this quick? Roxanne Korrine and Andrew Barrett are having an incredibly horrendous public break- up on the quad. Again.";
        //temp="半年後に銀行融資を受けたいのだが、今から何を準備すればいいのか？";
       //temp = "代わりと言っては何ですが、???私が、いささか、ご指南いたしましょう。退職を契機に茶道を始めた。滞りのない、優雅な仕草でグラスに水を注ぎ込んだ体壊したらどうするんだ。アンバランスな食事は、万病の元だぞ。" ;
        //temp ="本報訊 蘇崇琦、吳春輝報道：11月上旬，筆者在第20集團軍某團「戰神杯」千人百崗練兵比武場上看到：汽車連駕駛員李鵬在無千斤頂的情況下快速更換輪胎，用時不到5分鐘。教練班長沙仕萬和他所帶的徒弟謝家勝，包攬了瞄準手專業冠亞軍。該團團長王洪輝説，這是他們開展千人百崗大練兵帶來的可喜變化。";
        //temp="表示的測試總時長爲1180秒，總里程:11.007km，圖中有Part1和PArt2兩部分";
        //temp="買車時的售價雖然很重要，但之後養車的費用更是令許多人頭痛！養車成本主要可從擁車 3 年與 10 年兩個期間來看，大部分車款3年後都已過原廠保固，而 10 年則是一般車輛長期保養成本的分水嶺，也是民眾考慮換車的時候。美國《消費者報告 Consumer Reports》近日公布一項調查統計，比較平均保養成本。";
        //temp="美國相當具權威的《消費者報告 Consumer Reports》調查 32 家車廠，同時針對車主所提供的保養、維修費用進行統計，分別列出 3 年車與 10 年車的每年平均養車成本，數據顯示為 2014 年與 2007 年，其中部分品牌因為停產緣故，沒有 3 年養車成本數據，但仍可以比較 10 年養車成本。";
        temp="總長下午在相關聯參主管陪同下，先後前往國防部聯合作戰指揮中心、第3作戰區后山營區、憲兵與後備指揮部等執行世大運維安反恐任務之各級指揮所，聽取任務簡報，了解部隊執行任務指管通聯，以及兵力運用等內容。李總長慰勉官兵辛勞，並要求幹部完善危機應處，使維安滴水不漏，守護民眾安全。";
            //temp ="nihao woshi yingceshiyingwen";
        //测试用例
        //testModel(spark, temp);

       List<String> wordList = new ArrayList<String>();
        wordList = testModel1(spark,temp);
       System.out.println("测试1： "+wordList.toString());
        System.out.println("测试2内容： "+wordList.get(0).toString());
        System.out.println("测试3地区： "+wordList.get(1).toString());
        System.out.println("测试4标签： "+wordList.get(2).toString());

       /*、
       * mongodb操作 读取数据并分类
       * */

       MongoClient mongoClient = new MongoClient("localhost", 27017);
        DB db = mongoClient.getDB("TextClassifier");
        DBCollection collectiondata =  db.getCollection("data");
        DBCollection collectioncur =  db.getCollection("cur");
        DBCollection collectionresult =  db.getCollection("result");


       /* MongoDatabase mongoDatabase = mongoClient.getDatabase("TextClassifier");
        MongoCollection<Document> collectiondata = mongoDatabase.getCollection("data");
        MongoCollection<Document> collectioncur = mongoDatabase.getCollection("cur");
        MongoCollection<Document> collectionresult = mongoDatabase.getCollection("result");
*/


        System.out.println("Connect to database successfully");
        DBCursor cur1 = collectioncur.find();
       /* FindIterable<Document> findIterable = collectioncur.find();
        MongoCursor<Document> cur1 = findIterable.iterator();*/
        int t=0;
        //int  Identifier;
        //t = Integer.parseInt(cur1.next().get("cur").toString());
        while (cur1.hasNext()) {
            t = Integer.parseInt(cur1.next().get("cur").toString());
        }
       // DBCursor cur = collectiondata.find().skip(t);

        //处理数据跳过已经处理的数据
        BasicDBObject condition = new BasicDBObject();
        condition.put("ID", new BasicDBObject("$gt", t));
        DBCursor cur = collectiondata.find(condition);

       /* FindIterable<Document> find = collectiondata.find().skip(t);
        MongoCursor<Document> cur = find.iterator();*/

       /* while (cur1.hasNext()) {
            t = Integer.parseInt(cur1.next().get("cur").toString());
        }*/
        user user= new user();
        List<String> wordList1 = new ArrayList<>();
        long startTime=System.currentTimeMillis();
        while (cur.hasNext()) {

            //testModel1(spark, cur.next().get("text").toString());

            wordList1 = testModel1(spark,cur.next().get("text").toString());
            user.setText(wordList1.get(0).toString());
            user.setArea(wordList1.get(1).toString());
            user.setLabel(wordList1.get(2).toString());
            Gson gson=new Gson();
            //转换成json字符串，再转换成DBObject对象
            DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(user));
            //插入数据库
            collectionresult.insert(dbObject);
            /*Document document = new Document("area", user.getArea()).
                    append("label", user.getLabel()).
                    append("text", user.getText());
            List<Document> documents = new ArrayList<Document>();
            documents.add(document);
            collectionresult.insertMany(documents);*/
            //System.out.println("文档插入成功");
        }
        long endTime=System.currentTimeMillis();
        long count = collectiondata.count() - 1;
        BasicDBObject doc = new BasicDBObject();
        BasicDBObject res = new BasicDBObject();
        res.put("cur", count);
        doc.put("$set", res);
        collectioncur.update(new BasicDBObject(), doc, false, true);
       // collectioncur.updateMany(Filters.eq("cur", t), new Document("$set",new Document("cur",count)));




        /*
        *
        * 统计模块result
        * */
        System.out.println("spark处理数据运行时间： "+(endTime-startTime)+"ms");
        Statistics statistics = new Statistics();
        statistics.StatisticsUtil();
       // System.out.println("测试： "+statistics.StatisticsUtil().getCHNQuantity());




        //collection.drop();
        //db游标
        /*DBCursor cur1 = collection1.find();

        while(cur1.hasNext()) {
            DBCursor cur = collection.find(new BasicDBObject("_id", new BasicDBObject("$gte", cur1.next().get("cur"))));

            while (cur.hasNext()) {
                testModel(spark, cur.next().get("text").toString());
                //记录游标位置，避免重复计算
                BasicDBObject doc = new BasicDBObject();
                BasicDBObject res = new BasicDBObject();
                res.put("cur", cur.next().get("_id"));
                doc.put("$set", res);
                collection1.update(new BasicDBObject(), doc, false, true);

            }
        }*/





 /*String[] dirNames = new File("/Users/decrain/DESKTOP/ceshi").list();
        //System.out.println("路径： "+CLASS_PATH);
        if(null==dirNames || dirNames.length==0){
            new Exception("data is null").printStackTrace();
            return;
        }
        for(String dirName:dirNames) {*/



         /*
         *
         *
         * 文件夹存入数据库并分类
         * */
         /*   File f = null;
            //f = new File("/Users/decrain/IdeaProjects/Classifier/target/classes/data/ceshi"+'/'+dirName);
            f = new File("/Users/decrain/IdeaProjects/Classifier/target/classes/data/ceshi/C39-Sports");
           // System.out.println("路径： "+dirName);
            File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。
            List<File> list = new ArrayList<File>();
            for (File file : files) {
                list.add(file);
            }
            for (File file : files) {
                System.out.println("文件名字： " + file.getName());
                String text = FileUtils.readFile(file.getAbsolutePath());
                //text = text.replaceAll("\n","");
                text = text.replaceAll("\t","");
                text = text.replaceAll(" ","");
                //text = text.replaceAll("。","");
                text = text.replaceAll("\\u0000","");
                //System.out.println(HanLP.convertToTraditionalChinese(text));
                //System.out.println("文件名字1： " + fileName);
                // System.out.println("内容： " + text);
              // System.out.println("文本内容: "+text.toString());
                testModel(spark, text.toString());
            }*/
        //}

       // System.exit(0);
    }


    public static void batchTestModel(SparkSession sparkSession, String testPath) {

        Dataset<Row> test = sparkSession.read().json(testPath);
        //word frequency count
        Dataset<Row> featurizedData = hashingTF.transform(test);
        //count tf-idf
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);

        List<Row> rowList = rescaledData.select("category", "features").javaRDD().collect();

        List<Result> dataResults = new ArrayList<>();
        for (Row row : rowList) {
            Double category = row.getAs("category");
            SparseVector sparseVector = row.getAs("features");
            Vector features = Vectors.dense(sparseVector.toArray());
            double predict = model.predict(features);
            dataResults.add(new Result(category, predict));
        }

        Integer successNum = 0;
        Integer errorNum = 0;

        for (Result result : dataResults) {
            if (result.isCorrect()) {
                successNum++;
            } else {
                errorNum++;
            }
        }

        DecimalFormat df = new DecimalFormat("######0.0000");
        Double result = (Double.valueOf(successNum) / Double.valueOf(dataResults.size())) * 100;
        System.out.println("result: "+result);

        System.out.println("batch test");
        System.out.println("=======================================================");
        System.out.println("Summary");
        System.out.println("-------------------------------------------------------");
        System.out.println(String.format("Correctly Classified Instances          :      %s\t   %s%%",successNum,df.format(result)));
        System.out.println(String.format("Incorrectly Classified Instances        :       %s\t    %s%%",errorNum,df.format(100-result)));
        System.out.println(String.format("Total Classified Instances              :      %s",dataResults.size()));
        System.out.println("===================================");

    }

    //not use this mothod
    public static void testModel(SparkSession sparkSession, String content) throws UnsupportedEncodingException {
        List<Row> data = null;

        if(isTranditionalChinese.isTraditionalChinese(content)){
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.TraditionalChineseParticipleText(content))
            );
            System.out.println("繁体");
        }
        else  if(isChinese.isChinese(content)){
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.participleText(content))
            );
        }
        else if(isKorean.isKorean(content)){

            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.KoreanParticipleText(content))
            );
            System.out.println("韩语");
        }

        else if(isJapanese.isJapanese(content)){
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.JapaneseParticipleText(content))
            );
            System.out.println("日语");
        }


        else if ((isEnglish.isEnglish(content)))
        {
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.EnglishParticipleText(content))
            );
            System.out.println("英文");
        }
        else {
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.participleText(content))
            );
            //System.out.println("简体");
        }



        StructType schema = new StructType(new StructField[]{
                new StructField("text", new ArrayType(DataTypes.StringType, false), false, Metadata.empty())
        });

        Dataset<Row> testData = sparkSession.createDataFrame(data, schema);
        //word frequency count
        Dataset<Row> transform = hashingTF.transform(testData);
        //count tf-idf
        Dataset<Row> rescaledData = idfModel.transform(transform);

        Row row =rescaledData.select("features").first();
        SparseVector sparseVector = row.getAs("features");
        Vector features = Vectors.dense(sparseVector.toArray());
        Double predict = model.predict(features);
 /*
        * 结果插入mongo数据库中
        *
        * */
        MongoClient mongoClient = new MongoClient("127.0.0.1", 27017);
        DB mongoDatabase = mongoClient.getDB("TextClassifier");
       // MongoDatabase mongoDatabase = mongoClient.getDatabase("TextClassifier");
        System.out.println("Connect to database successfully");
      //  MongoCollection<Document> collectionresult = mongoDatabase.getCollection("result");
        DBCollection collectionresult =  mongoDatabase.getCollection("result");
        user user = new user();
        user.setText(content);
        user.setLabel(labels.get(predict.intValue()).toString().substring(labels.get(predict.intValue()).length()-3));
        user.setArea(labels.get(predict.intValue()).toString().substring(0,2));
       Gson gson=new Gson();
        //转换成json字符串，再转换成DBObject对象
        DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(gson.toJson(user));
        //插入数据库
        collectionresult.insert(dbObject);
       /* Document document = new Document("area", user.getArea()).
                append("label", user.getLabel()).
                append("text", user.getText());
        List<Document> documents = new ArrayList<Document>();
        documents.add(document);
        collectionresult.insertMany(documents);
*/

        System.out.println("test a single");
        System.out.println("=======================================================");
        System.out.println("原文： "+content);
        System.out.println("Result");
        System.out.println("-------------------------------------------------------");
        System.out.println(labels.get(predict.intValue()));
        System.out.println("===================================");

    }

//use this method
    public static List<String> testModel1(SparkSession sparkSession, String content) throws UnsupportedEncodingException {
        List<Row> data = null;
        List<String> ResultList = new ArrayList<String>();
        if(isTranditionalChinese.isTraditionalChinese(content)){
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.TraditionalChineseParticipleText(content))
            );
            //System.out.println("繁体");
        }
        else if(isChinese.isChinese(content)){
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.participleText(content))
            );
        }
        else if(isKorean.isKorean(content)){

            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.KoreanParticipleText(content))
            );
            //System.out.println("韩语");
        }

        else if(isJapanese.isJapanese(content)){
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.JapaneseParticipleText(content))
            );
           // System.out.println("日语");
        }



        else if ((isEnglish.isEnglish(content)))
        {
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.EnglishParticipleText(content))
            );
            //System.out.println("英文");
        }
        else {
            data = Arrays.asList(
                    RowFactory.create(AnsjUtils.participleText(content))
            );
            //System.out.println("简体");
        }



        StructType schema = new StructType(new StructField[]{
                new StructField("text", new ArrayType(DataTypes.StringType, false), false, Metadata.empty())
        });

        Dataset<Row> testData = sparkSession.createDataFrame(data, schema);
        //word frequency count
        Dataset<Row> transform = hashingTF.transform(testData);
        //count tf-idf
        Dataset<Row> rescaledData = idfModel.transform(transform);

        Row row =rescaledData.select("features").first();
        SparseVector sparseVector = row.getAs("features");
        Vector features = Vectors.dense(sparseVector.toArray());
        Double predict = model.predict(features);

        //content
        ResultList.add(content);
        //area
        ResultList.add(labels.get(predict.intValue()).toString().substring(0,2));
        //label
        ResultList.add(labels.get(predict.intValue()).toString().substring(labels.get(predict.intValue()).length()-3));



        return ResultList;
    }


}

