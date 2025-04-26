package com.example.a2_2081_dev_bhatt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.a2_2081_dev_bhatt.DatabaseProvider.EventCategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class New_CategoryForm extends AppCompatActivity {

    private EventCategoryViewModel eventCategoryViewModel;

    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category_form);

        MyRecyclerAdapter_Category myRecyclerAdapter = new MyRecyclerAdapter_Category();

        eventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);

        categoryList = new ArrayList<>();

        MyRecyclerAdapter_Category myRecyclerAdapterCategory = new MyRecyclerAdapter_Category();
        EventCategoryViewModel eventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);

        eventCategoryViewModel.getAllCategories().observe(this, newData -> {
            // cast List<Item> to ArrayList<Item>
            myRecyclerAdapterCategory.setData(new ArrayList<Category>(newData));
            myRecyclerAdapterCategory.notifyDataSetChanged();
        });

        }





    //Method to generate categoryId
    public void generateCategoryId() {
        TextView tvCatId = findViewById(R.id.editTextCategoryId);
        String generatedString = alphaNumericGenerator.generate();
        tvCatId.setText(generatedString);
    }

    //class for generating category id using the given format
    static class alphaNumericGenerator {

        public static String generate() {
            //formatting the category id
            return "C" + genRandChar(2) + "-" + genRandDigits(4);
        }

        private static String genRandChar(int count) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();  //we use stringBuilder for better string manipulation
            for (int i = 0; i < count; i++) {
                char randomChar = (char) (random.nextInt(26) + 'A');//generates char between A to Z
                sb.append(randomChar);
            }
            return sb.toString(); //converting stringBuilder to string
        }

        private static String genRandDigits(int count) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append(random.nextInt(10)); //generate random between 0 - 9
            }
            return sb.toString();  //converting stringBuilder to string
        }


    }

    public void onClickSaveCategory(View view) {
        generateCategoryId(); // Assuming this method exists and generates a unique category ID

        EditText tvCatID = findViewById(R.id.editTextCategoryId);
        EditText tvCatName = findViewById(R.id.editTextCategoryName);
        Switch issActive = findViewById(R.id.switchIsActive);
        EditText location = findViewById(R.id.locationName);

        String CatId = tvCatID.getText().toString();
        String CatName = tvCatName.getText().toString();
        boolean isActive = issActive.isChecked();
        String locationName = location.getText().toString();

        // Category name validation
        if (!isValidCategoryName(CatName)) {
            // Show error toast and return without saving
            Toast.makeText(this, "Invalid category name", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Category object
        Category category = new Category(CatId, CatName, 0, isActive,locationName); // Set event count to 0 initially

        // Retrieve existing JSON string from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("CategoryList", Context.MODE_PRIVATE);
        String existingJson = sharedPreferences.getString("categoryList", "[]");

        // Convert the existing JSON string to a list of categories
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<Category>>() {}.getType();
        ArrayList<Category> categoryList = gson.fromJson(existingJson, listType);

        // Add the new category to the list
        categoryList.add(category);

        // Convert the updated list to JSON
        String updatedJson = gson.toJson(categoryList);

        // Save updated JSON string to SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("categoryList", updatedJson);
        editor.apply();

        Toast.makeText(this, "Category successfully saved: " + CatId, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Press Refresh", Toast.LENGTH_SHORT).show();

        // Finish the current activity so that the user needs to manually navigate back to Dashboard
        finish();

        eventCategoryViewModel.insert(category);

    }




    // Method to validate category name
    private boolean isValidCategoryName(String categoryName) {
        // Check if the category name is not empty
        if (categoryName.isEmpty()) {
            return false;
        }

        // Check if the category name contains at least one alphabet character
        boolean hasAlphabet = false;
        for (char c : categoryName.toCharArray()) {
            if (Character.isLetter(c)) {
                hasAlphabet = true;
                break;
            }
        }
        if (!hasAlphabet) {
            return false;
        }

        // Check if the category name contains only letters, digits, spaces, or underscores
        for (char c : categoryName.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c) && c != '_') {
                return false;
            }
        }
        return true;
    }


    public String accessLocation(){
        EditText location = findViewById(R.id.locationName);
        String locationName = location.getText().toString();
        return locationName;
    }





}















