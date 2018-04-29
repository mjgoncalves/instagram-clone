package com.example.marcelo.instagramclone.home;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.BottomNavigationViewAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    private Context mContext = HomeActivity.this;
    private final static int ACTIVITY_NUM = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBottomNavigationView();
        setupViewPager();
    }

    /* Responsible for adding the three fragmenst: camera, home and messages */
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragments(new CameraFragment());
        adapter.addFragments(new HomeFragment());
        adapter.addFragments(new MessagesFragment());
        ViewPager viewPager = findViewById(R.id.container);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
        tabLayout.getTabAt(1).setIcon(R.drawable.instagram_logo);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_arrow);

    }

    /* Responsible for setting up the the bottom menu */

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
