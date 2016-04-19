package by.maesens.android.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import by.maesens.android.R;

/**
 * Created by Никита on 17.03.2016.
 */
public class BidConfirmFragment extends Fragment {

    public static final String PAYMENT_REFFERENCE = "https://secure.webpay.by/";

    public static final String ARG_BID = "bidValue";
    public static final String ARG_BALANCE = "balanceValue";
    private int mBid;
    private int mBalance;

    private TextView mTvBalanceConfirm;
    private TextView mTvAddConfirm;
    private Button mBtnBack;
    private Button mBtnSend;

    public static BidConfirmFragment newInstance(int balance, int bid) {
        Bundle args = new Bundle();
        args.putInt(ARG_BID, bid);
        args.putInt(ARG_BALANCE, balance);

        BidConfirmFragment fragment = new BidConfirmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBid = getArguments().getInt(ARG_BID);
        mBalance = getArguments().getInt(ARG_BALANCE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bid_confirm, container, false);

        mTvBalanceConfirm = (TextView) view.findViewById(R.id.tvBalanceValConfirm);
        mTvBalanceConfirm.setText(String.valueOf(mBalance));
        mTvAddConfirm = (TextView) view.findViewById(R.id.tvAddToBalance);
        mTvAddConfirm.setText(String.valueOf(mBid));

        mBtnBack = (Button) view.findViewById(R.id.btnBack);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        mBtnSend = (Button) view.findViewById(R.id.btnSend);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(PAYMENT_REFFERENCE));
                startActivity(intent);
            }
        });
        return view;
    }
}
