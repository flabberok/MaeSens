package by.maesens.android.adapters;

import android.view.View;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;

/**
 * Created by Никита on 02.04.2016.
 */
public class InterestsAdapterNoClick extends BaseRecyclerAdapter<String, InterestsAdapterNoClick.InterestHolder> {

    public InterestsAdapterNoClick(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected InterestHolder getHolder(View view) {
        return new InterestHolder(view);
    }

    @Override
    public void onBindViewHolder(InterestHolder holder, int position) {
        String string = mList.get(position);
        holder.bindData(string);
    }

    public class InterestHolder extends BaseRecyclerAdapter.BaseHolder<String> {

        private TextView mTvInterest;

        public InterestHolder(View itemView) {
            super(itemView);
            mTvInterest = (TextView) itemView.findViewById(R.id.tvUserInterest);
        }

        @Override
        public void bindData(String string) {
            mTvInterest.setText(string);
        }

        @Override
        public void onClick(View v) {
        }
    }
}
