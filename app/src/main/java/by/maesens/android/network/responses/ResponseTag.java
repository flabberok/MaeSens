package by.maesens.android.network.responses;

import by.maesens.android.model.Tag;

/**
 * Created by Никита on 02.04.2016.
 */
public class ResponseTag implements IBaseResponse<Tag> {

    private Tag mTag;

    public ResponseTag(Tag tag) {
        this.mTag = tag;
    }

    public void setTag(Tag tag) {
        mTag = tag;
    }

    @Override
    public Tag getResult() {
        return mTag;
    }
}
