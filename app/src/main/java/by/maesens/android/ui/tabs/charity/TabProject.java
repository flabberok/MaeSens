package by.maesens.android.ui.tabs.charity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventProject;
import by.maesens.android.events.EventProjectChildrenAuctions;
import by.maesens.android.model.Auction;
import by.maesens.android.model.Charity;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAuctions;
import by.maesens.android.ui.fragments.base.BaseNetworkFragment;
import by.maesens.android.ui.view.FBView;
import by.maesens.android.ui.view.TwitterView;
import by.maesens.android.ui.view.VKView;
import by.maesens.android.utils.Calculate;
import by.maesens.android.utils.SocialNetwork;

/**
 * Created by Sol on 19.03.2016.
 */
public class TabProject extends BaseNetworkFragment<EventProject> {

    public static final String LOG_TAG = "TabProject";
    public static final String KEY_ARGS_PROJECT_SLUG = "Project_Slug";
    private List<Auction> mListChildrenAuction;
    private Charity mProject;

    private ImageView mImgProject;
    private TextView mTvProjectTitle;
    private TextView mTvProjectCategory;
    private Button mButAllProjectAuctions;
    private RecyclerView mRecyclerView;
    private int mColumnCount;
    private TextView mTvPurposeProject;
    private TextView mTvDescriptionProject;
    private WebView mWebViewDescription;
    private WebView mWebViewPurpose;
    private ProgressBar mProjectProgress;
    private TextView mPercentMoney;
    private TextView mCountMoney;
    private TextView mRequiredMoney;

