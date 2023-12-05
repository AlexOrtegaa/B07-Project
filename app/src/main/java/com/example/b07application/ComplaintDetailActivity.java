package com.example.b07application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b07application.databinding.ActivityComplaintBinding;
import com.example.b07application.databinding.ActivityHomeBinding;
import com.example.b07application.databinding.ComplaintDetailBinding;
import com.example.b07application.databinding.FragmentHomeBinding;

public class ComplaintDetailActivity extends AppCompatActivity{
    TextView detailComplaintText;
    TextView detailComplaintDesc;
    TextView detailComplaintDate;
    TextView detailComplaintTime;
    private ComplaintDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ComplaintDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        detailComplaintDate = findViewById(R.id.detailComplaintDate);
        detailComplaintText = findViewById(R.id.detailComplaintText);
        detailComplaintDesc = findViewById(R.id.detailComplaintDesc);
        detailComplaintTime = findViewById(R.id.detailComplaintTime);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailComplaintDate.setText(bundle.getString("Date"));
            detailComplaintText.setText(bundle.getString("Text"));
            detailComplaintDesc.setText(bundle.getString("Desc"));
            detailComplaintTime.setText(bundle.getString("Time"));


        }
        binding.detailComplaintBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ComplaintDetailActivity.this, ComplaintActivity.class);
                startActivity(intent);}

        }) ;
    }
}


