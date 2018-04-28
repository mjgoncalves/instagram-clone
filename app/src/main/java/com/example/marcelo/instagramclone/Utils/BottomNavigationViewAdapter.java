package com.example.marcelo.instagramclone.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.marcelo.instagramclone.MainActivity;
import com.example.marcelo.instagramclone.R;
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
}
