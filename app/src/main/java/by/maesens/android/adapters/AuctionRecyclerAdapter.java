package by.maesens.android.adapters;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Auction;
import by.maesens.android.ui.fragments.AuctionFragment;

/**
 * Created by Никита on 18.02.2016.
 */
public class AuctionRecyclerAdapter extends BaseRecyclerAdapter<Auction, AuctionRecyclerAdapter.AuctionHolder> {

    public AuctionRecyclerAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected AuctionHolder getHolder(View view) {
        return new AuctionHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionHolder holder, int position) {
        Auction auction = mList.get(position);
        holder.bindData(auction);
    }

    public class AuctionHolder extends BaseRecyclerAdapter.BaseHolder<Auction> {

        private TextView mTvTitle;
        private ImageView mImageView;
        private Auction mAuction;
        private TextView mTvBid;
        private TextView mTvLastDay;

        public AuctionHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
            mTvBid = (TextView) itemView.findViewById(R.id.item_tv_bid);
            mTvLastDay = (TextView) itemView.findViewById(R.id.item_tv_last_day);
        }

        @Override
        public void bindData(Auction auction) {
            mAuction = auction;
            mTvTitle.setText(mAuction.getTitle());

            if (mAuction.getEnd() != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
                Date endDate = null;
                try {
                    endDate = dateFormat.parse(mAuction.getEnd());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date currentDate = new Date();

                long difference = endDate.getTime() - currentDate.getTime();
                if(difference > 0){
                    long days =  difference / (24 * 60 * 60 * 1000);
                    if (days == 1)
                        mTvLastDay.setText(R.string.one_day_left);
                }
            }

            mPicasso.load(mAuction.getSmall_image())
                    .into(mImageView);

            int lastBid = mAuction.getLast_bid();
            if(lastBid == 0){
                mTvBid.setText(R.string.no_bids);
            }else{
                StringBuilder sb = new StringBuilder(Integer.toString(lastBid));
                int j = 0;
                for (int i = sb.length(); i > 0; i--) {
                    if (j++ % 3 == 0) {
                        sb.insert(i, ' ');
                    }
                }
                mTvBid.setText(sb.delete(sb.length() - 1, sb.length()).toString());
            }
        }


        @Override
        public void onClick(View v) {
            super.onClick(v);

            mFragmentSetter.setFragment(AuctionFragment.getInstance(mAuction));
        }
    }
}
