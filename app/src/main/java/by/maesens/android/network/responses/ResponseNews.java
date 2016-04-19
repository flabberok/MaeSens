package by.maesens.android.network.responses;

import java.util.List;

import by.maesens.android.model.News;

/**
 * Created by Никита on 18.03.2016.
 */
public class ResponseNews implements IBaseResponse<List<News>> {

    private int count;
    private String next;
    private String previous;
    private List<News> results;

    @Override
    public List<News> getResult() {
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).getAction_obj() == null){
                results.remove(i);
            }
        }
        return results;
    }
}
