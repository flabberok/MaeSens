package by.maesens.android.ui.fragments;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventMyBids;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;

/**
 * Created by Никита on 29.03.2016.
 */
public class MyBidsFragment extends BaseRecyclerFragment<EventMyBids> {
    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_my_bets;
        mRecyclerId = R.id.my_bets_recycler;
        mSwipeRefreshId = R.id.my_bets_swipe_refresh;
        setColumnCount(2);
        setPagination(false);
    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        return new AuctionRecyclerAdapter(R.layout.item_auction, mPicasso, mFragmentSetter);
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventMyBids event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String[] getApiParams() {
        return new String[0];
    }

    @Override
    public EventMyBids getEvent() {
        return new EventMyBids();
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventMyBids eventMyBids) {
        super.onApiResponse(eventMyBids);
        turnOnControls();
    }
}
