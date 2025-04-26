package com.example.a2_2081_dev_bhatt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSignUp(View v) {

        //finding user inputs
        TextView tvuserName = findViewById(R.id.editTextUserName);
        TextView tvpasswords = findViewById(R.id.editTextPassword);
        TextView tvConfPass = findViewById(R.id.editTextConfirmPassword);

        //getting the text and converting it to string
        String userName = tvuserName.getText().toString();
        String password = tvpasswords.getText().toString();
        String ConfPass = tvConfPass.getText().toString();

        //if statement for confirm password does not match password using toast
        if (!password.equals(ConfPass)){
            Toast.makeText(this, "your password does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Storing user information once signed up so next time they could login
        SharedPreferences sharedPreferences = getSharedPreferences("User Information", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit(); //creating editor for shared pref

        editor.putString("username", userName); // editor for username (putting value and assigning them with a key)
        editor.putString("Password", password); //editor for password
        editor.commit();

        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show(); //message to show successful registration

        //If registration successful redirect to Login page
        Intent intent = new Intent(this, LoginPage.class);
        startActivity(intent);

    }

    public void onClickdirectToLoginPage(View view){

        Intent intent = new Intent(this, LoginPage.class);  //"already a user login" click
        startActivity(intent);

    }


}