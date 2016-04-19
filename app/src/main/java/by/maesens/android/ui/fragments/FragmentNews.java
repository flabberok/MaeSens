package by.maesens.android.ui.fragments;

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

import by.maesens.android.R;
import by.maesens.android.ui.tabs.news.TabNewsNewAuc;
import by.maesens.android.ui.tabs.news.TabNewsList;
import by.maesens.android.ui.tabs.news.TabNewsUniqueAuc;

/**
 * Created by Никита on 18.03.2016.
 */
public class FragmentNews extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_host, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new TabPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);
    }

    public class TabPagerAdapter extends FragmentPagerAdapter {

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("FragmentNews", "position = " + position);
            switch (position) {
                case 0:
                    return new TabNewsList();
                case 1:
                    return new TabNewsUniqueAuc();
                case 2:
                    return new TabNewsNewAuc();
                default:
                    return new TabNewsList();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "";
            switch (position) {
                case 0:
                    title = getString(R.string.news);
                    break;
                case 1:
                    title = getString(R.string.unique);
                    break;
                case 2:
                    title = getString(R.string.new_meetings);
                    break;
            }
            return title;
        }
    }
}
