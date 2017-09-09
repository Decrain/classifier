package com.decrain.spark.mllib.tools;

import org.apache.spark.sql.SparkSession;

/**
 * Created by Decrain on 2017/8/10.
 */
public class test {
    public static void main(String agrs[]) throws Exception {
        SparkSession spark = SparkSession.builder().appName("NaiveBayes").master("local")
                .config("spark.driver.memory", "1073741824")
                .config("spark.testing.memory", "10073741824")
                .getOrCreate();
    }
}
