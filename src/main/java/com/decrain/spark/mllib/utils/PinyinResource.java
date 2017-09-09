package com.decrain.spark.mllib.utils;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.decrain.spark.mllib.classifier.DataFactory.CLASS_PATH;

/**
 * Created by Decrain on 2017/8/2.
 */

/**
 * 资源文件加载类
 *
 * @author stuxuhai (dczxxuhai@gmail.com)
 */
public final class PinyinResource {

    public PinyinResource() {
    }
    public static final String DATA_TEST_PATH = CLASS_PATH+"/data/data-test.txt";

    protected static Reader newClassPathReader(String classpath) {
        InputStream is = PinyinResource.class.getResourceAsStream(classpath);
        try {
            return new InputStreamReader(is, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected static Reader newFileReader(String path) throws FileNotFoundException {
        try {
            return new InputStreamReader(new FileInputStream(path), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    protected static Map<String, String> getResource(Reader reader) {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        try {
            BufferedReader br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split("=");
                map.put(tokens[0], tokens[1]);
            }
            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return map;
    }

  /*  protected static Map<String, String> getPinyinResource() {
        return getResource(newClassPathReader("/data/pinyin.dict"));
    }

    protected static Map<String, String> getMutilPinyinResource() {
        return getResource(newClassPathReader("/data/mutil_pinyin.dict"));
    }
*/
    public static Map<String, String> getChineseResource() {
        return getResource(newClassPathReader("/dict/chinese.dict"));
    }
}