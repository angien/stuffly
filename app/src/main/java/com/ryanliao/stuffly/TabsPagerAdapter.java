package com.ryanliao.stuffly;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ryanliao on 10/31/14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter
{
    public TabsPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int index)
    {
        switch (index)
        {
            case 0:
                return new FeedFragment();
            case 1:
                return new MessagesFragment();
            case 2:
                return new MyItemsFragment();
            case 3:
                return new ProfileFragment();
        }

        return null;
    }

    @Override
    public int getCount()
    {
        return 3;
    }

}


