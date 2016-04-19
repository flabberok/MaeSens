package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.Auction;

/**
 * Created by Никита on 16.03.2016.
 */
public class ResponseProfileList implements IBaseResponse<List<Auction>> {

    private List<Auction> mList;

    public ResponseProfileList (List<Auction> list){
        mList = list;
    }

    public void setList(List<Auction> list){
        mList = list;
    }

    @Override
    public List<Auction> getResult() {
        return mList;
    }
}
