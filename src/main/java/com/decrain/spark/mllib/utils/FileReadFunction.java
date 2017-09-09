package com.decrain.spark.mllib.utils;

import java.io.Serializable;

/**
 * Created by Decrain on 2017/8/16.
 */
public interface FileReadFunction extends Serializable {

    String readLine(String line);

}
