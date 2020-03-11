package com.example.gdcmns2.Model;

import android.widget.ImageView;

public class ItemData {
        private Long Time;
        private String Weather_History;
        private float Highest_Temperature;
        private ImageView ImageLink;

        public ItemData(){

        }

    public ItemData(Long time, String weather_History, float highest_Temperature, ImageView imageLink) {
        Time = time;
        Weather_History = weather_History;
        Highest_Temperature = highest_Temperature;
        ImageLink = imageLink;
    }

    public Long getTime() {
        return Time;
    }

    public String getWeather_History() {
        return Weather_History;
    }


    public float getHighest_Temperature() {
        return Highest_Temperature;
    }

    public void setImageLink(ImageView imageLink) {
        ImageLink = imageLink;
    }

    public ImageView getImageLink() {
        return ImageLink;
    }
}