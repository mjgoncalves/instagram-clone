package com.example.marcelo.instagramclone.profile;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marcelo.instagramclone.R;
import com.example.marcelo.instagramclone.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Objects;

public class EditProfileFragment extends Fragment {

    private ImageView mProfilePhoto;

    private static final String TAG = "EditProfileFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mProfilePhoto = view.findViewById(R.id.profile_photo);

        setupBackArrow(view);
        setProfileImage();
        return view;
    }

    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image");
        String imgURL = "www.jrtstudio.com/sites/default/files/ico_android.png";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto, null, "http://");


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
}
