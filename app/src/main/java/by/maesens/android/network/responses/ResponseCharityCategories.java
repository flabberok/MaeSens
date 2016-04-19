package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Category;

/**
 * Created by Sol on 09.03.2016.
 */
public class ResponseCharityCategories implements IBaseResponse<List<Category>> {
    List <Category> mList;

    public void setList(List<Category> list) {
        mList = list;
    }

    @Override
    public List<Category> getResult() {
        return mList;
    }
}
