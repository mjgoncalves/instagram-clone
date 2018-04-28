package com.example.marcelo.instagramclone.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.marcelo.instagramclone.LikesActivity;
import com.example.marcelo.instagramclone.MainActivity;
import com.example.marcelo.instagramclone.ProfileActivity;
import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.SearchActivity;
import com.example.marcelo.instagramclone.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class BottomNavigationViewAdapter {
    private static final String TAG = "BottomNavigationViewAda";
    public static void adaptBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        Log.d(TAG, "adaptBottomNavigationView: adapting BottomNavigationViewEx");
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        bottomNavigationViewEx.enableAnimation(false);
    }
public static void enableNavigations(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_house:
                        Intent iOne = new Intent(context, MainActivity.class);
                        context.startActivity(iOne);
                        break;

                    case R.id.ic_search:
                        Intent iTwo = new Intent(context, SearchActivity.class);
                        context.startActivity(iTwo);
                        break;

                    case R.id.ic_android:
                        Intent iThree = new Intent(context, ProfileActivity.class);
                        context.startActivity(iThree);
                        break;

                    case R.id.ic_circle:
                        Intent iFour = new Intent(context, ShareActivity.class);
                        context.startActivity(iFour);
                        break;

                    case R.id.ic_alert:
                        Intent iFive = new Intent(context, LikesActivity.class);
                        context.startActivity(iFive);
                        break;
                }
                return false;
            }
        });
}

}
