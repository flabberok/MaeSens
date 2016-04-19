package by.maesens.android.ui.fragments.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import by.maesens.android.R;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventWithList;
import by.maesens.android.network.responses.IBaseResponse;

/**
 * Created by Никита on 18.02.2016.
 */
public abstract class BaseRecyclerFragment<EVENT extends EventWithList> extends BaseNetworkFragment<EVENT> {

    protected int mLayoutId;
    protected int mRecyclerId;
    protected int mSwipeRefreshId;

    protected int mNextPage = 1;
    private boolean mIsPagination = false;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected BaseRecyclerAdapter mAdapter;
    protected EndlessScrollListener mScrollListener;
    protected RecyclerView mRecyclerView;
    protected int mScrollPosition;

    private int mColumnCount = 3;

    public int getColumnCount() {
        return mColumnCount;
    }

    public void setColumnCount(int columnCount) {
        mColumnCount = columnCount;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(mLayoutId, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewsId();
        mAdapter = setAdapter();
    }

    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mScrollPosition);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) view.findViewById(mRecyclerId);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));

        mRecyclerView.setAdapter(mAdapter);
        mScrollListener = new EndlessScrollListener((GridLayoutManager) mRecyclerView.getLayoutManager());
        mRecyclerView.addOnScrollListener(mScrollListener);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(mSwipeRefreshId);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListener());
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);


        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract void setViewsId();

    protected abstract BaseRecyclerAdapter setAdapter();

    protected void init() {
        mAdapter.reset();
        mNextPage = 1;
        fetchData();
    }

    protected boolean fetchData() {
        if (!isPagination() && mNextPage > 1) {
            return false;
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
            sendApiRequest(getApiParams(),getEvent());
        }
        return true;
    }

//    protected abstract String[] getApiParams();

    public boolean isPagination() {
        return mIsPagination;
    }

    public void setPagination(boolean pagination) {
        this.mIsPagination = pagination;
    }

    private class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            mRecyclerView.removeOnScrollListener(mScrollListener);
            init();
        }
    }

    @Override
    public void onFailResponse(IBaseResponse response) {
        Log.d("BaseRecyclerFragment", "onFailResponse");
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        Log.d("BaseRecyclerFragment", "onSuccessResponse");
        initData((List)response.getResult());
    }

    public void initData(List list){
        if (list.size() == 0){
            // Временно уберу, так как сообщение странно выглядит на вкладках.
            //Toast.makeText(getActivity(), getString(R.string.nothing_found), Toast.LENGTH_SHORT).show();
        }
        mAdapter.addData(list);
        mNextPage++;
        if (mSwipeRefreshLayout.isRefreshing()) {
            mScrollListener.reset();
            mRecyclerView.addOnScrollListener(mScrollListener);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private class EndlessScrollListener extends RecyclerView.OnScrollListener {
        private int previousTotal;
        private int firstVisibleItem;
        private int visibleItemCount;
        private int totalItemCount;
        private final int visibleThreshold = 5;

        private boolean loading = true;

        private final GridLayoutManager mGridLayoutManager;

        public EndlessScrollListener(GridLayoutManager gridLayoutManager) {
            this.mGridLayoutManager = gridLayoutManager;
        }

        public void reset() {
            previousTotal = firstVisibleItem = visibleItemCount = totalItemCount = 0;
            loading = true;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mGridLayoutManager.getItemCount();
            firstVisibleItem = mGridLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold) && (totalItemCount > 0)) {
                loading = true;
                loadMore();
            }
        }

        public void loadMore() {
            fetchData();
        }
    }

}
