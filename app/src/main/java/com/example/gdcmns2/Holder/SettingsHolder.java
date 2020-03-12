package com.example.gdcmns2.Holder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class SettingsHolder extends RecyclerView.ViewHolder {
    public TextView _name;
    public TextView _light;
    public TextView _power;
    public TextView _option;
    public TextView _lightText;
    public MaterialButton _on;
    public MaterialButton _off;
    public MaterialButton _man;
    public MaterialButton _auto;
    public MaterialButton _reboot;
    public MaterialButton _shutdown;
    public MaterialButtonToggleGroup _buttonOption;
    public MaterialButtonToggleGroup _buttonLight;

    public SettingsHolder(@NonNull View itemView) {
        super(itemView);
        _name = (TextView)itemView.findViewById(R.id.set_streetlight_name);
        _light = (TextView)itemView.findViewById(R.id.set_light);
        _option = (TextView)itemView.findViewById(R.id.set_option);
        _power = (TextView)itemView.findViewById(R.id.set_power);
        _on = (MaterialButton)itemView.findViewById(R.id.set_on);
        _off = (MaterialButton)itemView.findViewById(R.id.set_off);
        _auto = (MaterialButton)itemView.findViewById(R.id.autox);
        _man = (MaterialButton)itemView.findViewById(R.id.manx);
        _reboot = (MaterialButton)itemView.findViewById(R.id.restart);
        _shutdown = (MaterialButton)itemView.findViewById(R.id.shutdown);
        _buttonOption = (MaterialButtonToggleGroup)itemView.findViewById(R.id.option);
        _buttonLight = (MaterialButtonToggleGroup)itemView.findViewById(R.id.light);
        _lightText = (TextView)itemView.findViewById(R.id.lightText);
    }
}
