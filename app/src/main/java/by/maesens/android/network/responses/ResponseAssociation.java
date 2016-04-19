package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Association;

/**
 * Created by Никита on 24.03.2016.
 */
public class ResponseAssociation implements IBaseResponse<List<Association>> {

    private List<Association> mList;

    public ResponseAssociation(List<Association> list) {
        mList = list;
    }

    public void setList(List<Association> list) {
        mList = list;
    }

    @Override
    public List<Association> getResult() {
        return mList;
    }
}
