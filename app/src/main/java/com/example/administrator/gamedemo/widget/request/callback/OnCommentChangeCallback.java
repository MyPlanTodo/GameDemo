package com.example.administrator.gamedemo.widget.request.callback;


import com.example.administrator.gamedemo.model.CommentInfo;

/**
 * Created by 大灯泡 on 2016/12/9.
 * <p>
 * 评论Callback
 */

public interface OnCommentChangeCallback {

    void onAddComment(CommentInfo response);

    void onDeleteComment(String commentid);

}
