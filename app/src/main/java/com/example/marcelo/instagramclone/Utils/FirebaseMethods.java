package com.example.marcelo.instagramclone.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.marcelo.instagramclone.models.Users;
import com.example.marcelo.instagramclone.models.UsersAccountSettings;
import com.example.marcelo.instagramclone.models.UsersSettings;
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
    private Context mContext;
    private FirebaseAuth mAuth;
    private String userID;
    private DatabaseReference myRef;

    public FirebaseMethods(Context context) {
        this.mContext = context;
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        if (mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
            Log.d(TAG, "FirebaseMethods: CONSTRUTOR" + mAuth.getCurrentUser().getUid() );
        }

    }

    /**
     * Register a new e-mail and password to firebase authentication.
     * @param email
     * @param password
     */
    public void registerNewEmail(String email, String password){
        Log.d(TAG, "registerNewEmail: TTYING TO REGISTER NEW EMAIL!!!");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
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

    /**
     * Adding information to the users and user_account_settings nodes
     * @param email
     * @param username
     * @param description
     * @param website
     * @param profile_photo
     */
    public void addNewUser(String email, String username, String description, String website, String profile_photo){
        Log.d(TAG, "addNewUser: ADDING USER FIELDS TO THE DATABASE!!!");

        Users users = new Users(userID, 1, email, StringManipulation.condenseUsername(username));

        myRef.child(mContext.getString(R.string.dbase_user)).child(userID).setValue(users);

        UsersAccountSettings settings = new UsersAccountSettings(

                description,
                username,
                0,
                0,
                0,
                profile_photo,
                StringManipulation.condenseUsername(username),
                website);


        myRef.child(mContext.getString(R.string.dbase_account_settings))
                .child(userID)
                .setValue(settings);
    }

    private void sendVerificationEmail(){
        Log.d(TAG, "sendVerificationEmail: TRYING TO SEND VERIFICATION EMAIL!!!");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){

            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                            }else {

                                //Toast.makeText(context, getString(R.string.emailNotSent), Toast.LENGTH_SHORT).show();
                                Toast.makeText(mContext, "Verification email couldn't be sent!", Toast.LENGTH_SHORT).show();


                            }

                        }
                    });
        }
    }

    /**
     *Retrieves the account settings for the user currently logged in
     * Database: user_account_settings node
     * @param dataSnapshot
     * @return
     */
    public UsersSettings getUsersSettings(DataSnapshot dataSnapshot){
        Log.d(TAG, "getUserAccountSettings: Retrieving user account_settings from firebase!");
        Users users = new Users();
        UsersAccountSettings usersAccountSettings = new UsersAccountSettings();


        for (DataSnapshot ds: dataSnapshot.getChildren()){

            // user_account_settings node
            if (ds.getKey().equals(mContext.getString(R.string.dbase_account_settings))){
                Log.d(TAG, "getUsersSettings: Datasnapshot " + ds);

                try {


                    usersAccountSettings.setDisplay_name(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getDisplay_name()
                    );

                    usersAccountSettings.setUsername(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getUsername()
                    );

                    usersAccountSettings.setWebsite(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getWebsite()
                    );

                    usersAccountSettings.setProfile_photo(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getProfile_photo()
                    );

                    usersAccountSettings.setFollowers(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getFollowers()
                    );

                    usersAccountSettings.setFollowing(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getFollowing()
                    );

                    usersAccountSettings.setPosts(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getPosts()
                    );

                    usersAccountSettings.setDescription(
                            ds.child(userID)
                                    .getValue(UsersAccountSettings.class)
                                    .getDescription());

                } catch (NullPointerException e) {
                    Log.d(TAG, "getUsersSettings: NullPointerException :" + e.getMessage());
                }

                Log.d(TAG, "getUsersSettings: Retrieves user account settings information " + usersAccountSettings.toString());

            }

            //user node
            if (ds.getKey().equals(mContext.getString(R.string.dbase_user))){
                Log.d(TAG, "getUsersSettings: " + ds);

                users.setUsername(
                        ds.child(userID)
                        .getValue(Users.class)
                        .getUsername()
                );

                users.setEmail(
                        ds.child(userID)
                        .getValue(Users.class)
                        .getEmail()
                );

                users.setPhone_number(
                        ds.child(userID)
                        .getValue(Users.class)
                        .getPhone_number()
                );

                users.setUser_id(
                        ds.child(userID)
                        .getValue(Users.class)
                        .getUser_id()
                );

                Log.d(TAG, "getUsersSettings: Retrieves users information " + users.toString());
            }

        }

        return new UsersSettings(users, usersAccountSettings);
    }

    public void updateUsername(String username) {
        Log.d(TAG, "updateUsername: Updating username to: " + username);
        myRef.child(mContext.getString(R.string.dbase_user))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);

        myRef.child(mContext.getString(R.string.dbase_account_settings))
                .child(userID)
                .child(mContext.getString(R.string.field_username))
                .setValue(username);
    }
}




















