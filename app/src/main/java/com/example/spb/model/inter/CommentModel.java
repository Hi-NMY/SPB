package com.example.spb.model.inter;

import com.example.spb.entity.Dto.CommentDto;
import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: CommentModel
 * @date 2022-01-30 14:49
 */
public interface CommentModel {

    void addComment(CommentDto comment, MyCallBack callBack);

    void queryCommentList(String pbId, MyCallBack callBack);

    void queryCommentOne(String pbId, String commentId, MyCallBack callBack);

    void deleteComment(String pbId, String commentId, MyCallBack callBack);


}
