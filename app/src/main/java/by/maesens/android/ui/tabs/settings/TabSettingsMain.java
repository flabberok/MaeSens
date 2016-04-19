package by.maesens.android.ui.tabs.settings;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import by.maesens.android.R;
import by.maesens.android.adapters.InterestsAdapter;
import by.maesens.android.events.EventDeleteTag;
import by.maesens.android.events.EventLocations;
import by.maesens.android.events.EventSettingsPersonal;
import by.maesens.android.events.EventTag;
import by.maesens.android.events.EventTags;
import by.maesens.android.events.EventUpdateUser;
import by.maesens.android.model.Location;
import by.maesens.android.model.LocationParentResource;
import by.maesens.android.model.Tag;
import by.maesens.android.model.User;
import by.maesens.android.model.settings.SettingsPersonal;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.network.responses.ResponseLocations;
import by.maesens.android.network.responses.ResponseSettingsPersonal;
import by.maesens.android.network.responses.ResponseTags;
import by.maesens.android.network.responses.ResponseUser;
import by.maesens.android.services.UploadUserInfoService;
import by.maesens.android.ui.fragments.base.BaseNetworkFragment;

/**
 * Created by Никита on 01.04.2016.
 */
public class TabSettingsMain extends BaseNetworkFragment<EventUpdateUser> {

    private static final int TAKE_PICTURE = 0;
    private static final int SELECT_PICTURE = 1;
    private static final int CANCEL = 2;
    private static final int MIN_YEAR = 1930;

    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private File mPhotoFile;

