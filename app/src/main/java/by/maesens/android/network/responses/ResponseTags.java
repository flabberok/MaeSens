package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Tag;

/**
 * Created by Никита on 02.04.2016.
 */
public class ResponseTags implements IBaseResponse<List<Tag>> {

    private List<Tag> mList;

    public ResponseTags(List<Tag> list) {
        this.mList = list;
    }

    public void setList(List<Tag> list) {
        mList = list;
    }

    @Override
    public List<Tag> getResult() {
        return mList;
    }
}
