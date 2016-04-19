package by.maesens.android.ui.fragments.base;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.maesens.android.events.EventWithList;
import by.maesens.android.model.comment.Comment;
import by.maesens.android.model.comment.CommentWithParent;
import by.maesens.android.network.responses.IBaseResponse;

/**
 * Created by Виктор on 05.04.2016.
 */
public abstract class BaseCommentsFragment<EVENT extends EventWithList> extends BaseRecyclerFragment<EVENT> {

    @Override
    public void onSuccessResponse(IBaseResponse response) {
        Log.d("BaseCommentsFragment", "onSuccessResponse");
        List<Comment> list = (List)response.getResult();
        ArrayList<CommentWithParent> commentsList = new ArrayList<>();

        for(Comment comment: list){
            commentsList.add(new CommentWithParent(comment, comment.getId()));
            if (comment.getChild().getData().size() > 0){ // есть дочерние комментарии
                for(Comment childComment: comment.getChild().getData()) {
                    commentsList.add(new CommentWithParent(childComment, comment.getId()));
                }
            }
        }
        initData(commentsList);
    }
}
