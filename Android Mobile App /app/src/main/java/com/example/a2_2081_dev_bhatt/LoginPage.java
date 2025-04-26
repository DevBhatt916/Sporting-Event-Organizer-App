package com.example.a2_2081_dev_bhatt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //Brings the last 'username value' to login page automatically from Signup page
        EditText etLogUsername = findViewById(R.id.editTextLoginUsername);
        SharedPreferences sharedPreferences = getSharedPreferences("User Information", MODE_PRIVATE);
        if(sharedPreferences.contains("username")){
            etLogUsername.setText(sharedPreferences.getString("username", ""));
        }

    }

    public void onClickLogin(View v){
        TextView tvUsername = findViewById(R.id.editTextLoginUsername);
        TextView tvPassword = findViewById(R.id.editTextLoginPassword);

        String Username = tvUsername.getText().toString();
        String Password = tvPassword.getText().toString();

        //verifying saved information form sign-up page
        SharedPreferences sharedPreferences = getSharedPreferences("User Information", MODE_PRIVATE);
        String InfoUser = sharedPreferences.getString("username" , "");
        String InfoPass = sharedPreferences.getString("Password", "");

        //If login-in successful directing to Dashboard Activity or else showing an error toast
        if (Username.equals(InfoUser) && Password.equals(InfoPass)){
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
            Toast.makeText(this,  "Login Successful", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(this,  "Incorrect username or password.", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickToSignUp(View v){

        Intent intent = new Intent(this, MainActivity.class); //new user sign-up option
        startActivity(intent);
    }

}