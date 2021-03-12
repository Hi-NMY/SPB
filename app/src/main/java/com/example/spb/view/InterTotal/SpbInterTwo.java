package com.example.spb.view.InterTotal;

public interface SpbInterTwo {

    /**
     *创建dialog
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
     *创建刷新
     * @Auther  nmynmy
     * @Date  2021-02-05  23:42
     */
    public void createRefresh();

    /**
     *关闭刷新
     * @Auther  nmynmy
     * @Date  2021-02-05  23:42
     */
    public void finishRefresh(int num);
}
