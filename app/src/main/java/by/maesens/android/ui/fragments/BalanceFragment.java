package by.maesens.android.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import by.maesens.android.R;
import by.maesens.android.account.AppAccount;
import by.maesens.android.adapters.BidsAdapter;
import by.maesens.android.ui.dialogs.BidDialog;
import by.maesens.android.events.EventDonation;
import by.maesens.android.events.EventOrder;
import by.maesens.android.events.EventProfile;
import by.maesens.android.events.EventProfileList;
import by.maesens.android.model.Auction;
import by.maesens.android.model.Donation;
import by.maesens.android.model.Order;
import by.maesens.android.model.Profile;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseDonation;
import by.maesens.android.network.responses.ResponseOrder;
import by.maesens.android.network.responses.ResponseProfileList;
import by.maesens.android.ui.fragments.base.BaseNetworkFragment;

/**
 * Created by Никита on 16.03.2016.
 */
public class BalanceFragment extends BaseNetworkFragment<EventProfile> implements View.OnClickListener {

    public static final int DIALOG_FRAGMENT = 1;
    public static final int DIALOG_RESULT_OK = 0;
    public static final String DIALOG_BID_VAL = "dialogBidValue";
    public static final String RULES_REFERENCE = "https://maesens.by/page/site_rules";

    private Profile mProfile;
    private Donation mDonation;
    private List<Auction> mProfileList;
    private List<Order> mOrderList;

    private TextView mTvBalanceVal;
    private Button mBtnBid1;
    private Button mBtnBid2;
    private Button mBtnBid3;
    private Button mBtnBid4;
    private Button mBtnBid5;
    private Button mBtnBidX;
    private CheckBox mCbAgreeRules;
    private TextView mTvAgreeRules;
    private TextView mTvAgreeRefference;
    private Button mBtnContinue;
    private boolean isBidChoosed = false;
    private TextView mTvMeetingsSold;
    private TextView mTvBidsMade;
    private TextView mRegisterDate;
    private RecyclerView mBidsRecycler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        initViews(view);
//        sendApiRequest();
        try {
            ServiceHelper.getInstance().getData(new String[]{String.valueOf(AppAccount.getId())},
                    new EventDonation(), getContext());
            turnOffControls();
            ServiceHelper.getInstance().getData(new String[]{String.valueOf(AppAccount.getId())},
                    new EventProfileList(), getContext());
            turnOffControls();
            ServiceHelper.getInstance().getData(new String[]{}, new EventOrder(), getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    protected String[] getApiParams() {
        return null;
    }

    private void initViews(View view) {
        Log.d("BalanceFragment", "initViews");
        mTvBalanceVal = (TextView) view.findViewById(R.id.tvBalanceVal);
        mBtnBid1 = (Button) view.findViewById(R.id.btnBid1);
        mBtnBid1.setOnClickListener(this);
        mBtnBid2 = (Button) view.findViewById(R.id.btnBid2);
        mBtnBid2.setOnClickListener(this);
        mBtnBid3 = (Button) view.findViewById(R.id.btnBid3);
        mBtnBid3.setOnClickListener(this);
        mBtnBid4 = (Button) view.findViewById(R.id.btnBid4);
        mBtnBid4.setOnClickListener(this);
        mBtnBid5 = (Button) view.findViewById(R.id.btnBid5);
        mBtnBid5.setOnClickListener(this);
        mBtnBidX = (Button) view.findViewById(R.id.btnBidX);
        mBtnBidX.setOnClickListener(this);

        mCbAgreeRules = (CheckBox) view.findViewById(R.id.checkboxAgreeRules);
        mCbAgreeRules.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("BalanceFragment", "onCheckedChanged");
                if (isChecked && isBidChoosed) {
                    mBtnContinue.setEnabled(true);
                } else
                    mBtnContinue.setEnabled(false);
            }
        });

