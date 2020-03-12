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
import com.google.firebase.database.Query;
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

    String optionStr,lightStr,powerStr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        toolsView = inflater.inflate(R.layout.fragment_settings, container, false);
        ref = FirebaseDatabase.getInstance().getReference();
        ref2 = FirebaseDatabase.getInstance().getReference();
        ref3 = FirebaseDatabase.getInstance().getReference().child("Streetlights");
        ref3.keepSynced(true);

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

        final Query query1 = ref3.orderByChild("Power").equalTo("On");
        options = new FirebaseRecyclerOptions.Builder<SettingsData>().setQuery(query1, SettingsData.class).build();
        adapter = new FirebaseRecyclerAdapter<SettingsData, SettingsHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final SettingsHolder settingsHolder, int i, @NonNull final SettingsData settingsData) {
//                Start of Holder
                settingsHolder._name.setText(settingsData.getName());
                optionStr = settingsData.getOption();
                lightStr = settingsData.getLight();
                powerStr = settingsData.getPower();
                String lightValue = "On";
                String optValue = "Auto" ;

//                if(lightStr.equals(lightValue)){
//                    settingsHolder._on.setChecked(true);
//                }
//                else {
//                    settingsHolder._off.setChecked(true);
//                }
//
//                if (optionStr.equals(optValue)){
//                    settingsHolder._auto.setChecked(true);
//                    settingsHolder._off.setEnabled(false);
//                    settingsHolder._on.setEnabled(false);
//                    settingsHolder._name.setEnabled(false);
//                }
//                else {
//                    settingsHolder._man.setChecked(true);
//                    settingsHolder._off.setEnabled(true);
//                    settingsHolder._on.setEnabled(true);
//                    settingsHolder._name.setEnabled(true);
//                }


                try {
                    ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String status = dataSnapshot.child(settingsData.getName()).child("Light").getValue().toString();
                            String value ="On";
                            if (status.equals(value))
                            {
                                settingsHolder._on.setChecked(true);
                            }
                            else
                            {
                                settingsHolder._off.setChecked(true);
                            }
                            return;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                    ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String status = dataSnapshot.child(settingsData.getName()).child("Option").getValue().toString();

                            String value ="Auto";
                            if (status.equals(value))
                            {
                                settingsHolder._auto.setChecked(true);
                                settingsHolder._off.setEnabled(false);
                                settingsHolder._on.setEnabled(false);
                                settingsHolder._lightText.setEnabled(false);
                            }
                            else
                            {
                                settingsHolder._man.setChecked(true);
                                settingsHolder._off.setEnabled(true);
                                settingsHolder._on.setEnabled(true);
                                settingsHolder._lightText.setEnabled(true);
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



                settingsHolder._shutdown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Are you sure to Shutdown the "+settingsData.getName()+"?");
                        builder.setMessage("Click Yes to Shutdown");
                        builder.setIcon(R.drawable.shutdown);
                        builder.setCancelable(true);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Streetlights/"+settingsData.getName()+"").child("Power");
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


                settingsHolder._reboot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Are you sure to Restart the "+settingsData.getName()+"?");
                        builder.setMessage("Click Yes to Restart");
                        builder.setIcon(R.drawable.refresh_black);
                        builder.setCancelable(true);

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Streetlights/"+settingsData.getName()+"").child("Power");
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

                settingsHolder._man.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Streetlights/"+settingsData.getName()+"").child("Option");
                        Toast.makeText(getActivity(), "Manual control.", Toast.LENGTH_SHORT).show();
                        myRef.setValue("Manual");
                        settingsHolder._off.setEnabled(true);
                        settingsHolder._on.setEnabled(true);
                        settingsHolder._lightText.setEnabled(true);
                        settingsHolder._man.setChecked(true);
                    }
                });

                settingsHolder._auto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Streetlights/"+settingsData.getName()+"").child("Option");
                        Toast.makeText(getActivity(), "Automatic control!", Toast.LENGTH_SHORT).show();
                        myRef.setValue("Auto");
                        settingsHolder._off.setEnabled(false);
                        settingsHolder._on.setEnabled(false);
                        settingsHolder._lightText.setEnabled(false);
                        settingsHolder._auto.setChecked(true);
                    }
                });

                settingsHolder._off.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Streetlights/"+settingsData.getName()+"").child("Light");
                        myRef.setValue("Off");
                        settingsHolder._off.setChecked(true);
                    }
                });

                settingsHolder._on.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("Streetlights/"+settingsData.getName()+"").child("Light");
                        myRef.setValue("On");
                        settingsHolder._on.setChecked(true);
                    }
                });


//                End of Holder
            }

            @NonNull
            @Override
            public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new SettingsHolder(LayoutInflater.from(getActivity()).inflate(R.layout.settings_data, parent, false));
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

        return toolsView;



    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        final Query query1 = ref.orderByChild("Power").equalTo("On");
//        options = new FirebaseRecyclerOptions.Builder<SettingsData>().setQuery(query1, SettingsData.class).build();
//        adapter = new FirebaseRecyclerAdapter<SettingsData, SettingsHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull final SettingsHolder settingsHolder, int i, @NonNull final SettingsData settingsData) {
//                try {
//                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            String status = dataSnapshot.child("StreetLight_1").child("Light").getValue().toString();
//
//                            String value ="On";
//                            if (status.equals(value))
//                            {
//                                settingsHolder._on.setChecked(true);
//                            }
//                            else
//                            {
//                                settingsHolder._off.setChecked(true);
//                            }
//                            return;
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    ref2.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            String status = dataSnapshot.child("StreetLight_1").child("Option").getValue().toString();
//
//                            String value ="Auto";
//                            if (status.equals(value))
//                            {
//                                settingsHolder._auto.setChecked(true);
//                                settingsHolder._off.setEnabled(false);
//                                settingsHolder._on.setEnabled(false);
//                                settingsHolder._name.setEnabled(false);
//                            }
//                            else
//                            {
//                                settingsHolder._man.setChecked(true);
//                                settingsHolder._off.setEnabled(true);
//                                settingsHolder._on.setEnabled(true);
//                                settingsHolder._name.setEnabled(true);
//                            }
//                            return;
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//            @NonNull
//            @Override
//            public SettingsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return new SettingsHolder(LayoutInflater.from(getActivity()).inflate(R.layout.settings_data, parent, false));
//            }
//        };
//
//    }



}