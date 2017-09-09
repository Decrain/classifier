package com.decrain.spark.mllib.dto;

/**
 * Created by Decrain on 2017/8/15.
 */
public class data {


    String text;

    int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {

        return ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
