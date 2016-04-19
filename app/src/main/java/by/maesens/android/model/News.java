package by.maesens.android.model;

/**
 * Created by Никита on 18.03.2016.
 */
public class News {
    private int action;
    private NewsObject action_obj;
    private String created_at;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public NewsObject getAction_obj() {
        return action_obj;
    }

    public void setAction_obj(NewsObject action_obj) {
        this.action_obj = action_obj;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
