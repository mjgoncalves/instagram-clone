package com.example.marcelo.instagramclone.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.marcelo.instagramclone.Models.Users;
import com.example.marcelo.instagramclone.Models.UserAccountSettings;
import com.example.marcelo.instagramclone.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FirebaseMethods {

    private static final String TAG = "FirebaseMethods";
    private Context context;
    private FirebaseAuth mAuth;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    public FirebaseMethods(Context context) {
        this.context = context;
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

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



                        } else if (task.isSuccessful()){
                            // send verification email
                            sendVerificationEmail();
                            userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Log.d(TAG, "createUserWithEmail:success " + userID);

                        }

                    }
                });
    }

    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot){
        Log.d(TAG, "checkIfUsernameExists: Checks if " + username + " already exists!");
        Users users = new Users();
        for (DataSnapshot ds: dataSnapshot.child(userID).getChildren()){
            Log.d(TAG, "checkIfUsernameExists: datasnapshot: " + ds);
            users.setUsername(Objects.requireNonNull(ds.getValue(Users.class)).getUsername());
            
            if (StringManipulation.condenseUsername(users.getUsername()).equals(username)){
                Log.d(TAG, "checkIfUsernameExists: Found a match:" + users.getUsername());
                return true;

            }
        }

        return false;
    }

    /**
     * Adding information to the users and user_account_settings nodes
     * @param email
     * @param username
     * @param description
     * @param website
     * @param profile_photo
     */
    public void AddNewUser(String email, String username, String description, String website, String profile_photo){

        Users users = new Users(userID, 1, email, StringManipulation.condenseUsername(username));
        myRef.child(context.getString(R.string.dbase_user))
        .child(userID)
        .setValue(users);

        UserAccountSettings settings = new UserAccountSettings(

                description,
                username,
                0,
                0,
                0,
                profile_photo,
                username,
                website);


        myRef.child(context.getString(R.string.dbase_account_settings))
                .child(userID)
                .setValue(settings);
    }

    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){

            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                            }else {

                                //Toast.makeText(context, getString(R.string.emailNotSent), Toast.LENGTH_SHORT).show();
                                Toast.makeText(context, "Verification email couldn't be sent!", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
        }
    }
}




















