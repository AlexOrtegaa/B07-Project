package com.example.b07application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ComplaintDetailActivity extends AppCompatActivity{
    TextView detailComplaintText;
    TextView detailComplaintDesc;
    TextView detailComplaintDate;
    TextView detailComplaintTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complaint_detail);

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
    }
}


