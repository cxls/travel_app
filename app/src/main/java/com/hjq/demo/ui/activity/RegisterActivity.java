package com.hjq.demo.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;
import com.hjq.base.BaseActivity;
import com.hjq.base.BaseDialog;
import com.hjq.demo.R;
import com.hjq.demo.aop.Log;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.AppActivity;
import com.hjq.demo.http.api.DictApi;
import com.hjq.demo.http.api.GetCodeApi;
import com.hjq.demo.http.api.RegisterApi;
import com.hjq.demo.http.model.HttpData;
import com.hjq.demo.manager.InputTextManager;
import com.hjq.demo.ui.dialog.SelectDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.CountdownView;
import com.hjq.widget.view.SubmitButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import okhttp3.Call;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 注册界面
 */
public final class RegisterActivity extends AppActivity
        implements TextView.OnEditorActionListener {

    private static final String INTENT_KEY_PHONE = "phone";
    private static final String INTENT_KEY_PASSWORD = "password";

    @Log
    public static void start(BaseActivity activity, String phone, String password, OnRegisterListener listener) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        intent.putExtra(INTENT_KEY_PHONE, phone);
        intent.putExtra(INTENT_KEY_PASSWORD, password);
        activity.startActivityForResult(intent, (resultCode, data) -> {

            if (listener == null || data == null) {
                return;
            }

            if (resultCode == RESULT_OK) {
                listener.onSucceed(data.getStringExtra(INTENT_KEY_PHONE), data.getStringExtra(INTENT_KEY_PASSWORD));
            } else {
                listener.onCancel();
            }
        });
    }

    private EditText mPhoneView;
//    private CountdownView mCountdownView;

    private EditText mSexView;
    private EditText mUsernameView;
    private EditText mProfessionView;
    private EditText mAgeView;

    private EditText mFirstPassword;
    private EditText mSecondPassword;

    private SubmitButton mCommitView;
    private List<DictApi.DictDataInfo> dictList;

    @Override
    protected int getLayoutId() {
        return R.layout.register_activity;
    }

    @Override
    protected void initView() {
        mPhoneView = findViewById(R.id.et_register_phone);
//        mCountdownView = findViewById(R.id.cv_register_countdown);
        mSexView = findViewById(R.id.et_register_sex);
        mProfessionView = findViewById(R.id.et_register_profession);
        mAgeView = findViewById(R.id.et_register_age);
        mFirstPassword = findViewById(R.id.et_register_password1);
        mSecondPassword = findViewById(R.id.et_register_password2);
        mCommitView = findViewById(R.id.btn_register_commit);
        mUsernameView = findViewById(R.id.et_register_username);

        setOnClickListener(mSexView, mProfessionView,mCommitView);

        mSecondPassword.setOnEditorActionListener(this);

        // 给这个 View 设置沉浸式，避免状态栏遮挡
        ImmersionBar.setTitleBar(this, findViewById(R.id.tv_register_title));

        InputTextManager.with(this)
                .addView(mPhoneView)
                .addView(mSexView)
                .addView(mUsernameView)
                .addView(mAgeView)
                .addView(mProfessionView)
                .addView(mFirstPassword)
                .addView(mSecondPassword)
                .setMain(mCommitView)
                .build();
    }

    @Override
    protected void initData() {
        // 自动填充手机号和密码
        mPhoneView.setText(getString(INTENT_KEY_PHONE));
        mFirstPassword.setText(getString(INTENT_KEY_PASSWORD));
        mSecondPassword.setText(getString(INTENT_KEY_PASSWORD));

        EasyHttp.get(this).api(new DictApi().setDictType("sys_profession")).request(new HttpCallback<HttpData<List<DictApi.DictDataInfo>>>(this){
            /**
             * @param call
             */
            @Override
            public void onStart(Call call) {
            }

            /**
             * @param result
             */
            @Override
            public void onSucceed(HttpData<List<DictApi.DictDataInfo>> result) {
                if (result.getCode() == 200){
                    dictList = result.getData();
                }
            }

        });
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mSexView){
            // 弹出选择性别
            new SelectDialog.Builder(this)
                    .setTitle("请选择你的性别")
                    .setList("男", "女")
                    // 设置单选模式
                    .setSingleSelect()
                    // 设置默认选中
                    .setSelect(0)
                    .setListener(new SelectDialog.OnListener<String>() {

                        @Override
                        public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
//                            toast("确定了：" + data.toString());
                            mSexView.setText(new ArrayList<>(data.values()).get(0));
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
//                            toast("取消了");
                        }
                    })
                    .show();
        }else if (view == mProfessionView){
            // 弹出选择职业
            new SelectDialog.Builder(this)
                    .setTitle("请选择你的职业")
                    .setList(dictList.stream().map(DictApi.DictDataInfo::getDictLabel).collect(Collectors.toList()))
                    // 设置单选模式
                    .setSingleSelect()
                    // 设置默认选中
                    .setSelect(0)
                    .setListener(new SelectDialog.OnListener<String>() {

                        @Override
                        public void onSelected(BaseDialog dialog, HashMap<Integer, String> data) {
//                            toast("确定了：" + data.toString());
                            mProfessionView.setText(new ArrayList<>(data.values()).get(0));
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
//                            toast("取消了");
                        }
                    })
                    .show();
        }else if (view == mCommitView){
            if (mPhoneView.getText().toString().length() != 11) {
                mPhoneView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
                mCommitView.showError(3000);
                toast(R.string.common_phone_input_error);
                return;
            }

            if (!mFirstPassword.getText().toString().equals(mSecondPassword.getText().toString())) {
                mFirstPassword.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
                mSecondPassword.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake_anim));
                mCommitView.showError(3000);
                toast(R.string.common_password_input_unlike);
                return;
            }

            // 隐藏软键盘
            hideKeyboard(getCurrentFocus());

