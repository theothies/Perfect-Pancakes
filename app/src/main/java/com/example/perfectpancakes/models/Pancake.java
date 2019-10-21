package com.example.perfectpancakes.models;

import android.os.AsyncTask;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="pancakes")
public class Pancake implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String title;
    private String date;
    private double diameter, thickness, batter, egg, milk, butter, flour, water;
    private int amount;

    public Pancake(AsyncTask<String, String, Pancake> execute){
    }

    public Pancake(String title, String date, double diameter, double thickness, double batter,
                   double egg, double milk, double butter, double flour, double water, int amount){
        this.title = title;
        this.date = date;
        this.diameter = diameter;
        this.thickness = thickness;
        this.batter = batter;
        this.egg = egg;
        this.milk = milk;
        this.butter = butter;
        this.flour = flour;
        this.water = water;
        this.amount = amount;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public String getDate(){
        return date;
    }

    public double getDiameter(){
        return diameter;
    }

    public double getThickness(){
        return thickness;
    }

    public double getBatter(){
        return batter;
    }

    public double getEgg(){
        return egg;
    }

    public double getMilk(){
        return milk;
    }

    public double getButter(){
        return butter;
    }

    public double getFlour(){
        return flour;
    }

    public double getWater(){
        return water;
    }

    public int getAmount(){
        return amount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}

