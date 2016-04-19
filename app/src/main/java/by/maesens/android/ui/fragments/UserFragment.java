package by.maesens.android.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import by.maesens.android.R;
import by.maesens.android.account.AppAccount;
import by.maesens.android.adapters.InterestsAdapterNoClick;
import by.maesens.android.adapters.ShortAuctionsAdapter;
import by.maesens.android.adapters.UsersAdapter;
import by.maesens.android.events.EventAssociation;
import by.maesens.android.events.EventDonation;
import by.maesens.android.events.EventProfileList;
import by.maesens.android.events.EventUser;
import by.maesens.android.loaders.PicassoLoader;
import by.maesens.android.model.Association;
import by.maesens.android.model.Auction;
import by.maesens.android.model.Donation;
import by.maesens.android.model.User;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseAssociation;
import by.maesens.android.network.responses.ResponseDonation;
import by.maesens.android.network.responses.ResponseProfileList;
import by.maesens.android.network.responses.ResponseUser;
import by.maesens.android.ui.fragments.base.BaseNetworkFragment;
import by.maesens.android.ui.view.UserPhotoView;

/**
 * Created by Никита on 24.03.2016.
 */
public class UserFragment extends BaseNetworkFragment<EventUser> {

    private static final String ARG_USER_ID = "user_id";
    private int mUserId;

    private Picasso mPicasso;
    private User mUser;
    private Donation mDonation;
    private UserPhotoView mUserPhotoView;
    private TextView mUserName;
    private TextView mUserLocation;
    private TextView mTotalSum;
    //private TextView mTvRegisterDate;
    /*private TextView mTvSellCount;
    private TextView mTvBidsCount;*/
    private ImageView mEmailVerified;
    private ImageView mPhoneVerified;
    private ImageView mVkVerified;
    private ImageView mFbVerified;
    private TextView mTvAboutUser;
    private RelativeLayout mRlUserUnterests;
    private RecyclerView mRecyclerFriends;
    private CardView mCvFriends;
    private TextView mTvFriendsCount;
    private List<Auction> mAuctionList;
    private CardView mCvActiveAuctions;
    private TextView mTvActiveAuctionsCount;
    private RecyclerView mActiveAuctionsRecycler;
    private CardView mCvEndedAuctions;
    private TextView mTvEndedAuctionsCount;
    private RecyclerView mEndedAuctionsRecycler;
    private Button mBtnUserSettings;
    private TextView mTvUserAge;
    private RecyclerView mInterestsRecycler;
    private TextView mAucSold;
    private TextView mAucWon;
    private TextView mBidsMade;
    private Button mBtnSubscribe;
    private Button mBtnOfferMeeting;
    private LinearLayout mLnButtons;

