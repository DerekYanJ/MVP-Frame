package com.yqy.mvp_frame.ui.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yqy.mvp_frame.R;
import com.yqy.mvp_frame.frame.ui.BaseFragment;

import butterknife.BindView;

/**
 * Created by DerekYan on 2017/6/27.
 */

public class LoginFragment extends BaseFragment implements LoginContract.View{
    @BindView(R.id.button)
    Button button;

    private LoginContract.Presenter mPresenter;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int preView() {
        return R.layout.login_frag;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                mPresenter.login("18801014484","654321");
                break;
        }
    }

    @Override
    public void setTitle(String title) {
        showToast("登录成功");
    }
/*
    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }*/

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean isActive() {
        return this.isAdded();
    }
}