    private User mUser;
    private ImageView mImgNewPhoto;
    private TextView mTvNewPhoto;
    private EditText mEtName;
    private EditText mEtSurname;
    private Spinner mSpinnerCity;
    private Spinner mSpinnerDay;
    private Spinner mSpinnerMonth;
    private Spinner mSpinnerYear;
    private RadioGroup mRgGender;
    private RadioButton mRbMale;
    private RadioButton mRbFemale;
    private EditText mEtUserAbout;
    private Button mBtnUpdateInfo;
    private List<Location> mLocationList;
    private Button mBtnCancelPersonal;
    private CheckBox mCbShowEndedAuc;
    private CheckBox mCbHideAge;
    private Button mBtnUpdate;
    private Button mBtnCancelInterests;
    private RecyclerView mRecyclerInterests;
    private EditText mEtInterests;
    private Button mBtnAddInterest;
    private List<Tag> mTags;
    private InterestsAdapter mAdapter;
    private Button mBtnCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_main, container, false);
        initViews(view);
        try {
            ServiceHelper.getInstance().getData(new String[]{},
                    new EventSettingsPersonal(), getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
        getTags();
        turnOffControls();
        return view;
    }

    private void initViews(View view) {
        mImgNewPhoto = (ImageView) view.findViewById(R.id.newPhoto);
        mImgNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
        mTvNewPhoto = (TextView) view.findViewById(R.id.tvNewPhoto);
        mTvNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImgNewPhoto.callOnClick();
            }
        });
        mEtName = (EditText) view.findViewById(R.id.etUserName);
        mEtSurname = (EditText) view.findViewById(R.id.etUserSurname);
        mSpinnerCity = (Spinner) view.findViewById(R.id.spinnerUserCity);
        mSpinnerCity.setEnabled(false);
        mSpinnerDay = (Spinner) view.findViewById(R.id.spinnerBirthDay);
        mSpinnerMonth = (Spinner) view.findViewById(R.id.spinnerBirthMonth);
        mSpinnerYear = (Spinner) view.findViewById(R.id.spinnerBirthYear);
        mRgGender = (RadioGroup) view.findViewById(R.id.rgUserGender);
        mRbMale = (RadioButton) view.findViewById(R.id.rbMale);
        mRbFemale = (RadioButton) view.findViewById(R.id.rbFemale);
        mEtUserAbout = (EditText) view.findViewById(R.id.etUserAbout);
        mBtnUpdateInfo = (Button) view.findViewById(R.id.btnUpdateMain);
        mBtnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setInfoForService();
            }
        });
        mBtnCancel = (Button) view.findViewById(R.id.btnUpdateMainCancel);
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        mCbShowEndedAuc = (CheckBox) view.findViewById(R.id.cbSettingsEndedAuc);
        mCbHideAge = (CheckBox) view.findViewById(R.id.cbSettingsHideAge);
        mBtnUpdate = (Button) view.findViewById(R.id.btnUpdateUserPersonal);
        mBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSettings();
            }
        });
        mBtnCancelPersonal = (Button) view.findViewById(R.id.btnUpdatePersonalCancel);
        mBtnCancelPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        mRecyclerInterests = (RecyclerView) view.findViewById(R.id.interestsRecycler);
        mEtInterests = (EditText) view.findViewById(R.id.etAddInterest);
        mBtnAddInterest = (Button) view.findViewById(R.id.btnAddInterest);
        mBtnAddInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInterest();
                mEtInterests.setText("");
            }
        });
        mBtnCancelInterests = (Button) view.findViewById(R.id.btnCancelInterest);
        mBtnCancelInterests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void setInfoForService() {
        Intent intent = new Intent(getActivity(), UploadUserInfoService.class);
        if (mPhotoFile != null) {
            intent.putExtra(UploadUserInfoService.ARG_FILE_PATH, mPhotoFile.getAbsolutePath());
        }
        intent.putExtra(UploadUserInfoService.ARG_USER, getUser(getNewLocation()));
        getActivity().startService(intent);
    }

    private void updateSettings() {
        SettingsPersonal settings = new SettingsPersonal();
        if (mCbShowEndedAuc.isChecked()) {
            settings.setShow_past(true);
        }
        if (mCbHideAge.isChecked()) {
            settings.setHide_age(true);
        }
        EventSettingsPersonal event = new EventSettingsPersonal();
        event.setSettingsPersonal(settings);
        try {
            ServiceHelper.getInstance().getData(new String[]{}, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addInterest() {
        String interest = mEtInterests.getText().toString();
        if (TextUtils.isEmpty(interest)) {
            Toast.makeText(getActivity(), getString(R.string.empty_interest), Toast.LENGTH_SHORT).show();
        } else if (interest.length() > 50) {
            Toast.makeText(getActivity(), getString(R.string.interest_length), Toast.LENGTH_SHORT).show();
        } else {
            Tag tag = new Tag();
            tag.setTag_name(interest);
            EventTag event = new EventTag();
            event.setTag(tag);
            try {
                ServiceHelper.getInstance().getData(new String[]{}, event, getContext());
                turnOffControls();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private User getUser(Location location) {
        User user = new User();
        user.setAbout(mEtUserAbout.getText().toString());

        String birthDate =
                mSpinnerYear.getSelectedItem().toString() + "-" +
                        mSpinnerMonth.getSelectedItem().toString() + "-" +
                        mSpinnerDay.getSelectedItem().toString();

        user.setBirth_date(birthDate);

        user.setFirst_name(mEtName.getText().toString());
        if (mRbMale.isChecked()) {
            user.setGender(1);
        } else if (mRbFemale.isChecked()) {
            user.setGender(2);
        }
        user.setLast_name(mEtSurname.getText().toString());
        user.setLocation(location);
        user.setSmall_avatar("");
        user.setUpdate_avatar("");
        user.setTags(new String[]{"tempTag"});
        return user;
    }

    private Location getNewLocation() {
        LocationParentResource parentResource = new LocationParentResource();
        parentResource.setId("search");
        parentResource.setRoute("locations");
        parentResource.setParentResource(null);

        String title = mSpinnerCity.getSelectedItem().toString();
        int id = 0;
        for (Location l : mLocationList) {
            if (l.getTitle().equals(title)) {
                id = l.getId();
            }
        }

        Location location = new Location();
        location.setFromServer(true);
        location.setId(id);
        location.setParentResource(parentResource);
        location.setReqParams(null);
        location.setRestangularCollection(false);
        location.setRestangularized(true);
        location.setRoute("");
        location.setTitle(title);

        return location;
    }

    private void selectPicture() {
        final String[] options = {
                getString(R.string.take_photo),
                getString(R.string.choose_photo),
                getString(R.string.cancel)};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.update_photo_title));
        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case TAKE_PICTURE:
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                            mPhotoFile = null;
                            try {
                                mPhotoFile = createImageFile();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                            if (mPhotoFile != null) {
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
                                startActivityForResult(cameraIntent, TAKE_PICTURE);
                            }
                        }
                        break;
                    case SELECT_PICTURE:
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                        break;
                    case CANCEL:
                        dialog.dismiss();
                        break;
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.d("onActivityResult", "RESULT_OK");
            if (requestCode == TAKE_PICTURE) {
                try {
                    mImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
                    setBitmap(mImageBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImage = data.getData();
                Log.d("onActivityResult", selectedImage.toString());
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePath, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                String picturePath = cursor.getString(columnIndex);
                if (picturePath != null) {
                    Log.d("onActivityResult", picturePath);
                    cursor.close();
                    mPhotoFile = new File(picturePath);
                    setBitmap(BitmapFactory.decodeFile(picturePath));
                }
            }
        } else {
            Log.d("onActivityResult", "NOT OK");
            if (mPhotoFile != null) {
                mPhotoFile.delete();
            }
        }
    }

    private void setBitmap(Bitmap bitmap) {
        int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
        mImgNewPhoto.setImageBitmap(scaled);
        mTvNewPhoto.setVisibility(View.GONE);
        mImgNewPhoto.setBackground(null);
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "MaeSens_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MaeSens");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File tempFile = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        mCurrentPhotoPath = "file:" + tempFile.getAbsolutePath();
        return tempFile;
    }

    @Override
    protected void loadDataFromBundle(Bundle savedInstanceState) {

    }

    @Override
    public void onFailResponse(IBaseResponse response) {
        Log.d("TabSettingsMain", "onFailResponse");
    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        mUser = ((ResponseUser) response).getResult();
        initUserSettings();
        try {
            ServiceHelper.getInstance().getData(new String[]{}, new EventLocations(), getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUserSettings() {
        mEtName.setText(mUser.getFirst_name());
        mEtSurname.setText(mUser.getLast_name());
        mEtUserAbout.setText(mUser.getAbout());

        if (mUser.getGender() == 1) {
            mRbMale.setChecked(true);
        } else if (mUser.getGender() == 2) {
            mRbFemale.setChecked(true);
        }
        initDayOfBirth(mUser.getBirth_date());
    }

    private void initDayOfBirth(String birth_date) {

        String day = birth_date.substring(8, 10);
        String month = birth_date.substring(5, 7);
        String year = birth_date.substring(0, 4);

        Integer days[] = new Integer[31];
        for (int i = 0; i < days.length; i++) {
            days[i] = i + 1;
        }
        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDay.setAdapter(dayAdapter);
        mSpinnerDay.setSelection(dayAdapter.getPosition(Integer.valueOf(day)));

        Integer months[] = new Integer[12];
        for (int i = 0; i < months.length; i++) {
            months[i] = i + 1;
        }
        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerMonth.setAdapter(monthAdapter);
        mSpinnerMonth.setSelection(monthAdapter.getPosition(Integer.valueOf(month)));

        Integer years[] = getYears();
        ArrayAdapter<Integer> yearsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, years);
        yearsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerYear.setAdapter(yearsAdapter);
        mSpinnerYear.setSelection(yearsAdapter.getPosition(Integer.valueOf(year)));
    }

    private Integer[] getYears() {
        Calendar calendar = GregorianCalendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int maxYear = currentYear - 18;
        Integer years[] = new Integer[maxYear - MIN_YEAR];
        for (int i = maxYear, j = 0; i > MIN_YEAR; i--, j++) {
            years[j] = maxYear - j;
        }
        return years;
    }

    @Override
    public void disableControls() {

    }

    @Override
    public void enableControls() {

    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventUpdateUser event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventUpdateUser eventUpdateUser) {
        super.onApiResponse(eventUpdateUser);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onLocationsResponse(EventLocations eventLocations) {
        mLocationList = ((ResponseLocations) eventLocations.getResponse()).getResult();
        Collections.sort(mLocationList);
        String[] locations = new String[mLocationList.size() + 1];
        for (int i = 0; i < mLocationList.size(); i++) {
            locations[i + 1] = mLocationList.get(i).getTitle();
        }
        initSpinner(mSpinnerCity, mLocationList, mUser.getLocation().getTitle());
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPersonalSettingsResponse(EventSettingsPersonal eventSettingsPersonal) {
        SettingsPersonal settingsPersonal = ((ResponseSettingsPersonal) eventSettingsPersonal.getResponse()).getResult();
        initCheckBoxes(settingsPersonal);
        turnOnControls();
    }

    private void initCheckBoxes(SettingsPersonal settingsPersonal) {
        if (settingsPersonal.isShow_past()) {
            mCbShowEndedAuc.setChecked(true);
        }
        if (settingsPersonal.isHide_age()) {
            mCbHideAge.setChecked(true);
        }
    }

    private void initSpinner(Spinner spinner, List list, String prompt) {

        String[] titles = new String[list.size() + 1];
        titles[0] = prompt;

        for (int i = 0; i < list.size(); i++) {
            titles[i + 1] = list.get(i).toString();
        }

        CustomAdapter adapter = new CustomAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                titles,
                0);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt(prompt);
        spinner.setAdapter(adapter);
        spinner.setEnabled(true);
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

    @Override
    protected String[] getApiParams() {
        return new String[0];
    }

    @Override
    public EventUpdateUser getEvent() {
        return new EventUpdateUser();
    }

    private void getTags() {
        try {
            ServiceHelper.getInstance().getData(new String[]{}, new EventTags(), getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onTagsResponse(EventTags event) {
        mTags = ((ResponseTags) event.getResponse()).getResult();
        mAdapter = new InterestsAdapter(R.layout.item_interest, mPicasso, mFragmentSetter);
        mAdapter.reset();
        mAdapter.addData(mTags);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        mRecyclerInterests.setLayoutManager(manager);
        mRecyclerInterests.setAdapter(mAdapter);
        turnOnControls();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onTagResponse(EventTag eventTag) {
        getTags();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onTagDelete(EventDeleteTag eventDeleteTag) {
        getTags();
    }
}
