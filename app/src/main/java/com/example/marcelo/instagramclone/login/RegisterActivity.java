package com.example.marcelo.instagramclone.login;

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

import com.example.marcelo.instagramclone.models.Users;
import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.FirebaseMethods;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String email, password, username;
    private EditText mEmail, mPassword, mUsername;
    private ProgressBar mProgressBar;
    private TextView loadingPleaseWait;
    private Button btnRegister;
    private FirebaseMethods firebaseMethods;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private String append = "";


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
                    firebaseMethods.registerNewEmail(email, password);

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


    /**
     * Checks if @param username exists in the database
     * @param username
     */
    private void checkIfUsernameExists(final String username){
        Log.d(TAG, "checkIfUsernameExists: Checking if " + username + " already exists!");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbase_user))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){
                        Log.d(TAG, "onDataChange: FOUND A MATCH: " + singleSnapshot.getValue(Users.class).getUsername());
                        append = Objects.requireNonNull(myRef.push().getKey()).substring(3, 10);
                        Log.d(TAG, "onDataChange: Username already exists. Appending random string to name :" + append + "!");

                    }
                }

                String mUsername = "";
                mUsername = username + append; // seria o caso de colocar essa linha dentro do if?

                // Add new user to the database

                firebaseMethods.addNewUser(mUsername, "", "", "", "");
                Toast.makeText(RegisterActivity.this,getString(R.string.signup_success), Toast.LENGTH_SHORT).show();
                mAuth.signOut();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupFirebaseAuth(){

        Log.d(TAG, "setupFirebaseAuth: Setting up firebase.auth!");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    Log.d(TAG, "onAuthStateChanged: user sign_in" + user.getUid());
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            checkIfUsernameExists(username);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    finish();

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
