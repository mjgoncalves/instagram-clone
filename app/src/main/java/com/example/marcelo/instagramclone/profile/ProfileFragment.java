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

import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.BottomNavigationViewAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private final static int ACTIVITY_NUM = 2;
    private TextView mPosts, mFollowing, mFollowers, mDisplayName, mUserName, mWebsite, mDescription;
    private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;
    private GridView mGridView;
    private Toolbar mToolbar;
    private BottomNavigationViewEx mViewEx;
    private ImageView profileMenu;
    private View view;
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Log.d(TAG, "onCreateView: Started!");
        setupWidgets();
        setupBottomNavigationView();
        setupToolbar();
        return view;
    }

    private void setupWidgets() {
        mDisplayName = view.findViewById(R.id.display_name);
        mUserName = view.findViewById(R.id.user_name);
        mWebsite = view.findViewById(R.id.website);
        mDescription = view.findViewById(R.id.description);
        mPosts = view.findViewById(R.id.posts);
        mFollowers = view.findViewById(R.id.txtViewFollower);
        mFollowing = view.findViewById(R.id.txtViewFollowing);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mGridView = view.findViewById(R.id.gridview);
        profileMenu = view.findViewById(R.id.profile_menu_settings);
        mViewEx = view.findViewById(R.id.botton_navView);
        mToolbar = view.findViewById(R.id.profileToolBar);
        mContext = getActivity();

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
}
















