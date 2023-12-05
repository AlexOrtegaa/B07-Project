package com.example.b07application.login;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.b07application.HomeActivity;

public interface ViewInterface {
    void changeActivity();
    void makeToast(String message);
}
