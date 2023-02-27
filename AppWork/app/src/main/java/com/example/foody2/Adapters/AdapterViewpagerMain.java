package com.example.foody2.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foody2.View.Fragments.EatwhatFragment;
import com.example.foody2.View.Fragments.WhereFragment;

public class AdapterViewpagerMain extends FragmentStatePagerAdapter {
    WhereFragment whereFragment;
    EatwhatFragment eatwhatFragment;
    public AdapterViewpagerMain(@NonNull FragmentManager fm) {
        super(fm);
         whereFragment = new WhereFragment();
         eatwhatFragment = new EatwhatFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return whereFragment;
            case 1:
                return eatwhatFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
