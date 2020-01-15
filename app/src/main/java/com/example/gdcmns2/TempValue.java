package com.example.gdcmns2;

public class TempValue {

    float Highest_Temperature;//y value
    long Time;//x value

    public  TempValue(){

    }

    public TempValue(float highest_Temperature, long time) {
        Highest_Temperature = highest_Temperature;
        Time = time;
    }

    public float getHighest_Temperature() {
        return Highest_Temperature;
    }

    public long getTime() {
        return Time;
    }
}
