package com.example.gdcmns2.ui.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gdcmns2.HumValue;
import com.example.gdcmns2.R;
import com.example.gdcmns2.TempValue;
import com.example.gdcmns2.WeathValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWeather extends Fragment {


    View HomeWeatherView;
    FirebaseDatabase database;
    DatabaseReference ref;

    GraphView graphView;
    LineGraphSeries series;


    public Calendar calendar;
    public SimpleDateFormat dateFormat;
    public String date;
    String Wnd,Snny,Rny,Cldy;

    public HomeWeather() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HomeWeatherView = inflater.inflate(R.layout.fragment_home_weather, container, false);

        graphView = (GraphView) HomeWeatherView.findViewById(R.id.graph);
        series = new LineGraphSeries();
        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setTextSize(20f);
        graphView.getGridLabelRenderer().reloadStyles();

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());



        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(23);

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(7);
        series.setThickness(3);

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);


        graphView.getGridLabelRenderer().setTextSize(20f);
        graphView.getGridLabelRenderer().reloadStyles();




        database = FirebaseDatabase.getInstance();
        ref=database.getReference("StreetLight_1/Data");


        return HomeWeatherView;
    }

    public void onStart() {
        super.onStart();
        Data();
    }

    public void Data(){




        Query query = ref.orderByChild("Date").equalTo(date);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint[] dp= new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index=0;
                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    HumValue humValue = myDataSnapshot.getValue(HumValue.class);
                    dp[index]=new DataPoint(humValue.getTime(),humValue.getHighest_Humidity());
                    index++;
                }
                series.resetData(dp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
