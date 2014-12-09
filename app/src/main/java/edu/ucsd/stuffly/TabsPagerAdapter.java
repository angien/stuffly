package edu.ucsd.stuffly;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.List;

/**
 * Created by ryanliao on 10/31/14.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter
{

    private List<Fragment> fragments;

    public TabsPagerAdapter(FragmentManager fm, List<Fragment> fragments)
    {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int index)
    {
//        switch (index)
//        {
//            case 0:
//                return new FeedFragment();
//            case 1:
//                return new MessagesFragment();
//            case 2:
//                return new MyItemsFragment();
//            case 3:
//                return new ProfileFragment();
//        }
//
//        return null;

        return this.fragments.get(index);
    }

    @Override
    public int getCount()
    {
        return 4;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }

}


