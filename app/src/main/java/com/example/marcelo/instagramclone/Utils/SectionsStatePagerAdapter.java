package com.example.marcelo.instagramclone.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionsStatePagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentsList = new ArrayList<>();
    private final HashMap<Fragment, Integer> mFragments = new HashMap<>();
    private final HashMap<String, Integer> mFragmentNumbers = new HashMap<>();
    private final HashMap<Integer, String> mFragmentNames = new HashMap<>();


    public SectionsStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }

    public void addFragments(Fragment fragment, String fragmentName) {
        mFragmentsList.add(fragment);
        mFragments.put(fragment, mFragmentsList.size() - 1);
        mFragmentNumbers.put(fragmentName, mFragmentsList.size() - 1);
        mFragmentNames.put(mFragmentsList.size() - 1, fragmentName);
    }

    /**
     * returns the fragment with the name @param
     *
     * @param fragmentName
     * @return
     */
    public Integer getFragmentNumber(String fragmentName) {
        if (mFragmentNumbers.containsKey(fragmentName)) {
            return mFragmentNumbers.get(fragmentName);
        } else {

            return null;
        }
    }

    /**
     * returns the fragment with the name @param
     *
     * @param fragment
     * @return
     */

    public Integer getFragmentNumber(Fragment fragment) {
        if (mFragmentNumbers.containsKey(fragment)) {
            return mFragmentNumbers.get(fragment);
        } else {

            return null;
        }

    }

    /**
     * returns the fragment with the name @param
     *
     * @param fragmentNumber
     * @return
     */

    public String getFragmentName(Integer fragmentNumber) {
        if (mFragmentNumbers.containsKey(fragmentNumber)) {
            return mFragmentNames.get(fragmentNumber);
        } else {

            return null;
        }

    }
}












