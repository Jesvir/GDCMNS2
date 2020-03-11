package com.example.gdcmns2.Model;

public class SettingsData {
    private String Option;
    private String Name;
    private String Power;
    private String Light;

    public SettingsData(){

    }

    public SettingsData(String option, String name, String power, String light) {
        Option = option;
        Name = name;
        Power = power;
        Light = light;
    }
    public String getOption() {
        return Option;
    }

    public String getName() {
        return Name;
    }

    public String getPower() {
        return Power;
    }

    public String getLight() {
        return Light;
    }
}
