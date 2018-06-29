package com.example.marcelo.instagramclone.login;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    String email, password, username;
    EditText mEmail, mPassword, mUsername;
    ProgressBar mProgressBar;
    TextView loadingPleaseWait;
    Button btnRegister;
    FirebaseMethods firebaseMethods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d(TAG, "onCreate: Started!");
        firebaseMethods = new FirebaseMethods(this);
        initWidgets();
        setupFirebaseAuth();
        init();
    }

    // Initialize the activity widgets

    private void initWidgets(){
        Log.d(TAG, "initWidgets: Initializing up the widgets!");

        mProgressBar = findViewById(R.id.registerProgressBar);
        mEmail = findViewById(R.id.txt_email);
        mPassword = findViewById(R.id.txt_password);
        mUsername = findViewById(R.id.txt_username);
        loadingPleaseWait = findViewById(R.id.txt_pleaseWait);
        mProgressBar.setVisibility(View.GONE);
        loadingPleaseWait.setVisibility(View.GONE);
        btnRegister = findViewById(R.id.btn_register);
    }

    private void init(){

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                password = mPassword.getText().toString();
                username = mUsername.getText().toString();

                if (checkInputs(email, password, username)){
                    mProgressBar.setVisibility(View.VISIBLE);
                    loadingPleaseWait.setVisibility(View.VISIBLE);
                    firebaseMethods.registerNewEmail(email, password, username);

                }
            }
        });
    }

    private boolean checkInputs(String email, String password, String username){
        Log.d(TAG, "checkInputs: Checking inputs for null values!");
        if (email.equals("") || password.equals("") || username.equals("")){
            Toast.makeText(this, getString(R.string.emptyField), Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }

    }

    // ************************************* FIREBASE **********************************************

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: Cheching string if null!");
        if (string.equals("")){
            return true;

        }else {
            return false;
        }
    }

    private void setupFirebaseAuth(){

        Log.d(TAG, "setupFirebaseAuth: Setting up firebase.auth!");
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: user sign_in" + user.getUid());

                }else {
                    Log.d(TAG, "onAuthStateChanged: user sign_out");
                }
            }
        };
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthStateListener != null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
