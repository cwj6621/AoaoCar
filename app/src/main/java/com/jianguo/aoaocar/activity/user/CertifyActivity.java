package com.jianguo.aoaocar.activity.user;
import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.it.jianguo.common.Utils.FileUtil;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.view.pop.ConfirmPhotoPop;
import com.jianguo.timedialog.AddressDialog;
import com.jianguo.timedialog.listener.OnAddressSetListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.File;

import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/30.
 */
public class CertifyActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CAMERA =120 ;
    private static final int REQUEST_CODE_DRIVING_LICENSE = 121;
    @Bind(R.id.fl_drivers_license)
    FrameLayout  fl_drivers;
    @Bind(R.id.fl_identify_positive)
    FrameLayout  fl_positive;
    @Bind(R.id.fl_identify_reverse)
    FrameLayout  fl_reverse;

    @Bind(R.id.iv_drivers_license)
    ImageView  iv_drivers;
    @Bind(R.id.iv_identify_positive)
    ImageView  iv_positive;
    @Bind(R.id.iv_identify_reverse)
    ImageView  iv_reverse;

    @Bind(R.id.tv_where_area)
    TextView where_area;
    private ConfirmPhotoPop pop;
    private int index=-1;
    private AddressDialog mAddressDialog;
    private String picture1;
    private String picture2;
    private String picture3;
    @Override
    public int getLayoutId() {
        return R.layout.activity_certify;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("认证");

        fl_drivers.setOnClickListener(this);
        fl_positive.setOnClickListener(this);
        fl_reverse.setOnClickListener(this);
        where_area.setOnClickListener(this);

        //Android7.0拍照
        if (Build.VERSION.SDK_INT >=24) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        initPictures();
        initAddressDialog();
    }

    private void initPictures() {
        picture1=FileUtil.getSaveFile(CertifyActivity.this,"driving_license").getAbsolutePath();
        picture2=FileUtil.getSaveFile(CertifyActivity.this,"card_front").getAbsolutePath();
        picture3=FileUtil.getSaveFile(CertifyActivity.this,"card_back").getAbsolutePath();
    }

    private void initAddressDialog() {
        mAddressDialog = new AddressDialog.Builder()
                .setCyclic(false)
                .setTitleStringId("地址")
                .setThemeColor(getResources().getColor(R.color.colorOrangePrimary))
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colorOrangePrimary))
                .setWheelItemTextSize(13)
                .setAddressBack(new OnAddressSetListener() {
                    @Override
                    public void onAddressDateSet(AddressDialog mAddressDialog, String provice, String city, String county) {
                        if(provice.equals(city)){
                            where_area.setText(city+" "+county);
                        }else{
                            where_area.setText(provice+" "+city+" "+county);
                        }
                        }
                })
                .build();
    }
    @Override
    public void onClick(View v) {
          switch (v.getId()){
              case R.id.fl_drivers_license:
                      index=0;
                      confirmPhoto(index);
                  break;
              case R.id.fl_identify_positive:
                      index=1;
                      confirmPhoto(index);
                  break;
              case R.id.fl_identify_reverse:
                      index=2;
                      confirmPhoto(index);
                  break;
              case R.id.tv_where_area:
                  mAddressDialog.show(getSupportFragmentManager(),"all");
                  break;
          }
    }
      private void confirmPhoto(final int index) {
         pop=new ConfirmPhotoPop(this, index, new ConfirmPhotoPop.OnDismissListener() {
          @Override
          public void onDismiss() {
              takePhone(index);
          }
       });
     }
    /**
     *
     */
    private   void takePhone(final int index) {
        requestPermission(Constant.RC_CAMERA_PERM, new String[] { Manifest.permission.CAMERA },
                getString(R.string.rationale_camera), new PermissionCallBackM() {
                    @Override public void onPermissionGrantedM(int requestCode, String... perms) {
                        Intent intent = new Intent(CertifyActivity.this, CameraActivity.class);
                       switch (index){
                           case 0: //驾驶证识别
                               intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, picture1);
                               intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                                       CameraActivity.CONTENT_TYPE_GENERAL);
                               startActivityForResult(intent, REQUEST_CODE_DRIVING_LICENSE);
                               break;
                           case 1://身份证正面识别
                               intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, picture2);
                               intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                               startActivityForResult(intent, REQUEST_CODE_CAMERA);
                               break;
                           case 2:
                               intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH, picture3);
                               intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                               startActivityForResult(intent, REQUEST_CODE_CAMERA);
                               break;
                       }
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
            switch (requestCode){
                case  REQUEST_CODE_DRIVING_LICENSE: //驾照识别
                  //  recDrivingLicense(FileUtil.getSaveFile(CertifyActivity.this,"driving_license"));
                    updateusrimg(0,picture1);
                    break;
                case   REQUEST_CODE_CAMERA: //身份证识别
                        String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                        if (!TextUtils.isEmpty(contentType)) {
                            if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                                updateusrimg(1,picture2);
                            } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                                recIDCard(IDCardParams.ID_CARD_SIDE_BACK, picture3);
                                updateusrimg(2,picture3);
                            }

                        }
                    break;
            }
        }
    }
    private void updateusrimg(int index,String path) {
        ImageView  v = null;
           switch (index){
               case 0:
                   v=iv_drivers;
                   break;
               case 1:
                   v=iv_positive;
                   break;
               case 2:
                   v=iv_reverse;
                   break;

           }
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(path), v );
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance().recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null) {
                showNoticeDialog(result.getJsonRes(),null);
            }
        }
            @Override
            public void onError(OCRError error) {
                error.getMessage();
            }
        });
    }

    public  void recDrivingLicense(String filePath ) {
        OcrRequestParams param = new OcrRequestParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeDrivingLicense(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
                if (result != null) {
                    showNoticeDialog(result.getJsonRes(), null);
                }
            }
            @Override
            public void onError(OCRError error) {
            }
        });
    }
}
