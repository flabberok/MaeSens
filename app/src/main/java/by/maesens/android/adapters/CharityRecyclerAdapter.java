package by.maesens.android.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import by.maesens.android.R;
import by.maesens.android.interfaces.IComponentSetter;
import by.maesens.android.model.Category;
import by.maesens.android.model.Charity;
import by.maesens.android.ui.fragments.ProjectPageWithTabs;
import by.maesens.android.utils.Calculate;

/**
 * Created by Миша on 24.02.2016.
 */
public class CharityRecyclerAdapter extends BaseRecyclerAdapter<Charity, CharityRecyclerAdapter.CharityHolder> {

    public CharityRecyclerAdapter(int itemId, Picasso picasso, IComponentSetter fragmentSetter) {
        super(itemId, picasso, fragmentSetter);
    }

    @Override
    protected CharityHolder getHolder(View view) {
        return new CharityHolder(view);
    }

    @Override
    public void onBindViewHolder(CharityHolder holder, int position) {
        Charity project = mList.get(position);
        holder.bindData(project);
    }

    public class CharityHolder extends BaseRecyclerAdapter.BaseHolder<Charity> {
        ImageView mImgProject;
        TextView mProjectName;
        TextView mProjectCategory;
        TextView mCountMoney;
        ProgressBar mProjectProgress;
        TextView mRemainingTime;
        TextView mPercentMoney;

        Charity mProject;


        public CharityHolder(View itemView) {
            super(itemView);
            mImgProject = (ImageView) itemView.findViewById(R.id.img_project);
            mProjectName = (TextView) itemView.findViewById(R.id.tv_project_name);
            mProjectCategory = (TextView) itemView.findViewById(R.id.tv_project_category);
            mCountMoney = (TextView) itemView.findViewById(R.id.tv_money);
            mProjectProgress = (ProgressBar) itemView.findViewById(R.id.pb_progress);
            mRemainingTime = (TextView) itemView.findViewById(R.id.tv_remaining_time);
            mPercentMoney = (TextView) itemView.findViewById(R.id.tv_percent);
        }


        @Override
        public void onClick(View v) {
            super.onClick(v);
            mFragmentSetter.setFragment(ProjectPageWithTabs.newInstance(mProject.getSlug(),mProject.getId()));
        }

        @Override
        public void bindData(Charity project) {
            mProject = project;
            mPicasso.load(mProject.getImage_140x140())
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(mImgProject);

            mProjectName.setText(mProject.getTitle());

            // Похоже, что на сайте выбирается лишь одна категория. Возможно существует какой-то критерий выбора.
            StringBuilder builder = new StringBuilder();
            for (Category category : mProject.getCategory()) {
                builder.append(category.getTitle()).append(", ");
            }
            builder.delete(builder.length() - 2, builder.length() - 1);
            mProjectCategory.setText(builder.toString().toUpperCase());

            mCountMoney.setText(String.format("%,d", mProject.getCurrent_amount()));

            int percent = Calculate.calculateCurrentPercent(mProject.getCurrent_amount(),mProject.getRequired_amount());
            mProjectProgress.setProgress(percent > 100 ? 100 : percent);
            mPercentMoney.setText(String.format("%d", percent) +
                    ((Context) mFragmentSetter).getString(R.string.text_persent));
            mRemainingTime.setText(definitionTimeByEnd());

        }

//        private int calculateCurrentPercent() {
//            return (int) ((mProject.getCurrent_amount() / (mProject.getRequired_amount() / 100)));
//        }

        private String definitionTimeByEnd() {
            String textTimeByEnd;
            if (mProject.is_ended()){
                textTimeByEnd = ((Context) mFragmentSetter).getString(R.string.text_project_endeded);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date currentTime = new Date();
                Date dateEnd = null;
                String endDate = mProject.getFinal_date();
                if (endDate == null || endDate.isEmpty()) {
                    textTimeByEnd = "";
                } else {
                    try {
                        dateEnd = dateFormat.parse(endDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    textTimeByEnd = ((Context) mFragmentSetter).getString(R.string.text_end_time_first) + " "
                            + String.format("%d", getDaysCountBetweenDates(currentTime, dateEnd)) + " "
                            + ((Context) mFragmentSetter).getString(R.string.text_end_time_second);
                }
            }
            return textTimeByEnd;
        }

        private long getDaysCountBetweenDates(Date currentTime, Date dateEnd) {
            return TimeUnit.DAYS.convert(dateEnd.getTime() - currentTime.getTime(), TimeUnit.MILLISECONDS);
        }
    }
}
