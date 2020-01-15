package com.example.gdcmns2.ui.tools;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gdcmns2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ToolsFragment extends Fragment {


    View toolsView;
    boolean isSuccess = false;
    Button On,Off;
    DatabaseReference ref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        toolsView = inflater.inflate(R.layout.fragment_tools, container, false);

        On = (Button)toolsView.findViewById(R.id.on);
        Off = (Button)toolsView.findViewById(R.id.off);
        On.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                On.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Off.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("StreetLight_1").child("Light");
                myRef.setValue("On");

            }
        });

        Off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                On.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("StreetLight_1").child("Light");
                myRef.setValue("Off");
            }
        });



        return toolsView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference();
        try {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status = dataSnapshot.child("StreetLight_1").child("Light").getValue().toString();
                    String value ="On";
                    if (status.equals(value))
                    {
                        On.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        Off.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    else
                    {
                        Off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        On.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    }
                    return;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}