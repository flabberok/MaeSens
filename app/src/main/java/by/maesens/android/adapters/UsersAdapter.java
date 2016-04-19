package by.maesens.android.adapters;

import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.User;
import by.maesens.android.ui.fragments.UserFragment;
import by.maesens.android.ui.view.UserPhotoView;

/**
 * Created by Никита on 24.03.2016.
 */
public class UsersAdapter extends BaseRecyclerAdapter<User, UsersAdapter.UsersHolder> {

    public UsersAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected UsersHolder getHolder(View view) {
        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersHolder holder, int position) {
        User user = mList.get(position);
        holder.bindData(user);
    }

    public class UsersHolder extends BaseRecyclerAdapter.BaseHolder<User>{

        private UserPhotoView mUserPhoto;
        private TextView mUserName;
        private User mUser;

        public UsersHolder(View itemView){
            super(itemView);
            mUserPhoto = (UserPhotoView) itemView.findViewById(R.id.friendPhoto);
            mUserName = (TextView) itemView.findViewById(R.id.friendName);
        }

        @Override
        public void bindData(User user) {
            mUser = user;

            mPicasso.load(mUser.getSmall_avatar())
                    .placeholder(R.drawable.ic_user)
                    .error(R.drawable.ic_user)
                    .into(mUserPhoto);

            mUserName.setText(mUser.getFirst_name());
        }

        @Override
        public void onClick(View v) {
            mFragmentSetter.setFragment(UserFragment.newInstance(mUser.getId()));
        }
    }
}
