package com.telran.borislav.masterhairsalonproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.telran.borislav.masterhairsalonproject.Fragments.ChoseRegistration;
import com.telran.borislav.masterhairsalonproject.Fragments.ClientFragments.RegisterFragment;
import com.telran.borislav.masterhairsalonproject.Fragments.LoginFragment;
import com.telran.borislav.masterhairsalonproject.Fragments.MasterFragments.FragmentRegistration;
import com.telran.borislav.masterhairsalonproject.Utilitis.Utils;

public class LoginActivity extends AppCompatActivity implements LoginFragment.TransactionControllerListener,
        FragmentRegistration.StartMainActivityListener, ChoseRegistration.ChoseRegistrationListener, RegisterFragment.RegisterFragmentListener {
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        manager = getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        loginFragment.setListener(this);
        transaction = manager.beginTransaction();
        transaction.add(R.id.fragment_login_registration_container, loginFragment, Utils.LOGIN_FRAGMENT);
        transaction.commit();
        SharedPreferences sharedPreferences = getSharedPreferences(Utils.AUTH, MODE_PRIVATE);
        if (!sharedPreferences.getString(Utils.TOKEN, "").isEmpty()) {
            if (sharedPreferences.getBoolean(Utils.MASTER_OR_CLIENT, false)) {
                startActivityForResult(new Intent(this, PrivateAccountActivity.class), 1);
            } else {
                startActivityForResult(new Intent(this, SecondActivity.class), 1);
            }
        }


    }

    @Override
    public void goToNextFragment() {
        ChoseRegistration choseRegistration = new ChoseRegistration();
        choseRegistration.setListener(this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_login_registration_container, choseRegistration, Utils.CHOSE_CLIENT_MASTER);
        transaction.addToBackStack(Utils.CHOSE_CLIENT_MASTER);
        transaction.commit();
    }

    @Override
    public void goToNextActivity() {
        manager.popBackStack();

        startActivityForResult(new Intent(this, PrivateAccountActivity.class), 1);
    }

    @Override
    public void goToClientActivity() {
        startActivityForResult(new Intent(this, SecondActivity.class), 1);
    }

    @Override
    public void showError(String s) {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void startMainActivity() {
        startActivityForResult(new Intent(this, PrivateAccountActivity.class), 1);
    }

    @Override
    public void myError(String s) {
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            finish();
        }
    }

    @Override
    public void toMasterRegister() {
        FragmentRegistration fragmentRegistration = new FragmentRegistration();
        fragmentRegistration.setListener(this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_login_registration_container, fragmentRegistration, Utils.REGISTER_FRAGMENT);
        transaction.addToBackStack(Utils.REGISTER_FRAGMENT);
        transaction.commit();
    }

    @Override
    public void toClientRegister() {
        RegisterFragment registerFragment = new RegisterFragment();
        registerFragment.setListener(this);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_login_registration_container, registerFragment, Utils.REGISTER_FRAGMENT);
        transaction.addToBackStack(Utils.REGISTER_FRAGMENT);
        transaction.commit();
    }

    @Override
    public void toClientActivity() {
        manager.popBackStack();
        startActivityForResult(new Intent(this, SecondActivity.class), 1);
    }
}
