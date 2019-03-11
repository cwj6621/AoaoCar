package com.jianguo.aoaocar.activity.driver;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.it.jianguo.common.Utils.FileUtil;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.user.CertifyActivity;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.view.pop.ConfirmPhotoPop;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import org.w3c.dom.Text;

import butterknife.Bind;

/**
 * Created by 22077 on 2018/1/23.
 */

public class SupplierCertifiyActivity extends BaseActivity{

    private static final int REQUEST_CODE_BUSINESS_LICENSE =123 ;
    @Bind(R.id.et_business_name)
    EditText business_name;

    @Bind(R.id.et_business_address)
    EditText business_address;
    @Bind(R.id.fl_business_license)
    FrameLayout flLicense;
    @Bind(R.id.iv_business_license)
    ImageView ivLicense;

    @Bind(R.id.btn_submit)
    Button btnSubmit;
    private int index=3;
    private ConfirmPhotoPop pop;
    private String picture;
    @Override
    public int getLayoutId() {
        return R.layout.activity_supplier_certify;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("供应商");
        //Android7.0拍照
        if (Build.VERSION.SDK_INT >=24) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }

        flLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmPhoto(index);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void confirmPhoto(final int index) {
        pop=new ConfirmPhotoPop(this, index, new ConfirmPhotoPop.OnDismissListener() {
            @Override
            public void onDismiss() {
                takePhone();
            }
        });
    }
    /**
     *
     */
    private void takePhone() {
        requestPermission(Constant.RC_CAMERA_PERM, new String[] { Manifest.permission.CAMERA },
                getString(R.string.rationale_camera), new PermissionCallBackM() {
                    @Override public void onPermissionGrantedM(int requestCode, String... perms) {
                        picture=FileUtil.getSaveFile(SupplierCertifiyActivity.this,"driving_license").getAbsolutePath();
                        Intent intent = new Intent(SupplierCertifiyActivity.this, CameraActivity.class);

                        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                                picture );

                        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                                CameraActivity.CONTENT_TYPE_GENERAL);
                        startActivityForResult(intent, REQUEST_CODE_BUSINESS_LICENSE);
                    }
                    @Override public void onPermissionDeniedM(int requestCode, String... perms) {
                        showShortToast("您没有相机权限，去设置里授权");
                        return;
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK &&data!=null){
         if(REQUEST_CODE_BUSINESS_LICENSE==requestCode){
             ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(picture),ivLicense );
          }
        }
    }
}
