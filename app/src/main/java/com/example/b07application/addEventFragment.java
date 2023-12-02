package com.example.b07application;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.b07application.databinding.FragmentAddEventBinding;
import com.example.b07application.databinding.FragmentFirstBinding;
import com.example.b07application.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import events.Event;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addEventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FragmentAddEventBinding binding;
    FirebaseDatabase db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static addEventFragment newInstance(String param1, String param2) {
        addEventFragment fragment = new addEventFragment();
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
        binding = FragmentAddEventBinding.inflate(inflater, container, false);
        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
        DatabaseReference ref = db.getReference("");
        binding.addEventCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                view.setDate(calendar.getTimeInMillis());
            }
        });
        binding.postEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference eventsRef = ref.child("events");
                String title = String.valueOf(binding.addEventTitle.getText());
                String location = String.valueOf(binding.addEventLocation.getText());
                String time = String.valueOf(binding.addEventTime.getText());

                //formatting the date
                long givenDate = binding.addEventCalender.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(givenDate);
                SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd", Locale.getDefault());
                String setDate = dateFormat.format(calendar.getTime());

                if(title.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter an title.", Toast.LENGTH_SHORT).show();
                }
                else if(location.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter an location.", Toast.LENGTH_SHORT).show();
                }
                else if(time.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter an time.", Toast.LENGTH_SHORT).show();
                }
                else {
                    eventsRef.push().setValue(new Event(setDate, title, location, time, 50, "description",
                            FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                    Toast.makeText(getActivity(), "Event successfully posted", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(addEventFragment.this)
                            .navigate(R.id.action_addEvent_to_home);
                }


            }
        });
        return binding.getRoot();
    }
}