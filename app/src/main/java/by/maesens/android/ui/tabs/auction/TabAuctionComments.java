package by.maesens.android.ui.tabs.auction;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionCommentsRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventAuctionComments;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.AuctionFragment;
import by.maesens.android.ui.fragments.base.BaseCommentsFragment;

/**
 * Created by Павел on 17.03.2016.
 */
public class TabAuctionComments extends BaseCommentsFragment<EventAuctionComments> {

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
            return new String[]{String.valueOf(getArguments().getInt(AuctionFragment.ARG_AUCTION_ID))};
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
    public void onApiResponse(EventAuctionComments event) {
        super.onApiResponse(event);
    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventAuctionComments event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EventAuctionComments getEvent() {
        return new EventAuctionComments();
    }

}