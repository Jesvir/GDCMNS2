package com.example.gdcmns2;

import android.widget.ImageView;

public class WeatherData {
    private String Time;
    private String Highest_Temperature;

    public WeatherData(String time, String highest_Temperature) {
        Time = time;
        Highest_Temperature = highest_Temperature;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getHighest_Temperature() {
        return Highest_Temperature;
    }

    public void setHighest_Temperature(String highest_Temperature) {
        Highest_Temperature = highest_Temperature;
    }
}
