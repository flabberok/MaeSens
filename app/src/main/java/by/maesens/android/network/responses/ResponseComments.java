package by.maesens.android.network.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import by.maesens.android.model.comment.Comment;

/**
 * Created by Павел on 15.03.2016.
 */
public class ResponseComments implements IBaseResponse<List<Comment>> {


    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("next")
    @Expose
    private String next;
    @SerializedName("previous")
    @Expose
    private String previous;
    @SerializedName("results")
    @Expose
    private List<Comment> results = new ArrayList<Comment>();


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }


    @Override
    public List<Comment> getResult() {
        return results;
    }
}
