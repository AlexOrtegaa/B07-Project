package com.example.b07application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07application.databinding.FragmentPostAnnouncementBinding;
import com.example.b07application.ui.Announcement;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostAnnouncementFragment extends Fragment {
    private FragmentPostAnnouncementBinding binding;
    private DatabaseReference databaseRef;
    private DatabaseReference announcementsRef;
    private FirebaseAuth databaseAuth;

    private boolean isReadyToPost(Announcement announcement) {
        String title = announcement.getTitle();
        String author = announcement.getAuthor();
        String body = announcement.getBody();

        return !title.equals("") && !author.equals("") && !body.equals("");
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentPostAnnouncementBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseRef = FirebaseDatabase
                .getInstance("https://b07firebase-default-rtdb.firebaseio.com/").getReference();
        announcementsRef = databaseRef.child("Announcements");

        binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.announcementTitle.getText().toString();
                String author = "Anonymous"; /* Replace with databaseAuth.getInstance().
                getCurrentUser().getEmail(); in final ver */
                String body = binding.announcementBody.getText().toString();
                Announcement announcement = new Announcement(title, author, body);

                if (isReadyToPost(announcement)) {
                    announcementsRef.push().setValue(announcement);
                    Toast.makeText(
                            getActivity(), "Announcement posted", Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            getActivity(), "Please fill out all fields", Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        binding.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PostAnnouncementFragment.this)
                        .navigate(R.id.action_First2Fragment_to_Second2Fragment);
            }
        });

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(PostAnnouncementFragment.this)
                        .navigate(R.id.action_First2Fragment_to_homeFragment2);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}