package by.maesens.android.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.comment.CommentWithParent;
import by.maesens.android.ui.fragments.UserFragment;
import by.maesens.android.ui.view.UserPhotoView;
import by.maesens.android.utils.DateConverter;

/**
 * Created by Павел/Виктор on 16.03.2016.
 */
public class AuctionCommentsRecyclerAdapter extends BaseRecyclerAdapter<CommentWithParent, AuctionCommentsRecyclerAdapter.AuctionCommentsHolder> {

    public AuctionCommentsRecyclerAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected AuctionCommentsHolder getHolder(View view) {
        return new AuctionCommentsHolder(view);
    }

    @Override
    public void onBindViewHolder(AuctionCommentsHolder holder, int position) {
        CommentWithParent comment = mList.get(position);
        holder.bindData(comment);
    }

    public class AuctionCommentsHolder extends BaseRecyclerAdapter.BaseHolder<CommentWithParent> {

        private CommentWithParent mComment;
        private UserPhotoView mUvComment;
        private TextView mTvUserName;
        private TextView mTvDate;
        private ImageButton mBtnLike;
        private ImageButton mBtnAnswer;
        private TextView mTvText;
        private View lineView;


        public AuctionCommentsHolder(View itemView) {
            super(itemView);

            lineView = itemView.findViewById(R.id.vLine);
            mUvComment = (UserPhotoView) itemView.findViewById(R.id.uvAuctionUserComment);
            mTvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
            mTvDate = (TextView) itemView.findViewById(R.id.tvAuctionCommentDate);
            mBtnLike = (ImageButton) itemView.findViewById(R.id.btnAuctionCommentLike);
            mBtnAnswer = (ImageButton) itemView.findViewById(R.id.btnAuctionCommentAnswer);
            mTvText = (TextView) itemView.findViewById(R.id.tvAuctionCommentText);
        }


        @Override
        public void bindData(CommentWithParent comment) {
            mComment = comment;

            if (mComment.getId() == mComment.getParentId())
                lineView.setVisibility(View.GONE);
            else
                lineView.setVisibility(View.VISIBLE);

            mTvUserName.setText(mComment.getAuthor().getFirstName());
            mTvDate.setText(DateConverter.convertDateForComments(mComment.getDateCreate()));
            mTvText.setText(mComment.getText());
            mPicasso.load(mComment.getAuthor().getSmallAvatar())
                    .into(mUvComment);
            mUvComment.setOnlineStatus(mComment.getAuthor().isIsOnline());

            mTvUserName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentSetter.setFragment(UserFragment.newInstance(mComment.getAuthor().getId()));
                }
            });

            mUvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFragmentSetter.setFragment(UserFragment.newInstance(mComment.getAuthor().getId()));
                }
            });

            mBtnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText((Context) mFragmentSetter, "Like", Toast.LENGTH_SHORT).show();
                }
            });

            mBtnAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText((Context) mFragmentSetter, ((Context) mFragmentSetter).getResources().getString(R.string.answer), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
