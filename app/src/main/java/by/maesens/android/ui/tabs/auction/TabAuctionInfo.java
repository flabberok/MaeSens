package by.maesens.android.ui.tabs.auction;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.photo.VKImageParameters;
import com.vk.sdk.api.photo.VKUploadImage;
import com.vk.sdk.dialogs.VKShareDialog;
import com.vk.sdk.dialogs.VKShareDialogBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import by.maesens.android.R;
import by.maesens.android.adapters.AuctionLeadersRecyclerAdapter;
import by.maesens.android.events.EventBid;
import by.maesens.android.helper.NumberTextWatcher;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.loaders.PicassoLoader;
import by.maesens.android.model.AuctionInfo;
import by.maesens.android.model.Bid;
import by.maesens.android.model.RequestBid;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.ui.fragments.AuctionFragment;
import by.maesens.android.ui.fragments.ProjectPageWithTabs;
import by.maesens.android.ui.fragments.UserFragment;
import by.maesens.android.ui.view.FBView;
import by.maesens.android.ui.view.UserPhotoView;
import by.maesens.android.ui.view.VKView;
import by.maesens.android.utils.Calculate;
import by.maesens.android.utils.DateConverter;

/**
 * Created by Виктор on 25.03.2016.
 */
public class TabAuctionInfo extends Fragment implements View.OnClickListener{

    private AuctionInfo mAuctionInfo;

    private ImageView mImgAuction;
    private ImageView mImgCharity;
    private UserPhotoView mAuctionOwner;
    private UserPhotoView mAuctionUserCurrentWinner;

    private ProgressBar mCharityProgressBar;

    private TextView mAuctionTitle;
    private TextView mAuctionDescription;
    private TextView mAuctionOwnerName;
    private TextView mCharityName;
    private TextView mCharityShortDescription;
    private TextView mCharityPurpose;
    private TextView mCharityCurrentSum;
    private TextView mCharityProgressValue;
    private TextView mCharityCountOfDays;
    private TextView mAuctionEndTitle;
    private TextView mAuctionDateOfEnd;
    private TextView mAuctionTimeToTheEnd;
    private TextView mLastBid;
    private TextView mCountOfBids;
    private TextView mLeadersTitle;
    private TextView mLastBidTitle;

    private EditText mChooseBid;

    private Button mDoBid;
    private Button mDonateDirectly;

    private AuctionLeadersRecyclerAdapter mLeadersAdapter;
    private RecyclerView mLeadersRecyclerView;

    private Picasso mPicasso;
    private IComponentSetter mFragmentSetter;

    private VKView mVKShare;
    private FBView mFBShare;

    private static final String[] sMyScope = new String[]{
            VKScope.WALL,
            VKScope.PHOTOS,
            VKScope.NOHTTPS,
            VKScope.MESSAGES,
            VKScope.OFFLINE,
    };

