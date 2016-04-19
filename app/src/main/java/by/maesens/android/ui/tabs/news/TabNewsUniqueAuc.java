package by.maesens.android.ui.tabs.news;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventAuctions;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;

/**
 * Created by Никита on 18.03.2016.
 */
public class TabNewsUniqueAuc extends BaseRecyclerFragment<EventAuctions> {
    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_news_unique_auc;
        mRecyclerId = R.id.news_unique_recycler;
        mSwipeRefreshId = R.id.news_unique__swipe_refresh;
        setColumnCount(2);
        setPagination(false);
    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        return new AuctionRecyclerAdapter(R.layout.item_auction, mPicasso, mFragmentSetter);
    }

    @Override
    protected String[] getApiParams() {
        String[] params = new String[1];
        params[0] = "True";
        return params;
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventAuctions event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EventAuctions getEvent() {
        return new EventAuctions();
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventAuctions eventAuctions) {
        super.onApiResponse(eventAuctions);
        turnOnControls();
    }
}
