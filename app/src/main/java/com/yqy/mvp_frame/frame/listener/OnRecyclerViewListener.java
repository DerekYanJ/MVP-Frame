package com.yqy.mvp_frame.frame.listener;

/**
 * RecyclerView item点击 长按事件
 * Created by Derek_Yan on 2016/10/11 0011.
 */

public interface OnRecyclerViewListener {
    /**
     * 点击
     * @param position
     */
    void onItemClick(int position);

    /**
     * 长按
     * @param position
     */
    void onItemLongClick(int position);
}
