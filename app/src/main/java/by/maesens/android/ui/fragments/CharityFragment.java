package by.maesens.android.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import by.maesens.android.R;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.adapters.CharityRecyclerAdapter;
import by.maesens.android.events.EventCharity;
import by.maesens.android.events.EventProjectsCategory;
import by.maesens.android.model.Category;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.ResponseCharityCategories;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;

/**
 * Created by Миша on 24.02.2016.
 */

public class CharityFragment extends BaseRecyclerFragment<EventCharity> {

    private List<Category> mCategories;
    private View mFilterBlock;
    private View mControlFilter;
    private Spinner mCategoriesSpinner;
    private Spinner mSortSpinner;


    public final static String PARAMETER_SORT_TOP = "-current_amount";
    public final static String PARAMETER_SORT_NEW = "-created_at";
    public final static String PARAMETER_SORT_OLD = "created_at";
    public final static String PARAMETER_CLOSE_ENOUGH = "-final_date,-current_amount";
    public final static String PARAMETER_ALL = "-current_amount";

    private String[] mListNameSortParameter;
    private String[] mListSortParameter = {PARAMETER_ALL, PARAMETER_SORT_TOP, PARAMETER_SORT_NEW, PARAMETER_SORT_OLD, PARAMETER_CLOSE_ENOUGH};

    @NonNull
    private String[] getArrayNameSortParameters(Context context) {
        return new String[]{context.getString(R.string.sort_projects_parameter_all),
                context.getString(R.string.sort_projects_parameter_top),
                context.getString(R.string.sort_projects_parameter_new),
                context.getString(R.string.sort_projects_parameter_old),
                context.getString(R.string.sort_projects_parameter_close_enough)};
    }

    public static CharityFragment newInstance() {

        Bundle args = new Bundle();

        CharityFragment fragment = new CharityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_charity;
        mRecyclerId = R.id.recycler;
        mSwipeRefreshId = R.id.swipeRefresh;
        setPagination(true);
        setColumnCount(1);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preparationListCategories();
    }

    private void preparationListCategories() {
//        if (mCategories == null) {
            try {
                ServiceHelper.getInstance().getData(null, (new EventProjectsCategory()), getContext());
            } catch (Exception e) {
                e.printStackTrace();
            }
//        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mListNameSortParameter = getArrayNameSortParameters(activity);

    }


    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFilterBlock = view.findViewById(R.id.projects_filter);
        mControlFilter = view.findViewById(R.id.tv_filter_charity);
        mCategoriesSpinner = (Spinner) view.findViewById(R.id.projects_spinner_category);
        mSortSpinner = (Spinner) view.findViewById(R.id.projects_spinner_sort);
        mSortSpinner.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, mListNameSortParameter));
        mSortSpinner.setSelection(0);
        mControlFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFilterBlock.getVisibility() == View.GONE) {
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            R.drawable.ic_navigation_arrow_drop_up, 0);
                    mFilterBlock.setVisibility(View.VISIBLE);
                    preparationListCategories();
                } else {
                    ((TextView) v).setCompoundDrawablesWithIntrinsicBounds(0, 0,
                            R.drawable.ic_navigation_arrow_drop_down, 0);
                    mFilterBlock.setVisibility(View.GONE);
                }
            }
        });
        view.findViewById(R.id.btn_search_projects).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView
                        .getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });





    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        return new CharityRecyclerAdapter(R.layout.item_recycler_charity, mPicasso, mFragmentSetter);
    }

    @Override
    protected String[] getApiParams() {
        String[] params = new String[3];
        if (mFilterBlock != null && mFilterBlock.getVisibility() == View.VISIBLE) {
            params[1] = getCategoriesParameter();
            params[2] = getSortParameter();
        }

        params[0] = String.valueOf(mNextPage);
        return params;
    }

    private String getCategoriesParameter() {
        Log.d("CharityFragment", "getSortParameter mCategoriesSpinner.getSelectedItem = " + mCategoriesSpinner.getSelectedItem());
        String params = null;
        String itemCategories = mCategoriesSpinner.getSelectedItem().toString();
        if (itemCategories != null && (!itemCategories.isEmpty())) {
            int i = 0;
            while (itemCategories != mCategories.get(i).getTitle()) {
                i++;
            }
            params = String.valueOf(mCategories.get(i).getId());
            Log.d("CharityFragment", "getSortParameter mCategories.get(i).getId = " + mCategories.get(i).getId());
        }
        return params;
    }

    private String getSortParameter() {

        Log.d("CharityFragment", "getSortParameter mSortSpinner.getSelectedItem = " + mSortSpinner.getSelectedItem());

        int itemSort = mSortSpinner.getSelectedItemPosition();

        String params = String.valueOf(mListSortParameter[itemSort]);
        Log.d("CharityFragment", "getSortParameter mListSortParameter[itemSort] = " + itemSort);

        return params;
    }

    @Override
    public void disableControls() {
        Log.d("CharityFragment", "DisableControl");
        if (mFilterBlock != null) {
            mFilterBlock.setEnabled(false);
        }
        if (mControlFilter != null) {
            mControlFilter.setEnabled(false);
        }
    }

    @Override
    public void enableControls() {
        Log.d("CharityFragment", "EnableControl");
        if (mFilterBlock != null) {
            mFilterBlock.setEnabled(true);
        }
        if (mControlFilter != null) {
            mControlFilter.setEnabled(true);
        }
    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventCharity eventCharity) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, eventCharity, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EventCharity getEvent() {
        return new EventCharity();
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventCharity eventCharity) {
        super.onApiResponse(eventCharity);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onResponseCategories(EventProjectsCategory eventProjectsCategory) {
        mCategories = ((ResponseCharityCategories) eventProjectsCategory.getResponse()).getResult();
        mCategories.add(new Category());
        ArrayAdapter<Category> categoriesAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, mCategories);
        mCategoriesSpinner.setAdapter(categoriesAdapter);

    }
}
