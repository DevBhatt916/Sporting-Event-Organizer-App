package com.example.a2_2081_dev_bhatt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Fragment_List_Event extends Fragment {

    private ArrayList<Event> listEvent = new ArrayList<>();
    private MyRecyclerAdapter_Event recyclerAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_List_Event() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_List_Event.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_List_Event newInstance(String param1, String param2) {
        Fragment_List_Event fragment = new Fragment_List_Event();
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
        return inflater.inflate(R.layout.fragment__list__event, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.eventList_RecyclerView);
        layoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize recyclerAdapter
        recyclerAdapter = new MyRecyclerAdapter_Event();
        recyclerView.setAdapter(recyclerAdapter);

        // Initialize EventManager
        EventManager eventManager = new EventManager(requireContext());

        // Retrieve the list of event objects
        ArrayList<Event> events = eventManager.getEventList();

        // Set data to the adapter
        recyclerAdapter.setData(events);
    }

    //Declaring retrieved/saved values of event elements
    public class EventManager {
        private Context context;

        public EventManager(Context context) {
            this.context = context;
        }

        public ArrayList<Event> getEventList() {
            ArrayList<Event> eventList = new ArrayList<>();

            // Retrieve the saved JSON string from SharedPreferences
            SharedPreferences sharedPreferences = context.getSharedPreferences("EventList", Context.MODE_PRIVATE);
            String json = sharedPreferences.getString("eventList", null);

            if (json != null) {
                // Parse the JSON string back into a list of event objects using Gson
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<Event>>() {}.getType();
                eventList = gson.fromJson(json, type);
            }
            return eventList;
        }
    }
}
