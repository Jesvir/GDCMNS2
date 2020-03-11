package com.example.gdcmns2.ui.settings;

import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.Holder.CameraHolder;
import com.example.gdcmns2.Holder.SettingsHolder;
import com.example.gdcmns2.Model.CameraData;
import com.example.gdcmns2.Model.SettingsData;
import com.example.gdcmns2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SettingsFragment extends Fragment {


    View toolsView;
    boolean isSuccess = false;
    Button shutdown,reboot;
    DatabaseReference ref,ref2,ref3;
    TextView t1,light_,power_,option_,name_;
    FrameLayout frameLayout;

    RecyclerView recyclerView;
    FirebaseRecyclerOptions<SettingsData> options;
    FirebaseRecyclerAdapter<SettingsData, SettingsHolder> adapter;
    ArrayList<SettingsData> arrayList;

    MaterialButtonToggleGroup option,light;
    MaterialButton auto1,manual1,on,off;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        toolsView = inflater.inflate(R.layout.fragment_settings, container, false);
        light_ = (TextView)toolsView.findViewById(R.id.set_light);
        name_ = (TextView)toolsView.findViewById(R.id.set_streetlight_name);
        option_ = (TextView)toolsView.findViewById(R.id.set_option);
        power_ = (TextView)toolsView.findViewById(R.id.set_power);
        recyclerView = (RecyclerView)toolsView.findViewById(R.id.settings_recycler);
        recyclerView.setHasFixedSize(true);
        frameLayout = (FrameLayout)toolsView.findViewById(R.id.settings_frame);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<SettingsData>();
        ref3 = FirebaseDatabase.getInstance().getReference().child("Streetlights");

        options = new FirebaseRecyclerOptions.Builder<SettingsData>().setQuery(ref3, SettingsData.class).build();
        adapter = new FirebaseRecyclerAdapter<SettingsData, SettingsHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull SettingsHolder settingsHolder, int i, @NonNull SettingsData settingsData) {
                settingsHolder._name.setText(settingsData.getName());
            }

            @NonNull
            @Override
            public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SettingsHolder(LayoutInflater.from(getActivity()).inflate(R.layout.settings_data, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


        on = (MaterialButton) toolsView.findViewById(R.id.on);
        shutdown = (Button)toolsView.findViewById(R.id.shutdown);
        reboot = (Button)toolsView.findViewById(R.id.restart);
        off = (MaterialButton)toolsView.findViewById(R.id.off);
        t1 = (TextView) toolsView.findViewById(R.id.textView2);

        option = (MaterialButtonToggleGroup)toolsView.findViewById(R.id.option);
        light = (MaterialButtonToggleGroup)toolsView.findViewById(R.id.light);
        auto1 = (MaterialButton)toolsView.findViewById(R.id.autox);
        manual1 = (MaterialButton) toolsView.findViewById(R.id.manx);





        reboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure to Restart the Pi?");
                builder.setMessage("Click Yes to Restart");
                builder.setIcon(R.drawable.refresh_black);
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("StreetLight_1").child("Power");
                        myRef.setValue("Reboot");
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure to Shutdown the Pi?");
                builder.setMessage("Click Yes to Shutdown");
                builder.setIcon(R.drawable.shutdown);
                builder.setCancelable(true);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("StreetLight_1").child("Power");
                        myRef.setValue("Off");
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

//        BtnChange();


        option.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (manual1.isChecked()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("StreetLight_1").child("Option");
                    myRef.setValue("Manual");
                    off.setEnabled(true);
                    on.setEnabled(true);
                    t1.setEnabled(true);
                    manual1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            manual1.setChecked(true);
                            Toast.makeText(getActivity(), "Manual control.", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                else{
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("StreetLight_1").child("Option");
                    myRef.setValue("Auto");
                    off.setEnabled(false);
                    on.setEnabled(false);
                    t1.setEnabled(false);
                    auto1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            auto1.setChecked(true);
                            Toast.makeText(getActivity(), "Automatic control!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });



        light.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (on.isChecked()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("StreetLight_1").child("Light");
                    myRef.setValue("On");
                    on.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            on.setChecked(true);
                        }
                    });
                    //Toast.makeText(getActivity(), "Light is turned on!", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("StreetLight_1").child("Light");
                    myRef.setValue("Off");
                    off.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            off.setChecked(true);
                        }
                    });
                    //Toast.makeText(getActivity(), "Light is turned off!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return toolsView;
    }


    @Override
    public void onStart() {
        super.onStart();
        ref = FirebaseDatabase.getInstance().getReference();
        ref2 = FirebaseDatabase.getInstance().getReference();
        try {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status = dataSnapshot.child("StreetLight_1").child("Light").getValue().toString();

                    String value ="On";
                    if (status.equals(value))
                    {
                        on.setChecked(true);
                    }
                    else
                    {
                        off.setChecked(true);
                    }
                    return;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }
            });

            ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status = dataSnapshot.child("StreetLight_1").child("Option").getValue().toString();

                    String value ="Auto";
                    if (status.equals(value))
                    {
                        auto1.setChecked(true);
                        off.setEnabled(false);
                        off.setEnabled(false);
                        t1.setEnabled(false);
                    }
                    else
                    {
                        manual1.setChecked(true);
                        off.setEnabled(true);
                        on.setEnabled(true);
                        t1.setEnabled(true);
                    }
                    return;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void BtnChange(){
//        ref = FirebaseDatabase.getInstance().getReference();
//        ref2 = FirebaseDatabase.getInstance().getReference();
//        try {
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String status = dataSnapshot.child("StreetLight_1").child("Light").getValue().toString();
//                    String value ="On";
//                    if (status.equals(value))
//                    {
//                        try{
//                            On.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                            Off.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                        }
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else
//                    {
//                        try{
//                            Off.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                            On.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                        }
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    return;
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//
//            ref2.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String status = dataSnapshot.child("StreetLight_1").child("Option").getValue().toString();
//
//                    String value ="Auto";
//                    if (status.equals(value))
//                    {
//                        try{
////                            auto.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
////                            manual.setBackgroundColor(getResources().getColor(R.color.colorBlu));
//                            Off.setEnabled(false);
//                            On.setEnabled(false);
//                            t1.setEnabled(false);
//                        }
//                        catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//
//                    }
//                    else
//                    {
//                        try{
////                            manual.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
////                            auto.setBackgroundColor(getResources().getColor(R.color.colorBlu));
//                            Off.setEnabled(true);
//                            On.setEnabled(true);
//                            t1.setEnabled(true);
//                        }
//                        catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                    return;
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }



}