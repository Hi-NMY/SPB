package com.example.spb.view.InterTotal;

public interface SpbInterTwo {

    /**
     *显示dialog
     * @Auther  nmynmy
     * @Date  2021-02-05  23:42
     */
    public void createDialog();
    /**
     *显示dialog
     * @Auther  nmynmy
     * @Date  2021-02-05  23:42
     */
    public void showDialogS(int i);

    /**
     *关闭dialog
     * @Auther  nmynmy
     * @Date  2021-02-05  23:43
     */
    public void closeDialog(int i);

    /**
     *设置click监听
     * @Auther  nmynmy
     * @Date  2021-02-05  23:43
     */
    public void setMyListener();
    /**
     *设置手机状态栏
     * @Auther  nmynmy
     * @Date  2021-02-05  23:43
     */
    public void setBar();
    /**
     *设置应用导航栏
     * @Auther  nmynmy
     * @Date  2021-02-05  23:43
     */
    public void setActivityBar();

    /**
     *头部刷新
     * @Auther  nmynmy
     * @Date  2021-02-05  23:42
     */
    public void startRefresh();

    /**
     *获取更多刷新
     * @Auther  nmynmy
     * @Date  2021-02-05  23:42
     */
    public void obtainMoreRefresh();

    /**
     *关闭顶部刷新
     * @Auther  nmynmy
     * @Date  2021-02-05  23:44
     */
    public void stopRefresh();
    /**
     *关闭更多刷新
     * @Auther  nmynmy
     * @Date  2021-02-05  23:44
     */
    public void stopMoreRefresh();
}
