package by.maesens.android.ui.tabs.auction;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionBidsRecyclerAdapter;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.loaders.PicassoLoader;
import by.maesens.android.model.Bid;
import by.maesens.android.ui.fragments.AuctionFragment;

/**
 * Created by Виктор on 25.03.2016.
 */
public class TabAuctionBids extends Fragment {

    private Bid[] mBids = new Bid[]{};

    private TextView mBidsTitle;
    private AuctionBidsRecyclerAdapter mBidsAdapter;
    private RecyclerView mBidsRecyclerView;

    private Picasso mPicasso;
    private IComponentSetter mFragmentSetter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicasso = PicassoLoader.getInstance(getActivity());
        if (getActivity() instanceof IComponentSetter) {
            mFragmentSetter = (IComponentSetter) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_auction_bids_story, container, false);
        findBidsViews(view);
        setArgumentsData();
        return view;
    }

    public void setArgumentsData(){
        // достаем аргументы
        if (getArguments() != null){
            Parcelable[] parcelableArray = getArguments().getParcelableArray(AuctionFragment.ARG_AUCTION_BIDS);
            if (parcelableArray != null) {
                mBids = Arrays.copyOf(parcelableArray, parcelableArray.length, Bid[].class);
            }
        }

        if (mBids.length > 0)
            initAuctionBidsListInfo();
        else
            mBidsTitle.setText(R.string.no_bids);
    }

    private void findBidsViews(View view) {
        mBidsTitle = (TextView)view.findViewById(R.id.tvBidsStoryTitle);
        initBidsRecyclerView(view);
    }

    private void initBidsRecyclerView(View view){
        mBidsRecyclerView = (RecyclerView) view.findViewById(R.id.rvListOfBids);
        mBidsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mBidsAdapter = new AuctionBidsRecyclerAdapter(R.layout.item_auction_bids, mPicasso, mFragmentSetter);
        mBidsRecyclerView.setAdapter(mBidsAdapter);
    }

    private void initAuctionBidsListInfo(){
        // История ставок аукциона
        mBidsAdapter.reset();
        if (mBids.length > 0)
            mBidsAdapter.addData(new ArrayList<>(Arrays.asList(mBids)));
        mBidsTitle.setText(R.string.bids_story);
    }
}