//            if (true) {
//                mCommitView.showProgress();
//                postDelayed(() -> {
//                    mCommitView.showSucceed();
//                    postDelayed(() -> {
//                        setResult(RESULT_OK, new Intent()
//                                .putExtra(INTENT_KEY_PHONE, mPhoneView.getText().toString())
//                                .putExtra(INTENT_KEY_PASSWORD, mFirstPassword.getText().toString()));
//                        finish();
//                    }, 1000);
//                }, 2000);
//                return;
//            }

            // 提交注册
            EasyHttp.post(this)
                    .api(new RegisterApi()
                            .setPhone(mPhoneView.getText().toString())
                            .setUserName(mUsernameView.getText().toString())
                            .setSex(mSexView.getText().toString())
                            .setAge(Integer.parseInt(mAgeView.getText().toString()))
                            .setProfession(mProfessionView.getText().toString())
                            .setPassword(mFirstPassword.getText().toString()))
                    .request(new HttpCallback<HttpData<RegisterApi.Bean>>(this) {

                        @Override
                        public void onStart(Call call) {
                            mCommitView.showProgress();
                        }

                        @Override
                        public void onEnd(Call call) {}

                        @Override
                        public void onSucceed(HttpData<RegisterApi.Bean> data) {
                            if (data.getCode() != 200){
                                toast(data.getMessage());
                                return;
                            }
                            postDelayed(() -> {
                                mCommitView.showSucceed();
                                postDelayed(() -> {
                                    setResult(RESULT_OK, new Intent()
                                            .putExtra(INTENT_KEY_PHONE, mPhoneView.getText().toString())
                                            .putExtra(INTENT_KEY_PASSWORD, mFirstPassword.getText().toString()));
                                    finish();
                                }, 1000);
                            }, 1000);
                        }

                        @Override
                        public void onFail(Exception e) {
                            super.onFail(e);
                            postDelayed(() -> {
                                mCommitView.showError(3000);
                            }, 1000);
                        }
                    });
        }
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 指定导航栏背景颜色
                .navigationBarColor(R.color.white)
                // 不要把整个布局顶上去
                .keyboardEnable(true);
    }

    /**
     * {@link TextView.OnEditorActionListener}
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE && mCommitView.isEnabled()) {
            // 模拟点击注册按钮
            onClick(mCommitView);
            return true;
        }
        return false;
    }

    /**
     * 注册监听
     */
    public interface OnRegisterListener {

        /**
         * 注册成功
         *
         * @param phone             手机号
         * @param password          密码
         */
        void onSucceed(String phone, String password);

        /**
         * 取消注册
         */
        default void onCancel() {}
    }
}