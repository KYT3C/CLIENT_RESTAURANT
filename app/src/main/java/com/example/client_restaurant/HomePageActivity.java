package com.example.client_restaurant;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    RecyclerView recyclerView;

    List<Item> mData;
    MenuAdapter menuAdapter;

    public static final int MENU_CODE = 0;
    public static final int TICKET_CODE = 0;
    public static final int TABLE_CODE = 0;
    public static final int STOCK_CODE = 0;
    public static final int ALCOHOLICDRINKS_CODE = 0;
    public static final int SETTINGS_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        mData = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mData.add(new Item(R.drawable.ic_menu,MENU_CODE,"Menu Fragemnt"));

        menuAdapter = new MenuAdapter(HomePageActivity.this,mData);
        recyclerView.setAdapter(menuAdapter);

        HomePageFragment homePageFragment = new HomePageFragment();

        fragmentTransaction.replace(R.id.container,homePageFragment);
        fragmentTransaction.commit();
    }
}
