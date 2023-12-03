package com.example.b07application.complaints;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07application.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ComplaintsActivity extends AppCompatActivity {

    public FirebaseDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
        Button buttonPrev = findViewById(R.id.button_previous);
        buttonPrev.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onClickSubmit(View view) {
        DatabaseReference ref = db.getReference();
        EditText complaintsText = findViewById(R.id.editTextComplaints);
        String text = complaintsText.getText().toString();
        EditText descriptionText = findViewById(R.id.editTextDescription);
        String desc = descriptionText.getText().toString();
        complaintsText.setText("");
        descriptionText.setText("");
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Complaints complaint = new Complaints(currentDate, currentTime, desc, text);
        ref.child("Complaints").push().setValue(complaint);
    }
}