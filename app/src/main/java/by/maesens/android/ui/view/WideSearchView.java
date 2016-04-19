package by.maesens.android.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import by.maesens.android.R;

/**
 * Created by Никита on 25.02.2016.
 */
public class WideSearchView extends LinearLayout {

    private View mRootView;
    private Spinner mSpinnerCities;
    private Spinner mSpinnerCategories;
    private Spinner mSpinnerSort;
    private RadioGroup mRgGroup;
    private CheckBox mShowEnded;
    private Button mSearchButton;

    public Button getCancelButton() {
        return mCancelButton;
    }

    private Button mCancelButton;

    public Spinner getSpinnerCities() {
        return mSpinnerCities;
    }

    public Spinner getSpinnerCategories() {
        return mSpinnerCategories;
    }

    public Spinner getSpinnerSort() {
        return mSpinnerSort;
    }

    public RadioGroup getRgGroup() {
        return mRgGroup;
    }

    public CheckBox getShowEnded() {
        return mShowEnded;
    }

    public Button getSearchButton() {
        return mSearchButton;
    }

    public WideSearchView(Context context) {
        super(context);
        init(context);
    }

    public WideSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WideSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WideSearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mRootView = inflate(context, R.layout.view_wide_search, this);

        mSpinnerCities = (Spinner) mRootView.findViewById(R.id.wide_search_spinner_cities);
        mSpinnerCategories = (Spinner) mRootView.findViewById(R.id.wide_search_spinner_category);
        mSpinnerSort = (Spinner) mRootView.findViewById(R.id.wide_search_spinner_sort);
        mRgGroup = (RadioGroup) mRootView.findViewById(R.id.wide_search_radoigroup);
        mShowEnded = (CheckBox) mRootView.findViewById(R.id.wide_search_checkbox);
        mSearchButton = (Button) mRootView.findViewById(R.id.wide_search_btn_search);
        mCancelButton = (Button) mRootView.findViewById(R.id.wide_search_btn_cancel);
    }
}
