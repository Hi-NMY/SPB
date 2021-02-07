package com.example.spb.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public abstract class BasePresenter<T> {
    
    /**
     *声明view弱引用
     * @Auther  nmynmy
     * @Date  2021-02-01  19:03
     */
    protected Reference<T> myViewRef;

    /**
     *view关联建立
     * @Auther  nmynmy
     * @Date  2021-02-01  19:03
     */
    public void attachView(T view){
        myViewRef = new WeakReference<>(view);
    }

    /**
     *view解除关联
     * @Auther  nmynmy
     * @Date  2021-02-01  19:05
     */
    public void deleteView(){
        if (myViewRef != null){
            myViewRef.clear();
            myViewRef = null;
        }
    }

    /**
     *获取view
     * @Auther  nmynmy
     * @Date  2021-02-01  19:04
     */
    protected T getView(){
        return myViewRef.get();
    }

    /**
     *判断是否关联
     * @Auther  nmynmy
     * @Date  2021-02-01  19:07
     */
    public boolean isAttachView(){
        return myViewRef != null && myViewRef.get() != null;
    }



}
