package com.example.btl.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.btl.fragment.FragmentHome;
import com.example.btl.fragment.FragmentProfile;
import com.example.btl.fragment.FragmentTasks;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new FragmentHome();
            case 1 :return new FragmentTasks();
            case 2 :return new FragmentProfile();


        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
