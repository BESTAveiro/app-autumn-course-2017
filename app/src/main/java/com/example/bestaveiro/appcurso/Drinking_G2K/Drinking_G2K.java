package com.example.bestaveiro.appcurso.Drinking_G2K;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bestaveiro.appcurso.R;

import java.util.ArrayList;
import java.util.List;


public class Drinking_G2K extends Fragment
{
    String className;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        className = getClass().getSimpleName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d(className, "onCreateView");
        View myView;
        myView = inflater.inflate(R.layout.fragment_drinking__g2_k, container, false);

        viewPager = (ViewPager) myView.findViewById(R.id.viewpager_drinking_g2k);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);
        tabLayout.removeAllTabs();
        Log.d("onCreateView", String.format("%d tabs", tabLayout.getTabCount()));

        return myView;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.d(className, "onStart");
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
                viewPager.getAdapter().notifyDataSetChanged();
                Log.d(className, String.format("onStart %d tabs", tabLayout.getTabCount()));
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Drinking(), Drinking.className);
        adapter.addFragment(new G2K(), G2K.className);
        viewPager.setAdapter(adapter);
        Log.d(className, String.format("setupViewPager %d fragments no view pager", adapter.getCount()));
    }

    class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private List<Fragment> mFragmentList;
        private List<String> mFragmentTitleList;

        public ViewPagerAdapter(FragmentManager manager)
        {
            super(manager);
            mFragmentList = new ArrayList<>();
            mFragmentTitleList = new ArrayList<>();
        }

        @Override
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position)
        {
            //Log.d(className, String.format("fragment get item %d", position));
            return mFragmentList.get(position);
        }

        @Override
        public int getCount()
        {
            //Log.d(className, String.format("fragment adapter count %d", mFragmentList.size()));
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title)
        {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mFragmentTitleList.get(position);
        }
    }

}
