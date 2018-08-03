package com.example.marcelo.instagramclone.profile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marcelo.instagramclone.Models.Users;
import com.example.marcelo.instagramclone.Models.UsersAccountSettings;
import com.example.marcelo.instagramclone.Models.UsersSettings;
import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.FirebaseMethods;
import com.example.marcelo.instagramclone.Utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private CircleImageView mProfilePhoto;
    private EditText mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private UsersSettings mUserSettings;
    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;
    private String userID;
    private static final String TAG = "EditProfileFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        firebaseMethods = new FirebaseMethods(getActivity());
        setupFirebaseAuth();
        setupBackArrow(view);
        setupCheckmark(view);
        setupWidgets(view);
        return view;
    }

    private void setupCheckmark(View view) {

        Log.d(TAG, "setupCheckmark: Attempting to save changes!");
        ImageView checkmark = view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfileSettings();
            }
        });
    }

    private void setupWidgets(View view){
        Log.d(TAG, "setupWidgets: Setting up EditProfileFragment widgets!");
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mDisplayName = view.findViewById(R.id.display_name);
        mUsername = view.findViewById(R.id.username);
        mWebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mEmail = view.findViewById(R.id.email);
        mPhoneNumber = view.findViewById(R.id.phone_number);
    }

    /**
     * Retrieves data contained in the widgets and submits it to the database
     * Before doing so it checks if the username chosen is unique
     */
    private void saveProfileSettings(){

        final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();
        final String website = mWebsite.getText().toString();
        final String description = mDescription.getText().toString();
        final String email = mEmail.getText().toString();
        final Long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = new Users();
                Log.d(TAG, "onDataChange: CURRENT USERNAME " + users.getUsername());

                // case 1: the user did not change their name
                if (!mUserSettings.getUsers().getUsername().equals(username)){
                    checkIfUsernameExists(username);


                }
                // case 2: the user changed their name therefore we have to check uniqueness
                else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

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

                if (!dataSnapshot.exists()){
                    // add username
                    firebaseMethods.updateUsername(username);
                    Log.d(TAG, "onDataChange: USERNAME CHANGED!!!");
                    Toast.makeText(getActivity(), getString(R.string.username_saved), Toast.LENGTH_SHORT)
                            .show();
                }

                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    Toast.makeText(getActivity(), getString(R.string.username_exists), Toast.LENGTH_SHORT).show();
                    if (singleSnapshot.exists()){
                        Log.d(TAG, "onDataChange: FOUND A MATCH: " + singleSnapshot.getValue(Users.class).getUsername());
                        Toast.makeText(getActivity(), getString(R.string.username_exists), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void setProfileWidgets(UsersSettings usersSettings){
        Log.d(TAG, "setProfileWidgets: Setting widgets with data retrieving from database!");

        mUserSettings = usersSettings;
        UsersAccountSettings settings = usersSettings.getUsersAccountSettings();
        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(usersSettings.getUsers().getEmail());
        mPhoneNumber.setText(String.valueOf(usersSettings.getUsers().getPhone_number()));

    }

    private  void setupBackArrow(View view){

        ImageView imageView = view.findViewById(R.id.backarrow);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(getActivity()).finish();
            }
        });
    }

    //************************************* FIREBASE ************************************************

    private void setupFirebaseAuth(){

        Log.d(TAG, "setupFirebaseAuth: Setting up firebase.auth!");
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid(); // pesquisar quando envolver em Objects.requireNoNull
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

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // retrieve information from the database

                setProfileWidgets(firebaseMethods.getUsersSettings(dataSnapshot));

                // retrieve images from the user in question

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
