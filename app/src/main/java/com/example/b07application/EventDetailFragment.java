package com.example.b07application;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07application.databinding.FragmentEventDetailBinding;
import com.example.b07application.databinding.FragmentEventListBinding;
import com.example.b07application.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

import Misc.SessionInfo;
import events.Event;
import users.User;

public class EventDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentEventDetailBinding binding;
    FirebaseDatabase db;
    FirebaseUser user;
    String id;
    public EventDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailFragment newInstance(String param1, String param2) {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEventDetailBinding.inflate(inflater, container, false);
        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
        user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference ref = db.getReference("");
        id = getArguments().getString("eventID");

        DatabaseReference eventsRef = ref.child("events");

        Query query = eventsRef.orderByKey().equalTo(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    for (DataSnapshot event : dataSnapshot.getChildren()) {
                        Event eventItem = event.getValue(Event.class);

                        //Log.d("myTag",eventItem.date + " " + eventItem.author);
                        binding.eventDetailTitle.setText(eventItem.title);
                        binding.eventDetailTime.setText("" + eventItem.getTime());
                        binding.eventDetailDate.setText(eventItem.getDate());
                        binding.eventDetailLocation.setText(eventItem.location);
                        binding.eventDetailNumReviews.setText(String.valueOf(eventItem.numReviews));
                        binding.eventDetailAvgRating.setText(String.valueOf(eventItem.avgRating));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("myTag",String.valueOf(databaseError.getMessage()));
            }
        });


        if (!SessionInfo.getInstance().isAdmin){
            binding.eventDetailAdminInfo.setVisibility(View.GONE);
        }

        binding.eventDetailReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("eventID", id);
                NavHostFragment.findNavController(EventDetailFragment.this)
                        .navigate(R.id.action_eventDetail_to_eventReview, b);
            }
        });

        return binding.getRoot();
    }
}