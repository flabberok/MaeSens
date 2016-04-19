package by.maesens.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import by.maesens.android.interfaces.IComponentSetter;

/**
 * Created by Никита on 18.02.2016.
 */
public abstract class BaseRecyclerAdapter<DATA, HOLDER extends BaseRecyclerAdapter.BaseHolder> extends RecyclerView.Adapter<HOLDER> {

    protected List<DATA> mList;
    private int mItemId;
    protected Picasso mPicasso;
    protected IComponentSetter mFragmentSetter;

    public BaseRecyclerAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        mItemId = itemId;
        mPicasso = picasso;
        mFragmentSetter = fragmentSetter;
    }

    public void addData(List<DATA> list) {
        if (mList != null) {
            if (list != null)
                mList.addAll(list);
            else
                mList.clear();

            notifyDataSetChanged();
        }
    }

    @Override
    public HOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mItemId, parent, false);
        return getHolder(view);
    }

    @Override
    public int getItemCount() {
//       Log.d("BaseRecyclerAdapter","getItemCount mList  "+mList);
        if (mList == null)
            return 0;
        else
            return mList.size();
    }

    public void reset() {
        if (mList == null) {
            mList = new ArrayList<>();
        } else {
            mList.clear();
        }
        notifyDataSetChanged();
    }

    protected abstract HOLDER getHolder(View view);

    public static abstract class BaseHolder<DATA> extends RecyclerView.ViewHolder implements View.OnClickListener {

        public BaseHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public abstract void bindData(DATA data);

        @Override
        public void onClick(View v) {
            Log.d("BaseHolder", "itemPressed");
        }
    }
}
