package com.example.b07application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.b07application.databinding.FragmentEventReviewBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import events.Event;
import events.EventReview;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventReviewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseDatabase db;
    private FragmentEventReviewBinding binding;
    String id;
    FirebaseUser user;
    public EventReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventReview.
     */
    // TODO: Rename and change types and number of parameters
    public static EventReviewFragment newInstance(String param1, String param2) {
        EventReviewFragment fragment = new EventReviewFragment();
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
        DatabaseReference ref = db.getReference("");
        binding = FragmentEventReviewBinding.inflate(inflater, container, false);
        id = getArguments().getString("eventID");
        binding.eventReviewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference eventsReviewsRef = ref.child("eventReviews");
                String comment = String.valueOf(binding.eventReviewComment.getText());
                String ratingString = String.valueOf(binding.eventReviewRating.getText());
                int rating = ratingString.isEmpty() ? -1 : Integer.parseInt(ratingString);

                if(comment.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter an comment.", Toast.LENGTH_SHORT).show();
                }
                else if(rating > 10 || rating < 0) {
                    Toast.makeText(getActivity(), "Please enter an rating between 0-10.", Toast.LENGTH_SHORT).show();
                }
                else {
                    eventsReviewsRef.push().setValue(new EventReview(FirebaseAuth.getInstance().getCurrentUser().getEmail(), comment, rating, id));
                    Toast.makeText(getActivity(), "Event review successfully posted", Toast.LENGTH_SHORT).show();

                    DatabaseReference eventRef = ref.child("events").child(id);
                    Query eventQuery = ref.child("events").orderByKey().equalTo(id);
                    eventQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot event : dataSnapshot.getChildren()) {
                                    Event eventItem = event.getValue(Event.class);

                                    eventItem.avgRating = (eventItem.avgRating * eventItem.numReviews + rating) / (eventItem.numReviews + 1);
                                    eventItem.numReviews += 1;
                                    Log.d("myTag",String.valueOf(eventItem.avgRating));

                                    eventRef.setValue(eventItem);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.d("myTag",String.valueOf(databaseError.getMessage()));
                        }
                    });
                    Bundle b = new Bundle();
                    b.putString("eventID", id);
                    NavHostFragment.findNavController(EventReviewFragment.this)
                            .popBackStack();
                }
            }
        });

        return binding.getRoot();
    }
}