package by.maesens.android.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionRecyclerAdapter;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.events.EventAuctions;
import by.maesens.android.events.EventCategory;
import by.maesens.android.events.EventLocations;
import by.maesens.android.model.Category;
import by.maesens.android.model.Location;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.ResponseAuctions;
import by.maesens.android.network.responses.ResponseCategory;
import by.maesens.android.network.responses.ResponseLocations;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;
import by.maesens.android.ui.view.WideSearchView;

/**
 * Created by Никита on 22.02.2016.
 */
public class FragmentAllAuctions extends BaseRecyclerFragment<EventAuctions> {

    private static final String SORT_BY_NEW = "auctionsNew";
    private static final String SORT_BY_POPULAR = "auctionsPopular";
    private String mAuctionsSort = SORT_BY_NEW;
    private boolean isWideSearch = false;

    private TextView mTvSortNew;
    private TextView mTvSortPopular;
    private TextView mTvWideSearch;
    private WideSearchView mViewWideSearch;

    private Spinner mSpinnerCities;
    private Spinner mSpinnerCategories;
    private Spinner mSpinnerSort;
    private int mHidingItemIndex = 0;
    private ArrayAdapter<String> mAdapterSort;

    private static final String API_PARAMS_PAIR = "1";
    private static final String API_PARAMS_GROUP = "2";
    private static final String API_PARAMS_WITH_ENDED = "true";
    private static final String API_PARAMS_WITHOUT_ENDED = "false";
    private static final String API_PARAMS_SORT_NEW = "-start";
    private static final String API_PARAMS_SORT_OLD = "start";
    private static final String API_PARAMS_SORT_POPULAR = "-likes_amount";
    private static final String API_PARAMS_SORT_NOT_POPULAR = "likes_amount";
    private static final String API_PARAMS_SORT_MAX_BID = "-last_bid";
    private static final String API_PARAMS_SORT_MIN_BID = "last_bid";
    private static final String PARAM_NEW_SEARCH = "isNewSearch";
    private static final String PARAM_SEARCH = "searchParams";
    private static final String APP_PREFERENCES = "mysettings";
    private static final int PARAM_CITY_INDEX = 4;
    private static final int PARAM_CATEGORY_INDEX = 5;

    private String[] mLocations;
    private List<Category> mCategories;
    private boolean isNewSearch;
    private String[] mApiParams = new String[6];
    private SharedPreferences mPreferences;


    public FragmentAllAuctions() {

    }

    //если этот фрагмент вызван из BaseActivity, будет установелн флаг isNewSearch и никаких фильтров для поиска не будет
    //если же мы вернемся на этот фрагмент с фрагмента аукциона, то флаг будет false и мы установим ранее сохраненные параметры фильтров
    public static FragmentAllAuctions newInstance(boolean isNewSearch) {
        Bundle args = new Bundle();
        args.putBoolean(PARAM_NEW_SEARCH, isNewSearch);

        FragmentAllAuctions auctionsFragment = new FragmentAllAuctions();
        auctionsFragment.setArguments(args);
        return auctionsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNewSearch = getArguments().getBoolean(PARAM_NEW_SEARCH);
    }

    //сохраняем параметры фильтрации в строку. параметры разделяем пробелами
    private void saveSearchParams() {
        mPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        //в самом начале устанавливаем номер страницы. его всегда ставим в 1, иначе сервер ничего не вернет на запрос
        String params = "1 ";
        //далее берем текущие параметры и сохраняем их, начиная со второго
        //если параметр null, то сохраняем соответствующую строку
        for (int i = 1; i < mApiParams.length; i++) {
            String temp = mApiParams[i];
            if (temp == null) {
                temp = "null";
            }
            //после последнего пробел не ставим
            if (i < mApiParams.length - 1) {
                params = params + temp + " ";
            } else {
                params += temp;
            }
        }
        Log.d("FragmentAllAuctions", "params " + params);
        editor.putString(PARAM_SEARCH, params);
        editor.apply();
    }

