package com.suraj.rtp.ui.widgets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.suraj.rtp.ui.fragments.KeyBoardFragment;
import com.suraj.rtp.ui.fragments.PowerFragment;
import com.suraj.rtp.ui.fragments.TouchPadFragment;


public class PagerAdapter extends FragmentPagerAdapter {
    public static String[] CONTENT = new String[]{};
    public int mCount;
    public int p_no;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        CONTENT = new String[]{"Touchpad", "Keyboard", "Power"};
        mCount = CONTENT.length;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TouchPadFragment();
            case 1:
                return new KeyBoardFragment();

            case 2:
                return new PowerFragment();
        }
        return null;

    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return PagerAdapter.CONTENT[position % CONTENT.length];
    }
}