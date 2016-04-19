package by.maesens.android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Auction;
import by.maesens.android.model.News;
import by.maesens.android.ui.fragments.AuctionFragment;
import by.maesens.android.ui.fragments.UserFragment;
import by.maesens.android.ui.view.UserPhotoView;

/**
 * Created by Никита on 18.03.2016.
 */
public class NewsRecyclerAdapter extends BaseRecyclerAdapter<News, NewsRecyclerAdapter.NewsHolder> {

    public NewsRecyclerAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected NewsHolder getHolder(View view) {
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        holder.bindData(mList.get(position));
    }

    public class NewsHolder extends BaseRecyclerAdapter.BaseHolder<News> {

        private static final int ACTION_MEETING_ENDED = 6;
        private static final int ACTION_BID = 2;
        private static final int ACTION_COMMENT = 5;

        private News mNews;
        private ImageView mImageView;
        private TextView mTvDescription;

        private TextView mNewsTitle;
        private TextView mNewsDate;
        private TextView mNewsImageTitle;
        private LinearLayout mLayoutWinner;
        private UserPhotoView mNewsWinnerImage;
        private TextView mTvComment;
        private UserPhotoView mNewsMakerPhoto;

        public NewsHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.imgNews);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Auction auction = new Auction();
                    int id = 0;
                    switch (mNews.getAction()) {
                        case ACTION_MEETING_ENDED:
                            id = mNews.getAction_obj().getId();
                            break;
                        case ACTION_BID:
                            id = mNews.getAction_obj().getAuction().getId();
                            break;
                        case ACTION_COMMENT:
                            id = mNews.getAction_obj().getAuction().getId();
                            break;
                    }
                    auction.setId(id);
                    mFragmentSetter.setFragment(AuctionFragment.getInstance(auction));
                }
            });
            mNewsTitle = (TextView) view.findViewById(R.id.newsTitle);
            mNewsDate = (TextView) view.findViewById(R.id.newsDate);
            mNewsImageTitle = (TextView) view.findViewById(R.id.newsImageTitle);
            mLayoutWinner = (LinearLayout) view.findViewById(R.id.newsLnWinner);
            mNewsWinnerImage = (UserPhotoView) view.findViewById(R.id.newsWinnerImage);
            mNewsWinnerImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NewsHolder", "переходим на страницу победителя");
                    mFragmentSetter.setFragment(UserFragment.newInstance((mNews.getAction_obj().getWinners())[0].getUser().getId()));
                }
            });
            mTvComment = (TextView) view.findViewById(R.id.newsComment);
            mNewsMakerPhoto = (UserPhotoView) view.findViewById(R.id.newsMakerPhoto);
            mNewsMakerPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (mNews.getAction()){
                        case ACTION_MEETING_ENDED:
                            Log.d("NewsHolder", "MEETING ENDED " + mNews.getAction());
                            mFragmentSetter.setFragment(UserFragment.newInstance(mNews.getAction_obj().getUser().getId()));
                            break;
                        case ACTION_BID:
                            Log.d("NewsHolder", "BID " + mNews.getAction());
                            mFragmentSetter.setFragment(UserFragment.newInstance(mNews.getAction_obj().getUser().getId()));
                            break;
                        case ACTION_COMMENT:
                            Log.d("NewsHolder", "COMMENT " + mNews.getAction());
                            mFragmentSetter.setFragment(UserFragment.newInstance(mNews.getAction_obj().getAuthor().getId()));
                            break;
                    }
                }
            });

        }

        @Override
        public void bindData(News news) {
            mNews = news;
            //TODO сделать через SimpleDateFormat
            String date = mNews.getCreated_at().substring(0, 10);
            mNewsDate.setText(date);

            mLayoutWinner.setVisibility(View.GONE);
            mTvComment.setVisibility(View.GONE);

            switch (mNews.getAction()) {
                case ACTION_MEETING_ENDED:
                    mNewsTitle.setText(((Context)mFragmentSetter).getString(R.string.meeting_ended));

                    mNewsImageTitle.setText(mNews.getAction_obj().getTitle());

                    mPicasso.load(mNews.getAction_obj().getSmall_image())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(mImageView);

                    mLayoutWinner.setVisibility(View.VISIBLE);
                    mPicasso.load(mNews.getAction_obj().getWinners()[0].getUser().getSmall_avatar())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(mNewsWinnerImage);

                    mPicasso.load(mNews.getAction_obj().getUser().getSmall_avatar())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(mNewsMakerPhoto);
                    break;

                case ACTION_BID:
                    String user = mNews.getAction_obj().getUser().getFirst_name();
                    String bid = String.valueOf(mNews.getAction_obj().getAmount());
                    Spannable title = new SpannableString(user + " " + ((Context)mFragmentSetter).getString(R.string.made_bid) + " " + bid);
                    title.setSpan(new StyleSpan(Typeface.BOLD), 0, user.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    title.setSpan(new ForegroundColorSpan(Color.GRAY), 0, user.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    title.setSpan(new UnderlineSpan(), title.length() - bid.length(), title.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                    mNewsTitle.setText(title);

                    mNewsImageTitle.setText(mNews.getAction_obj().getAuction().getTitle());

                    mPicasso.load(mNews.getAction_obj().getAuction().getSmall_image())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(mImageView);

                    mPicasso.load(mNews.getAction_obj().getUser().getSmall_avatar())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(mNewsMakerPhoto);
                    break;

                case ACTION_COMMENT:
                    String author = mNews.getAction_obj().getAuthor().getFirst_name();
                    Spannable text = new SpannableString(author + " " + ((Context)mFragmentSetter).getString(R.string.made_comment));
                    text.setSpan(new StyleSpan(Typeface.BOLD), 0, author.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    text.setSpan(new ForegroundColorSpan(Color.GRAY), 0, author.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mNewsTitle.setText(text);

                    mNewsImageTitle.setText(mNews.getAction_obj().getAuction().getTitle());

                    mPicasso.load(mNews.getAction_obj().getAuction().getSmall_image())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(mImageView);

                    mPicasso.load(mNews.getAction_obj().getAuthor().getSmall_avatar())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .into(mNewsMakerPhoto);
                    mNewsMakerPhoto.setOnlineStatus(mNews.getAction_obj().getAuthor().getIs_online());

                    mTvComment.setVisibility(View.VISIBLE);
                    mTvComment.setText(mNews.getAction_obj().getText());
                    break;
            }

        }

    }
}
