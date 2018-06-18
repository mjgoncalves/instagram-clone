package com.example.marcelo.instagramclone.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.BottomNavigationViewAdapter;
import com.example.marcelo.instagramclone.Utils.GridImageAdapter;
import com.example.marcelo.instagramclone.Utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private Context mContext = ProfileActivity.this;
    private final static int ACTIVITY_NUM = 2;
    private final int GRID_COL_NUM = 3;
    private ImageView profilePhoto;
    private ProgressBar progressBar;
    private GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();
        setupToolbar();
        setupActivityWidgets();
        setProfileImage();
        tempGridSetup();

    }

    private void setupToolbar(){

        Toolbar toolbar = findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);
        ImageView profileMenu = findViewById(R.id.profile_menu_settings);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating to account settings");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: Setting up profile photo!");
        String imgUrl = "https://http2.mlstatic.com/ursinho-de-pelucia-aviador-25cm-decoraco-anti-alergico-D_NQ_NP_830415-MLB25232632902_122016-F.jpg";
        UniversalImageLoader.setImage(imgUrl, profilePhoto, progressBar, "");


    }

    private void setupActivityWidgets(){

        Log.d(TAG, "setupActivityWidgets: Setting up profileActivity widgets!");
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        profilePhoto = findViewById(R.id.profile_photo);
        gridView = findViewById(R.id.gridview);


    }

    private void setupImageGrid(ArrayList<String> imgURLs){
        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview, "", imgURLs);
        gridView.setAdapter(adapter);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/GRID_COL_NUM;
        gridView.setColumnWidth(imageWidth);

    }

    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("http://1.bp.blogspot.com/-gB55QpVTLW4/VpjLCZOT5FI/AAAAAAAAk8s/kpOjWY5_ec0/s1600/IMG_3075.JPG");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        imgURLs.add("https://abrilviagemeturismo.files.wordpress.com/2016/12/11-120241176.jpg?quality=70&strip=info&w=680&h=453&crop=1");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        imgURLs.add("http://travelzom.com/wp-content/uploads/2017/06/bali-indonesia-2.jpg");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        imgURLs.add("https://abrilviagemeturismo.files.wordpress.com/2016/12/11-120241176.jpg?quality=70&strip=info&w=680&h=453&crop=1");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        imgURLs.add("http://1.bp.blogspot.com/-gB55QpVTLW4/VpjLCZOT5FI/AAAAAAAAk8s/kpOjWY5_ec0/s1600/IMG_3075.JPG");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        imgURLs.add("https://abrilviagemeturismo.files.wordpress.com/2016/12/11-120241176.jpg?quality=70&strip=info&w=680&h=453&crop=1");
        imgURLs.add("https://cdn.cheapoguides.com/wp-content/uploads/sites/3/2017/09/Otagi-Nenbutsu-ji-Shrine.jpg");
        setupImageGrid(imgURLs);

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