        mTvAgreeRules = (TextView) view.findViewById(R.id.tvAgreeRules);
        mTvAgreeRefference = (TextView) view.findViewById(R.id.tvAgreeReference);
        Spannable text = new SpannableString(getString(R.string.agree_rules));
        text.setSpan(new UnderlineSpan(), 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvAgreeRefference.setText(text);
        mTvAgreeRefference.setOnClickListener(this);

        mBtnContinue = (Button) view.findViewById(R.id.btnContinue);
        mBtnContinue.setEnabled(false);
        mBtnContinue.setOnClickListener(this);

        mTvMeetingsSold = (TextView) view.findViewById(R.id.tvSellCountVal);
        mTvBidsMade = (TextView) view.findViewById(R.id.tvBidCountVal);
        mRegisterDate = (TextView) view.findViewById(R.id.tvRegisterDate);

        mBidsRecycler = (RecyclerView) view.findViewById(R.id.bidsRecycler);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBid1:
                mBtnBidX.setText(getString(R.string.bid_1));
                isBidChoosed = true;
                break;
            case R.id.btnBid2:
                mBtnBidX.setText(getString(R.string.bid_2));
                isBidChoosed = true;
                break;
            case R.id.btnBid3:
                mBtnBidX.setText(getString(R.string.bid_3));
                isBidChoosed = true;
                break;
            case R.id.btnBid4:
                mBtnBidX.setText(getString(R.string.bid_4));
                isBidChoosed = true;
                break;
            case R.id.btnBid5:
                mBtnBidX.setText(getString(R.string.bid_5));
                isBidChoosed = true;
                break;
            case R.id.btnBidX:
                showDialog();
                break;
            case R.id.btnContinue:
                int bid = Integer.valueOf(mBtnBidX.getText().toString().replace(" ", ""));
                mCbAgreeRules.setChecked(false);
                mFragmentSetter.setFragment(BidConfirmFragment.newInstance(mProfile.getBalance(), bid));
                break;
            case R.id.tvAgreeReference:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(RULES_REFERENCE));
                startActivity(intent);
                break;
        }
        if (isBidChoosed && mCbAgreeRules.isChecked()) {
            mBtnContinue.setEnabled(true);
        } else mBtnContinue.setEnabled(false);
    }

    void showDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        BidDialog dialog = new BidDialog();
        dialog.setTargetFragment(this, DIALOG_FRAGMENT);
        dialog.show(ft, "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DIALOG_FRAGMENT) {
            if (resultCode == DIALOG_RESULT_OK) {
                String val = String.valueOf(data.getIntExtra(DIALOG_BID_VAL, 0));
                mBtnBidX.setText(val);
                isBidChoosed = true;
            }
        }
    }

    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onFailResponse(IBaseResponse response) {
        Log.d("BalanceFragment", "onFailResponse");
    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        Log.d("BalanceFragment", "onSuccessResponse");
        mProfile = (Profile) response.getResult();
        mTvBalanceVal.setText(String.valueOf(mProfile.getBalance()));

        setDate(mProfile.getDate_joined());

    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventProfile event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventProfile eventProfile) {
        super.onApiResponse(eventProfile);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onDonationResponse(EventDonation eventDonation) {
        mDonation = ((ResponseDonation) eventDonation.getResponse()).getResult();
        Log.d("BalanceFragment", "donation == " + mDonation.getDonation());
        Log.d("BalanceFragment", "auctions_count == " + mDonation.getAuctions_count());
        Log.d("BalanceFragment", "bids_count == " + mDonation.getBids_count());
        mTvBidsMade.setText(String.valueOf(mDonation.getBids_count()));
        mTvMeetingsSold.setText(String.valueOf(mDonation.getAuctions_count()));
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onProfileListResponse(EventProfileList event) {
        mProfileList = ((ResponseProfileList) event.getResponse()).getResult();
        Log.d("BalanceFragment", "profileList == " + mProfileList.size());
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onOrdersResponse(EventOrder event) {
        mOrderList = ((ResponseOrder) event.getResponse()).getResult();
        Log.d("BalanceFragment", "orders == " + mOrderList.size());

        BidsAdapter adapter = new BidsAdapter(R.layout.item_bid, mPicasso, mFragmentSetter);
        adapter.reset();
        adapter.addData(mOrderList);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mBidsRecycler.setLayoutManager(manager);
        mBidsRecycler.setAdapter(adapter);

        turnOnControls();
    }

    @Override
    public EventProfile getEvent() {
        return new EventProfile();
    }

    public void setDate(String strDate) {
        String start = strDate.substring(0, 10);
        mRegisterDate.setText(getString(R.string.register_date) + " " + start);
        //TODO допилить разницу между датами. пока ставим просто дату регистрации
        /*String dateFormat = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(dateFormat, Locale.getDefault());

        Date date1 = null;
        Date date2 = new Date();
        try {
            date1 = df.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);

        long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);

        cal2.setTime(date2);
        long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);

        int hr1   = (int)(ldate1/3600000); //60*60*1000
        int hr2   = (int)(ldate2/3600000);

        int days1 = (int)hr1/24;
        int days2 = (int)hr2/24;


        int dateDiff  = days2 - days1;
        int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK))<0 ? 1 : 0;
        int weekDiff  = dateDiff/7 + weekOffset;
        int yearDiff  = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
        int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);


        Log.d("setDate", "weekDiff-" + weekDiff);
        Log.d("setDate", "yearDiff-" + yearDiff);
        Log.d("setDate", "monthDiff-" + monthDiff);*/
    }
}
