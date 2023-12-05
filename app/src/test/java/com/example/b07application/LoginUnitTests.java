package com.example.b07application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Handler;

import androidx.constraintlayout.utils.widget.MockView;

import com.example.b07application.login.FirstFragment;
import com.example.b07application.login.LoginModel;
import com.example.b07application.login.LoginPresenter;
import com.example.b07application.login.SecondFragment;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginUnitTests {
    @Mock
    FirstFragment loginView;
    @Mock
    SecondFragment registerView;
    @Mock
    LoginModel model;

    @Test
    public void testActivityChangeLogin() {
        LoginPresenter presenter = new LoginPresenter(loginView, model);

        presenter.changeViewActivity();
        verify(loginView).changeActivity();

    }
    @Test
    public void testToastLogin() {
        LoginPresenter presenter = new LoginPresenter(loginView, model);

        presenter.makeViewToast("test");
        verify(loginView).makeToast("test");


    }
    @Test
    public void testLogin() {

        LoginPresenter presenter = new LoginPresenter(loginView, model);
        presenter.doLogin("testemail@gmail.com", "password");
        verify(model).attemptLogin("testemail@gmail.com", "password", presenter);
    }

    @Test
    public void testActivityChangeRegister() {
        LoginPresenter presenter = new LoginPresenter(registerView, model);

        presenter.changeViewActivity();
        verify(registerView).changeActivity();

    }
    @Test
    public void testToastRegister() {
        LoginPresenter presenter = new LoginPresenter(registerView, model);

        presenter.makeViewToast("test");
        verify(registerView).makeToast("test");


    }
    @Test
    public void testRegister() {

        LoginPresenter presenter = new LoginPresenter(registerView, model);
        presenter.doRegister("testemail@gmail.com", "password");
        verify(model).attemptRegister("testemail@gmail.com", "password", presenter);
    }
}
