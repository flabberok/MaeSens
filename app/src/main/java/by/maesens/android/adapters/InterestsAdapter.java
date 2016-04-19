package by.maesens.android.adapters;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import by.maesens.android.R;
import by.maesens.android.events.EventDeleteTag;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Tag;
import by.maesens.android.network.ServiceHelper;

/**
 * Created by Никита on 02.04.2016.
 */
public class InterestsAdapter extends BaseRecyclerAdapter<Tag, InterestsAdapter.InterestHolder> {

    public InterestsAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected InterestHolder getHolder(View view) {
        return new InterestHolder(view);
    }

    @Override
    public void onBindViewHolder(InterestHolder holder, int position) {
        Tag tag = mList.get(position);
        holder.bindData(tag);
    }

    public class InterestHolder extends BaseRecyclerAdapter.BaseHolder<Tag> {

        private TextView mTvInterest;
        private Tag mTag;

        public InterestHolder(View itemView) {
            super(itemView);
            mTvInterest = (TextView) itemView.findViewById(R.id.tvUserInterest);
        }

        @Override
        public void bindData(Tag tag) {
            mTag = tag;
            mTvInterest.setText(tag.getTag_name());
        }

        @Override
        public void onClick(View v) {
            Log.d("InterestHolder", "удаляем тэг");
            try {
                ServiceHelper.getInstance().getData(new String[]{String.valueOf(mTag.getId())},
                        new EventDeleteTag(), v.getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
