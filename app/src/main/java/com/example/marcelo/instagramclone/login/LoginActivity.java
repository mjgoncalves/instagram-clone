package com.example.marcelo.instagramclone.login;

import android.content.Context;
import android.content.Intent;
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
import com.example.marcelo.instagramclone.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private Context mContext;
    private ProgressBar progressBar;
    private EditText mEmail, mPassword;
    private TextView pleaseWait;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: Started!");

        progressBar = findViewById(R.id.loginProgressBar);
        mEmail = findViewById(R.id.input_email);
        mPassword = findViewById(R.id.input_password);
        pleaseWait = findViewById(R.id.txt_pleaseWait);
        mContext = LoginActivity.this;
        progressBar.setVisibility(View.GONE);
        pleaseWait.setVisibility(View.GONE);
        setupFirebaseAuth();
        init();

    }

    private boolean isStringNull(String string){
        Log.d(TAG, "isStringNull: Cheching string if null!");
        if (string.equals("")){
            return true;

        }else {
            return false;
        }
    }

    // ************************************* FIREBASE **********************************************

    // Initialize the button for logging in

    private void init(){

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: Attempting to log in!");

                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (isStringNull(email) && isStringNull(password)){
                    Toast.makeText(LoginActivity.this, "All the fields must be filled!",
                            Toast.LENGTH_LONG).show();

                }else {

                    progressBar.setVisibility(View.VISIBLE);
                    pleaseWait.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(TAG, "onComplete: " + task.isSuccessful());
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    if (task.isSuccessful()) {

                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:Login successful");
                                        try {
                                            //assert user != null; check later how exactly to use this statement!
                                            if (user.isEmailVerified()){
                                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }else{
                                                Toast.makeText(mContext, getString(R.string.emailNotVefied), Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                pleaseWait.setVisibility(View.GONE);
                                                mAuth.signOut();
                                            }

                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException: " + e.getMessage());
                                            
                                        }

                                        /**

                                        progressBar.setVisibility(View.GONE);
                                        pleaseWait.setVisibility(View.GONE);

                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_success),
                                                Toast.LENGTH_SHORT).show();

                                         */

                                    } else {

                                        // If sign in fails, display a message to the user.
                                        Log.d(TAG, "signInWithEmail:failure", task.getException());

                                        progressBar.setVisibility(View.GONE);
                                        pleaseWait.setVisibility(View.GONE);

                                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                    }
            }

        });

        /**
         * Navigate to the HomeActivity if the user is logged in!!
         */
        if (mAuth.getCurrentUser() != null){
            Log.d(TAG, "init: Navigating to the register screen!");
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        TextView linkSignup = findViewById(R.id.linkSignup);
        linkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating to  register screen!");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });


        /**
         *  if the user is logged in then navigate to HomeActivity and finishes the current one
         *
         */
        if ( mAuth.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
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
