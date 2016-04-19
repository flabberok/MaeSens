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

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import by.maesens.android.R;
import by.maesens.android.events.EventAuctionInfo;
import by.maesens.android.ui.fragments.base.BaseNetworkFragment;
import by.maesens.android.ui.tabs.auction.TabAuctionBids;
import by.maesens.android.ui.tabs.auction.TabAuctionComments;
import by.maesens.android.ui.tabs.auction.TabAuctionInfo;
import by.maesens.android.model.Auction;
import by.maesens.android.model.AuctionInfo;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAuctionInfo;

/**
 * Created by Виктор on 20.02.2016.
 */
public class AuctionFragment extends BaseNetworkFragment<EventAuctionInfo> {

    public static final String ARG_AUCTION_ID = "AuctionFragment.auction.id";
    public static final String ARG_AUCTION_INFO = "AuctionFragment.auction.auctionInfo";
    public static final String ARG_AUCTION_BIDS = "AuctionFragment.auction.bids";

    private int mAuctionId;
    private AuctionInfo mAuctionInfo;

    private ViewPager mAuctionPager;
    private TabLayout mAuctionTabs;
    private TabPagerAdapter tabPagerAdapter;

    public static AuctionFragment getInstance(Auction auction) {
        Bundle args = new Bundle();
        args.putInt(ARG_AUCTION_ID, auction.getId());

        AuctionFragment auctionFragment = new AuctionFragment();
        auctionFragment.setArguments(args);
        return auctionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuctionId = getArguments().getInt(ARG_AUCTION_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auction, container, false);
        mAuctionPager = (ViewPager) view.findViewById(R.id.vpAuctionPager);
        mAuctionTabs = (TabLayout) view.findViewById(R.id.tbAuctionTabs);
        return view;
    }

    @Override
    public void onStop() {
        tabPagerAdapter = null;
        super.onStop();
    }

    @Override
    protected String[] getApiParams() {
        return new String[]{String.valueOf(mAuctionId)};
    }


    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {
    }

    @Override
    public void disableControls() {
        Log.d("AuctionFragment", "disableControls");
    }


    @Override
    public void onFailResponse(IBaseResponse response) {
        Log.d("AuctionFragment", "onFailResponse");
    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        Log.d("AuctionFragment", "onSuccessResponse");
        mAuctionInfo = ((ResponseAuctionInfo) response).getResult();

        if (tabPagerAdapter == null) {
            tabPagerAdapter = new TabPagerAdapter(getChildFragmentManager());
            mAuctionPager.setAdapter(tabPagerAdapter);
            mAuctionPager.setOffscreenPageLimit(0);
            mAuctionTabs.setupWithViewPager(mAuctionPager);
        } else
            tabPagerAdapter.updateDataInTabs();
    }

    @Override
    public void enableControls() {
        Log.d("AuctionFragment", "enableControls");
    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventAuctionInfo eventAuctionInfo) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, eventAuctionInfo, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventAuctionInfo eventAuctionInfo) {
        super.onApiResponse(eventAuctionInfo);
    }

    @Override
    public EventAuctionInfo getEvent() {
        return new EventAuctionInfo();
    }


    public class TabPagerAdapter extends FragmentPagerAdapter {

        private TabAuctionInfo tabAuctionInfo;
        private TabAuctionBids tabAuctionBids;
        private TabAuctionComments tabAuctionComments;

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    if (tabAuctionInfo == null) {
                        tabAuctionInfo = new TabAuctionInfo();
                        bundle.putParcelable(ARG_AUCTION_INFO, mAuctionInfo);
                        tabAuctionInfo.setArguments(bundle);
                    }
                    return tabAuctionInfo;
                case 1:
                    if (tabAuctionBids == null) {
                        tabAuctionBids = new TabAuctionBids();
                        bundle.putParcelableArray(ARG_AUCTION_BIDS, mAuctionInfo.getBids());
                        tabAuctionBids.setArguments(bundle);
                    }
                    return tabAuctionBids;
                case 2:
                    if (tabAuctionComments == null) {
                        tabAuctionComments = new TabAuctionComments();
                        bundle.putInt(ARG_AUCTION_ID, mAuctionId);
                        tabAuctionComments.setArguments(bundle);
                    }
                    return tabAuctionComments;
                default:
                    return null;
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
                    title = getString(R.string.auction_info);
                    break;
                case 1:
                    title = getString(R.string.auction_bids);
                    break;
                case 2:
                    title = getString(R.string.auction_comments);
                    break;
            }
            return title;
        }

        public void updateDataInTabs(){
            // обновляем данные аукциона и историю ставок
            tabAuctionInfo.getArguments().putParcelable(ARG_AUCTION_INFO, mAuctionInfo);
            tabAuctionInfo.setArgumentsData();

            tabAuctionBids.getArguments().putParcelableArray(ARG_AUCTION_BIDS, mAuctionInfo.getBids());
            tabAuctionBids.setArgumentsData();
        }
    }

    public void updateAuctionData(){
        sendApiRequest(getApiParams(), getEvent());
    }
}
