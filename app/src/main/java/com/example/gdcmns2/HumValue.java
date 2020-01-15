package com.example.gdcmns2;

public class HumValue {


    float Highest_Humidity;//y value
    long Time;//x value

    public  HumValue(){

    }

    public HumValue(float highest_Humidity, long time) {
        Highest_Humidity = highest_Humidity;
        Time = time;
    }

    public float getHighest_Humidity() {
        return Highest_Humidity;
    }

    public long getTime() {
        return Time;
    }
}
