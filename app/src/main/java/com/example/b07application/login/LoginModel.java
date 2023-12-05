package com.example.b07application.login;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.b07application.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import Misc.SessionInfo;
import users.User;

public class LoginModel {
    public FirebaseAuth mAuth;
    public FirebaseDatabase db;

    public LoginModel() {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance("https://b07firebase-default-rtdb.firebaseio.com/");
    }

    public void attemptLogin(String email, String password, LoginPresenter presenter) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            getUserInfo(presenter);
                        } else {
                            presenter.makeViewToast("Error: login failed");

                        }
                    }
                });
    }
    public void attemptRegister(String email, String password, LoginPresenter presenter) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(presenter.getViewActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //We add user objects in our database to track admins,
                            //as firebase does not provide admin capabilities
                            FirebaseUser user = mAuth.getCurrentUser();
                            DatabaseReference ref = db.getReference("users");
                            ref.push().setValue(new User(false, user.getUid()));
                            presenter.changeViewActivity();

                        } else {
                            presenter.makeViewToast("Registration failed.");
                        }
                    }
                });


    }
    public void getUserInfo(LoginPresenter presenter) {
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference userRef = db.getReference("users");
        Query userQuery = userRef.orderByChild("uid").equalTo(user.getUid());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        User userExtraInfo = user.getValue(User.class);
                        SessionInfo.getInstance().isAdmin=userExtraInfo.admin;
                    }
                    presenter.changeViewActivity();

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
