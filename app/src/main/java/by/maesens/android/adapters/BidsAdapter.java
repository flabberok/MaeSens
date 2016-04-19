package by.maesens.android.adapters;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.ContentHandler;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Auction;
import by.maesens.android.model.Order;
import by.maesens.android.ui.fragments.AuctionFragment;
import by.maesens.android.ui.view.UserPhotoView;

/**
 * Created by Никита on 17.03.2016.
 */
public class BidsAdapter extends BaseRecyclerAdapter<Order, BidsAdapter.OrderHolder> {

    public BidsAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected OrderHolder getHolder(View view) {
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        Order order = mList.get(position);
        holder.bindData(order);
    }

    public class OrderHolder extends BaseRecyclerAdapter.BaseHolder<Order>{

        private TextView mTvBidDate;
        private TextView mTvBidType;
        private UserPhotoView mPhotoUser;
        private Order mOrder;

        public OrderHolder(View itemView){
            super(itemView);
            mTvBidDate = (TextView) itemView.findViewById(R.id.itemBidDate);
            mTvBidType = (TextView) itemView.findViewById(R.id.itemBidType);
            mPhotoUser = (UserPhotoView) itemView.findViewById(R.id.itemBidUser);
        }

        @Override
        public void bindData(Order order) {
            mOrder = order;

            //TODO сделать через SimpleDateFormat
            String date = mOrder.getPayment_date().substring(0, 10);
            mTvBidDate.setText(date);

            if(mOrder.getPayment_type() == 1){
                mTvBidType.setText(((Context)mFragmentSetter).getString(R.string.bid_type));
            }

            mPicasso.load(mOrder.getAuction().getImage())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(mPhotoUser);
        }

        @Override
        public void onClick(View v) {
            Auction auction = new Auction();
            auction.setId(mOrder.getAuction().getId());
            mFragmentSetter.setFragment(AuctionFragment.getInstance(auction));
        }
    }
}
