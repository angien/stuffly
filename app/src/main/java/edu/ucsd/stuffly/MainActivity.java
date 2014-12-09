package edu.ucsd.stuffly;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.facebook.UiLifecycleHelper;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.widget.FacebookDialog;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.StandardExceptionParser;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.stuffly.R;

/**
 * Created by ryanliao on 10/31/14.
 */
public class MainActivity extends FragmentActivity implements ActionBar.TabListener{

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    private DialogFragment df;
    // Tab titles
    private String[] tabs = { "Feed", "Messages", "My Items", "Profile" };

    MessagesFragment mf = new MessagesFragment();
    FeedFragment ff = new FeedFragment();
    MyItemsFragment mif = new MyItemsFragment();
    ProfileFragment pf = new ProfileFragment();
    private List<Fragment> fragments;

    //Facebook facebook = new Facebook("1507378456205227");
    //public static UiLifecycleHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();

        fragments = new ArrayList<Fragment>();
        fragments.add(ff);
        fragments.add(mf);
        fragments.add(mif);
        fragments.add(pf);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(),fragments);

        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(4); // make sure this is the amount of tabs available...otherwise memory leak

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        DisplayImageOptions ioptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration iconfig = new ImageLoaderConfiguration.Builder(this)
                .memoryCache(new LruMemoryCache(2*1024*1024))
                .memoryCacheSize(2*1024*2014)
                .memoryCacheSizePercentage(13)
                .defaultDisplayImageOptions(ioptions)
                .build();
        ImageLoader.getInstance().init(iconfig);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        //ViewPager can't be cast to FrameLayout
        //fl = (FrameLayout)findViewById(R.id.pager);
        //fl.getForeground().setAlpha(0);

        /**
         * Facebook integration; login
         */
//        facebook.authorize(this, new String[] { "publish_actions" }, new Facebook.DialogListener() {
//            @Override
//            public void onComplete(Bundle values) {}
//
//            @Override
//            public void onFacebookError(FacebookError error) {}
//
//            @Override
//            public void onError(DialogError e) {}
//
//            @Override
//            public void onCancel() {}
//        });
//
//        /**
//         * Facebook share post
//         */
//        uiHelper = new UiLifecycleHelper(this, null);
//        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        facebook.authorizeCallback(requestCode, resultCode, data);
//
//        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
//            @Override
//            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
//                Log.e("Activity", String.format("Error: %s", error.toString()));
//            }
//
//            @Override
//            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
//                Log.i("Activity", "Success!");
//            }
//        });
    }
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft)
//    {
//    }
//
//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft)
//    {
//
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft)
//    {
//    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.createPost:
                df = new CreatePostFragment();
                df.show(getSupportFragmentManager(), "createPost");

                return true;
            case R.id.refresh:
                //final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();

        EasyTracker.getInstance(this).activityStop(this);
    }

    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments){
            if(fragment != null && fragment.isVisible()){
                return fragment;
            }
        }
        return null;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        //uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //uiHelper.onDestroy();
    }
}