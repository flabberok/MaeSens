package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Category;

/**
 * Created by Никита on 10.03.2016.
 */
public class ResponseCategory implements IBaseResponse<List<Category>> {

    private List<Category> mList;

    public ResponseCategory(List<Category> list){
        mList = list;
    }

    @Override
    public List<Category> getResult() {
        return mList;
    }
}
