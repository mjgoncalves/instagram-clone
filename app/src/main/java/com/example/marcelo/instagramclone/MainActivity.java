package com.example.marcelo.instagramclone;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marcelo.instagramclone.Utils.BottomNavigationViewAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;
    private final static int ACTIVITY_NUM = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigationView();
    }

    public void setupBottomNavigationView(){

        Log.d(TAG, "setupBottomNavigationView: Starting BottonNavigationViewEx");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.botton_navView);
        BottomNavigationViewAdapter.adaptBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewAdapter.enableNavigations(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
