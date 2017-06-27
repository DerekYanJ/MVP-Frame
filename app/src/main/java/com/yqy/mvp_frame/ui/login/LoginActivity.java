package com.yqy.mvp_frame.ui.login;

import android.view.View;

import com.yqy.mvp_frame.R;
import com.yqy.mvp_frame.frame.ui.ToolbarActivity;
import com.yqy.mvp_frame.frame.utils.ActivityUtils;

public class LoginActivity extends ToolbarActivity {

    private LoginPresenter loginPresenter;

    @Override
    protected int preView() {
        return R.layout.login_act;
    }

    @Override
    protected void initView() {
        setToolBarCenterTitle("登录");
        
        LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.framelayout);
        if (loginFragment == null) {
            loginFragment = loginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    loginFragment, R.id.framelayout);
        }
        loginPresenter = new LoginPresenter();
        bindPresenter(loginPresenter,loginFragment,new LoginModel());
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected OnClickBackListener getOnBackClickListener() {
        return null;
    }

    @Override
    public void onClick(View v) {
    }
}
