package com.example.a2_2081_dev_bhatt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2_2081_dev_bhatt.DatabaseProvider.EventCategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_List_Category#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_List_Category extends Fragment {


    private ArrayList<Category> listCategory = new ArrayList<>();
    public MyRecyclerAdapter_Category recyclerAdapter;
   private RecyclerView recyclerView;
   private RecyclerView.LayoutManager layoutManager;

   EventCategoryViewModel eventCategoryViewModel;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_List_Category() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_List_Category.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_List_Category newInstance(String param1, String param2) {
        Fragment_List_Category fragment = new Fragment_List_Category();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__list__category, container, false);



    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){


        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.CategoryList_RecyclerView);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize recyclerAdapter
        recyclerAdapter = new MyRecyclerAdapter_Category();
        recyclerView.setAdapter(recyclerAdapter);

        // Initialize CategoryManager
        CategoryManager categoryManager = new CategoryManager(requireContext());

        // Retrieve the list of category objects
        ArrayList<Category> categories = categoryManager.getCategoryList();

        // Set data to the adapter
        recyclerAdapter.setData(categories);

        eventCategoryViewModel = new ViewModelProvider(this).get(EventCategoryViewModel.class);
        eventCategoryViewModel.getAllCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                recyclerAdapter.setData(new ArrayList<>(categories));
                recyclerAdapter.notifyDataSetChanged();
            }
        });

    }




    public void refresh(){
        CategoryManager cm = new CategoryManager(getContext());
        recyclerAdapter.setData(cm.getCategoryList());
        recyclerAdapter.notifyDataSetChanged();
    }




    //Declaring retrieved/saved values of category elements
    public class CategoryManager {
        private Context context;
        public CategoryManager(Context context) {
            this.context = context;
        }
        public ArrayList<Category> getCategoryList() {
            ArrayList<Category> categoryList = new ArrayList<>();

            // Retrieve the saved JSON string from SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("CategoryList", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("categoryList", null);

            if (json != null) {
                // Parse the JSON string back into a list of category objects using Gson
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Category>>() {}.getType();
                categoryList = gson.fromJson(json, type);
            }
            return categoryList;
        }

    }

}