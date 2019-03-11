package com.jianguo.aoaocar.activity;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.commonutils.ToastUitl;
import com.jianguo.aoaocar.R;
import com.orhanobut.logger.Logger;
import butterknife.Bind;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 *二维码扫描
 */
public class ZxingStartActivity extends BaseActivity implements QRCodeView.Delegate{
    @Bind(R.id.zxingview)
    ZXingView mZxingview;
    @Bind(R.id.FAB_left_zxingstart)
    FloatingActionButton mFABLeftZxingstart;
    private boolean  isOpenLight=false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_zxing_start;
    }
    @Override
    public void initView() {
          initFAB();
        // 初始化 CameraManager
          initZxingView();
    }
    private void initZxingView() {
        mZxingview.setDelegate(this);
        mZxingview.startSpot();
    }
    private void initFAB() {
        mFABLeftZxingstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(isOpenLight){
                   mZxingview.closeFlashlight();
               }else{
                   mZxingview.openFlashlight();
               }
                isOpenLight=!isOpenLight;
            }
        });
    }
    /**
     * 隐藏/显示 扫描框
     *
     * @param notShowRect
     */
    private void changeRect(boolean notShowRect) {
        if (notShowRect) {
            mZxingview.hiddenScanRect();
        } else {
            mZxingview.showScanRect();
        }
    }
    /**
     * 扫描二维码回调事件
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        showSuccessDialog(result);
        vibrate();
    }
    RadioGroup radioGroup;
    public void showSuccessDialog(final String result) {
        closeLight();
        mZxingview.stopSpot();
        ToastUitl.showShort(this,result);
//        mDialogBuilder = new AlertDialog.Builder(this);
//        mDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialog) {
//                 mZxingview.startSpot();
//            }
//        });
//
//        if (mZxingStyle == STYLE_ALL) {
//            View inflate = LayoutInflater.from(this).inflate(R.layout.selectspot_dialog_zxing, null);
//            radioGroup = (RadioGroup) inflate.findViewById(R.id.radiogroup_dialog_zxing);
//            radioGroup.check(R.id.rb_text);
//            mDialogBuilder.setView(inflate);
//        }
//            mDialogBuilder.setTitle("扫取结果");
//            mDialogBuilder.setMessage(result);
//
//            View view = LayoutInflater.from(this).inflate(R.layout.headtitle_dialog_zxing, null);
//            view.findViewById(R.id.copy_dialog_zxing).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onCopyTextToClipboard(result);
//                }
//            });
//
//            mDialogBuilder.setCustomTitle(view);
//            mDialogBuilder.setNegativeButton(R.string.cancel, null);
//            mDialogBuilder.setPositiveButton(R.string.imtrue, new DialogInterface.OnClickListener() {
//              @Override
//               public void onClick(DialogInterface dialog, int which) {
//                switch (mZxingStyle) {
//                    case STYLE_ALL:
//                        actionAll(result,radioGroup);
//                        break;
//                    case STYLE_DOWNLOAD:
//                        break;
//                    case STYLE_IMG:
//                        actionImg(result);
//                        break;
//                    case STYLE_TEXT:
//                        actionText();
//                        break;
//                    case STYLE_WEB:
//                        actionWeb(result);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });
//        mDialogBuilder.show();
    }

    /**
     * 复制到粘贴板
     * @param string
     */
    private void onCopyTextToClipboard(String string) {
        if (!TextUtils.isEmpty(string)) {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("website", string);
            clipboardManager.setPrimaryClip(clip);
            Toast.makeText(this, R.string.copysuccess, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.copyfail, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onScanQRCodeOpenCameraError() {
          Logger.e("打开相机出错");
    }
    @Override
    protected void onStart() {
        super.onStart();
        mZxingview.startCamera();
        mZxingview.showScanRect();
    }
    @Override
    protected void onStop() {
        closeLight();
        mZxingview.stopCamera();
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        mZxingview.onDestroy();
        super.onDestroy();
    }
    /**
     * 震动手机
     */
    @SuppressLint("MissingPermission")
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
     }

    public void closeLight() {
        if(isOpenLight){
            isOpenLight=false;
            mZxingview.closeFlashlight();
        }
    }
}
