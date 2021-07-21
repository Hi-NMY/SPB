package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.BarCommentAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Comment;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.CommentModellmpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPostBarDetailPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyResolve;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.inter.IPostBarDetailPageAView;
import com.example.spb.xserver.AndroidNotification;
import com.example.spb.xserver.AndroidUnicast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostBarDetailPageAPresenterImpl extends BasePresenter<IPostBarDetailPageAView> implements IPostBarDetailPageAPresenter {

    private boolean userFollowKey = false;
    private String commentText = "";
    private String commenttouser = "";
    private String commentpbid = "";
    private String barUser = "";
    private SpbModelBasicInter commentModel;
    private SpbModelBasicInter barModel;
    private BarCommentAdapter barCommentAdapter;
    private BaseMVPActivity baseMVPActivity;

    public PostBarDetailPageAPresenterImpl(Activity activity) {
        commentModel = new CommentModellmpl();
        barModel = new BarModelImpl();
        baseMVPActivity = (BaseMVPActivity)activity;
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

    public void obtainBar(String acc,String pbid,OnReturn onReturn){
        Bar bar = new Bar();
        bar.setUser_account(acc);
        bar.setPb_one_id(pbid);
        barModel.selectData(barModel.DATABAR_SELECT_SEVEN, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        Bar b = new Gson().fromJson(a.substring(3),Bar.class);
                        onReturn.onReturn(b);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainCommentOne(int commentId,String pbId){
        Comment comment = new Comment();
        comment.setPb_one_id(pbId);
        comment.setComment_id(commentId);
        commentModel.selectData(commentModel.DATACOMMENT_SELECT_TWO, comment, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        Comment c = new Gson().fromJson(a.substring(3),Comment.class);
                        List<Comment> cs = new ArrayList<>();
                        cs.add(c);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),0,"",(Serializable) cs);
                    }
                } catch (Exception e) {
                    baseMVPActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Comment> cs = new ArrayList<>();
                            MyToastClass.ShowToast(MyApplication.getContext(),"该评论已被删除");
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),0,"",(Serializable) cs);
                        }
                    });
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainComment(){
        Comment comment = new Comment();
        comment.setPb_one_id(getCommentpbid());
        commentModel.selectData(commentModel.DATACOMMENT_SELECT_ONE, comment, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Comment> comments = new Gson().fromJson(a.substring(3),new TypeToken<List<Comment>>(){}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),0,"",(Serializable) comments);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void sendNewComment(String account,String baruser){
        Comment comment = new Comment();
        comment.setComment_art(getCommentText());
        comment.setComment_user(account);
        comment.setComment_touser(getCommenttouser());
        comment.setPb_one_id(getCommentpbid());
        comment.setCache_account(baruser);
        commentModel.addData(commentModel.DATACOMMENT_ADD_ONE, comment, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        int nadNum = a.indexOf("&");
                        Comment c = MyResolve.InComment(a.substring(3,nadNum));
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),1,"+1",(Serializable)c);
                        String user_ip = a.substring(nadNum + 1);
                        AndroidUnicast unicast = null;
                        if (getCommenttouser() == null && !account.equals(baruser)){
                            try {
                                unicast = new AndroidUnicast();
                                unicast.setDeviceToken(user_ip);
                                unicast.setTicker( "Android unicast ticker");
                                unicast.setTitle(InValues.send(R.string.Push_Title));
                                unicast.setText(InValues.send(R.string.Push_Comment_txt));
                                unicast.setExtraField(InValues.send(R.string.Push_fun),String.valueOf(unicast.PUSHCOMMENTKEY));
                                unicast.setExtraField(InValues.send(R.string.Push_pbid_key),getCommentpbid());
                                unicast.setExtraField(InValues.send(R.string.Push_commentid_key),String.valueOf(c.getComment_id()));
                                unicast.clientSend(unicast);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else if (getCommenttouser() != null && !account.equals(getCommenttouser())){
                            try {
                                unicast = new AndroidUnicast();
                                unicast.setDeviceToken(user_ip);
                                unicast.setTicker( "Android unicast ticker");
                                unicast.setTitle(InValues.send(R.string.Push_Title));
                                unicast.setText(InValues.send(R.string.Push_CommentToUser_txt));
                                unicast.setExtraField(InValues.send(R.string.Push_fun),String.valueOf(unicast.PUSHCOMMENTTOUSERKEY));
                                unicast.setExtraField(InValues.send(R.string.Push_pbid_key),getCommentpbid());
                                unicast.setExtraField(InValues.send(R.string.Push_commentid_key),String.valueOf(c.getComment_id()));
                                unicast.clientSend(unicast);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });

        Bar bar = new Bar();
        bar.setUser_account(baruser);
        bar.setPb_one_id(getCommentpbid());
        barModel.updateData(barModel.DATABAR_UPDATE_THREE,bar,null);
    }

    public void removeComment(int commentid,String baruser){
        if (barCommentAdapter != null){
            barCommentAdapter.removeComment();
        }
        Comment comment = new Comment();
        comment.setPb_one_id(getCommentpbid());
        comment.setComment_id(commentid);
        commentModel.deleteData(commentModel.DATACOMMENT_DELETE_ONE, comment, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == 200){
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),1,"-1",null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });

        Bar bar = new Bar();
        bar.setUser_account(baruser);
        bar.setPb_one_id(getCommentpbid());
        barModel.updateData(barModel.DATABAR_UPDATE_FOUR,bar,null);
    }

    public void setCommentList(List<Comment> c, RecyclerView recyclerView){
        barCommentAdapter = new BarCommentAdapter(c,baseMVPActivity);
        barCommentAdapter.setBarUser(getBarUser());
        recyclerView.setAdapter(barCommentAdapter);
    }

    public void addOneComment(Comment c){
        if (barCommentAdapter != null){
            barCommentAdapter.addNewAcooment(c);
        }
    }

    public interface OnReturn{
        void onReturn(Bar bar);
    }
}
