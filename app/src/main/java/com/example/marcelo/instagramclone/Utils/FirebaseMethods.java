package com.example.marcelo.instagramclone.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";
    private Context context;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private String userID;

    public FirebaseMethods(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }

    }

    /**
     * Register a new e-mail and password to firebase authentication.
     * @param email
     * @param password
     * @param username
     */

    public void registerNewEmail(String email, String password, String username){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //updateUI(null);


                        } else if (task.isSuccessful()){
                            // Sign in success, update UI with the signed-in user's information
                            userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Log.d(TAG, "createUserWithEmail:success " + userID);

                            //updateUI(user);

                        }


                        // ...
                    }
                });

    }
}
