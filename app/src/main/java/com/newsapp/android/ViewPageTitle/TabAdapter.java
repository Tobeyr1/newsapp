package com.newsapp.android.ViewPageTitle;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import java.util.List;

public class TabAdapter extends FragmentPagerAdapter {
    public static String[] TITLES = new String[]{"头条", "社会", "国际", "体育", "时尚", "科技"};
    private Fragment fragment;


    public TabAdapter(FragmentManager fm,String TITLES) {
        super(fm);

        /*TITLES = Utils.getStringArray(R.array.tab_names);*/
    }
    public TabAdapter(FragmentManager fm ){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //新建Fragment


       MainFragment fragment = new MainFragment();
      /* Bundle args = new Bundle();
       args.putString("arg",TITLES[position]);
       fragment.setArguments(args);*/
        /*MainFragment.setArguments(bundle);*/
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position % TITLES.length];
    }

    @Override
    public int getCount() {
        return TITLES.length;


    }

}
