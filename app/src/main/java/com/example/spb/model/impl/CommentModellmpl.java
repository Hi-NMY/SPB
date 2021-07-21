package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Comment;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class CommentModellmpl extends SpbModelAbstrate implements SpbModelBasicInter<Comment> {
    @Override
    public void addData(int fun, Comment data, MyCallBack callBack) {
        switch (fun){
            case DATACOMMENT_ADD_ONE:
                if (data.getComment_touser() != null && !data.getComment_touser().equals("")){
                    requestBody = new FormBody.Builder()
                            .add("fun", String.valueOf(fun))
                            .add("pb_one_id",String.valueOf(data.getPb_one_id()))
                            .add("comment_art",String.valueOf(data.getComment_art()))
                            .add("comment_user",String.valueOf(data.getComment_user()))
                            .add("comment_touser",String.valueOf(data.getComment_touser()))
                            .add("cache_account",String.valueOf(data.getCache_account()))
                            .build();
                }else {
                    requestBody = new FormBody.Builder()
                            .add("fun", String.valueOf(fun))
                            .add("pb_one_id",String.valueOf(data.getPb_one_id()))
                            .add("comment_art",String.valueOf(data.getComment_art()))
                            .add("comment_user",String.valueOf(data.getComment_user()))
                            .add("cache_account",String.valueOf(data.getCache_account()))
                            .build();
                }
                sendHttp(InValues.send(R.string.Comment),requestBody,callBack);
                break;
        }
    }

    @Override
    public void selectData(int fun, Comment data, MyCallBack callBack) {
        switch (fun){
            case DATACOMMENT_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("pb_one_id",String.valueOf(data.getPb_one_id()))
                        .build();
                sendHttp(InValues.send(R.string.Comment),requestBody,callBack);
                break;
            case DATACOMMENT_SELECT_TWO:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("pb_one_id",String.valueOf(data.getPb_one_id()))
                        .add("comment_id",String.valueOf(data.getComment_id()))
                        .build();
                sendHttp(InValues.send(R.string.Comment),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Comment data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Comment data, MyCallBack callBack) {
        switch (fun){
            case DATACOMMENT_DELETE_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("comment_id",String.valueOf(data.getComment_id()))
                        .add("pb_one_id",String.valueOf(data.getPb_one_id()))
                        .build();
                sendHttp(InValues.send(R.string.Comment),requestBody,callBack);
                break;
        }
    }
}
