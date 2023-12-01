package com.example.b07application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.b07application.databinding.FragmentSecondBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import users.User;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private FirebaseAuth mAuth;

    FirebaseDatabase db;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(binding.registrationEmail.getText());
                String password = String.valueOf(binding.registrationPassword.getText());

                if(email.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter an email.", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter a password.", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //We add user objects in our database to track admins,
                                        //as firebase does not provide admin capabilities
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        DatabaseReference ref = db.getReference("users");
                                        ref.push().setValue(new User(false, user.getUid()));

                                        // Sign in success, update UI
                                        Toast.makeText(getActivity(), "Account successfully created.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getActivity(), "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}