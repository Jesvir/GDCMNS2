package com.example.gdcmns2.ui.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gdcmns2.Holder.CameraHolder;
import com.example.gdcmns2.Model.CameraData;
import com.example.gdcmns2.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class ToolsFragment extends Fragment {

    View toolsView;
    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseRecyclerOptions<CameraData> options;
    FirebaseRecyclerAdapter<CameraData, CameraHolder> adapter;
    ArrayList<CameraData> arrayList;

    FrameLayout frameLayout;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        toolsView = inflater.inflate(R.layout.fragment_tools, container, false);
        ref = FirebaseDatabase.getInstance().getReference().child("Streetlights");
        ref.keepSynced(true);
        recyclerView = (RecyclerView)toolsView.findViewById(R.id.recycler);
        frameLayout = (FrameLayout)toolsView.findViewById(R.id.tools_frame);
        textView = (TextView)toolsView.findViewById(R.id.label);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<CameraData>();

        options = new FirebaseRecyclerOptions.Builder<CameraData>().setQuery(ref, CameraData.class).build();
        adapter = new FirebaseRecyclerAdapter<CameraData, CameraHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CameraHolder cameraHolder, int i, @NonNull final CameraData cameraData) {
                cameraHolder._cameraName.setText(cameraData.getCameraName());
                final String getIP = cameraData.getCamera_IP();

                cameraHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CameraIP._ip = getIP;
                        CameraIP.name = cameraData.getCameraName();
                        Intent intent = new Intent(getActivity(), CameraFinal.class);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public CameraHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new CameraHolder(LayoutInflater.from(getActivity()).inflate(R.layout.camera_list, parent, false));
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

        return toolsView;
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.tools_frame, fragment);
        fragmentTransaction.commit();

    }


}