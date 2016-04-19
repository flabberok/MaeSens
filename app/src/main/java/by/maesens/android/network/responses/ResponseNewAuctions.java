package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Auction;

/**
 * Created by Никита on 06.03.2016.
 */
public class ResponseNewAuctions implements IBaseResponse<List<Auction>> {

    private List<Auction> mList;

    public ResponseNewAuctions(List<Auction> list) {
        this.mList = list;
    }

    public void setList(List<Auction> list) {
        mList = list;
    }

    @Override
    public List<Auction> getResult() {
        return mList;
    }

}
