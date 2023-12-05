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
import com.example.b07application.databinding.ActivityFeedbackBinding;
import com.example.b07application.databinding.ActivityHomeBinding;
import com.example.b07application.databinding.ComplaintDetailBinding;
import com.example.b07application.databinding.FeedbackDetailBinding;
import com.example.b07application.databinding.FragmentHomeBinding;

public class FeedbackDetailActivity extends AppCompatActivity{
    TextView detailFeedbackComment;
    TextView detailFeedbackRating;

    private FeedbackDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FeedbackDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        detailFeedbackComment = findViewById(R.id.detailFeedbackComment);
        detailFeedbackRating = findViewById(R.id.detailFeedbackRating);


        binding.detailFeedbackBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FeedbackDetailActivity.this, FeedbackActivity.class);
                startActivity(intent);}

        }) ;

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailFeedbackComment.setText(bundle.getString("comment"));
            detailFeedbackRating.setText(bundle.getString("rating"));



        }

    }
}


