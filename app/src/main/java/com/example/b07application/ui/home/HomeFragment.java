package com.example.b07application.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.b07application.AnnouncementActivity;
import com.example.b07application.HomeActivity;
import com.example.b07application.PostActivity;
import com.example.b07application.databinding.FragmentHomeBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import events.Event;

public class HomeFragment extends Fragment {
    FirebaseDatabase db;
    private FragmentHomeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
        DatabaseReference ref = db.getReference("");

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.checkPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });

        //Im gonna use this stuff later, so im not gonna delete it yet. Sorry for the mess.
        /*
        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                view.setDate(calendar.getTimeInMillis());
            }
        });
        binding.addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference eventsRef = ref.child("events");

                //formatting the date
                long givenDate = binding.calendarView.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(givenDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                String setDate = dateFormat.format(calendar.getTime());

                eventsRef.push().setValue(new Event(setDate, 50, "description", FirebaseAuth.getInstance().getCurrentUser().getEmail()));

            }
        });
        */

        //example of fetching an event
        //BTW, you cant just get a single object from the database, whenever you make a query,
        //a list is returned in the form of DataSnapshot
        //we just iterate over the list to get our objects
        //also im pretty sure that whenever the database is changed, the addListenerForSingleValueEvent
        //(or whatever other listener you use) gets called again
        /*
        DatabaseReference eventsRef = ref.child("events");
        Query query = eventsRef.orderByChild("author").equalTo("testemail@gmail.com");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "event" node with all children with email testemail@gmail.com
                    for (DataSnapshot event : dataSnapshot.getChildren()) {
                        Event test = event.getValue(Event.class);
                        //Toast.makeText(getActivity(), String.valueOf(test.date + " " + test.author),
                        //        Toast.LENGTH_SHORT).show();
                        Log.d("myTag",test.date + " " + test.author);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), String.valueOf(databaseError.getMessage()),
                        Toast.LENGTH_SHORT).show();
            }
        });
        */

        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.announcementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AnnouncementActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}