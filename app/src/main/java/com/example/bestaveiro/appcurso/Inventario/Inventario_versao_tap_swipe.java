package com.example.bestaveiro.appcurso.Inventario;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bestaveiro.appcurso.Inventario.Alcool.Alcool;
import com.example.bestaveiro.appcurso.Inventario.Comida.Comida;
import com.example.bestaveiro.appcurso.Inventario.Outros.Outros;
import com.example.bestaveiro.appcurso.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by filipe on 13/05/2016.
 */
public class Inventario_versao_tap_swipe extends Fragment
{
    String className = "Inventário";
    TabLayout tabLayout;
    public static ViewPager viewPager;
    static Boolean loadAgain;
    AppBarLayout appBarLayout;

    public static Inventario_versao_tap_swipe fragmentAtual;

    public static Inventario_versao_tap_swipe NewInstance()
    {
        if(fragmentAtual != null)
        {
            loadAgain = true;
            return fragmentAtual;
        }

        Inventario_versao_tap_swipe tmp = new Inventario_versao_tap_swipe();
        if(loadAgain == null) loadAgain = false;
        return tmp;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        fragmentAtual = this;
        getActivity().setTitle(className);
        Log.d(className, "onCreate");
        Log.d(className, String.format("loadAgain = %b ", loadAgain));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.d(className, "onCreateView");
        View myView;
        myView = inflater.inflate(R.layout.inventario_versao_tab_swipe, container, false);

        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);

        viewPager = (ViewPager) myView.findViewById(R.id.viewpager_inventario);
        viewPager.removeAllViews();
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
                Log.d(className, "onStart - load again " + loadAgain);
                tabLayout.setupWithViewPager(viewPager);
                if(loadAgain) viewPager.getAdapter().notifyDataSetChanged();
                Log.d("onCreateView", String.format("%d tabs", tabLayout.getTabCount()));
            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.d(className, "onDestroy");
        fragmentAtual = null;
        loadAgain = true;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new Alcool(), Alcool.className);
        adapter.addFragment(new Comida(), Comida.className);
        adapter.addFragment(new Outros(), Outros.className);
        viewPager.setAdapter(adapter);

        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);

        Log.d("setupViewPager", String.format("%d fragments no view pager", adapter.getCount()));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                // para voltar a mostrar a toolbar cada vez que se mudar a página
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                if(behavior!=null)
                {
                    behavior.setTopAndBottomOffset(0);
                    behavior.onNestedPreScroll(coordinatorLayout, appBarLayout, null, 0, 1, new int[2]);
                }

                Log.d(className, "vai enviar evento");
                // evento para registar o onClick do fab
                EventBus.getDefault().post(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
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
        public Fragment getItem(int position) {
            Log.d(className, String.format("Adapter fragment get item %d", position));
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            //Log.d(className, String.format("fragment Adapter count %d", mFragmentList.size()));
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
