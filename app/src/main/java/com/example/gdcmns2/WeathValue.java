package com.example.gdcmns2;

public class WeathValue {

    float Weather_History;//y value
    long Time;//x value

    public  WeathValue(){

    }

    public WeathValue(float weather_History, long time) {
        Weather_History = weather_History;
        Time = time;
    }

    public float getWeather_History() {
        return Weather_History;
    }

    public long getTime() {
        return Time;
    }
}
