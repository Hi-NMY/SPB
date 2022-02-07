package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.BarCommentAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Comment;
import com.example.spb.entity.Dto.CommentDto;
import com.example.spb.model.implA.CommentModelImpl;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.inter.CommentModel;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPostBarDetailPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.IPostBarDetailPageAView;
import com.example.spb.xserver.AndroidUnicast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostBarDetailPageAPresenterImpl extends BasePresenter<IPostBarDetailPageAView> implements IPostBarDetailPageAPresenter {

    private boolean userFollowKey = false;
    private String commentText = "";
    private String commenttouser = "";
    private String commentpbid = "";
    private String barUser = "";
    private final CommentModel commentModel;
    private final PostBarModel barModel;
    private BarCommentAdapter barCommentAdapter;
    private final BaseMVPActivity baseMVPActivity;

    public PostBarDetailPageAPresenterImpl(Activity activity) {
        commentModel = new CommentModelImpl();
        barModel = new PostBarModelImpl();
        baseMVPActivity = (BaseMVPActivity) activity;
    }

    public boolean isUserFollowKey() {
        return userFollowKey;
    }

    public void setUserFollowKey(boolean userFollowKey) {
        this.userFollowKey = userFollowKey;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommenttouser() {
        return commenttouser;
    }

    public void setCommenttouser(String commenttouser) {
        this.commenttouser = commenttouser;
    }

    public String getCommentpbid() {
        return commentpbid;
    }

    public void setCommentpbid(String commentpbid) {
        this.commentpbid = commentpbid;
    }

    public String getBarUser() {
        return barUser;
    }

    public void setBarUser(String barUser) {
        this.barUser = barUser;
    }

    public void obtainBar(String pbid, OnReturn onReturn) {
        barModel.queryBarDetatilForPbid(pbid, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<Bar> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        onReturn.onReturn(requestEntityJson.getData());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainCommentOne(int commentId, String pbId) {
        commentModel.queryCommentOne(pbId, String.valueOf(commentId), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<Comment> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<Comment>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        List<Comment> cs = new ArrayList<>();
                        cs.add(requestEntityJson.getData());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment), 0, "", (Serializable) cs);
                    } else {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment), 0, "", new ArrayList<>());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainComment() {
        commentModel.queryCommentList(getCommentpbid(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Comment> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Comment>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment)
                                , 0, "", (Serializable) requestListJson.getDataList());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void sendNewComment(String account, String baruser) {
        CommentDto comment = new CommentDto();
        comment.setComment_art(getCommentText());
        comment.setComment_user(account);
        comment.setComment_touser(getCommenttouser());
        comment.setPb_one_id(getCommentpbid());
        if (!baruser.equals(account)) {
            comment.setCache_account(baruser);
        }
        commentModel.addComment(comment, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<CommentDto> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<CommentDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment)
                                , 1, "+1", requestEntityJson.getData());
                        toMessage(requestEntityJson.getData());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    private void toMessage(CommentDto c) {
        AndroidUnicast unicast = null;
        String ip = c.getUser_ip();
        if (getCommenttouser() == null && !"".equals(ip)) {
            try {
                unicast = new AndroidUnicast();
                unicast.setDeviceToken(ip);
                unicast.setTicker("Android unicast ticker");
                unicast.setTitle(InValues.send(R.string.Push_Title));
                unicast.setText(InValues.send(R.string.Push_Comment_txt));
                unicast.setExtraField(InValues.send(R.string.Push_fun), String.valueOf(AndroidUnicast.PUSHCOMMENTKEY));
                unicast.setExtraField(InValues.send(R.string.Push_pbid_key), getCommentpbid());
                unicast.setExtraField(InValues.send(R.string.Push_commentid_key), String.valueOf(c.getComment_id()));
                unicast.clientSend(unicast);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (getCommenttouser() != null && !"".equals(ip)) {
            try {
                unicast = new AndroidUnicast();
                unicast.setDeviceToken(ip);
                unicast.setTicker("Android unicast ticker");
                unicast.setTitle(InValues.send(R.string.Push_Title));
                unicast.setText(InValues.send(R.string.Push_CommentToUser_txt));
                unicast.setExtraField(InValues.send(R.string.Push_fun), String.valueOf(AndroidUnicast.PUSHCOMMENTTOUSERKEY));
                unicast.setExtraField(InValues.send(R.string.Push_pbid_key), getCommentpbid());
                unicast.setExtraField(InValues.send(R.string.Push_commentid_key), String.valueOf(c.getComment_id()));
                unicast.clientSend(unicast);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeComment(int commentid) {
        if (barCommentAdapter != null) {
            barCommentAdapter.removeComment();
        }
        commentModel.deleteComment(getCommentpbid(), String.valueOf(commentid), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment), 1, "-1", null);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setCommentList(List<Comment> c, RecyclerView recyclerView) {
        barCommentAdapter = new BarCommentAdapter(c, baseMVPActivity);
        barCommentAdapter.setBarUser(getBarUser());
        recyclerView.setAdapter(barCommentAdapter);
    }

    public void addOneComment(Comment c) {
        if (barCommentAdapter != null) {
            barCommentAdapter.addNewAcooment(c);
        }
    }

    public interface OnReturn {
        void onReturn(Bar bar);
    }
}
