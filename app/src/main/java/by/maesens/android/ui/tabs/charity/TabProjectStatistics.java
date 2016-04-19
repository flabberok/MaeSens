package by.maesens.android.ui.tabs.charity;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.adapters.ProjectStatisticsRecyclerAdapter;
import by.maesens.android.events.EventProjectStatistics;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;

/**
 * Created by Sol on 02.04.2016.
 */
public class TabProjectStatistics extends BaseRecyclerFragment<EventProjectStatistics> {

    public static final String KEY_ARGS_PROJECT_SLUG = "Project_Slug";

    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_project_statistics;
        mRecyclerId = R.id.recycler;
        mSwipeRefreshId = R.id.swipeRefresh;
        setPagination(true);
        setColumnCount(1);

    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        return new ProjectStatisticsRecyclerAdapter(R.layout.item_project_statistics_donation,mPicasso,mFragmentSetter);
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventProjectStatistics event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI,event, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventProjectStatistics event) {
        super.onApiResponse(event);
    }

    @Override
    protected String[] getApiParams() {
        return new String[]{getArguments().getString(KEY_ARGS_PROJECT_SLUG),String.valueOf(mNextPage)};
    }

    @Override
    public EventProjectStatistics getEvent() {
        return new EventProjectStatistics();
    }

    public static TabProjectStatistics newInstance(String slug) {

        Bundle args = new Bundle();
        args.putString(KEY_ARGS_PROJECT_SLUG,slug);
        TabProjectStatistics fragment = new TabProjectStatistics();
        fragment.setArguments(args);
        return fragment;
    }
}
