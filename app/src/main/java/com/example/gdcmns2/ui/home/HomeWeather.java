package com.example.gdcmns2.ui.home;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.Holder.WeatherHolder;
import com.example.gdcmns2.Model.ItemData;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWeather extends Fragment  {


    View HomeWeatherView;

    public Calendar calendar;
    public SimpleDateFormat dateFormat;
    public String date;
    public Button selDateBtn;
    public TextView dateSelected;

    ArrayList<ItemData> arrayList;
    DatabaseReference ref, getImg;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<ItemData> options;
    FirebaseRecyclerAdapter<ItemData, WeatherHolder> adapter;
    int day,month,year;
    ImageView wthImg,dataview;
    MaterialButtonToggleGroup option;
    MaterialButton auto,manual;


    AlertDialog dialog;
    String Wnd, Snny, Rny, Cldy;
    DatePickerDialog dpd;

    public HomeWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HomeWeatherView = inflater.inflate(R.layout.fragment_home_weather, container, false);
        option = (MaterialButtonToggleGroup)HomeWeatherView.findViewById(R.id.option);

        ref = FirebaseDatabase.getInstance().getReference().child("StreetLight_1/Data");
        ref.keepSynced(true);


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);


        selDateBtn = (Button)HomeWeatherView.findViewById(R.id.date_pick2);
        wthImg = (ImageView)HomeWeatherView.findViewById(R.id.weather_image);
        dateSelected = (TextView) HomeWeatherView.findViewById(R.id.date_choosen);
        recyclerView = (RecyclerView) HomeWeatherView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<ItemData>();


        selDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        dateSelected.setText(String.format("%02d", month)+"-"+String.format("%02d", dayOfMonth)+"-"+year);
                        final Query query1 = ref.orderByChild("Date").equalTo(dateSelected.getText().toString());

                        query1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                options = new FirebaseRecyclerOptions.Builder<ItemData>().setQuery(query1, ItemData.class).build();
                                adapter = new FirebaseRecyclerAdapter<ItemData, WeatherHolder>(options) {
                                    @Override
                                    protected void onBindViewHolder(@NonNull WeatherHolder weatherHolder, int i, @NonNull ItemData itemData) {

                                        weatherHolder._temp.setText(String.valueOf(itemData.getHighest_Temperature()+" °C"));//String.valueOfString.valueOf() converts anything into a string
                                        weatherHolder._weather.setText(itemData.getWeather_History());
                                        //weatherHolder._time.setText(String.valueOf(itemData.getTime()));
                                        long value = itemData.getTime();
                                        if(value >= 12){
                                            if(value == 12){
                                                weatherHolder._time.setText(String.valueOf(value+" PM"));
                                            }
                                            else {
                                                long pm_value = value - 12;
                                                weatherHolder._time.setText(String.valueOf(pm_value+" PM"));
                                            }
                                        }
                                        else{
                                            if (value == 0){
                                                weatherHolder._time.setText(String.valueOf(12+" AM"));
                                            }
                                            else {
                                                weatherHolder._time.setText(String.valueOf(itemData.getTime()+" AM"));
                                            }

                                        }


                                        //////////////////////////////////////////////////////////
                                        String Sunny ="Sunny";
                                        String Raining = "Raining";
                                        String Night = "Night";
                                        String RNight = "Raining.";
                                        String Dawn = "Dawn";
                                        if (itemData.getWeather_History().equals(Sunny))
                                        {
                                            weatherHolder._image.setImageResource(R.drawable.sunny);
                                        }
                                        else if(itemData.getWeather_History().equals((Raining)))
                                        {
                                            weatherHolder._image.setImageResource(R.drawable.rainday);
                                        }
                                        else if(itemData.getWeather_History().equals((Night)))
                                        {
                                            weatherHolder._image.setImageResource(R.drawable.night);
                                        }
                                        else if(itemData.getWeather_History().equals((RNight)))
                                        {
                                            weatherHolder._image.setImageResource(R.drawable.rainnight);
                                        }

                                        else if(itemData.getWeather_History().equals((Dawn)))
                                        {
                                            weatherHolder._image.setImageResource(R.drawable.dawn);
                                        }
                                        //////////////////////////////////////////////////////////


                                        weatherHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(getActivity(), "Currently under progress!", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @NonNull
                                    @Override
                                    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                                        return new WeatherHolder(LayoutInflater.from(getActivity()).inflate(R.layout.weather_data, parent, false));
                                    }
                                };
                                recyclerView.setAdapter(adapter);
                                adapter.startListening();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                },year,month,day);
                dpd.show();

                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            }
        });











        return HomeWeatherView;
    }





    @Override
    public void onStart() {
        super.onStart();
        dateSelected.setText(date);
        final Query query2 = ref.orderByChild("Date").equalTo(dateSelected.getText().toString());
        options = new FirebaseRecyclerOptions.Builder<ItemData>().setQuery(query2, ItemData.class).build();
        adapter = new FirebaseRecyclerAdapter<ItemData, WeatherHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull WeatherHolder weatherHolder, int i, @NonNull ItemData itemData) {
                    weatherHolder._temp.setText(String.valueOf(itemData.getHighest_Temperature()+" °C"));//String.valueOfString.valueOf() converts anything into a string
                    weatherHolder._weather.setText(itemData.getWeather_History());
                    //weatherHolder._time.setText(String.valueOf(itemData.getTime()));

                    long value = itemData.getTime();
                    if(value >= 12){
                        if(value == 12){
                            weatherHolder._time.setText(String.valueOf(value+" PM"));
                        }
                        else {
                            long pm_value = value - 12;
                            weatherHolder._time.setText(String.valueOf(pm_value+" PM"));
                        }
                    }
                    else{
                        if (value == 0){
                            weatherHolder._time.setText(String.valueOf(12+" AM"));
                        }
                        else {
                            weatherHolder._time.setText(String.valueOf(itemData.getTime()+" AM"));
                        }

                    }

                //////////////////////////////////////////////////////////
                String Sunny ="Sunny";
                String Raining = "Raining";
                String Night = "Night";
                String RNight = "Raining.";
                String Dawn = "Dawn";
                if (itemData.getWeather_History().equals(Sunny))
                {
                    weatherHolder._image.setImageResource(R.drawable.sunny);
                }
                else if(itemData.getWeather_History().equals((Raining)))
                {
                    weatherHolder._image.setImageResource(R.drawable.rainday);
                }
                else if(itemData.getWeather_History().equals((Night)))
                {
                    weatherHolder._image.setImageResource(R.drawable.night);
                }
                else if(itemData.getWeather_History().equals((RNight)))
                {
                    weatherHolder._image.setImageResource(R.drawable.rainnight);
                }

                else if(itemData.getWeather_History().equals((Dawn)))
                {
                    weatherHolder._image.setImageResource(R.drawable.dawn);
                }
                //////////////////////////////////////////////////////////


                weatherHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "Currently under progress!", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @NonNull
            @Override
            public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new WeatherHolder(LayoutInflater.from(getActivity()).inflate(R.layout.weather_data, parent, false));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public void GetWeatherImages(){
        getImg = FirebaseDatabase.getInstance().getReference();
        getImg.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
