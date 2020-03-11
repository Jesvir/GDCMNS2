package com.example.gdcmns2.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.R;

public class WeatherHolder extends RecyclerView.ViewHolder {

    public TextView _temp,_time,_weather;
    public ImageView _image;
    public WeatherHolder(@NonNull View itemView) {
        super(itemView);

        _time = itemView.findViewById(R.id.tme);
        _weather = itemView.findViewById(R.id.weatherText);
        _temp = itemView.findViewById(R.id.tempWth);
        _image = itemView.findViewById(R.id.weather_image);
    }
}
