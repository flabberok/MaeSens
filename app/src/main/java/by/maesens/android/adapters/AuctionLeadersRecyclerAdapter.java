package by.maesens.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Bid;
import by.maesens.android.ui.fragments.UserFragment;
import by.maesens.android.ui.view.UserPhotoView;

/**
 * Created by Виктор on 14.03.2016.
 */
public class AuctionLeadersRecyclerAdapter extends BaseRecyclerAdapter<Bid, AuctionLeadersRecyclerAdapter.LeadersHolder> {

    public AuctionLeadersRecyclerAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected LeadersHolder getHolder(View view) {
        return new LeadersHolder(view);
    }

    @Override
    public void onBindViewHolder(LeadersHolder holder, int position) {
        Bid project = mList.get(position);
        holder.bindData(project);
    }

    public class LeadersHolder extends BaseRecyclerAdapter.BaseHolder<Bid> {
        UserPhotoView mUserPhoto;
        TextView mUserName;
        TextView mUserBid;
        Bid mBid;

        public LeadersHolder(View itemView) {
            super(itemView);
            mUserPhoto = (UserPhotoView) itemView.findViewById(R.id.uvUserPhoto);
            mUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mUserBid = (TextView) itemView.findViewById(R.id.tvUserBid);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            if (mBid.getHidden())
                Toast.makeText((Context) mFragmentSetter, ((Context) mFragmentSetter).getResources().getString(R.string.hidden_bid), Toast.LENGTH_SHORT).show();
            else
                mFragmentSetter.setFragment(UserFragment.newInstance(mBid.getUser().getId()));
        }

        @Override
        public void bindData(Bid bid) {
            mBid = bid;
            mUserName.setText(mBid.getUser().getFirst_name());
            mUserBid.setText(String.format("%,d", mBid.getAmount()));
            mPicasso.load(mBid.getUser().getSmall_avatar())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(mUserPhoto);
            mUserPhoto.setOnlineStatus(mBid.getUser().getIs_online());
        }
    }
}
