package com.example.gdcmns2;

public class TempValue {

    float Highest_Temperature;//y value
    float Lowest_Temperature;
    long Time;//x value

    public  TempValue(){

    }

    public TempValue(float highest_Temperature,float lowest_Temperature, long time) {
        Highest_Temperature = highest_Temperature;
        Lowest_Temperature = lowest_Temperature;
        Time = time;
    }

    public float getHighest_Temperature() {
        return Highest_Temperature;
    }

    public float getLowest_Temperature() {
        return Lowest_Temperature;
    }

    public long getTime() {
        return Time;
    }

}
