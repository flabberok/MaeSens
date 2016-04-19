package by.maesens.android.ui.tabs.news;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import by.maesens.android.R;
import by.maesens.android.adapters.BaseRecyclerAdapter;
import by.maesens.android.adapters.NewsRecyclerAdapter;
import by.maesens.android.events.EventNews;
import by.maesens.android.model.News;
import by.maesens.android.network.ServiceHelper;
import by.maesens.android.network.responses.IBaseResponse;
import by.maesens.android.ui.fragments.base.BaseRecyclerFragment;

/**
 * Created by Никита on 18.03.2016.
 */
public class TabNewsList extends BaseRecyclerFragment<EventNews> {
    @Override
    protected void setViewsId() {
        mLayoutId = R.layout.fragment_news;
        mRecyclerId = R.id.news_recycler;
        mSwipeRefreshId = R.id.news_swipe_refresh;
        setColumnCount(1);
        setPagination(true);
    }

    @Override
    protected BaseRecyclerAdapter setAdapter() {
        return new NewsRecyclerAdapter(R.layout.item_news, mPicasso, mFragmentSetter);
    }

    @Override
    protected String[] getApiParams() {
        String[] params = new String[1];
        params[0] = String.valueOf(mNextPage);
        return params;
    }

    @Override
    public void disableControls() {
        mRecyclerView.setEnabled(false);
    }

    @Override
    public void enableControls() {
        mRecyclerView.setEnabled(true);
    }

    @Override
    public void sendApiRequest(String[] paramsForAPI, EventNews event) {
        try {
            ServiceHelper.getInstance().getData(paramsForAPI, event, getContext());
            turnOffControls();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public EventNews getEvent() {
        return new EventNews();
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onApiResponse(EventNews eventNews) {
        super.onApiResponse(eventNews);
        turnOnControls();
    }

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        List<News> list = (List)response.getResult();

        // Как оказывается, существуют пустые новости, которые нужно просто игнорировать.
        // Перед отправкой данных в адаптер, пробежимся по новым элементам. Идти будем
        // из конца в начало, что позволит не думать о смещении элеменотов при удалении пустых.
        for(int index = list.size() - 1; index >= 0; index--){
            if (list.get(index).getAction_obj() == null) {
                list.remove(index);
            }
        }

        if (list.size() > 0)
            initData(list);
        else {
            // Если оказалось, что все новости были пустыми, добавляем к счетчику страниц 1
            // и запускаем загрузку следующей страницы.
            mNextPage++;
            fetchData();
        }
    }
}
