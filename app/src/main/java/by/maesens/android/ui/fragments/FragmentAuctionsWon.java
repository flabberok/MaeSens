package by.maesens.android.ui.fragments;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventAuctionsWon;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;

/**
 * Created by Никита on 18.04.2016.
 */
public class FragmentAuctionsWon extends BaseRecyclerFragment<EventAuctionsWon> {
    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_auctions_won;
        mRecyclerId = R.id.auc_won_recycler;
        mSwipeRefreshId = R.id.auc_won_swipe_refresh;
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
    public void sendApiRequest(String[] paramsForAPI, EventAuctionsWon event) {
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
    public EventAuctionsWon getEvent() {
        return new EventAuctionsWon();
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventAuctionsWon eventAuctionsWon) {
        super.onApiResponse(eventAuctionsWon);
        turnOnControls();
    }
}