    public static UserFragment newInstance(int userId) {
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        UserFragment userFragment = new UserFragment();
        userFragment.setArguments(args);
        return userFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserId = getArguments().getInt(ARG_USER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        initViews(view);

        try {
            ServiceHelper.getInstance().getData(new String[]{String.valueOf(mUserId)},
                    new EventDonation(), getContext());
            turnOffControls();
            ServiceHelper.getInstance().getData(new String[]{String.valueOf(mUserId)},
                    new EventAssociation(), getContext());
            turnOffControls();
            ServiceHelper.getInstance().getData(new String[]{String.valueOf(mUserId)},
                    new EventProfileList(), getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void initViews(View view) {
        mPicasso = PicassoLoader.getInstance(getActivity());
        mUserPhotoView = (UserPhotoView) view.findViewById(R.id.cvUserUserPhoto);
        mUserPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUserAvatar();
            }
        });
        mUserName = (TextView) view.findViewById(R.id.cvUserUserName);
        mUserLocation = (TextView) view.findViewById(R.id.cvUserUserLocation);
        mTotalSum = (TextView) view.findViewById(R.id.cvUserTotalSum);
        //mTvRegisterDate = (TextView) view.findViewById(R.id.tvRegisterDate);
        /*mTvSellCount = (TextView) view.findViewById(R.id.tvSellCountVal);
        mTvBidsCount = (TextView) view.findViewById(R.id.tvBidCountVal);*/
        mEmailVerified = (ImageView) view.findViewById(R.id.imgEmailVerify);
        mPhoneVerified = (ImageView) view.findViewById(R.id.imgPhoneVerify);
        mVkVerified = (ImageView) view.findViewById(R.id.imgVkVerify);
        mFbVerified = (ImageView) view.findViewById(R.id.imgFbVerify);
        mTvAboutUser = (TextView) view.findViewById(R.id.tvAboutUser);
        mRlUserUnterests = (RelativeLayout) view.findViewById(R.id.rlUserInterests);
        mRecyclerFriends = (RecyclerView) view.findViewById(R.id.friendsRecycler);
        mCvFriends = (CardView) view.findViewById(R.id.cvUserFriends);
        mTvFriendsCount = (TextView) view.findViewById(R.id.tvFriendsCount);
        mCvActiveAuctions = (CardView) view.findViewById(R.id.cvActiveAuctions);
        mTvActiveAuctionsCount = (TextView) view.findViewById(R.id.tvActiveAuctionsCount);
        mActiveAuctionsRecycler = (RecyclerView) view.findViewById(R.id.activeAuctionsRecycler);
        mCvEndedAuctions = (CardView) view.findViewById(R.id.cvEndedAuctions);
        mTvEndedAuctionsCount = (TextView) view.findViewById(R.id.tvEndedAuctionsCount);
        mEndedAuctionsRecycler = (RecyclerView) view.findViewById(R.id.endedAuctionsRecycler);
        mBtnUserSettings = (Button) view.findViewById(R.id.btnUserSettings);
        mBtnUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("UserFragment", "переходим к настройкам");
                mFragmentSetter.setFragment(new FragmentSettings());
            }
        });
        if (mUserId == AppAccount.getId()) {
            mBtnUserSettings.setVisibility(View.VISIBLE);
        } else {
            mBtnUserSettings.setVisibility(View.GONE);
        }
        mTvUserAge = (TextView) view.findViewById(R.id.userAge);
        mInterestsRecycler = (RecyclerView) view.findViewById(R.id.interestsRecycler);
        mAucSold = (TextView) view.findViewById(R.id.tvSellVal);
        mAucWon = (TextView) view.findViewById(R.id.tvWinVal);
        mBidsMade = (TextView) view.findViewById(R.id.tvBids);
        mBtnSubscribe = (Button) view.findViewById(R.id.btnSubscribe);
        mBtnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mBtnSubscribe", "подписываемся на пользователя");
            }
        });
        mBtnOfferMeeting = (Button) view.findViewById(R.id.btnOfferMeeting);
        mBtnOfferMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mBtnOfferMeeting", "предлагаем встречу");
            }
        });
        mLnButtons = (LinearLayout) view.findViewById(R.id.lnButtons);
    }

    private void showUserAvatar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_user_avatar, null);
        ImageView imgAvatar = (ImageView) view.findViewById(R.id.imgUserAvatar);
        mPicasso.load(mUser.getProfile_avatar())
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(imgAvatar);

        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onFailResponse(IBaseResponse response) {
        Log.d("UserFragment", "onFailResponse");
    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        Log.d("UserFragment", "onSuccessResponse");
        mUser = ((ResponseUser) response).getResult();

        mPicasso.load(mUser.getProfile_avatar())
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(mUserPhotoView);
        mUserPhotoView.setOnlineStatus(mUser.getIs_online());

        mUserName.setText(mUser.getFirst_name());
        mUserLocation.setText(mUser.getLocation().getTitle());

       // setRegistrationDate();
        verifyEmailAndPhone();
        setUserInfo();
        setUserFriends();
        if (mUser.getBirth_date() != null) {
            setAge(mUser.getBirth_date());
        }

        if (mUser.getId() == AppAccount.getId()){
            mLnButtons.setVisibility(View.GONE);
        }
    }

    private void setAge(String date) {
        Calendar calendar = GregorianCalendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int age = currentYear - Integer.valueOf(date.substring(0, 4));
        mTvUserAge.setText(String.valueOf(age));
        mTvUserAge.setVisibility(View.VISIBLE);
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventUser event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String[] getApiParams() {
        String[] params = new String[1];
        params[0] = String.valueOf(mUserId);
        return params;
    }

    @Override
    public EventUser getEvent() {
        return new EventUser();
    }


    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventUser eventUser) {
        super.onApiResponse(eventUser);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDonationResponse(EventDonation eventDonation) {
        mDonation = ((ResponseDonation) eventDonation.getResponse()).getResult();

        StringBuilder sb = new StringBuilder(Integer.toString(mDonation.getDonation()));
        int j = 0;
        for (int i = sb.length(); i > 0; i--) {
            if (j++ % 3 == 0) {
                sb.insert(i, ' ');
            }
        }
        mTotalSum.setText(sb.delete(sb.length() - 1, sb.length()).toString());
        /*mTvSellCount.setText(String.valueOf(mDonation.getAuctions_count()));
        mTvBidsCount.setText(String.valueOf(mDonation.getBids_count()));*/
        mAucSold.setText(String.valueOf(mDonation.getAuctions_count()));
        mBidsMade.setText(String.valueOf(mDonation.getBids_count()));
        mAucWon.setText(String.valueOf(mDonation.getAuctions_win_count()));
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onAssociationResponse(EventAssociation eventAssociation) {
        List<Association> associations = ((ResponseAssociation) eventAssociation.getResponse()).getResult();
        verifyVkAndFb(associations);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onProfileListResponse(EventProfileList event) {
        mAuctionList = ((ResponseProfileList) event.getResponse()).getResult();
        Log.d("BalanceFragment", "profileList == " + mAuctionList.size());
        if (mAuctionList.size() > 0) {
            List<Auction> activeAuctions = new ArrayList<>();
            List<Auction> endedAuctions = new ArrayList<>();
            for (Auction a : mAuctionList) {
                if (!a.is_ended()) {
                    activeAuctions.add(a);
                } else {
                    endedAuctions.add(a);
                }
            }
            initActiveAuctions(activeAuctions);
            initEndedAuctions(endedAuctions);
        }
        turnOnControls();
    }

    private void initActiveAuctions(List<Auction> activeAuctions) {
        if (activeAuctions.size() > 0) {
            mCvActiveAuctions.setVisibility(View.VISIBLE);
            mTvActiveAuctionsCount.setText("(" + String.valueOf(activeAuctions.size()) + ")");
            ShortAuctionsAdapter adapter = new ShortAuctionsAdapter(R.layout.item_auction, mPicasso, mFragmentSetter);
            adapter.reset();
            adapter.addData(activeAuctions);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
            mActiveAuctionsRecycler.setLayoutManager(manager);
            mActiveAuctionsRecycler.setAdapter(adapter);
        }
    }

    private void initEndedAuctions(List<Auction> endedAuctions) {
        if (endedAuctions.size() > 0) {
            mCvEndedAuctions.setVisibility(View.VISIBLE);
            mTvEndedAuctionsCount.setText("(" + String.valueOf(endedAuctions.size()) + ")");
            ShortAuctionsAdapter adapter = new ShortAuctionsAdapter(R.layout.item_auction, mPicasso, mFragmentSetter);
            adapter.reset();
            adapter.addData(endedAuctions);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
            mEndedAuctionsRecycler.setLayoutManager(manager);
            mEndedAuctionsRecycler.setAdapter(adapter);
        }
    }

    private void verifyEmailAndPhone() {
        if (mUser.isEmail_verified()) {
            mEmailVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
        } else {
            mEmailVerified.setBackgroundResource(R.drawable.ic_verify_no_24dp);
        }

        if (mUser.isPhone_verified()) {
            mPhoneVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
        } else {
            mPhoneVerified.setBackgroundResource(R.drawable.ic_verify_no_24dp);
        }
    }

    private void verifyVkAndFb(List<Association> associations) {
        mFbVerified.setBackgroundResource(R.drawable.ic_verify_no_24dp);
        mVkVerified.setBackgroundResource(R.drawable.ic_verify_no_24dp);
        for (Association a : associations) {
            if (a.getProvider().equals("vk-oauth2"))
                mVkVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
            if (a.getProvider().equals("facebook"))
                mFbVerified.setBackgroundResource(R.drawable.ic_verify_yes_24dp);
        }
    }

    /*private void setRegistrationDate() {
        String strDate = mUser.getDate_joined();
        String start = strDate.substring(0, 10);
        mTvRegisterDate.setText(getString(R.string.register_date) + " " + start);
    }*/

    private void setUserInfo() {
        String aboutUser = mUser.getAbout();
        if (TextUtils.isEmpty(aboutUser)) {
            mTvAboutUser.setText(getString(R.string.no_info));
        } else {
            mTvAboutUser.setText(aboutUser);
        }

        if (mUser.getTags().length > 0) {
            List<String> interests = Arrays.asList(mUser.getTags());
            mRlUserUnterests.setVisibility(View.VISIBLE);
            InterestsAdapterNoClick adapter = new InterestsAdapterNoClick(R.layout.item_interest, mPicasso, mFragmentSetter);
            adapter.reset();
            adapter.addData(interests);
            LinearLayoutManager manager = new LinearLayoutManager(getActivity());
            mInterestsRecycler.setLayoutManager(manager);
            mInterestsRecycler.setAdapter(adapter);
        }
    }

    private void setUserFriends() {
        if (mUser.getFriends_limited().length > 0) {
            mTvFriendsCount.setText("(" + String.valueOf(mUser.getFriends_limited().length) + ")");
            mCvFriends.setVisibility(View.VISIBLE);
            List<User> userList = Arrays.asList(mUser.getFriends_limited());
            UsersAdapter adapter = new UsersAdapter(R.layout.item_user, mPicasso, mFragmentSetter);
            adapter.reset();
            adapter.addData(userList);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);
            mRecyclerFriends.setLayoutManager(manager);
            mRecyclerFriends.setAdapter(adapter);
        }
    }
}
