//package com.ryanliao.stuffly;
//
///**
// * Created by ryanliao on 10/31/14.
// */
//import android.app.ActionBar;
//import android.app.ActionBar.Tab;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//public class FragmentContainer extends FragmentActivity  implements ActionBar.TabListener
//{
//    private ViewPager mViewPager;
//    private TabsPagerAdapter mTabsAdapter;
//    private ActionBar actionBar;
//
//    LinearLayout profileLayout;
//    LinearLayout historyLayout;
//    LinearLayout inspectLayout;
//    View profileTabIndex;
//    View historyTabIndex;
//    View inspectTabIndex;
//
//    //ViewPager.OnPageChangeListener pageChangeListener; // only use this IF refactoring code
//
//    @Override
//    public void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_main);
//        // Initialization
//        mViewPager = (ViewPager) findViewById(R.id.pager);
//        actionBar = getActionBar();
//        mTabsAdapter = new TabsPagerAdapter(getSupportFragmentManager());
//        mViewPager.setAdapter(mTabsAdapter);
//        actionBar.setHomeButtonEnabled(false);
//        mViewPager.setOffscreenPageLimit(4); // make sure this is the amount of tabs available
//
//        profileTabIndex = (View)findViewById(R.id.profileTabScroll);
//        historyTabIndex = (View)findViewById(R.id.historyTabScroll);
//        inspectTabIndex = (View)findViewById(R.id.inspectTabScroll);
//
//        profileLayout = (LinearLayout)findViewById(R.id.profileTabContainer);
//
//
//        profileLayout.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                mViewPager.setCurrentItem(0);
//            }
//        });
//
//        historyLayout = (LinearLayout)findViewById(R.id.historyTabContainer);
//        historyLayout.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                mViewPager.setCurrentItem(1);
//            }
//        });
//
//        inspectLayout = (LinearLayout)findViewById(R.id.inspectTabContainer);
//        inspectLayout.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                mViewPager.setCurrentItem(2);
//            }
//        });
//
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
//        {
//            @Override
//            public void onPageSelected(int position)
//            {
//                switch(position)
//                {
//                    case 0:
//                        profileTabIndex.setBackgroundColor(Color.parseColor("#007dc4"));
//                        historyTabIndex.setBackgroundColor(Color.parseColor("#404041"));
//                        inspectTabIndex.setBackgroundColor(Color.parseColor("#404041"));
//                        break;
//                    case 1:
//                        profileTabIndex.setBackgroundColor(Color.parseColor("#404041"));
//                        historyTabIndex.setBackgroundColor(Color.parseColor("#007dc4"));
//                        inspectTabIndex.setBackgroundColor(Color.parseColor("#404041"));
//                        break;
//                    case 2:
//                        profileTabIndex.setBackgroundColor(Color.parseColor("#404041"));
//                        historyTabIndex.setBackgroundColor(Color.parseColor("#404041"));
//                        inspectTabIndex.setBackgroundColor(Color.parseColor("#007dc4"));
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
//    }
//
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//        // on tab selected
//        // show respected fragment view
//        mViewPager.setCurrentItem(tab.getPosition());
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
//
//    @Override
//    public boolean onPrepareOptionsMenu(Menu menu){
//        //code here
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId())
//        {
//
//        }
//        return false;
//    }
//
//
//    @Override
//    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onTabReselected(Tab tab, FragmentTransaction ft) {
//        // TODO Auto-generated method stub
//
//    }
//}
