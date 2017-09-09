package com.decrain.spark.mllib.dto;

/**
 * Created by Decrain on 2017/8/16.
 */
public class StatisticsElement {


    String Area;

    long count;

    long IT;

    long Military;

    long Game;

    long Car;

    long PE;

    long Culture;

    long Economy;

    long Education;

    long OtherLabel;

    long political;

    public void setPolitical(long political) {
        this.political = political;
    }

    public long getPolitical() {

        return political;
    }

    public void setOtherLabel(long otherLabel) {
        OtherLabel = otherLabel;
    }

    public long getOtherLabel() {

        return OtherLabel;
    }

    public void setArea(String area) {
        Area = area;
    }



    public void setCount(long count) {
        this.count = count;
    }

    public void setIT(long IT) {
        this.IT = IT;
    }

    public void setMilitary(long military) {
        Military = military;
    }

    public void setGame(long game) {
        Game = game;
    }

    public void setCar(long car) {
        Car = car;
    }

    public void setPE(long PE) {
        this.PE = PE;
    }

    public void setCulture(long culture) {
        Culture = culture;
    }

    public void setEconomy(long economy) {
        Economy = economy;
    }

    public void setEducation(long education) {
        Education = education;
    }



    public long getCount() {
        return count;
    }

    public long getIT() {
        return IT;
    }

    public long getMilitary() {
        return Military;
    }

    public long getGame() {
        return Game;
    }

    public long getCar() {
        return Car;
    }

    public long getPE() {
        return PE;
    }

    public long getCulture() {
        return Culture;
    }

    public long getEconomy() {
        return Economy;
    }

    public long getEducation() {
        return Education;
    }

    public String getArea() {

        return Area;
    }

}