    public final static int MIN_BID_DIFFERENCE = 20000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPicasso = PicassoLoader.getInstance(getActivity());
        if (getActivity() instanceof IComponentSetter) {
            mFragmentSetter = (IComponentSetter) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_auction_info, container, false);
        findAuctionInfoViews(view);
        setArgumentsData();
        return view;
    }

    public void setArgumentsData(){
        if (getArguments() != null)
            mAuctionInfo = getArguments().getParcelable(AuctionFragment.ARG_AUCTION_INFO);

        if (mAuctionInfo != null)
            updateScreenItems();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void updateScreenItems(){
        initTitleAndOwnerInfo();
        initAuctionInfoCard();
        initBidsCard();
        initAuctionLeadersInfo();
        initCharityCard();
    }

    private void findAuctionInfoViews(View view) {
        mImgAuction = (ImageView) view.findViewById(R.id.imgAuction);
        mImgCharity = (ImageView) view.findViewById(R.id.imgCharity);
        mAuctionOwner = (UserPhotoView) view.findViewById(R.id.uvAuctionOwner);
        mAuctionUserCurrentWinner = (UserPhotoView) view.findViewById(R.id.uvAuctionUserCurrentWinner);

        mAuctionOwnerName = (TextView) view.findViewById(R.id.tvAuctionOwnerName);
        mAuctionTitle = (TextView) view.findViewById(R.id.tvAuctionTitle);
        mAuctionDescription = (TextView) view.findViewById(R.id.tvAuctionDescription);
        mCharityName = (TextView) view.findViewById(R.id.tvCharityName);
        mCharityShortDescription = (TextView) view.findViewById(R.id.tvCharityShortDescription);
        mCharityPurpose = (TextView) view.findViewById(R.id.tvCharityPurpose);
        mCharityCurrentSum = (TextView) view.findViewById(R.id.tvCharityCurrentSum);
        mCharityProgressValue = (TextView) view.findViewById(R.id.tvCharityProgressPercent);
        mCharityCountOfDays = (TextView) view.findViewById(R.id.tvCharityCountOfDays);
        mLeadersTitle = (TextView) view.findViewById(R.id.tvLeadersTitle);
        mLastBidTitle = (TextView) view.findViewById(R.id.tvLastBidTitle);

        mAuctionEndTitle = (TextView) view.findViewById(R.id.tvEndedTitle);
        mAuctionDateOfEnd = (TextView) view.findViewById(R.id.tvAuctionDateOfEnd);
        mAuctionTimeToTheEnd = (TextView) view.findViewById(R.id.tvAuctionTimeToTheEnd);
        mLastBid = (TextView) view.findViewById(R.id.tvLastBid);
        mCountOfBids = (TextView) view.findViewById(R.id.tvCountOfBids);

        mChooseBid = (EditText) view.findViewById(R.id.etChooseBidValue);

        mDoBid = (Button) view.findViewById(R.id.bDoBid);
        mDonateDirectly = (Button) view.findViewById(R.id.bDonateDirectly);

        mCharityProgressBar = (ProgressBar) view.findViewById(R.id.pbCharityProgress);
        initLeadersRecyclerView(view);

        mVKShare = (VKView) view.findViewById(R.id.vkShare);
        mFBShare = (FBView) view.findViewById(R.id.fbShare);
        mVKShare.setText("1");
        mFBShare.setText("0");
    }

    private void initLeadersRecyclerView(View view){
        mLeadersRecyclerView = (RecyclerView) view.findViewById(R.id.rvLeaders);
        mLeadersRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLeadersAdapter = new AuctionLeadersRecyclerAdapter(R.layout.item_auction_leaders, mPicasso, mFragmentSetter);
        mLeadersRecyclerView.setAdapter(mLeadersAdapter);
    }


    private void initTitleAndOwnerInfo() {
        // Заполняем верхнюю часть экрана: название аукциона и данные о создателе (фото и имя)
        mAuctionTitle.setText(mAuctionInfo.getTitle());

        SpannableString ownerNameUnderlined = new SpannableString(mAuctionInfo.getUser().getFirst_name());
        ownerNameUnderlined.setSpan(new UnderlineSpan(), 0, ownerNameUnderlined.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAuctionOwnerName.setText(ownerNameUnderlined);

        // выводим фото стандартным методом Picasso
        mPicasso.load(mAuctionInfo.getUser().getSmall_avatar())
                .placeholder(R.drawable.ic_user) // выводим в процессе загрузки
                .error(R.drawable.ic_user) // выводиться, если не можем загрузить изображение
                .into(mAuctionOwner);
        mAuctionOwner.setOnlineStatus(mAuctionInfo.getUser().getIs_online());

        mAuctionOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSetter.setFragment(UserFragment.newInstance(mAuctionInfo.getUser().getId()));
            }
        });
        mAuctionOwnerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentSetter.setFragment(UserFragment.newInstance(mAuctionInfo.getUser().getId()));
            }
        });
    }

    private void initAuctionInfoCard() {
        // Данные карточки с аукционом (фото и описание).
        mPicasso.load(mAuctionInfo.getImage())
                .into(mImgAuction);
        mAuctionDescription.setText(mAuctionInfo.getDescription());

        mVKShare.setOnClickListener(this);
        mFBShare.setOnClickListener(this);
    }

    private void initBidsCard() {
        // Карточка с данными по ставкам

        // в аукционе есть поле min_bid, которое должно хранить в себе минимальную выигрышную ставку.
        // однако, как показало тестирование, данное поле часто содержит 0, а не нужно число.
        // поэтому мы получаем список лидеров и уже из него находим минимальную выигрышную ставку.
        List<Bid> leaders = mAuctionInfo.getAuctionLeaders();

        if (mAuctionInfo.getIs_ended()) {
            String title = getResources().getString(R.string.meeting_ended)
                    + ": " + DateConverter.convertDateToString(mAuctionInfo.getEndDate(), "d MMMM yyyy").toLowerCase();
            mAuctionEndTitle.setText(title);
            mAuctionEndTitle.setTypeface(null, Typeface.BOLD);
            setVisibilityBidsButtons(View.GONE);
            setVisibilityBidsDays(View.GONE);
        }
        else {
            setVisibilityBidsButtons(View.VISIBLE);
            setVisibilityBidsDays(View.VISIBLE);
            mAuctionDateOfEnd.setText(DateConverter.convertDateToString(mAuctionInfo.getEndDate(), "d MMM").toLowerCase());
            mAuctionTimeToTheEnd.setText("(" + getResources().getString(R.string.until_the_end) + " "
                    + String.format(" %,d", getDaysCountBetweenDates(new Date(), mAuctionInfo.getEndDate())) + getResources().getString(R.string.days) + ")");


            // проверяем существование ставок
            long minBidAmount = leaders != null ? leaders.get(leaders.size() - 1).getAmount() : 0;
            mChooseBid.setText(String.format("%,d", minBidAmount + 100000));
            mChooseBid.addTextChangedListener(new NumberTextWatcher(mChooseBid));

            mDoBid.setOnClickListener(this);
        }

        if (mAuctionInfo.getBids().length > 0) {
            setVisibilityLastBid(View.VISIBLE);

            // точно знаем, что ставки есть
            final Bid minBid = leaders.get(leaders.size() - 1);

            mLastBid.setText(String.format("%,d", minBid.getAmount()));

            // выводим фото стандартным методом Picasso
            mPicasso.load(minBid.getUser().getProfile_avatar()) // данные приходят отсортированными (от максимальной до минимальной ставки)
                    .placeholder(R.drawable.ic_user) // выводим в процессе загрузки
                    .error(R.drawable.ic_user) // выводиться, если не можем загрузить изображение
                    .into(mAuctionUserCurrentWinner);
            mAuctionUserCurrentWinner.setOnlineStatus(minBid.getUser().getIs_online());
            mAuctionUserCurrentWinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (minBid.getHidden()) // обрабатываем скрытую ставку
                        Toast.makeText(getActivity(), getString(R.string.hidden_bid), Toast.LENGTH_SHORT).show();
                    else
                        mFragmentSetter.setFragment(UserFragment.newInstance(mAuctionInfo.getBids()[0].getUser().getId()));
                }
            });

            SpannableString ownerNameUnderlined = new SpannableString(String.format("%,d ", mAuctionInfo.getBids().length) + " " + getResources().getString(R.string.bids));
            ownerNameUnderlined.setSpan(new UnderlineSpan(), 0, ownerNameUnderlined.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            mCountOfBids.setText(ownerNameUnderlined);

            mLastBidTitle.setText(R.string.last_bid);
        } else {
            setVisibilityLastBid(View.GONE);
        }
    }

    private void setVisibilityLastBid(int visibility){
        mLastBidTitle.setVisibility(visibility);
        mLastBid.setVisibility(visibility);
        mAuctionUserCurrentWinner.setVisibility(visibility);
        mCountOfBids.setVisibility(visibility);
    }

    private void setVisibilityBidsButtons(int visibility){
        mChooseBid.setVisibility(visibility);
        mDoBid.setVisibility(visibility);
    }

    private void setVisibilityBidsDays(int visibility){
        mAuctionDateOfEnd.setVisibility(visibility);
        mAuctionTimeToTheEnd.setVisibility(visibility);
    }

    private void initAuctionLeadersInfo(){
        if (mAuctionInfo.getIs_ended())
            mLeadersTitle.setText(R.string.auction_winners);
        else
            mLeadersTitle.setText(R.string.auction_leaders);

        if (mAuctionInfo.getBids().length > 0) {
            // Список лидеров аукциона
            mLeadersAdapter.reset();
            mLeadersAdapter.addData(mAuctionInfo.getAuctionLeaders());
            mLeadersTitle.setVisibility(View.VISIBLE);
        }
        else{
            mLeadersTitle.setVisibility(View.GONE);
        }
    }

    private void initCharityCard(){
        // Данные карточки с выбранным в поддержку проектом.
        mPicasso.load(mAuctionInfo.getCharity().getImage_64x64())
                .into(mImgCharity);

        mCharityName.setText(mAuctionInfo.getCharity().getTitle());
        mCharityPurpose.setText(mAuctionInfo.getCharity().getPurpose());

        mImgCharity.setOnClickListener(this);
        mCharityName.setOnClickListener(this);
        mCharityShortDescription.setOnClickListener(this);

        // необходим парсинг строки (приходит xml код). Пока отложу, возможно вообще откажемся от лишней информации.
        //mCharityShortDescription.setText(mAuctionInfo.getCharity().getDescription());

        mCharityCurrentSum.setText(String.format("%,d", mAuctionInfo.getCharity().getCurrent_amount()));
        int progressPercent = Calculate.calculateCurrentPercent(mAuctionInfo.getCharity().getCurrent_amount(), mAuctionInfo.getCharity().getRequired_amount());

        mCharityProgressBar.setProgress(progressPercent > 100 ? 100 : progressPercent); // сумма может перевалить за 100%
        mCharityProgressValue.setText(String.format("%,d", progressPercent) + "%");

        mCharityCountOfDays.setText(definitionTimeByEnd());

        mDonateDirectly.setOnClickListener(this);
    }

    /**
     * В настоящий момент скопировано из CharityAdapter. Вдальнейшем нужно будет вынести отдельно.
     *
     * @return
     */
    private String definitionTimeByEnd() {
        String textTimeByEnd;
        if (mAuctionInfo.getCharity().is_ended()) {
            textTimeByEnd = ((Context) mFragmentSetter).getString(R.string.text_project_endeded);
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date currentTime = new Date();
            Date dateEnd = null;
            String endDate = mAuctionInfo.getCharity().getFinal_date();
            if (endDate == null || endDate.isEmpty()) {
                textTimeByEnd = "";
            } else {
                try {
                    dateEnd = dateFormat.parse(endDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                textTimeByEnd = ((Context) mFragmentSetter).getString(R.string.text_end_time_first)
                        + getDaysCountBetweenDates(currentTime, dateEnd)
                        + ((Context) mFragmentSetter).getString(R.string.text_end_time_second);
            }
        }
        return textTimeByEnd;
    }

    private long getDaysCountBetweenDates(Date currentTime, Date dateEnd) {
        return TimeUnit.DAYS.convert(dateEnd.getTime() - currentTime.getTime(), TimeUnit.MILLISECONDS);
    }

    /**
     * Checks if the user logged in VK. If not, offers to log in.
     * If user logged in shows dialog with sharing to social network.
     */
    private void vkShare() {
        if (VKSdk.isLoggedIn()) {
            showDialog();
        } else {
            VKSdk.login(getActivity(), sMyScope);
        }
    }

    /**
     * Shows dialog to share auction to Facebook social network
     */
    private void fbShare() {
        CallbackManager callbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d("AuctionFragment", "onSuccess " + result);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("AuctionFragment", "onError " + error);
            }
        });
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(mAuctionTitle.getText().toString())
                    .setContentDescription(mAuctionDescription.getText().toString())
                    .setContentUrl(Uri.parse(ServiceHelper.URLServer + "lot/" + mAuctionInfo.getId()))
                            .setImageUrl(Uri.parse(mAuctionInfo.getImage()))
                            .build();

            shareDialog.show(linkContent);
        }
    }

    /**
     * Shows dialog to share auction to VK social network
     */
    private void showDialog(){
        final Bitmap image = ((BitmapDrawable) mImgAuction.getDrawable()).getBitmap();
        new VKShareDialogBuilder()
                .setAttachmentLink(mAuctionTitle.getText().toString(),
                        ServiceHelper.URLServer + "lot/" + mAuctionInfo.getId())
                .setText(mAuctionDescription.getText())
                .setAttachmentImages(new VKUploadImage[]{
                        new VKUploadImage(image, VKImageParameters.pngImage())
                })
                .setShareDialogListener(new VKShareDialog.VKShareDialogListener() {
                    @Override
                    public void onVkShareComplete(int postId) {
                        Log.d("AuctionFragment", "onVkShareComplete " + postId);
                    }

                    @Override
                    public void onVkShareCancel() {
                    }

                    @Override
                    public void onVkShareError(VKError error) {
                        Log.d("AuctionFragment", "onVkShareError " + error);
                    }
                })
                .show(getFragmentManager(), "VK_SHARE_DIALOG");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vkShare:
                vkShare();
                break;
            case R.id.fbShare:
                fbShare();
                break;
            case R.id.bDoBid:
                // Текущая ставка пользователя
                int amount = NumberTextWatcher.trimCommaOfString(mChooseBid.getText().toString());

                // Ставка лидера (минимальная из ставок лидеров в случае группового лота)
                int currentMinBid = 0;
                if (mAuctionInfo.getBids().length > 0) {
                    List<Bid> leaders = mAuctionInfo.getAuctionLeaders();
                    currentMinBid = leaders.get(leaders.size() - 1).getAmount();
                }

                if (amount < currentMinBid)
                    Toast.makeText(getActivity(), getString(R.string.bid_less_than_minimum), Toast.LENGTH_SHORT).show();
                else if (amount < currentMinBid + MIN_BID_DIFFERENCE)
                    Toast.makeText(getActivity(), getString(R.string.min_bid) + " " + String.valueOf(currentMinBid + MIN_BID_DIFFERENCE), Toast.LENGTH_SHORT).show();
                else
                    showConfirmDialog(amount);

                break;
            case R.id.bDonateDirectly:
                mFragmentSetter.setFragment(ProjectPageWithTabs.newInstance(mAuctionInfo.getCharity().getSlug(), mAuctionInfo.getCharity().getId()));
                break;
            case R.id.imgCharity:
                mFragmentSetter.setFragment(ProjectPageWithTabs.newInstance(mAuctionInfo.getCharity().getSlug(), mAuctionInfo.getCharity().getId()));
                break;
            case R.id.tvCharityName:
                mFragmentSetter.setFragment(ProjectPageWithTabs.newInstance(mAuctionInfo.getCharity().getSlug(), mAuctionInfo.getCharity().getId()));
                break;
            case R.id.tvCharityShortDescription:
                mFragmentSetter.setFragment(ProjectPageWithTabs.newInstance(mAuctionInfo.getCharity().getSlug(), mAuctionInfo.getCharity().getId()));
                break;
            default:
        }
    }

    private void showConfirmDialog(final int amount){
        String newBid = getString(R.string.bid_amount)
                + " " + String.valueOf(amount)
                + System.getProperty("line.separator")
                + System.getProperty("line.separator")
                + getString(R.string.confirm_info);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(newBid)
                .setTitle(R.string.bid_confirmation)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sendNewBidRequest(amount);
                    }
                })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorAccent));
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void sendNewBidRequest(int amount){
        try {
            EventBid event = new EventBid();
            event.setNewBid(new RequestBid(amount, false));
            ServiceHelper.getInstance().getData(new String[]{String.valueOf(mAuctionInfo.getId())},
                    event, getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onBidResponse(EventBid eventBid) {
        updateAuctionData();
    }

    public void updateAuctionData(){
        // Запускаем обновление аукциона у родителя
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof AuctionFragment)
            ((AuctionFragment)parentFragment).updateAuctionData();
    }
}
