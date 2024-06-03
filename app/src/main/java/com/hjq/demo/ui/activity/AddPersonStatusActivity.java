package com.hjq.demo.ui.activity;

import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.demo.R;
import com.hjq.demo.aop.SingleClick;
import com.hjq.demo.app.AppActivity;
import com.hjq.demo.http.api.HotelAddApi;
import com.hjq.demo.http.api.PersonalStatusAddApi;
import com.hjq.demo.http.api.UpdateImageApi;
import com.hjq.demo.http.glide.GlideApp;
import com.hjq.demo.http.model.HttpData;
import com.hjq.demo.http.model.HttpUplodaData;
import com.hjq.demo.ui.adapter.ImagesAdapter;
import com.hjq.demo.ui.adapter.PostsAdapter;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  发布动态activity
 * @author flight
 */
public final class AddPersonStatusActivity extends AppActivity {

    public RecyclerView imagesRecyclerView;
    public ImagesAdapter imagesAdapter;

    private ClearEditText descriptionView;

    private SubmitButton submitView;
    private SettingBar addressView;



    /** 省 */
    private String mProvince = "广东省";
    /** 市 */
    private String mCity = "广州市";
    /** 区 */
    private String mArea = "天河区";


    private List<Uri> mAvatarUrls = new ArrayList<>();

    private final int maxSelectImages = 9;

    @Override
    protected int getLayoutId() {
        return R.layout.add_person_activity;
    }

    @Override
    protected void initView() {
        imagesRecyclerView = findViewById(R.id.postImagesRecyclerView);
        descriptionView = findViewById(R.id.et_description);
        addressView = findViewById(R.id.sb_address);
        submitView = findViewById(R.id.btn_submit);

        imagesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3)); // 3 columns
        imagesAdapter = new ImagesAdapter(this); // Assuming max 9 images
        imagesAdapter.setMaxImages(maxSelectImages);
        imagesRecyclerView.setAdapter(imagesAdapter);

        imagesAdapter.setData(new ArrayList<>());

        imagesAdapter.setOnAddImageListener(() -> {
            ImageSelectActivity.start(this, maxSelectImages, data -> {
                data.forEach(url -> {
                    selectImages(new File(url));
                });
            });
        });

        setOnClickListener(addressView,submitView);
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
       }else if (view == submitView){
           EasyHttp.post(this)
                   .api(new PersonalStatusAddApi()
                           .setContent(descriptionView.getText().toString())
                           .setRegion(addressView.getRightText().toString())
                           .setImageUrls(String.join(",", imagesAdapter.getData())))
                   .request(new HttpCallback<HttpData<Void>>(this) {
                       @Override
                       public void onSucceed(HttpData<Void> result) {
                           if (result.getCode() == 200){
                               setResult(RESULT_OK);
                               finish();
                           }else {
                               postDelayed(() -> {
                                   submitView.showError(3000);
                               }, 1000);
                           }
                       }

                       @Override
                       public void onFail(Exception e) {
                           super.onFail(e);
                           postDelayed(() -> {
                               submitView.showError(3000);
                           }, 1000);
                       }
                   });
       }
    }

    private void selectImages(File file) {
        EasyHttp.post(this)
                .api(new UpdateImageApi()
                        .setImage(file))
                .request(new HttpCallback<HttpUplodaData>(this) {

                    @Override
                    public void onSucceed(HttpUplodaData data) {
                        if (data.isRequestSucceed()){
                            mAvatarUrls.add(Uri.parse(data.getUrl()));
//                            imageUrls.add(data.getFileName());
                            imagesAdapter.addData(new ArrayList<>(Collections.singleton(data.getFileName())));
                        }else {
                            toast("上传失败," + data.getMessage());
                        }
                    }
                });

    }
}