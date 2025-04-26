package com.example.a2_2081_dev_bhatt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.FrameMetricsAggregator;
import androidx.core.graphics.Insets;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.a2_2081_dev_bhatt.DatabaseProvider.EventCategoryViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class Dashboard extends AppCompatActivity {


    NavigationView navigationView;
    Fragment_List_Category fragmentCategory;
    Fragment_List_Event fragmentEvent;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private EventCategoryViewModel eventCategoryViewModel;

    View tvGesture;
    private GestureDetector gestureDetector;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);

        // Implement toolbar
        Toolbar mytoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mytoolbar);

        // Handlers for navigation drawer
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MynavigationHandler());

        eventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);

        // Gestures
        tvGesture = findViewById(R.id.gesturesView);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent event) {

                onClickSaveEvent(null);
                return true;
            }
            @Override
            public void onLongPress(MotionEvent event) {
                super.onLongPress(event);

                UNDO();
            }

            @Override
            public boolean onDown(MotionEvent event) {
                return true;
            }
        });

        tvGesture.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                int action = event.getAction();


                return true; // Changed from false to true to indicate the touch event was handled
            }
        });
    }



    //separate onCreate for Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    // Different OnClick methods for options menu items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId(); //used to identify each items mentioned in options

        //Method for clearing event form
        if (itemId == R.id.options_clearEventForm) {
            UNDO(); //used 'UNDO' as it does the same thing of clearing all fields

            //Method for deleting all categories
        } else if (itemId == R.id.options_DeleteAllCategories){
            deleteAllCategories();
            Fragment_List_Category fragment = (Fragment_List_Category) getSupportFragmentManager().findFragmentById(R.id.fragment_category);
            fragment.refresh();

            deleteAllCategoriesdb();
        }
        // Method for deleting all events
        if (itemId == R.id.optionsDeleteAllEvents) {
            deleteAllEvents();
            Toast.makeText(this, "all events deleted", Toast.LENGTH_SHORT).show();
            Fragment_List_Category fragmentEvent = (Fragment_List_Category) getSupportFragmentManager().findFragmentById(R.id.fragment_category);
            deleteAllEventsdb();

            //Method Refresh
        } else if (itemId == R.id.options_refresh){
            fragmentCategory = (Fragment_List_Category) fragmentManager.findFragmentById(R.id.fragment_category);
            fragmentCategory.refresh();

        }
        return super.onOptionsItemSelected(item);
    }



    // Class for implementing methods for nav_drawer_menu options
    class MynavigationHandler implements NavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            int itemId = menuItem.getItemId();
            //Method
            if (itemId == R.id.nav_drawer_ViewCategories){
                Intent intent = new Intent(Dashboard.this, ViewAllCategory.class);
                startActivity(intent);



            } else if (itemId == R.id.nav_drawer_AddCategory){
                Intent intent = new Intent(Dashboard.this, New_CategoryForm.class);
                startActivity(intent);
            }

            if (itemId == R.id.nav_drawer_AllEvents){
                Intent intent = new Intent(Dashboard.this, ViewAllEvent.class);
                startActivity(intent);



            } else if (itemId == R.id.nav_drawer_Logout) {
                logout();
            }

            return true;


        }
    }
















    // New Event form

    //Method for generating eventID
    public static String generateAlphaNumeric() {
        return "E" + genRandomCharacters(2) + "-" + genRandomDigits(5); //formatting
    }

    private static String genRandomCharacters(int count) {     //to generate random characters
        Random random = new Random();
        StringBuilder sb = new StringBuilder(); //we use stringBuilder for better string manipulation
        for (int i = 0; i < count; i++) {
            char randomChar = (char) (random.nextInt(26) + 'A');
            sb.append(randomChar);
        }
        return sb.toString();//converting string builder to string
    }

    private static String genRandomDigits(int count) {         //generate random digits
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }



    //Method when you click floating action button
    public void onClickSaveEvent(View view) {
        EditText editTextEventId = findViewById(R.id.editTextEventId);
        EditText editTextEventName = findViewById(R.id.editTextEventName);
        EditText editTextEventCategoryId = findViewById(R.id.editTextEventCategoryId);
        EditText editTextTicketsAvailable = findViewById(R.id.editTextTicketsAvailable);
        Switch switchEventIsActive = findViewById(R.id.switchEventIsActive);

        String eventId = generateAlphaNumeric(); // Generate event ID using method
        String eventName = editTextEventName.getText().toString();
        String eventCategoryId = editTextEventCategoryId.getText().toString();
        int ticketsAvailable = Integer.parseInt(editTextTicketsAvailable.getText().toString());
        boolean eventIsActive = switchEventIsActive.isChecked();

        editTextEventId.setText(eventId);
        verifyFields();

        // Validate category name
        if (!isValidEventName(eventName)) {
            // Show error toast
            Toast.makeText(this, "Invalid event name", Toast.LENGTH_SHORT).show();
            return;
        }



        // Check if the category ID exists
        if (!isCategoryExists(eventCategoryId)) {
            // If category ID doesn't exist, show error
            Toast.makeText(this, "Category ID does not exist", Toast.LENGTH_SHORT).show();
            return; // Exit the method
        }

        // Create a new Event object
        Event event = new Event(eventId,eventName,eventCategoryId,ticketsAvailable,eventIsActive); // ???
        event.setEventID(eventId);
        event.setEventName(eventName);
        event.setCategoryName(eventCategoryId);
        event.setTicketsAvailable(ticketsAvailable);
        event.setActive(eventIsActive);

        // Retrieve existing JSON string from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("EventList", Context.MODE_PRIVATE);
        String existingJson = sharedPreferences.getString("eventList", "[]");

        // Convert the existing JSON string to a list of events
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
        ArrayList<Event> eventList = gson.fromJson(existingJson, listType);

        // Add the new event to the list
        eventList.add(event);

        // Convert the updated list to JSON
        String updatedJson = gson.toJson(eventList);

        // Save updated JSON string to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("eventList", updatedJson);
        editor.apply();

        /* Show a Snackbar with UNDO action
        Snackbar snackbar = Snackbar.make(view, "Event Saved successfully: " + eventId + " to " + eventCategoryId, Snackbar.LENGTH_SHORT);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UNDO();
            }
        });
        snackbar.show(); */

        Toast.makeText(this, "Event Saved successfully: " + eventId + " to " + eventCategoryId, Toast.LENGTH_SHORT).show();

        eventCategoryViewModel.insert(event);


    }




    // Method to validate event name
    private boolean isValidEventName(String eventName) {
        // Check if the category name is not empty
        if (eventName.isEmpty()) {
            return false;
        }

        // Check if the category name contains at least one alphabet character
        boolean hasAlphabet = false;
        for (char c : eventName.toCharArray()) {
            if (Character.isLetter(c)) {
                hasAlphabet = true;
                break;
            }
        }
        if (!hasAlphabet) {
            return false;
        }

        // Check if the category name contains only letters, digits, spaces, or underscores
        for (char c : eventName.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c) && c != '_') {
                return false;
            }
        }
        return true;
    }


    //Clears all fields for event form
    public void UNDO (){
        EditText editTexteventId = findViewById(R.id.editTextEventId);
        EditText editTextEventName = findViewById(R.id.editTextEventName);
        EditText editTextEventCategoryId = findViewById(R.id.editTextEventCategoryId);
        EditText editTextTicketsAvailable = findViewById(R.id.editTextTicketsAvailable);
        Switch switchEventIsActive = findViewById(R.id.switchEventIsActive);

        // Clearing the text fields
        editTexteventId.setText("");
        editTextEventName.setText("");
        editTextEventCategoryId.setText("");
        editTextTicketsAvailable.setText("");

        // Resetting the Switch to default state (assuming default state is off)
        switchEventIsActive.setChecked(false);

    }

    private boolean verifyFields() {
        EditText editTextEventName = findViewById(R.id.editTextEventName);
        EditText editTextEventCategoryId = findViewById(R.id.editTextEventCategoryId);
        EditText editTextTicketsAvailable = findViewById(R.id.editTextTicketsAvailable);


        if (TextUtils.isEmpty(editTextEventName.getText().toString().trim())) {
            Toast.makeText(this, "Please fill in the Event Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(editTextEventCategoryId.getText().toString().trim())) {
            Toast.makeText(this, "Please fill in the Event Category ID", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(editTextTicketsAvailable.getText().toString().trim())) {
            Toast.makeText(this, "Please fill in the Tickets Available", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    // Method to check if a category ID exists
    private boolean isCategoryExists(String categoryId) {
        // Retrieve existing JSON string from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("CategoryList", Context.MODE_PRIVATE);
        String existingJson = sharedPreferences.getString("categoryList", "[]");
        EditText editTexteventId = findViewById(R.id.editTextEventId);

        // Convert the existing JSON string to a list of categories
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Category>>() {}.getType();
        ArrayList<Category> categoryList = gson.fromJson(existingJson, listType);


        // Check if the category ID exists in the list
        for (Category category : categoryList) {
            if (category.getCategoryID().equals(categoryId)) {
                category.setEventCount(5);
                eventCategoryViewModel.updateCategory(category);

                return true; // Category ID exists
            }
        }
        // If the category ID doesn't exist, clear the EditText
        editTexteventId.setText(""); // Clear the EditText
        return false; // Category ID doesn't exist

    }





    //Method to delete all categories
    public void deleteAllCategories() {
        // Clear the list of categories stored in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("CategoryList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(new ArrayList<Category>());
        editor.putString("categoryList", jsonString);
        editor.apply();

        // Notify the user that all categories have been deleted
        Toast.makeText(this, "PRESS REFRESH", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllCategoriesdb() {
        eventCategoryViewModel.deleteAllCategories();
    }


    //method to delete all events
    public void deleteAllEvents() {
        // Clear the list of events stored in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("EventList", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonString = gson.toJson(new ArrayList<Category>());
        editor.putString("eventList", jsonString);
        editor.apply();

        // Notify the user that all categories have been deleted
        Toast.makeText(this, "All events have been deleted", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllEventsdb() {
        eventCategoryViewModel.deleteAllEvents();
    }


    // Logout method
    public void logout() {
        // Clear all the activities from the stack and start LoginActivity as a new task
        Intent intent = new Intent(Dashboard.this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        // Notify the user that logout was successful
        Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // get the type of Motion Event detected which is represented by a pre-defined integer value
        int action = event.getAction();

        // compare the detected event type against pre-defined values
        if (action == MotionEvent.ACTION_DOWN){
            Toast.makeText(this, "debug action UP", Toast.LENGTH_SHORT).show();
        } else if (action == MotionEvent.ACTION_UP){
            Toast.makeText(this, "debug action UP", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


}