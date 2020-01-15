package com.example.gdcmns2.ui.home;


import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.R;
import com.example.gdcmns2.TempValue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeTemperature extends Fragment {


    View HomeTemperatureVIew;
    FirebaseDatabase database;
    DatabaseReference ref;

    GraphView graphView;
    LineGraphSeries series;

    public Calendar calendar;
    public SimpleDateFormat dateFormat;
    public String date;
    public String xxx;

    public HomeTemperature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HomeTemperatureVIew = inflater.inflate(R.layout.fragment_home_teperature, container, false);

        graphView = (GraphView) HomeTemperatureVIew.findViewById(R.id.graph);
        series = new LineGraphSeries();
        graphView.addSeries(series);





        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(23);



        series.setDrawDataPoints(true);
        series.setDataPointsRadius(7);
        series.setThickness(3);




//        GridLabelRenderer gridLabel = graphView.getGridLabelRenderer();
//        gridLabel.setHorizontalAxisTitle("Time");
//        gridLabel.setHorizontalAxisTitleTextSize(35f);
//        gridLabel.setHorizontalAxisTitleColor(Color.BLACK);

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().setScrollableY(true);


        graphView.getGridLabelRenderer().setTextSize(20f);
        graphView.getGridLabelRenderer().reloadStyles();



        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());

        database = FirebaseDatabase.getInstance();
        ref=database.getReference("StreetLight_1/Data");






        return HomeTemperatureVIew;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = ref.orderByChild("Date").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataPoint[] dp= new DataPoint[(int) dataSnapshot.getChildrenCount()];
                int index=0;
                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
                {
                    final TempValue tempValue = myDataSnapshot.getValue(TempValue.class);
                    dp[index]=new DataPoint(tempValue.getTime(),tempValue.getHighest_Temperature());
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
