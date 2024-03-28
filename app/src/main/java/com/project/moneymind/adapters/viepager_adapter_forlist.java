package com.project.moneymind.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.moneymind.Daily_frag;
import com.project.moneymind.Monthly_frag;

public abstract class viepager_adapter_forlist extends FragmentPagerAdapter {
    public viepager_adapter_forlist(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new Daily_frag();
        }else {
            return new Monthly_frag();
        }

    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0) {
            return "Daily";
        } else {
            return "Monthly";
        }
    }
}