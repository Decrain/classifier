package com.decrain.spark.mllib.dto;

import org.ietf.jgss.Oid;

/**
 * Created by Decrain on 2017/8/15.
 */
public class user {

    String area;

    String label;

    String text;

    Oid _id;


    public String getArea() {
        return area;
    }

    public String getLabel() {
        return label;
    }

    public String getText() {
        return text;
    }

    public Oid get_id() {
        return _id;
    }



    public void setArea(String area) {
        this.area = area;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void set_id(Oid _id) {
        this._id = _id;
    }
}
