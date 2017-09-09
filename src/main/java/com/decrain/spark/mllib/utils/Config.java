package com.decrain.spark.mllib.utils;

import static com.decrain.spark.mllib.classifier.DataFactory.CLASS_PATH;

/**
 * Created by Decrain on 2017/7/26.
 */
public class Config {

    public static final String STOP_DICT_PATH = CLASS_PATH+"/dict/stopwords.dict";
    public static final String STOP_JAPAN_DICT_PATH = CLASS_PATH+"/dict/Japanstop.dict";
    public static final String STOP_TC_DICT_PATH = CLASS_PATH+"/dict/traditionalChinesestop.dict";
    public static final String STOP_Korean_DICT_PATH = CLASS_PATH+"/dict/Koreanstop.dict";
}
