package com.example.client_restaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Item> mData;
    MenuAdapter menuAdapter;

    public static final int USER_CODE = 0;
    public static final int MENU_CODE = 1;
    public static final int TICKET_CODE = 2;
    public static final int TABLES_CODE = 3;
    public static final int STOPWATCH_CODE = 4;
    public static final int SETTINGS_CODE = 5;
    public static final int STOCK_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mData = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mData.add(new Item(R.drawable.ic_user,USER_CODE,"User Fragment"));
        mData.add(new Item(R.drawable.ic_menu,MENU_CODE,"Menu Fragemnt"));
        mData.add(new Item(R.drawable.ic_bill,TICKET_CODE,"Ticket Fragment"));
        mData.add(new Item(R.drawable.ic_tables,TABLES_CODE,"Tables Fragment"));
        mData.add(new Item(R.drawable.ic_stopwatch,STOPWATCH_CODE,"Stopwatch Fragment"));
        mData.add(new Item(R.drawable.ic_settings,SETTINGS_CODE,"Settings Fragment"));


        menuAdapter = new MenuAdapter(HomePageActivity.this,mData);
        recyclerView.setAdapter(menuAdapter);
    }
}
