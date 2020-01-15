package com.example.gdcmns2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.gdcmns2.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class HomeFragment extends Fragment {

    private View TempView;
    private HomeViewModel homeViewModel;
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    DatabaseReference ref;
    TextView tempVal,humVal,speed,direction,weather,date_now,time_now;
    ImageView wthrLogo;



    private HomeTemperature homeTemperature;
    private HomeHumidity homeHumidity;
    private HomeWeather homeWeather;
    private HomeWind homeWind;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        TempView = inflater.inflate(R.layout.fragment_home, container, false);

        tempVal = (TextView)TempView.findViewById(R.id.temperature);
        humVal = (TextView)TempView.findViewById(R.id.humidity);
        weather = (TextView)TempView.findViewById(R.id.weather);
        speed = (TextView)TempView.findViewById(R.id.windspeed);
        direction = (TextView)TempView.findViewById(R.id.winddirection);
        date_now = (TextView)TempView.findViewById(R.id.date);
        time_now = (TextView)TempView.findViewById(R.id.time_id);
        wthrLogo = (ImageView)TempView.findViewById(R.id.weatherLogo);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        String currentTime = ""+ format.format(calendar.getTime());

        date_now.setText(currentDate);
        time_now.setText(currentTime);



        //MM-dd-yyyy

        mMainNav = (BottomNavigationView)TempView.findViewById(R.id.home_bar);
        mMainFrame = (FrameLayout)TempView.findViewById(R.id.home_frame);

        homeTemperature = new HomeTemperature();
        homeHumidity = new HomeHumidity();
        homeWeather = new HomeWeather();
        homeWind = new HomeWind();

        setFragment(homeWeather);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.home_weather :
                        setFragment(homeWeather);
                        return true;

                    case R.id.home_temp :
                        setFragment(homeTemperature);
                        return true;

                    case R.id.home_humidity :
                        setFragment(homeHumidity);
                        return true;

                    case R.id.home_wind :
                        setFragment(homeWind);
                        return true;

                        default:
                            return false;

                }
            }
        });

        return TempView;
    }

    @Override
    public void onStart() {
        super.onStart();

        ref = FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String temp = dataSnapshot.child("StreetLight_1").child("Temperature").getValue().toString();
                String hum = dataSnapshot.child("StreetLight_1").child("Humidity").getValue().toString();
                String wthr = dataSnapshot.child("StreetLight_1").child("Weather").getValue().toString();
                weather.setText(wthr);
                humVal.setText(hum+" %");
                tempVal.setText(temp+" °C");//Fix soon for the °F
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_frame, fragment);
        fragmentTransaction.commit();
    }





}