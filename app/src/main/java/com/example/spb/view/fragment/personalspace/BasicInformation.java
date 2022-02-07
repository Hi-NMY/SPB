package com.example.spb.view.fragment.personalspace;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.presenter.impl.BasicInformationFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.activity.ChangeInformationPage;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.inter.IBasicInformationFView;
import com.example.spb.view.utils.JumpIntent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

public class BasicInformation extends BaseMVPFragment<IBasicInformationFView, BasicInformationFPresenterImpl> implements IBasicInformationFView, View.OnClickListener {

    private PersonalSpacePage personalSpacePage;
    private TextView mBasicinformationChange;
    private TextView mBasicinformationOnline;
    private TextView mBasicinformationBirth;
    private TextView mBasicinformationConstellation;
    private TagFlowLayout mBasicinformationFavorite;
    private TextView mBasicinformationHome;
    private LayoutInflater layoutInflater;
    private RefreshMsg refreshMsg;
    private UserToUser userToUser;
    private UserDto toUserDto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected BasicInformationFPresenterImpl createPresenter() {
        refreshMsg = new RefreshMsg();
        userToUser = new UserToUser();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_userMsg),refreshMsg);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_UserSpace_user),userToUser);
        return new BasicInformationFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_personalspace_basic_information;
    }

    @Override
    protected void initFragView(View view) {
        personalSpacePage = (PersonalSpacePage)getActivity();
        layoutInflater = LayoutInflater.from(personalSpacePage);
        mBasicinformationChange = (TextView) view.findViewById(R.id.basicinformation_change);
        mBasicinformationOnline = (TextView) view.findViewById(R.id.basicinformation_online);
        mBasicinformationHome = (TextView)view.findViewById(R.id.basicinformation_home);
        mBasicinformationFavorite = (TagFlowLayout)view.findViewById(R.id.basicinformation_favorite);
        mBasicinformationConstellation = (TextView)view.findViewById(R.id.basicinformation_constellation);
        mBasicinformationBirth = (TextView)view.findViewById(R.id.basicinformation_birth);
        setMyListener();
    }

    @Override
    protected void initData() {
        if (!personalSpacePage.getDataUserMsgPresenter().getUser_home().equals("")){
            mBasicinformationHome.setText(personalSpacePage.getDataUserMsgPresenter().user_home);
        }

        if (!personalSpacePage.getDataUserMsgPresenter().getUser_birth().equals("")){
            mBasicinformationBirth.setText(personalSpacePage.getDataUserMsgPresenter().user_birth);
            mBasicinformationConstellation.setText(MyDateClass.getConstellation(personalSpacePage.getDataUserMsgPresenter().user_birth.substring(5)));
        }

        mBasicinformationOnline.setText(personalSpacePage.getDataUserMsgPresenter().getUser_longDay() + "天");

        mBasicinformationFavorite.setAdapter(new TagAdapter<String>(mPresenter.setFavorite(personalSpacePage.getDataUserMsgPresenter().getUser_favorite())) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                View view = layoutInflater.inflate(R.layout.item_favorite_tag_one, mBasicinformationFavorite, false);
                if (mPresenter.strings == null || mPresenter.strings.size() == 0 || mPresenter.strings.get(0).equals("null")){
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    textView.setText("无");
                }else {
                    RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.r);
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    textView.setText(o);
                    textView.setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color.theme_color));
                    relativeLayout.setBackground(personalSpacePage.getDrawable(R.drawable.favorite_tag_two));
                }
                return view;
            }
        });

        mBasicinformationHome.postInvalidate();
        mBasicinformationFavorite.postInvalidate();
        mBasicinformationConstellation.postInvalidate();
        mBasicinformationBirth.postInvalidate();
        mBasicinformationOnline.postInvalidate();
    }

    private void initUserData(){
        mBasicinformationChange.setVisibility(View.GONE);
        mBasicinformationOnline.setText(toUserDto.getUser_longday() + "天");
        if (toUserDto.getUser_home() != null && !toUserDto.getUser_home().equals("")){
            mBasicinformationHome.setText(toUserDto.getUser_home());
        }else {
            mBasicinformationHome.setText("一个神秘的地方");
        }

        if (toUserDto.getUser_birth() != null && !toUserDto.getUser_birth().equals("")){
            mBasicinformationBirth.setText(toUserDto.getUser_birth());
            mBasicinformationConstellation.setText(MyDateClass.getConstellation(toUserDto.getUser_birth().substring(5)));
        }else {
            mBasicinformationBirth.setText("无");
            mBasicinformationConstellation.setText("无");
        }

        mBasicinformationFavorite.setAdapter(new TagAdapter<String>(mPresenter.setFavorite(toUserDto.getUser_favorite())) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                View view = layoutInflater.inflate(R.layout.item_favorite_tag_one, mBasicinformationFavorite, false);
                if (mPresenter.strings == null || mPresenter.strings.size() == 0 || mPresenter.strings.get(0).equals("null")){
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    textView.setText("无");
                }else {
                    RelativeLayout relativeLayout = (RelativeLayout)view.findViewById(R.id.r);
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    textView.setText(o);
                    textView.setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color.theme_color));
                    relativeLayout.setBackground(personalSpacePage.getDrawable(R.drawable.favorite_tag_two));
                }
                return view;
            }
        });

        for (int i = 0 ; i < mPresenter.getKeys().size() ; i++){
            switch (i){
                case 0:
                    if (mPresenter.getKeys().get(0) != 1){
                        mBasicinformationOnline.setText("秘密");
                    }
                    break;
                case 1:
                    if (mPresenter.getKeys().get(1) != 1){
                        mBasicinformationBirth.setText("秘密");
                    }
                    break;
                case 2:
                    if (mPresenter.getKeys().get(2) != 1){
                        mBasicinformationConstellation.setText("秘密");
                    }
                    break;
                case 3:
                    if (mPresenter.getKeys().get(3) != 1){
                        mBasicinformationHome.setText("秘密");
                    }
                    break;
                case 4:
                    if (mPresenter.getKeys().get(4) != 1){
                        mBasicinformationFavorite.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }

        mBasicinformationOnline.postInvalidate();
        mBasicinformationHome.postInvalidate();
        mBasicinformationFavorite.postInvalidate();
        mBasicinformationConstellation.postInvalidate();
        mBasicinformationBirth.postInvalidate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.basicinformation_change:
                JumpIntent.startMyIntent(ChangeInformationPage.class);
                break;
        }
    }

    @Override
    public void createDialog() {

    }

    @Override
    public void showDialogS(int i) {

    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {
        mBasicinformationChange.setOnClickListener(this);
    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }

    class RefreshMsg extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    }

    class UserToUser extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            toUserDto = (UserDto) intent.getSerializableExtra("key_two");
            if (toUserDto.getUser_account().equals(personalSpacePage.userAccount)){
                mPresenter.setMyPrivacy(toUserDto.getUser_privacy());
                initUserData();
            }
        }
    }
}
