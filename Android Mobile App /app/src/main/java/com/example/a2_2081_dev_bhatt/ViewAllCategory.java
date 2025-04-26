package com.example.a2_2081_dev_bhatt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewAllCategory extends AppCompatActivity {

    private String location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_all_category);

        //Method to implement toolbar and Back button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Show the back button in the toolbar

        toolbar.setNavigationOnClickListener(v -> onBackPressed()); // Handle back button click

        };

    public void getLocation(){
        New_CategoryForm new_categoryForm = new New_CategoryForm();
        location = new_categoryForm.accessLocation();
    }

    //onclick on this button in category card layout will lead you to map activity
//    public void onClickLocation(View view) {
//        Intent intent = new Intent(this, A3MapsActivity.class);
//        intent.putExtra("location", location);
//        startActivity(intent);
//    }
}
