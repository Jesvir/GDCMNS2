package com.example.gdcmns2.Holder;

import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.R;

public class CameraHolder extends RecyclerView.ViewHolder {

    public TextView _cameraLink;
    public TextView _cameraName;

    public CameraHolder(@NonNull View itemView) {
        super(itemView);
        _cameraName = itemView.findViewById(R.id.camera_name);
    }
}
