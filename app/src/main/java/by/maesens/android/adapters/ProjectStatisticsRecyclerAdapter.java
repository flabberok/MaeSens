package by.maesens.android.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Order;
import by.maesens.android.ui.view.UserPhotoView;
import by.maesens.android.utils.DateConverter;

/**
 * Created by Sol on 04.04.2016.
 */
public class ProjectStatisticsRecyclerAdapter extends BaseRecyclerAdapter<Order, ProjectStatisticsRecyclerAdapter.OrderHolder> {

    public ProjectStatisticsRecyclerAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected OrderHolder getHolder(View view) {
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, int position) {
        holder.bindData(mList.get(position));


    }

    public class OrderHolder extends BaseRecyclerAdapter.BaseHolder<Order> {


        public static final String TAG = "OrderHolder";
        private final TextView mTvDate;
        private final TextView mTvTypeTransaction;
        private final TextView mTvAmount;
        private final UserPhotoView mUpvDonated;

        public OrderHolder(View itemView) {
            super(itemView);
            mTvDate = (TextView) itemView.findViewById(R.id.tv_statistics_date);
            mTvTypeTransaction = (TextView) itemView.findViewById(R.id.tv_statistics_type_transaction);
            mTvAmount = (TextView) itemView.findViewById(R.id.tv_statistics_amount);
            mUpvDonated = (UserPhotoView) itemView.findViewById(R.id.upv_statistics_donated);
        }


        @Override

        public void bindData(Order order) {
            String date = DateConverter.convertDateToString(
                    DateConverter.convertStringToDate(order.getPayment_date().replace("T"," ").replace("Z",""), "yyyy-MM-dd HH:mm:SS"),
                    "yyyy-MM-dd  HH:mm");
            Log.d(TAG,DateConverter.convertStringToDate(order.getPayment_date().replace("T", " ").replace("Z", ""), "yyyy-MM-dd HH:mm:SS").toString());
            Log.d(TAG,order.getPayment_date());
            Log.d(TAG, " -----  " + date);
            mTvDate.setText(date);
           mTvAmount.setText(String.valueOf(order.getAmount()));
            mTvTypeTransaction.setText(getTypeDonation(order.getPayment_type()));
            mPicasso.load(order.getUser().getSmall_avatar())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(mUpvDonated);
        }


        String getTypeDonation(int type) {
            String typeDonation = ((Context) mFragmentSetter).getString(R.string.bid_type);
            switch (type) {
                case 1: {
                    typeDonation = ((Context) mFragmentSetter).getString(R.string.type_donation_1);
                }
                case 2: {
                    typeDonation = ((Context) mFragmentSetter).getString(R.string.type_donation_2);
                }
                case 4: {
                    typeDonation = ((Context) mFragmentSetter).getString(R.string.type_donation_4);
                }
            }
            return typeDonation;
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            Log.d(TAG, "OrderHolder onClick");
        }
    }
}
