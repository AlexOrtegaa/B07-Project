package com.example.b07application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.b07application.databinding.FragmentViewAnnouncementsBinding;
import com.example.b07application.ui.Announcement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAnnouncementsFragment extends Fragment {

    private FragmentViewAnnouncementsBinding binding;
    private DatabaseReference databaseRef;
    private AnnouncementViewAdapter announcementViewAdapter;
    private ArrayList<Announcement> announcementList;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentViewAnnouncementsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseRef = FirebaseDatabase
                .getInstance("https://b07firebase-default-rtdb.firebaseio.com/")
                .getReference("Announcements");

        binding.announcementList.setHasFixedSize(true);
        binding.announcementList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        announcementList = new ArrayList<>();
        announcementViewAdapter = new AnnouncementViewAdapter(this.getContext(), announcementList);
        binding.announcementList.setAdapter(announcementViewAdapter);

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Announcement announcement = snapshot.getValue(Announcement.class);
                    announcementList.add(announcement);
                }
                announcementViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ViewAnnouncementsFragment.this)
                        .navigate(R.id.action_Second2Fragment_to_First2Fragment);
            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}