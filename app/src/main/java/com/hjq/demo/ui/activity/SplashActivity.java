package com.hjq.demo.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.demo.R;
import com.hjq.demo.app.AppActivity;
import com.hjq.demo.http.api.UserInfoApi;
import com.hjq.demo.http.api.UserInfoListApi;
import com.hjq.demo.http.model.HttpData;
import com.hjq.demo.manager.ActivityManager;
import com.hjq.demo.other.AppConfig;
import com.hjq.demo.sqlite.model.User;
import com.hjq.demo.sqlite.viewModel.UserViewModel;
import com.hjq.demo.utils.TravelPrefs;
import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.SlantedTextView;

import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.json.JSONUtil;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 闪屏界面
 */
public final class SplashActivity extends AppActivity {

    private LottieAnimationView mLottieView;
    private SlantedTextView mDebugView;

    TravelPrefs myPrefs;

    private UserViewModel userViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.splash_activity;
    }

    @Override
    protected void initView() {
        mLottieView = findViewById(R.id.lav_splash_lottie);
        mDebugView = findViewById(R.id.iv_splash_debug);

        myPrefs = TravelPrefs.getInstance(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        // 设置动画监听
        mLottieView.addAnimatorListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mLottieView.removeAnimatorListener(this);
//                HomeActivity.start(getContext());
//                finish();
            }
        });
    }

    @Override
    protected void initData() {
        mDebugView.setText(AppConfig.getBuildType().toUpperCase());
        if (AppConfig.isDebug()) {
            mDebugView.setVisibility(View.VISIBLE);
        } else {
            mDebugView.setVisibility(View.INVISIBLE);
        }

        EasyConfig.getInstance().addHeader("Authorization", "Bearer " + myPrefs.token().get());
        // 刷新用户信息
        EasyHttp.get(this)
                .api(new UserInfoApi())
                .request(new HttpCallback<HttpData<UserInfoApi.Bean>>(this) {

                    @Override
                    public void onSucceed(HttpData<UserInfoApi.Bean> data) {
                        // 已登录，跳转首页
                        HomeActivity.start(getContext());
                        finish();
                    }

                    /**
                     * @param e
                     */
                    @Override
                    public void onFail(Exception e) {
                        startActivity(LoginActivity.class);
                        // 进行内存优化，销毁除登录页之外的所有界面
                        ActivityManager.getInstance().finishAllActivities(LoginActivity.class);
                    }
                });

        // 异步线程执行信息缓存
        ThreadUtil.execute(() -> {
            // 获取全部用户信息
            EasyHttp.get(this)
                    .api(new UserInfoListApi())
                    .request(new HttpCallback<HttpData<List<UserInfoListApi.Bean>>>(this) {

                        @Override
                        public void onSucceed(HttpData<List<UserInfoListApi.Bean>> data) {
                            if (data.getCode() != 200){
                                toast(data.getMessage());
                                return;
                            }
                            // 保存用户信息到本地sqlite
                            userViewModel.insertAll(JSONUtil.toList(JSONUtil.toJsonStr(data.getData()), User.class));
                        }

                        /**
                         * @param e
                         */
                        @Override
                        public void onFail(Exception e) {
                            toast(e.getMessage());
                        }
                    });
        });
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 隐藏状态栏和导航栏
                .hideBar(BarHide.FLAG_HIDE_BAR);
    }

    @Override
    public void onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    @Override
    protected void initActivity() {
        // 问题及方案：https://www.cnblogs.com/net168/p/5722752.html
        // 如果当前 Activity 不是任务栈中的第一个 Activity
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            // 如果当前 Activity 是通过桌面图标启动进入的
            if (intent != null && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && Intent.ACTION_MAIN.equals(intent.getAction())) {
                // 对当前 Activity 执行销毁操作，避免重复实例化入口
                finish();
                return;
            }
        }
        super.initActivity();
    }

    @Deprecated
    @Override
    protected void onDestroy() {
        // 因为修复了一个启动页被重复启动的问题，所以有可能 Activity 还没有初始化完成就已经销毁了
        // 所以如果需要在此处释放对象资源需要先对这个对象进行判空，否则可能会导致空指针异常
        super.onDestroy();
    }
}