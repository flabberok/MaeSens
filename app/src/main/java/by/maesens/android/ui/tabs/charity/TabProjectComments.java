package by.maesens.android.ui.tabs.charity;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionCommentsRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventCharityComments;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.base.BaseCommentsFragment;

/**
 * Created by Виктор on 05.04.2016.
 */
public class TabProjectComments extends BaseCommentsFragment<EventCharityComments> {

    public static final String ARG_CHARITY_ID = "Charity.id";

    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_auction_comments;
        mRecyclerId = R.id.rvAuctionComments;
        mSwipeRefreshId = R.id.auctions_comments_swipe_refresh;
        setColumnCount(1);
        setPagination(false);
    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        return new AuctionCommentsRecyclerAdapter(R.layout.item_auction_comments, mPicasso, mFragmentSetter);
    }

    @Override
    protected String[] getApiParams() {
        if (getArguments() != null)
            return new String[]{String.valueOf(getArguments().getLong(ARG_CHARITY_ID))};
        else
            return new String[0];
    }


    @Override
    public void disableControls() {
        mRecyclerView.setEnabled(false);
    }

    @Override
    public void enableControls() {
        mRecyclerView.setEnabled(true);
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventCharityComments event) {
        super.onApiResponse(event);
    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventCharityComments event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EventCharityComments getEvent() {
        return new EventCharityComments();
    }

    public static TabProjectComments newInstance(long CharityId) {
        Bundle bundle = new Bundle();
        TabProjectComments fragment = new TabProjectComments();
        bundle.putLong(ARG_CHARITY_ID, CharityId);
        fragment.setArguments(bundle);
        return fragment;
    }

}