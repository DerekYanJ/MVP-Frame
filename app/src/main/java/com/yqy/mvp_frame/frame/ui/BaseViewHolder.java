package com.yqy.mvp_frame.frame.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by DerekYan on 2017/4/21.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public BaseViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        ButterKnife.bind(this,itemView);
    }
}
