package com.example.gdcmns2.ui.home;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.gdcmns2.R;
import com.example.gdcmns2.Registration;
import com.example.gdcmns2.TempValue;
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
public class HomeTemperature extends Fragment {


    View HomeTemperatureVIew;
    FirebaseDatabase database;
    DatabaseReference ref;

    GraphView graphView;
    LineGraphSeries series,series2;

    DatePickerDialog dpd;

    public Calendar calendar;
    public SimpleDateFormat dateFormat,dateFormat2;
    public String date;
    public String xxx;
    public Button selDateBtn;
    public TextView dateSelected;
    int day,month,year;

    public HomeTemperature() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HomeTemperatureVIew = inflater.inflate(R.layout.fragment_home_teperature, container, false);

        graphView = (GraphView) HomeTemperatureVIew.findViewById(R.id.graph);
        selDateBtn = (Button) HomeTemperatureVIew.findViewById(R.id.date_pick);
        dateSelected = (TextView) HomeTemperatureVIew.findViewById(R.id.date_choosen);


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        date = dateFormat.format(calendar.getTime());

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);






        selDateBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                dpd = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        month = month+1;
                        dateSelected.setText(String.format("%02d", month)+"-"+String.format("%02d", dayOfMonth)+"-"+year);

                        //Changes the data on the graph
                        Query query = ref.orderByChild("Date").equalTo(dateSelected.getText().toString());
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

//                                DataPoint[] dp1= new DataPoint[(int) dataSnapshot.getChildrenCount()];
//                                int index1=0;
//                                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
//                                {
//                                    final TempValue tempValue = myDataSnapshot.getValue(TempValue.class);
//                                    dp1[index1]=new DataPoint(tempValue.getTime(),tempValue.getLowest_Temperature());
//                                    index1++;
//                                }
//                                series2.resetData(dp1);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        //end of data change
                    }
                },year,month,day);
                dpd.show();


                dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            }

        });



        series = new LineGraphSeries();
        graphView.addSeries(series);
//
//        series2 = new LineGraphSeries();
//        graphView.addSeries(series2);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(23);

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(4);
        series.setThickness(3);
//
//        series2.setDrawDataPoints(false);
//        series2.setDataPointsRadius(7);
//        series2.setThickness(3);
//        series2.setColor(Color.RED);



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

        database = FirebaseDatabase.getInstance();
        ref=database.getReference("Streetlights/StreetLight_1/Data");




        return HomeTemperatureVIew;
    }

    @Override
    public void onStart() {
        super.onStart();

        dateSelected.setText(date);
        Query query = ref.orderByChild("Date").equalTo(dateSelected.getText().toString());
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


//                DataPoint[] dp1= new DataPoint[(int) dataSnapshot.getChildrenCount()];
//                int index1=0;
//                for (DataSnapshot myDataSnapshot : dataSnapshot.getChildren())
//                {
//                    final TempValue tempValue = myDataSnapshot.getValue(TempValue.class);
//                    dp1[index1]=new DataPoint(tempValue.getTime(),tempValue.getLowest_Temperature());
//                    index1++;
//                }
//                series2.resetData(dp1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


}
