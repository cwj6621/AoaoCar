package com.jianguo.aoaocar.fragment.rental;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.it.jianguo.common.Utils.FileUtil;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseFragment;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.user.CertifyActivity;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.view.pop.ConfirmPhotoPop;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;


import java.io.File;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/12/11.
 */

@SuppressLint("ValidFragment")
public class CarRentOneFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.fl_vehicle_license)
    FrameLayout fl_vehicle_license;
    @Bind(R.id.iv_vehicle_license)
    ImageView vehicle_license;

    @Bind(R.id.btn_rental_next)
    Button btnNext;
    private OnCarRentalSubmit  onCarRentalSubmit;
    private String carfile;
    public CarRentOneFragment(OnCarRentalSubmit  _onCarRentalSubmit){
        this.onCarRentalSubmit=_onCarRentalSubmit;
    }
    @Override
    protected void fetchData() {
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_car_rental;
    }
    @Override
    protected void initView() {
        btnNext.setOnClickListener(this);
        fl_vehicle_license.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_rental_next:
                  onCarRentalSubmit.CarRentalSubmit();
                break;
            case R.id.fl_vehicle_license:
                 new ConfirmPhotoPop(mActivity, 3, new ConfirmPhotoPop.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        carfile =  FileUtil.getSaveFile(mActivity,"").getAbsolutePath();
                        takePhone(carfile);
                    }
                });
                break;
        }
    }
     /*
      *照片
      */
    private void takePhone(final String carfile) {
        mActivity.requestPermission(Constant.RC_CAMERA_PERM, new String[] { Manifest.permission.CAMERA },
                getString(R.string.rationale_camera), new PermissionCallBackM() {
                    @Override public void onPermissionGrantedM(int requestCode, String... perms) {
                        Intent intent = new Intent(mActivity, CameraActivity.class);
                        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                                carfile);
                        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                                CameraActivity.CONTENT_TYPE_GENERAL);
                        startActivityForResult(intent, Constant.REQUEST_CODE_VEHICLE_LICENSE);
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
       if(resultCode==mActivity.RESULT_OK&&data!=null) {
            switch (requestCode){
                case  Constant.REQUEST_CODE_VEHICLE_LICENSE: //选择图片
                    updateusrimg(carfile);
                    recVehicleLicense(carfile);
                    break;
            }
        }
    }

    private void updateusrimg(String path) {
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(path), vehicle_license );
    }
    public static void recVehicleLicense(String filePath ) {
        OcrRequestParams param = new OcrRequestParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeVehicleLicense(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {

            }

            @Override
            public void onError(OCRError error) {error.getMessage();
            }
        });
    }
    /**
     * 车辆行驶证提交
     */
    public interface OnCarRentalSubmit{
        void  CarRentalSubmit();
    }
}
