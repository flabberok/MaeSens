package by.maesens.android.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Auction;
import by.maesens.android.ui.fragments.AuctionFragment;

/**
 * Created by Никита on 24.03.2016.
 */
public class ShortAuctionsAdapter extends BaseRecyclerAdapter<Auction, ShortAuctionsAdapter.AuctionHolder> {

    public ShortAuctionsAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
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

    public class AuctionHolder extends BaseRecyclerAdapter.BaseHolder<Auction>{

        private TextView mTvTitle;
        private ImageView mImageView;
        private Auction mAuction;
        private TextView mTvBid;

        public AuctionHolder(View itemView){
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_text_view);
            mImageView = (ImageView) itemView.findViewById(R.id.item_image_view);
            mTvBid = (TextView) itemView.findViewById(R.id.item_tv_bid);
        }

        @Override
        public void bindData(Auction auction) {
            mAuction = auction;
            mPicasso.load(mAuction.getSmall_image())
                    .into(mImageView);
            mTvTitle.setText(mAuction.getTitle());
            mTvBid.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            mFragmentSetter.setFragment(AuctionFragment.getInstance(mAuction));
        }
    }
}
