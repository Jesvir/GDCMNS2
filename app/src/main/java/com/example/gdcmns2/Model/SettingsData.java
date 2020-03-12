package com.example.gdcmns2.Model;

import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class SettingsData {
    private String Option;
    private String Name;
    private String Power;
    private String Light;
    private MaterialButton On;
    private MaterialButton Off;
    private MaterialButton Manual;
    private MaterialButton Automatic;
    private MaterialButton Reboot;
    private MaterialButton Shutdown;
    private MaterialButtonToggleGroup ButtonOptions;
    private MaterialButtonToggleGroup ButtonLights;


    public SettingsData(){

    }

    public SettingsData(String option, String name, String power, String light, MaterialButton on, MaterialButton off, MaterialButton manual, MaterialButton automatic, MaterialButton reboot, MaterialButton shutdown,MaterialButtonToggleGroup buttonOptions, MaterialButtonToggleGroup buttonLights) {
        Option = option;
        Name = name;
        Power = power;
        Light = light;
        On = on;
        Off = off;
        Manual = manual;
        Automatic = automatic;
        Reboot = reboot;
        Shutdown = shutdown;
        ButtonOptions = buttonOptions;
        ButtonLights = buttonLights;
    }

    public String getOption() {
        return Option;
    }

    public void setOption(String option) {
        Option = option;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPower() {
        return Power;
    }

    public void setPower(String power) {
        Power = power;
    }

    public String getLight() {
        return Light;
    }

    public void setLight(String light) {
        Light = light;
    }

    public MaterialButton getOn() {
        return On;
    }

    public void setOn(MaterialButton on) {
        On = on;
    }

    public MaterialButton getOff() {
        return Off;
    }

    public void setOff(MaterialButton off) {
        Off = off;
    }

    public MaterialButton getManual() {
        return Manual;
    }

    public void setManual(MaterialButton manual) {
        Manual = manual;
    }

    public MaterialButton getAutomatic() {
        return Automatic;
    }

    public void setAutomatic(MaterialButton automatic) {
        Automatic = automatic;
    }

    public MaterialButton getReboot() {
        return Reboot;
    }

    public void setReboot(MaterialButton reboot) {
        Reboot = reboot;
    }

    public MaterialButton getShutdown() {
        return Shutdown;
    }

    public void setShutdown(MaterialButton shutdown) {
        Shutdown = shutdown;
    }

    public MaterialButtonToggleGroup getButtonOptions() { return ButtonOptions; }

    public void setButtonOptions(MaterialButtonToggleGroup buttonOptions) { ButtonOptions = buttonOptions; }

    public MaterialButtonToggleGroup getButtonLights() { return ButtonLights; }

    public void setButtonLights(MaterialButtonToggleGroup buttonLights) { ButtonLights = buttonLights; }
}