    private String[] loadSearchParams() {
        if (mPreferences.contains(PARAM_SEARCH)) {
            Log.d("FragmentAllAuctions", "loadSearchParams");
            String param = mPreferences.getString(PARAM_SEARCH, "");
            Log.d("FragmentAllAuctions", "load params = " + param);
            String[] params = param.split(" ");
            for (int i = 0; i < params.length; i++) {
                if (params[i].equals("null")) {
                    params[i] = null;
                }
            }
            return params;
        } else
            return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        saveSearchParams();
        isNewSearch = false;
    }

    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_all_auctions;
        mRecyclerId = R.id.all_auctions_recycler;
        mSwipeRefreshId = R.id.all_auctions_swipe_refresh;
        setColumnCount(2);
        setPagination(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mViewWideSearch = (WideSearchView) view.findViewById(R.id.viewWideSearch);

        mSpinnerCities = mViewWideSearch.getSpinnerCities();
        mSpinnerCategories = mViewWideSearch.getSpinnerCategories();
        mSpinnerSort = mViewWideSearch.getSpinnerSort();

        mSpinnerCities.setEnabled(false);
        mSpinnerCategories.setEnabled(false);
        try {
            ServiceHelper.getInstance().getData(new String[]{}, new EventLocations(), getContext());
            turnOffControls();
            ServiceHelper.getInstance().getData(new String[]{}, new EventCategory(), getContext());
            turnOffControls();

        } catch (Exception e) {
            e.printStackTrace();
        }


        mTvSortNew = (TextView) view.findViewById(R.id.all_auctions_sort_new);
        mTvSortNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareSortNew();
                sendApiRequest(getApiParams(), getEvent());
            }
        });
        mTvSortPopular = (TextView) view.findViewById(R.id.all_auctions_sort_popular);
        mTvSortPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareSortPopular();
                sendApiRequest(getApiParams(), getEvent());
            }
        });

        mTvWideSearch = (TextView) view.findViewById(R.id.all_auctions_wide_search);
        mTvWideSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewWideSearch.getVisibility() == View.GONE) {
                    mViewWideSearch.setVisibility(View.VISIBLE);
                    mTvWideSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigation_arrow_drop_up, 0);
                } else {
                    closeSearchView();
                }
            }
        });
        return view;
    }

    private void prepareSortNew() {
        mAuctionsSort = SORT_BY_NEW;
        mTvSortNew.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
        mTvSortPopular.setBackgroundColor(getResources().getColor(R.color.colorBackgroundScreens));
        resetList();
        isWideSearch = false;
    }

    private void prepareSortPopular() {
        mAuctionsSort = SORT_BY_POPULAR;
        mTvSortPopular.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
        mTvSortNew.setBackgroundColor(getResources().getColor(R.color.colorBackgroundScreens));
        resetList();
        isWideSearch = false;
    }

    private void resetList() {
        mNextPage = 1;
        mAdapter.reset();
    }

    private void closeSearchView() {
        mViewWideSearch.setVisibility(View.GONE);
        mTvWideSearch.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_navigation_arrow_drop_down, 0);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // При первоначальной загрузке экрана загружается первая страница новых аукционов,
        // однако другие элементы еще не прорисованы, а переменные не проинициализированы.
        // Поэтому вызываем метод настройки, но не перезагружаем данные.
        prepareSortNew();
        setViewListeners();
    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        return new AuctionRecyclerAdapter(R.layout.item_auction, mPicasso, mFragmentSetter);
    }

    @Override
    protected String[] getApiParams() {
        Log.d("FragmentAllAuctions", "isNewSearch = " + isNewSearch);
        //если поиск новый, то получаем параметры спиннеров и т.д.
        if (isNewSearch) {
            String[] params = new String[6];
            params[0] = String.valueOf(mNextPage);

            if (mAuctionsSort.equals(SORT_BY_NEW)) {
                params[1] = API_PARAMS_SORT_NEW;
            } else {
                params[1] = API_PARAMS_SORT_POPULAR;
            }

            if (isWideSearch) {

                switch (mSpinnerSort.getSelectedItemPosition()) {
                    case 0:
                        params[1] = API_PARAMS_SORT_NEW;
                        break;
                    case 1:
                        params[1] = API_PARAMS_SORT_OLD;
                        break;
                    case 2:
                        params[1] = API_PARAMS_SORT_MAX_BID;
                        break;
                    case 3:
                        params[1] = API_PARAMS_SORT_MIN_BID;
                        break;
                    case 4:
                        params[1] = API_PARAMS_SORT_POPULAR;
                        break;
                    case 5:
                        params[1] = API_PARAMS_SORT_NOT_POPULAR;
                        break;
                }

                if (mViewWideSearch.getShowEnded().isChecked())
                    params[2] = API_PARAMS_WITH_ENDED;
                else
                    params[2] = API_PARAMS_WITHOUT_ENDED;

                if (mViewWideSearch.getRgGroup().getCheckedRadioButtonId() == R.id.wide_search_rb_pair) {
                    params[3] = API_PARAMS_PAIR;
                } else if (mViewWideSearch.getRgGroup().getCheckedRadioButtonId() == R.id.wide_search_rb_group) {
                    params[3] = API_PARAMS_GROUP;
                } else {
                    params[3] = null;
                }

                if (mSpinnerCities.getSelectedItemPosition() < 1) {
                    params[4] = null;
                } else {
                    params[4] = mLocations[mSpinnerCities.getSelectedItemPosition()];
                }

                if (mSpinnerCategories.getSelectedItemPosition() < 1) {
                    params[5] = null;
                } else {
                    int id = mCategories.get(mSpinnerCategories.getSelectedItemPosition() - 1).getId();
                    params[5] = String.valueOf(id);
                }
            }
            mApiParams = params;
            return mApiParams;
        }
        //если isNewSearch false, то загружаем параметры
        else {
            mApiParams = loadSearchParams();
            return mApiParams;
        }
    }

    @Override
    public void disableControls() {
        Log.d("FragmentAllAuctions", "disableControls FragmentAllAuctions");
    }

    @Override
    public void enableControls() {
        Log.d("FragmentAllAuctions", "enableControls FragmentAllAuctions");
    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventAuctions eventAuctions) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, eventAuctions, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EventAuctions getEvent() {
        return new EventAuctions();
    }

    private void setViewListeners() {
        mViewWideSearch.getSearchButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWideSearch = true;
                isNewSearch = true;
                resetList();
                sendApiRequest(getApiParams(), getEvent());
                closeSearchView();
            }
        });
        mViewWideSearch.getSearchButton().setEnabled(false);

        mViewWideSearch.getCancelButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isNewSearch = true;
                mSpinnerCategories.setSelection(0);
                mSpinnerCities.setSelection(0);
                mSpinnerSort.setSelection(0);
                mViewWideSearch.getRgGroup().clearCheck();
                mViewWideSearch.getShowEnded().setChecked(false);
            }
        });
        mViewWideSearch.getCancelButton().setEnabled(false);

        mAdapterSort = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.wide_search_sort));
        mAdapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSort.setPrompt(getString(R.string.choose_order));
        mSpinnerSort.setAdapter(mAdapterSort);

        if (!isNewSearch) {
            if (mApiParams[1] != null) {
                switch (mApiParams[1]) {
                    case API_PARAMS_SORT_NEW:
                        mSpinnerSort.setSelection(0);
                        break;
                    case API_PARAMS_SORT_OLD:
                        mSpinnerSort.setSelection(1);
                        break;
                    case API_PARAMS_SORT_MAX_BID:
                        mSpinnerSort.setSelection(2);
                        break;
                    case API_PARAMS_SORT_MIN_BID:
                        mSpinnerSort.setSelection(3);
                        break;
                    case API_PARAMS_SORT_POPULAR:
                        mSpinnerSort.setSelection(4);
                        break;
                    case API_PARAMS_SORT_NOT_POPULAR:
                        mSpinnerSort.setSelection(5);
                        break;
                }
            }

            if (mApiParams[2] != null) {
                mViewWideSearch.getShowEnded().setChecked(true);
            }

            if (mApiParams[3] != null) {
                switch (mApiParams[3]){
                    case API_PARAMS_PAIR:
                        mViewWideSearch.getRgGroup().check(R.id.wide_search_rb_pair);
                        break;
                    case API_PARAMS_GROUP:
                        mViewWideSearch.getRgGroup().check(R.id.wide_search_rb_group);
                        break;
                }
            }
        }
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventAuctions eventAuctions) {
        super.onApiResponse(eventAuctions);
        turnOnControls();
        if (((ResponseAuctions) eventAuctions.getResponse()).getResult().size() < 18) {
            setPagination(false);
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationsResponse(EventLocations eventLocations) {
        List<Location> list = ((ResponseLocations) eventLocations.getResponse()).getResult();
        Collections.sort(list);
        mLocations = new String[list.size() + 1];
        for (int i = 0; i < list.size(); i++) {
            mLocations[i + 1] = list.get(i).getTitle();
        }
        initSpinner(mSpinnerCities, list, getString(R.string.choose_city), PARAM_CITY_INDEX);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onCategoryResponse(EventCategory eventCategory) {
        mCategories = ((ResponseCategory) eventCategory.getResponse()).getResult();
        initSpinner(mSpinnerCategories, mCategories, getString(R.string.choose_category), PARAM_CATEGORY_INDEX);
        turnOnControls();
    }

    private void initSpinner(Spinner spinner, List list, String prompt, int paramToInit) {

        String[] titles = new String[list.size() + 1];
        titles[0] = prompt;

        for (int i = 0; i < list.size(); i++) {
            titles[i + 1] = list.get(i).toString();
        }

        CustomAdapter adapter = new CustomAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                titles,
                mHidingItemIndex);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt(prompt);
        spinner.setAdapter(adapter);
        spinner.setEnabled(true);

        //если поиск не новый, то нужно выбрать старую позицию спиннера
        if (!isNewSearch) {
            if (mApiParams[paramToInit] != null){
                //выбор по номеру параметра из mApiParams
                switch (paramToInit) {
                    //города
                    case 4:
                        spinner.setSelection(adapter.getPosition(mApiParams[paramToInit]));
                        break;
                    //категории
                    case 5:
                        int category = Integer.valueOf(mApiParams[paramToInit]);
                        spinner.setSelection(adapter.getPosition(titles[category]));
                        break;
                }
            }
        }

        mViewWideSearch.getSearchButton().setEnabled(true);
        mViewWideSearch.getCancelButton().setEnabled(true);
    }

    public class CustomAdapter extends ArrayAdapter<String> {

        private int hidingItemIndex;

        public CustomAdapter(Context context, int textViewResourceId, String[] objects, int hidingItemIndex) {
            super(context, textViewResourceId, objects);
            this.hidingItemIndex = hidingItemIndex;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View v;
            if (position == hidingItemIndex) {
                TextView tv = new TextView(getContext());
                tv.setVisibility(View.GONE);
                v = tv;
            } else {
                v = super.getDropDownView(position, null, parent);
            }
            return v;
        }
    }
}
