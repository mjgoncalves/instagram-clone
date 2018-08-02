package com.example.marcelo.instagramclone.profile;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.marcelo.instagramclone.Models.UsersAccountSettings;
import com.example.marcelo.instagramclone.Models.UsersSettings;
import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.BottomNavigationViewAdapter;
import com.example.marcelo.instagramclone.Utils.FirebaseMethods;
import com.example.marcelo.instagramclone.Utils.UniversalImageLoader;
import com.example.marcelo.instagramclone.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private final static int ACTIVITY_NUM = 2;
    private TextView editProfile, mPosts, mFollowing, mFollowers, mDisplayName, mUsername, mWebsite, mDescription;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private GridView mGridView;
    private Toolbar mToolbar;
    private BottomNavigationViewEx mViewEx;
    private ImageView profileMenu;
    private View view;
    private Context mContext;
    //FireBase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseDatabase mDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods firebaseMethods;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.d(TAG, "onCreateView: Started!");
        setupWidgets();
        firebaseMethods = new FirebaseMethods(mContext);
        setupBottomNavigationView();
        setupToolbar();
        setupFirebaseAuth();
        navigateToEditProfile();
        return view;
    }

    private void setupWidgets() {
        mContext = getActivity();
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        mDisplayName = view.findViewById(R.id.display_name);
        mUsername = view.findViewById(R.id.username);
        mWebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mPosts = view.findViewById(R.id.posts);
        mFollowers = view.findViewById(R.id.txtViewFollower);
        mFollowing = view.findViewById(R.id.txtViewFollowing);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.GONE);
        mGridView = view.findViewById(R.id.gridview);
        profileMenu = view.findViewById(R.id.profile_menu_settings);
        mViewEx = view.findViewById(R.id.botton_navView);
        mToolbar = view.findViewById(R.id.profileToolBar);
        editProfile = view.findViewById(R.id.textEditProfile);



    }

        public void setupBottomNavigationView(){

        Log.d(TAG, "setupBottomNavigationView: Starting BottonNavigationViewEx");
        BottomNavigationViewAdapter.adaptBottomNavigationView(mViewEx);
        BottomNavigationViewAdapter.enableNavigations(mContext, mViewEx);
        Menu menu = mViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
        private void setupToolbar(){

            //((ProfileActivity)getActivity()).setSupportActionBar(mToolbar);
            ((ProfileActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbar);

            profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Navigating to account settings");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setProfileWidgets(UsersSettings usersSettings){
        Log.d(TAG, "setProfileWidgets: Setting widgets with data retrieving from database!");

        UsersAccountSettings settings = usersSettings.getUsersAccountSettings();
        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mPosts.setText(String.valueOf(settings.getPosts()));
        mFollowing.setText(String.valueOf(settings.getFollowing()));
        mFollowers.setText(String.valueOf(settings.getFollowers()));
    }

    private void navigateToEditProfile(){
        Log.d(TAG, "navigateToEditProfile: Navigating to " + mContext.getString(R.string.edit_profile_fragment));
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                intent.putExtra(getString(R.string.calling_activity), getString(R.string.profile_activity));
                startActivity(intent);
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
















