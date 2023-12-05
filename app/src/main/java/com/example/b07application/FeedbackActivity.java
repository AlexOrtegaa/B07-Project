package com.example.b07application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.b07application.databinding.ActivityFeedbackBinding;
import com.example.b07application.databinding.ComplaintDetailBinding;
import com.example.b07application.databinding.FeedbackDetailBinding;
import com.example.b07application.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<com.example.b07application.FeedbackDataClass> dataList;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    com.example.b07application.MyAdapterFeedback adapter;
    private ActivityFeedbackBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.activityFeedbackEventAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FeedbackActivity.this, HomeFragment.class);
                startActivity(intent);}

        }) ;

        recyclerView = findViewById(R.id.recyclerViewFeedback);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FeedbackActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(FeedbackActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        dataList = new ArrayList<>();

        adapter = new com.example.b07application.MyAdapterFeedback(FeedbackActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("eventReviews");
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for(DataSnapshot itemSnapshot: snapshot.getChildren()) {

                    com.example.b07application.FeedbackDataClass dataClass = itemSnapshot.getValue((com.example.b07application.FeedbackDataClass.class));
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