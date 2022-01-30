package com.example.spb.xserver;


import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;

public class AndroidUnicast extends AndroidNotification {

	private PushClient client = new PushClient();
	//对应点赞通知
	public static final int PUSHLIKEKEY = 1;
	//对应收藏通知
	public static final int PUSHCOLLECTKEY = 2;
	//对应评论通知
	public static final int PUSHCOMMENTKEY = 3;
	//对应关注通知
	public static final int PUSHFOLLOWKEY = 4;
	//对应系统通知
	public static final int PUSHSYSTEMKEY = 5;
	//对应评论回复通知
	public static final int PUSHCOMMENTTOUSERKEY = 6;

	public AndroidUnicast() throws Exception {
			setAppMasterSecret("30ururoedxwitzqujnaghtkh3zyzrmnl");
			setPredefinedKeyValue("appkey", "60e59fdd72748106e47642df");
			this.setPredefinedKeyValue("type", "unicast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }

    public void clientSend(AndroidUnicast umengNotification) throws Exception {
		SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_User));
		String user_account = sharedPreferences.getString(InValues.send(R.string.user_account),"");
		String user_name = sharedPreferences.getString(InValues.send(R.string.user_name),"");
		String timestamp = Integer.toString((int)(System.currentTimeMillis() / 1000));
		umengNotification.setExtraField(InValues.send(R.string.Push_useraccount_key),user_account);
		umengNotification.setExtraField(InValues.send(R.string.Push_username_key),user_name);
		umengNotification.setPredefinedKeyValue("timestamp", timestamp);
		umengNotification.goAppAfterOpen();
		umengNotification.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		umengNotification.setProductionMode();
		client.send(umengNotification);
	}
}