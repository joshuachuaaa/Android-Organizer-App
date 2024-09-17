package com.example.assignment1_fit2081_32781555.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignment1_fit2081_32781555.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    EditText etUserName;
    EditText etPassword;
    Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_app);
        sharedPreferences = getSharedPreferences("LOGIN_DETAILS", Context.MODE_PRIVATE);

        etUserName = findViewById(R.id.etUsernameLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        etUserName.setText(sharedPreferences.getString("KEY_LAST_ENTERED_USERNAME",""));
        setUpToolBar();
    }

    private void setUpToolBar(){
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Objects.requireNonNull(getSupportActionBar()).setTitle("Assignment 2");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void Login(View view) {

        //Retrieve EditText String values
        String str_Username = etUserName.getText().toString();
        String str_Password = etPassword.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();


        //Check if username or password is empty
        if (str_Username.isEmpty()){
            ShortToastMessage("Please enter your username");}

        else if (str_Password.isEmpty()){
            ShortToastMessage("Please enter your password");}

        else{

            try {
                //Retrieving the password based of the username as the key value,
                // if not found value is null
                String storedPassword = sharedPreferences.getString(str_Username,null);

                //This means that the username is not found in the file as we got the default value
                if (storedPassword == null) {
                    ShortToastMessage("This username is not registered");
                }
                //If storedPassword is not null means we have valid value
                else{
                    //If it matches, login
                    if (storedPassword.equals(str_Password)){
                        ShortToastMessage("Logged in successfully!");
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }
                    //If it doesn't, password is wrong
                    else{
                        ShortToastMessage("Invalid password! Please try again!");
                    }
                }

            }
            //Catch any errors and handle exceptions
            catch(Exception e){
                ShortToastMessage("An error has occurred");
            }
        }

        //Update the last entered value into the file
        editor.putString("KEY_LAST_ENTERED_USERNAME",str_Username);

        //Apply the changes in the editor
        editor.apply();

    }

    //Back functionality
    public void back(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }


    private void ShortToastMessage(String input){
        Toast.makeText(LoginActivity.this,input,Toast.LENGTH_SHORT).show();
    }



}