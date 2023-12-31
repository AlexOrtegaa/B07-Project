package com.example.b07application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.b07application.databinding.ActivityComplaintBinding;
import com.example.b07application.databinding.ComplaintDetailBinding;
import com.example.b07application.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ComplaintActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<com.example.b07application.ComplaintDataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    com.example.b07application.MyAdapterComplaint adapter;
    private ActivityComplaintBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityComplaintBinding.inflate(getLayoutInflater());


        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.recyclerViewComplaint);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ComplaintActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(ComplaintActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new com.example.b07application.MyAdapterComplaint(ComplaintActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Complaints");
        dialog.show();
         // admin?
        binding.activityComplaintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ComplaintActivity.this, HomeActivity.class);
                startActivity(intent);}

        }) ;
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()) {

                    com.example.b07application.ComplaintDataClass dataClass = itemSnapshot.getValue((com.example.b07application.ComplaintDataClass.class));
                    dataList.add(dataClass);
                }


                adapter.notifyDataSetChanged();
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        adapter.notifyDataSetChanged();

    }
}