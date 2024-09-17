package com.example.assignment1_fit2081_32781555.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment1_fit2081_32781555.R;
import com.example.assignment1_fit2081_32781555.SMS.SMSReceiver;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    EditText etPassword;
    EditText etUserName;
    EditText etConfirmPassword;

    SharedPreferences sharedPreferences;
    Toolbar myToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUserName = findViewById(R.id.etUsernameSignup);
        etPassword = findViewById(R.id.etPasswordSignup);
        etConfirmPassword = findViewById(R.id.etConfirmPasswordSignup);
        sharedPreferences = getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE);

        //Request Permission from user to recieve,read and send sms
        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        //Register receiver SMS Receiver
        registerReceiver(
                new SMSReceiver(),
                new IntentFilter("android.provider.Telephony.SMS_RECEIVED"),
                RECEIVER_EXPORTED);
        setUpToolBar();
    }

    private void setUpToolBar(){
        myToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment 2");
    }
    public void Signup(View view){

        //Variables of username and passwords entered in String format
        String str_UserName = etUserName.getText().toString();
        String str_Password = etPassword.getText().toString();
        String str_ConfirmPassword = etConfirmPassword.getText().toString();

        //Creating sharedPreferences editor to store username and password into file
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (str_UserName.isEmpty() || str_Password.isEmpty()){
            ShortToastMessage("Please do not leave empty fields");
            return;
        }

        //Check if the passwords match
        if (str_Password.equals(str_ConfirmPassword)){

            //Store the entered values of username and password into file
            //Use the username as the key to ensure the binding of the password to username
            editor.putString(str_UserName,str_Password);
            editor.apply();

            //User Feedback
            ShortToastMessage("Successfully signed up!");

            //Bring user to Login page
            Login(view);
        }
        else{
            //Error Message
            ShortToastMessage("Passwords do not match");
        }
        editor.putString("KEY_LAST_ENTERED_USERNAME",str_UserName);
        editor.apply();
    }

    // To Launch the Login Page
    public void Login(View view){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void ShortToastMessage(String message){
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}