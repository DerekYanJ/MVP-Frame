package com.yqy.mvp_frame.frame.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本Recycler适配器
 * Created by yanqy on 2017/3/29.
 */

public abstract class BaseRecyclerViewAdapter<D, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    /**
     * 数据源
     */
    private List<D> data;
    /**
     * 布局资源id
     */
    private int layoutResId;

    public BaseRecyclerViewAdapter(int layoutResId, List<D> data) {
        this.data = data == null ? new ArrayList<D>() : data;
        if (layoutResId != 0) {
            this.layoutResId = layoutResId;
        } else {
            throw new NullPointerException("请设置Item资源id");
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResId, parent, false);
        return (VH) new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        bindTheData(holder, data.get(position),position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 绑定数据
     *
     * @param holder 视图管理者
     * @param data   数据源
     */
    protected abstract void bindTheData(VH holder, D data,int position);

}
