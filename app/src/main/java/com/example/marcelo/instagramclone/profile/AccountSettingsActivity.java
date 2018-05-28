package com.example.marcelo.instagramclone.profile;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.BottomNavigationViewAdapter;
import com.example.marcelo.instagramclone.Utils.SectionsStatePagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class AccountSettingsActivity extends AppCompatActivity {
    private static final String TAG = "AccountSettingsActivity";
    private final static int ACTIVITY_NUM = 2;
    private Context mContext;
    private SectionsStatePagerAdapter pagerAdaper;
    private ViewPager mViewPager;
    private RelativeLayout mRelativLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started!");
        super.onCreate(savedInstanceState);
        mContext = AccountSettingsActivity.this;
        setContentView(R.layout.activity_account_settings);

        setupBottomNavigationView();

        mViewPager = findViewById(R.id.container);
        mRelativLayout = findViewById(R.id.rellayout1);
        setupListView();
        setupFragments();

        // setting up the backarrow for navigating back to profile activity
        ImageView backArrow = findViewById(R.id.backarrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to the profile activity");
                finish();
            }
        });
    }

    private void setupFragments() {

        pagerAdaper = new SectionsStatePagerAdapter(getSupportFragmentManager());
        pagerAdaper.addFragments(new EditProfileFragment(), getString(R.string.edit_profile_fragment)); // frag 1
        pagerAdaper.addFragments(new SignOutFragment(), getString(R.string.sign_out_fragment)); // frag 2
    }

    public void setViewPager(int fragmentNumber){
        mRelativLayout.setVisibility(View.GONE);
        Log.d(TAG, "setupViewPager: navigating to fragment #" + fragmentNumber);
        mViewPager.setAdapter(pagerAdaper);
        mViewPager.setCurrentItem(fragmentNumber);
        
    }
    private void setupListView(){
        Log.d(TAG, "setupListView: Initializing listview");
        ListView listView = findViewById(R.id.listview_account_settings);
        List<String> listOptions = new ArrayList<>();
        listOptions.add(getString(R.string.edit_profile_fragment));
        listOptions.add(getString(R.string.sign_out_fragment));

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_expandable_list_item_1, listOptions);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: navigating to fragment " + position);
                setViewPager(position);
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
}
