package com.example.b07application.login;

import android.app.Activity;
import android.widget.Toast;

public class LoginPresenter {
    public ViewInterface view;
    public LoginModel model;
    public LoginPresenter(ViewInterface view, LoginModel model) {
        this.view = view;
        this.model=model;
    }
    public void changeViewActivity() {
        view.changeActivity();
    }

    public void makeViewToast(String message) {
        view.makeToast(message);
    }

    public void doLogin(String email, String password) {

        model.attemptLogin(email, password, this);

    }
    public void doRegister(String email, String password) {
        model.attemptRegister(email, password, this);
    }
}
