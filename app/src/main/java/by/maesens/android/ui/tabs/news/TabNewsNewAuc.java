package by.maesens.android.ui.tabs.news;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventAuctionsNew;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;

/**
 * Created by Никита on 18.03.2016.
 */
public class TabNewsNewAuc extends BaseRecyclerFragment<EventAuctionsNew> {

    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_news_auc_new;
        mRecyclerId = R.id.rc_news_auction_new;
        mSwipeRefreshId = R.id.sr_news_auction_swipe_refresh;
        setColumnCount(2);
        setPagination(false);
    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        mAdapter = new AuctionRecyclerAdapter(R.layout.item_auction, mPicasso, mFragmentSetter);
        return mAdapter;
    }

    @Override
    protected String[] getApiParams() {
        return new String[0];
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventAuctionsNew event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventAuctionsNew event) {
        super.onApiResponse(event);
        turnOnControls();
    }

    @Override
    public EventAuctionsNew getEvent() {
        return new EventAuctionsNew();
    }
}
