package com.project.moneymind.adapters;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.project.moneymind.Budget_frag;
import com.project.moneymind.Goals_frag;

public class ViewPager_adpater_budGoal extends FragmentPagerAdapter{
    public ViewPager_adpater_budGoal(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new Budget_frag();
        }else {
            return new Goals_frag();
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
            return "Set Budget";
        } else {
            return "Set Goal";
        }
    }
}
