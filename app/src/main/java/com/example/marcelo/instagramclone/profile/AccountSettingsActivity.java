package com.example.marcelo.instagramclone.profile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.marcelo.instagramclone.R;

public class AccountSettingsActivity extends AppCompatActivity {
    private static final String TAG = "AccountSettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: started!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
    }
}