    private VKView mVKShare;
    private FBView mFBShare;
    private TwitterView mTwitterShare;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        try {
            ServiceHelper.getInstance().getData(getApiParams(),
                    new EventProjectChildrenAuctions(), getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initMainProjectCard(view);
        initAuctionsProjectCard(view);
        initDescriptionProjectCard(view);
        initProgressProjectCard(view);

        return view;


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initMainProjectCard(View view) {
        Log.d(LOG_TAG, " initMainProjectCard");
        mImgProject = (ImageView) view.findViewById(R.id.img_project);
        mTvProjectTitle = (TextView) view.findViewById(R.id.tv_project_name);
        mTvProjectCategory = (TextView) view.findViewById(R.id.tv_project_category);

        mVKShare = (VKView) view.findViewById(R.id.vkShare);
        mFBShare = (FBView) view.findViewById(R.id.fbShare);
        mTwitterShare = (TwitterView) view.findViewById(R.id.twitterShare);
        mVKShare.setOnClickListener(new ShareListener());
        mFBShare.setOnClickListener(new ShareListener());
        mTwitterShare.setOnClickListener(new ShareListener());
        mWebViewPurpose = (WebView) view.findViewById(R.id.wv_purpose);
    }

    class ShareListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.vkShare:
                    Log.d(LOG_TAG, "VK share  " + mProject.getDescription());
                    SocialNetwork.vkShare(getActivity(), mProject.getTitle(),
                            mProject.getDescription(),
                            ServiceHelper.URLServer + "cause/" + mProject.getSlug(),
                            mImgProject.getDrawable());
                    break;
                case R.id.fbShare:
                    SocialNetwork.fbShare(getActivity(), mProject.getTitle(),
                            mProject.getDescription(),
                            ServiceHelper.URLServer + "cause/" + mProject.getSlug(),
                            mProject.getBackground_image());
                    break;
                case R.id.twitterShare:
                    SocialNetwork.twitterShare(getActivity(), mProject.getTitle(),
                            ServiceHelper.URLServer + "cause/" + mProject.getSlug());
                    break;
                default:
            }
        }
    }

    private void setDataToMainCard() {
        Log.d(LOG_TAG, " setDataToMainCard");
        if (mProject != null) {
            Log.d(LOG_TAG, " setDataToMainCard  mProject != null");
            mPicasso.load(mProject.getBackground_image())
/*                    .placeholder(R.drawable.logo_m)
                    .error(R.drawable.logo_m)*/
                    .into(mImgProject);
            mTvProjectTitle.setText(mProject.getTitle());
            mTvProjectCategory.setText(((mProject.getCategory())[0]).toString());
            mWebViewPurpose.loadDataWithBaseURL(null, mProject.getPurpose(), "text/html", "en_US", null);
        }
    }

    private void initDescriptionProjectCard(View view) {
        Log.d(LOG_TAG, " initDescriptionProjectCard");
        mWebViewDescription = (WebView) view.findViewById(R.id.wv_description);
    }

    private void setDataToDescriptionCard() {
        Log.d(LOG_TAG, " setDataToDescriptionCard()");
        if (mProject != null) {
            mWebViewDescription.loadDataWithBaseURL(null, mProject.getDescription(), "text/html", "en_US", null);
        }

    }

    private void initProgressProjectCard(View view) {
        Log.d(LOG_TAG, "initProgressProjectCard");
        mProjectProgress = (ProgressBar) view.findViewById(R.id.pb_progress);
        mPercentMoney = (TextView) view.findViewById(R.id.tv_percent);
        mCountMoney = (TextView) view.findViewById(R.id.tv_charity_current_sum);
        mRequiredMoney = (TextView) view.findViewById(R.id.tv_required_sum);
    }

    private void setDataToProgressCard() {
        if (mProject != null) {
            int percent = Calculate.calculateCurrentPercent(mProject.getCurrent_amount(), mProject.getRequired_amount());
            mProjectProgress.setProgress(percent > 100 ? 100 : percent);
            mPercentMoney.setText(String.format("%d", percent) + ((Context) mFragmentSetter).getString(R.string.text_persent));
            mCountMoney.setText(String.format("%,d", mProject.getCurrent_amount()));
            mRequiredMoney.setText(getString(R.string.raised_funds_from) + " " + String.format("%,d", mProject.getRequired_amount()));
        }
    }

    private void initAuctionsProjectCard(final View view) {
        Log.d(LOG_TAG, "initAuctionsProjectCard");
        final TextView textView = (TextView) view.findViewById(R.id.tv_project_auctions);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mButAllProjectAuctions.getVisibility() == View.VISIBLE) {
                    mButAllProjectAuctions.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigation_arrow_drop_down, 0);
                } else {
                    Log.d(LOG_TAG, "initAuctionsProjectCard onClick mButAllProjectAuctionsGone");
                    mButAllProjectAuctions.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    setDataToAuctionsCard();
                    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigation_arrow_drop_up, 0);
                }

            }
        });

        mButAllProjectAuctions = (Button) view.findViewById(R.id.b_charity_auctions);
        mButAllProjectAuctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, " mButAllProjectAuctions onClick");
            }
        });

        mColumnCount = 2;
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_auctions_recycler);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));


    }

    private void setDataToAuctionsCard() {
        Log.d(LOG_TAG, " setDataToAuctionsCard()");
        if (mButAllProjectAuctions.getVisibility() == View.VISIBLE && mListChildrenAuction.size() > 0) {
            Log.d(LOG_TAG, " setDataToAuctionsCard()    mButAllProjectAuctions.getVisibility() == View.VISIBLE && mListChildrenAuction.size() > 0");
            BaseRecyclerAdapter adapter = new AuctionRecyclerAdapter(R.layout.item_auction, mPicasso, mFragmentSetter);
            adapter.reset();
            mRecyclerView.setAdapter(adapter);

            adapter.addData(mListChildrenAuction);

        }
    }

    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onFailResponse(IBaseResponse response) {

    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        Log.d(LOG_TAG, "onSuccessResponse  " + response.getResult());
        mProject = (Charity) response.getResult();
        setDataToMainCard();
        setDataToDescriptionCard();
        setDataToProgressCard();
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventProject event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String[] getApiParams() {
        return new String[]{getArguments().getString(KEY_ARGS_PROJECT_SLUG)};
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventProject event) {
        super.onApiResponse(event);

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onProjectChildrenAuctionResponse(EventProjectChildrenAuctions eventProjectChildrenAuctions) {
        Log.d(LOG_TAG, "onProjectChildrenAuctionResponse  " + eventProjectChildrenAuctions.getResponse().getResult());
        mListChildrenAuction = ((ResponseAuctions) eventProjectChildrenAuctions.getResponse()).getResult();
        Log.d(LOG_TAG, "onProjectChildrenAuctionResponse  " + mListChildrenAuction.toString());
        setDataToAuctionsCard();
    }

    @Override
    public EventProject getEvent() {
        return new EventProject();
    }

    public static TabProject newInstance(String slug) {

        Bundle args = new Bundle();
        args.putString(KEY_ARGS_PROJECT_SLUG, slug);
        TabProject fragment = new TabProject();
        fragment.setArguments(args);
        return fragment;
    }
}
