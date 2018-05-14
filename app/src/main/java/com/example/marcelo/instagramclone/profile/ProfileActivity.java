package com.example.marcelo.instagramclone.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.BottomNavigationViewAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private Context mContext = ProfileActivity.this;
    private final static int ACTIVITY_NUM = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();
        setupToolBar();
    }

    private void setupToolBar(){
        Toolbar toolbar = findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick: clicked menu item" + item);
                switch (item.getItemId()){

                    case R.id.profileMenu:
                        Log.d(TAG, "onMenuItemClick: navigation to profile preferences");
                        break;
                }
                return false;
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }
}
