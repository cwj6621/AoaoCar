package com.jianguo.aoaocar.fragment.rental;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.it.jianguo.common.Utils.FileUtil;
import com.it.jianguo.common.base.BaseFragment;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.view.pop.SelectPicturePop;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/12/11.
 */

public class CarRentNextFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.btn_submit)
    Button submit;
    @Bind(R.id.fl_all_car_picture)
    FrameLayout rl_car_picture;
    @Bind(R.id.iv_all_car_picture)
    ImageView car_picture;
    private File file;
    private Uri outputUri;
    @Override
    protected void fetchData() {
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_car_rental_next;
    }
    @Override
    protected void initView() {
        submit.setOnClickListener(this);
        rl_car_picture.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_splash:

                break;
            case R.id.fl_all_car_picture:
                new SelectPicturePop(mActivity, new SelectPicturePop.OnSelectedSharetener() {
                    @Override
                    public void onShareSelected(int index) {
                        switch (index) {
                            case 1:
                                selectPicture();
                                break;
                            case 2:
                                takePhone();
                                break;
                        }
                    }
                });
                break;
        }
    }

    /**
     *
     */
    private void takePhone() {
        file = new File(FileUtil.createRootPath(mActivity) + "/" + System.currentTimeMillis() + ".png");

        mActivity.requestPermission(Constant.RC_CAMERA_PERM, new String[] { Manifest.permission.CAMERA },
                getString(R.string.rationale_camera), new PermissionCallBackM() {
                    @Override public void onPermissionGrantedM(int requestCode, String... perms) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(intent, Constant.CAMERA_REQUEST_CODE);

                    }
                    @Override public void onPermissionDeniedM(int requestCode, String... perms) {
                        showShortToast("您没有相机权限，去设置里授权");
                        return;
                    }
                });
    }
    /**
     *
     */
    private void selectPicture() {
        mActivity.requestPermission(Constant.RC_READ_EXTERNAL_STORAGE, new String[] { Manifest.permission.READ_EXTERNAL_STORAGE ,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                getString(R.string.rationale_storage), new PermissionCallBackM() {
                    @Override public void onPermissionGrantedM(int requestCode, String... perms) {
                        Intent intent = new Intent("android.intent.action.GET_CONTENT");
                        intent.setType("image/*");
                        startActivityForResult(intent, Constant.GALLERY_REQUEST_CODE); // 打开相册
                    }
                    @Override public void onPermissionDeniedM(int requestCode, String... perms) {
                        showShortToast("您没有存储权限，去设置里授权");
                        return;
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == mActivity.RESULT_OK){
            switch (requestCode){
                case  Constant.GALLERY_REQUEST_CODE: //选择图片
                    // 判断手机系统版本号
                    if (Build.VERSION.SDK_INT >= 19) {
                        // 4.4及以上系统使用这个方法处理图片
                        handleImageOnKitKat(data);
                    } else {
                        // 4.4以下系统使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                    break;
                case  Constant.CAMERA_REQUEST_CODE: //选择图片
                    //  updateusrimg(file.getPath());
                    cropPhoto(Uri.fromFile(file));
                    break;

                case  Constant.GALLERY_PICTURE_CUT://裁剪
                    updateusrimg(outputUri.getPath());
                    break;
            }
        }

    }


    // 4.4及以上系统使用这个方法处理图片 相册图片返回的不再是真实的Uri,而是分装过的Uri
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String   imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(mActivity, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        updateusrimg(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = mActivity.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String   imagePath = getImagePath(uri, null);
        updateusrimg(imagePath);
    }

    private void updateusrimg(String path) {
        Log.i("com.jianguo.aoaocar",path);
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(path), car_picture );
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        File file = new File(mActivity.getExternalCacheDir(), "crop_image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("crop", "true");//可裁剪
        // 裁剪后输出图片的尺寸大小
        //intent.putExtra("outputX", 400);
        //intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片格式
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        startActivityForResult(intent, Constant.GALLERY_PICTURE_CUT);
    }
}

