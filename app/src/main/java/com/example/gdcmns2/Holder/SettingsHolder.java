package com.example.gdcmns2.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.R;

public class SettingsHolder extends RecyclerView.ViewHolder {
    public TextView _name;
    public TextView _light;
    public TextView _power;
    public TextView _option;
    public SettingsHolder(@NonNull View itemView) {
        super(itemView);
        _name = (TextView)itemView.findViewById(R.id.set_streetlight_name);
        _light = (TextView)itemView.findViewById(R.id.set_light);
        _option = (TextView)itemView.findViewById(R.id.set_option);
        _power = (TextView)itemView.findViewById(R.id.set_power);
    }
}
