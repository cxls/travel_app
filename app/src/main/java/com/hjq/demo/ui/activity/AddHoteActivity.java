package com.hjq.demo.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.hjq.demo.R;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.AppActivity;
import com.hjq.demo.http.api.HotelAddApi;
import com.hjq.demo.http.api.UpdateImageApi;
import com.hjq.demo.http.glide.GlideApp;
import com.hjq.demo.http.model.HttpData;
import com.hjq.demo.http.model.HttpUplodaData;
import com.hjq.demo.manager.ActivityManager;
import com.hjq.demo.ui.dialog.AddressDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.model.FileContentResolver;
import com.hjq.widget.layout.SettingBar;
import com.hjq.widget.view.ClearEditText;
import com.hjq.widget.view.SubmitButton;

import java.io.File;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class AddHoteActivity extends AppActivity {

    private ClearEditText nameView;
    private ClearEditText priceView;
    private ClearEditText descriptionView;

    private SubmitButton submitView;
    private SettingBar addressView;

    private AppCompatImageView mImageView;

    /** 省 */
    private String mProvince = "广东省";
    /** 市 */
    private String mCity = "广州市";
    /** 区 */
    private String mArea = "天河区";

    private Uri mAvatarUrl;

    private String imageUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.add_hote_activity;
    }

    @Override
    protected void initView() {
        nameView = findViewById(R.id.et_name);
        priceView = findViewById(R.id.et_price);
        descriptionView = findViewById(R.id.et_description);
        addressView = findViewById(R.id.sb_address);
        mImageView = findViewById(R.id.iv_image_url);
        submitView = findViewById(R.id.btn_submit);

        setOnClickListener(addressView,mImageView,submitView);
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View view) {
       if (view == addressView){
           new AddressDialog.Builder(this)
                   //.setTitle("选择地区")
                   // 设置默认省份
                   .setProvince(mProvince)
                   // 设置默认城市（必须要先设置默认省份）
                   .setCity(mCity)
                   // 不选择县级区域
                   //.setIgnoreArea()
                   .setListener((dialog, province, city, area) -> {
                       String address = province + "/" + city + "/" + area;
                       if (!addressView.getRightText().equals(address)) {
                           mProvince = province;
                           mCity = city;
                           mArea = area;
                           addressView.setRightText(address);
                       }
                   })
                   .show();
       }else if (view == mImageView) {
           // 选择头像
           ImageSelectActivity.start(this, data -> {
               // 裁剪头像
               cropImageFile(new File(data.get(0)));
           });
       }else if (view == submitView){
           Log.d("点击提交", "onClick: " + imageUrl);
           EasyHttp.post(this)
                   .api(new HotelAddApi()
                           .setName(nameView.getText().toString())
                           .setPrice(BigDecimal.valueOf(Double.valueOf(priceView.getText().toString())))
                           .setDescription(descriptionView.getText().toString())
                           .setRegion(addressView.getRightText().toString())
                           .setImageUrl(imageUrl))
                   .request(new HttpCallback<HttpData<Void>>(this) {
                       @Override
                       public void onSucceed(HttpData<Void> result) {
                           if (result.getCode() == 200){
                               setResult(RESULT_OK);
                               finish();
                           }
                       }
                   });
       }
    }

    private void cropImageFile(File sourceFile) {
        ImageCropActivity.start(this, sourceFile, 1, 1, new ImageCropActivity.OnCropListener() {

            @Override
            public void onSucceed(Uri fileUri, String fileName) {
                File outputFile;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    outputFile = new FileContentResolver(getActivity(), fileUri, fileName);
                } else {
                    try {
                        outputFile = new File(new URI(fileUri.toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        outputFile = new File(fileUri.toString());
                    }
                }
                updateCropImage(outputFile, true);
            }

            @Override
            public void onError(String details) {
                // 没有的话就不裁剪，直接上传原图片
                // 但是这种情况极其少见，可以忽略不计
                updateCropImage(sourceFile, false);
            }
        });
    }

    private void updateCropImage(File file, boolean deleteFile) {
        if (file instanceof FileContentResolver) {
            mAvatarUrl = ((FileContentResolver) file).getContentUri();
        } else {
            mAvatarUrl = Uri.fromFile(file);
        }
        GlideApp.with(getActivity())
                .load(mAvatarUrl)
//                    .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                .into(mImageView);

        EasyHttp.post(this)
                .api(new UpdateImageApi()
                        .setImage(file))
                .request(new HttpCallback<HttpUplodaData>(this) {

                    @Override
                    public void onSucceed(HttpUplodaData data) {
                        if (data.isRequestSucceed()){
                            mAvatarUrl = Uri.parse(data.getUrl());
                            imageUrl = data.getFileName();
                            GlideApp.with(getActivity())
                                    .load(mAvatarUrl)
//                                .transform(new MultiTransformation<>(new CenterCrop(), new CircleCrop()))
                                    .into(mImageView);
                            if (deleteFile) {
                                file.delete();
                            }
                        }else {
                            toast("上传失败," + data.getMessage());
                        }
                    }
                });
    }
}