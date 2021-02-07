package com.example.spb.view.InterTotal;

public interface SpbInterRefresh {
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
