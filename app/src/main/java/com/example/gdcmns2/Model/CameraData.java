package com.example.gdcmns2.Model;

public class CameraData {
    String Camera_IP;
    String CameraName;

    public CameraData(){

    }

    public CameraData(String camera_IP, String cameraName) {
        Camera_IP = camera_IP;
        CameraName = cameraName;
    }

    public String getCamera_IP() {
        return Camera_IP;
    }

    public String getCameraName() {
        return CameraName;
    }
}
