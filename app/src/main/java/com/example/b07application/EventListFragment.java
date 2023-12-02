package com.example.b07application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.b07application.databinding.FragmentAddEventBinding;
import com.example.b07application.databinding.FragmentEventListBinding;
import com.example.b07application.ui.home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import events.Event;
import events.EventAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventListFragment extends Fragment implements RecyclerViewInterface{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentEventListBinding binding;
    FirebaseDatabase db;
    ArrayList<Event> eventList;
    EventAdapter eventAdapter;
    RecyclerView recyclerView;

    public EventListFragment() {
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
    public static EventListFragment newInstance(String param1, String param2) {
        EventListFragment fragment = new EventListFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEventListBinding.inflate(inflater, container, false);
        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
        DatabaseReference ref = db.getReference("");

        recyclerView = binding.eventList;
        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(getActivity(), eventList, this);

        recyclerView.setAdapter(eventAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference eventsRef = ref.child("events");
        Query query = eventsRef.orderByChild("date");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot event : dataSnapshot.getChildren()) {
                        Event eventItem = event.getValue(Event.class);

                        eventItem.id = event.getKey();
                        eventList.add(eventItem);

                    }
                    Collections.reverse(eventList);
                    eventAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), String.valueOf(databaseError.getMessage()),
                        Toast.LENGTH_SHORT).show();
            }
        });



        return binding.getRoot();
    }

    @Override
    public void onItemClick(int position) {
        Bundle b = new Bundle();
        b.putString("eventID", eventList.get(position).id);

        NavHostFragment.findNavController(EventListFragment.this)
                .navigate(R.id.action_events_to_eventDetail, b);
    }
}