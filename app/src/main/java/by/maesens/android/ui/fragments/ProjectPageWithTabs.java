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
import by.maesens.android.ui.tabs.charity.TabProject;
import by.maesens.android.ui.tabs.charity.TabProjectComments;
import by.maesens.android.ui.tabs.charity.TabProjectStatistics;

/**
 * Created by Sol on 31.03.2016.
 */
public class ProjectPageWithTabs extends Fragment {
    public static final String KEY_ARG_PAGES_COUNT = "PAGES_COUNT";
    public static final String KEY_ARGS_PROJECT_SLUG = "Key Args Project Slug";
    public static final String KEY_ARGS_PROJECT_ID = "KEY_ARGS_PROJECT_ID";
    private String[] mTabNames;

    private int mTabCount;
    private String mProjectSlug;
    long mProjectId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mProjectSlug = getArguments().getString(KEY_ARGS_PROJECT_SLUG);
        mProjectId=getArguments().getLong(KEY_ARGS_PROJECT_ID);
        Log.d("ProjectPageWithTabs","onCreate  " +mProjectSlug +mProjectId);
        setTabFragments();
    }

    public void setTabFragments() {
        mTabCount = 3;
        mTabNames = new String[mTabCount];
        mTabNames[0] = getString(R.string.charity_info);
        mTabNames[1] = getString(R.string.charity_statistic) ;
        mTabNames[2] = getString(R.string.charity_comments);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab_host, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        viewPager.setAdapter(new ProjectsTabPagerAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    public class ProjectsTabPagerAdapter extends FragmentPagerAdapter {

        public static final int TAB_PROJECT_DESCRIPTION = 0;
        public static final int TAB_PROJECT_STATISTICS = 1;
        public static final int TAB_PROJECT_COMMENTS = 2;

        public ProjectsTabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("ProjectsTabPagerAdapter","Fragment getItem position = " + position);
            switch (position) {
                case TAB_PROJECT_DESCRIPTION: {
                    return TabProject.newInstance(mProjectSlug);
                }
                case TAB_PROJECT_STATISTICS: {
                    return TabProjectStatistics.newInstance(mProjectSlug);
                }
                case TAB_PROJECT_COMMENTS:{
                    return TabProjectComments.newInstance(mProjectId);
                }
                default:
                    return TabProject.newInstance(mProjectSlug);
            }

        }

        @Override
        public int getCount() {
            return mTabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabNames[position];
        }
    }

    public static ProjectPageWithTabs newInstance(String slug,long id) {
        Bundle args = new Bundle();
        args.putString(KEY_ARGS_PROJECT_SLUG, slug);
        args.putLong(KEY_ARGS_PROJECT_ID,id);
        ProjectPageWithTabs fragment = new ProjectPageWithTabs();
        fragment.setArguments(args);
        return fragment;
    }
}