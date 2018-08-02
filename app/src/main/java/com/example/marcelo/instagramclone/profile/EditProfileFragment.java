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
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;

    //editprofile widgets
    private CircleImageView mProfilePhoto;
    private EditText mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;

    private static final String TAG = "EditProfileFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        firebaseMethods = new FirebaseMethods(getActivity());
        setupFirebaseAuth();
        setupBackArrow(view);
        //setProfileImage();

        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mDisplayName = view.findViewById(R.id.display_name);
        mUsername = view.findViewById(R.id.username);
        mWebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mEmail = view.findViewById(R.id.email);
        mPhoneNumber = view.findViewById(R.id.phone_number);
        return view;
    }

//    private void setupWidgets(){
//        Log.d(TAG, "setupWidgets: Setting up EditProfileFragment widgets!");
//        //mDisplayName = Objects.requireNonNull(getView()).findViewById(R.id.display_name);
//        mUsername = getView().findViewById(R.id.username);
//        mWebsite = Objects.requireNonNull(getView()).findViewById(R.id.website);
//        mDescription = Objects.requireNonNull(getView()).findViewById(R.id.description);
//        mEmail = Objects.requireNonNull(getView()).findViewById(R.id.email);
//        mPhoneNumber = Objects.requireNonNull(getView()).findViewById(R.id.phone_number);
//    }

    private void setProfileWidgets(UsersSettings usersSettings){
        Log.d(TAG, "setProfileWidgets: Setting widgets with data retrieving from database!");

        UsersAccountSettings settings = usersSettings.getUsersAccountSettings();
        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(usersSettings.getUsers().getEmail());
        mPhoneNumber.setText(String.valueOf(usersSettings.getUsers().getPhone_number()));

    }
//
//    private void setProfileImage(){
//        Log.d(TAG, "setProfileImage: setting profile image");
//        String imgURL = "www.jrtstudio.com/sites/default/files/ico_android.png";
//        UniversalImageLoader.setImage(imgURL, mProfilePhoto, null, "http://");
//    }

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
