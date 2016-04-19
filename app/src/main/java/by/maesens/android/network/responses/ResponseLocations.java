package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Location;

/**
 * Created by Никита on 23.02.2016.
 */
public class ResponseLocations implements IBaseResponse<List<Location>> {

    private List<Location> mList;

    public ResponseLocations(List<Location> list) {
        this.mList = list;
    }

    public void setList(List<Location> list) {
        mList = list;
    }

    @Override
    public List<Location> getResult() {
        return mList;
    }
}
