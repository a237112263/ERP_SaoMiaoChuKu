package com.keyi.erp_saomiaochuku.scanner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.keyi.erp_saomiaochuku.view.MainActivity;
import com.keyi.erp_saomiaochuku.R;
import com.keyi.erp_saomiaochuku.bean.FinishMsg;
import com.keyi.erp_saomiaochuku.bean.ShopMsg;
import com.keyi.erp_saomiaochuku.bean.TagMsg;
import com.keyi.erp_saomiaochuku.db.Code;
import com.keyi.erp_saomiaochuku.db.CodeDao;
import com.keyi.erp_saomiaochuku.db.User;
import com.keyi.erp_saomiaochuku.db.UserDao;
import com.keyi.erp_saomiaochuku.interfaces.MsgView;
import com.keyi.erp_saomiaochuku.present.MsgPresent;
import com.keyi.erp_saomiaochuku.scanner.camera.CameraManager;
import com.keyi.erp_saomiaochuku.scanner.decoding.CaptureActivityHandler;
import com.keyi.erp_saomiaochuku.scanner.decoding.InactivityTimer;
import com.keyi.erp_saomiaochuku.scanner.view.ViewfinderView;
import com.keyi.erp_saomiaochuku.utils.ACache;
import com.keyi.erp_saomiaochuku.utils.DatasUtils;
import com.keyi.erp_saomiaochuku.view.RegisterActivity;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

public class CaptureActivity extends Activity implements Callback, MsgView {
    public static final String QR_RESULT = "RESULT";
    private  int IS_SAOMIAO_PICI=0;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private SurfaceView surfaceView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private String tagNo;
    // private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    CameraManager cameraManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_capture);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinderview);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        // CameraManager.init(getApplication());
        cameraManager = new CameraManager(getApplication());

        viewfinderView.setCameraManager(cameraManager);

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        cameraManager.closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            // CameraManager.get().openDriver(surfaceHolder);
            cameraManager.openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        //showResult(obj, barcode);
        storeResult(obj);
    }

    private void storeResult(final Result rawResult) {
        String[] msg = rawResult.toString().split("\\$");

            switch (msg.length) {
                case 2:
                    if (IS_SAOMIAO_PICI==1){
                        new AlertDialog.Builder(this).setMessage("已经扫了批次单，请不要重复扫描批次单!")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        restartPreviewAfterDelay(2000L);
                                    }
                                }).show();
                    }else {
                        if (msg[1].equals("KYSOFT")) {
                            ACache aCache = ACache.get(this);
                            aCache.put("guid", msg[0]);
                            MsgPresent msgPresent = new MsgPresent(this, 0);
                            msgPresent.getMsg(DatasUtils.shopShowUrl(this, msg[0]));
                            Log.e("tagurl", DatasUtils.shopShowUrl(this, msg[0]));
                        } else {
                            playSound(R.raw.fail);
                            Toast.makeText(CaptureActivity.this, "扫描错误!请扫正确的批次二维码", Toast.LENGTH_SHORT).show();
                            restartPreviewAfterDelay(2000L);
                        }
                    }
                    break;
                case 4:
                    if (msg[1].equals("KYSOFT")) {
                        tagNo = msg[0];
                        MsgPresent msgPresent = new MsgPresent(this, 2);
                        msgPresent.getMsg(DatasUtils.getTagCheckUrlUrl(this, msg[0]));
                        Log.e("tagurl", DatasUtils.getTagCheckUrlUrl(this, msg[0]));
                    } else {
                        playSound(R.raw.fail);
                        Toast.makeText(CaptureActivity.this, "二维码不是商品标签！", Toast.LENGTH_SHORT).show();
                        restartPreviewAfterDelay(2000L);
                    }
                    break;
                default:
                    playSound(R.raw.fail);
                    Toast.makeText(CaptureActivity.this, "扫描错误", Toast.LENGTH_SHORT).show();
                    restartPreviewAfterDelay(2000L);
                    break;
            }
        }




    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(MessageIDs.restart_preview, delayMS);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            try {
                AssetFileDescriptor fileDescriptor = getAssets().openFd("qrbeep.ogg");
                this.mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                this.mediaPlayer.setVolume(0.1F, 0.1F);
                this.mediaPlayer.prepare();
            } catch (IOException e) {
                this.mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_CANCELED);
            finish();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_FOCUS || keyCode == KeyEvent.KEYCODE_CAMERA) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void isTrue(String istrue, int queneFlag) {
        Gson gson = new Gson();
        switch (queneFlag) {
            case 0:
                ShopMsg shopMsg = gson.fromJson(istrue, ShopMsg.class);
                if (shopMsg.isIsOK()) {
                    IS_SAOMIAO_PICI=1;
                    playSound(R.raw.sucee);
                    Toast.makeText(CaptureActivity.this, "批次单扫描成功!", Toast.LENGTH_SHORT).show();
                    UserDao userDao = new UserDao(this);
                    for (int i = 0; i < shopMsg.getData().size(); i++) {
                        User user = new User(i, shopMsg.getData().get(i).getMergerSysNo(),
                                shopMsg.getData().get(i).getOuterIid(), shopMsg.getData().get(i).getOuterSkuId(),
                                shopMsg.getData().get(i).getNum(), shopMsg.getData().get(i).getWareName(), "未扫描", shopMsg.getData().get(i).getSuppName(), 0);
                        userDao.add(user);
                    }
                    restartPreviewAfterDelay(2000L);
                } else {
                    playSound(R.raw.fail);
                    if (shopMsg.getErrMsg().toString().equals("手机号码不正确或者登录超时,请重新登录!")) {
                        new AlertDialog.Builder(this).setMessage("验证码已失效，请重新登录！")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MainActivity.activity.finish();
                                        Intent intent = new Intent(CaptureActivity.this, RegisterActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();

                    } else {
                        new AlertDialog.Builder(this).setTitle("扫描错误！").setMessage(shopMsg.getErrMsg().toString())
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        MainActivity.activity.finish();
                                        Intent intent = new Intent(CaptureActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).show();
                    }
                }
                break;

            case 1:
                FinishMsg finishMsg = gson.fromJson(istrue, FinishMsg.class);
                if (finishMsg.isIsOK()) {
                    playSound(R.raw.ok);
                    new AlertDialog.Builder(this).setMessage("成功完结批次!")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    MainActivity.activity.finish();
                                    Intent intent1 = new Intent();
                                    intent1.setClass(CaptureActivity.this, MainActivity.class);
                                    startActivity(intent1);
                                    finish();
                                }
                            }).show();
                } else {
                    new AlertDialog.Builder(this).setMessage(finishMsg.getData().getRemark().toString())
                            .setPositiveButton("确定", null).show();
                }
                break;
        }

        if (queneFlag == 2) {
            TagMsg tagMsg = gson.fromJson(istrue, TagMsg.class);
            UserDao userDao = new UserDao(this);
            CodeDao codeDao = new CodeDao(this);
            int flag = 0;
            int flag1 = 0;
            if (tagMsg.isIsOK()) {
                List<Code> codes = DatasUtils.queryTagAll(this);
                for (int j = 0; j < codes.size(); j++) {
                    Log.e("code", codes.get(j).getWeiYiCode());
                    if (codes.get(j).getWeiYiCode().equals(tagNo)) {
                        flag1 = 1;
                    }
                }
                if (flag1 == 1) {
                    Toast.makeText(CaptureActivity.this, "该标签已被扫描！", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < DatasUtils.queryAll(this).size(); i++) {

                        if (tagMsg.getData().get(0).getOuterIid().equals(DatasUtils.queryAll(this).get(i).getOuterIid())
                                && tagMsg.getData().get(0).getOuterSkuId().equals(DatasUtils.queryAll(this).get(i).getOuterSkuId())
                                && tagMsg.getData().get(0).getSuppName().equals(DatasUtils.queryAll(this).get(i).getSuppname())
                                && tagMsg.getData().get(0).getWareName().equals(DatasUtils.queryAll(this).get(i).getWareHouseName())) {
                            if (DatasUtils.queryAll(this).get(i).getZhuangTai().equals("已全部扫描")) {
                                Toast.makeText(CaptureActivity.this, "该商品数量已扫满！", Toast.LENGTH_SHORT).show();
                                playSound(R.raw.fail);
                                restartPreviewAfterDelay(2000L);
                            } else {
                                if (DatasUtils.queryAll(this).get(i).getYiSaoNum() == (Integer.parseInt(DatasUtils.queryAll(this).get(i).getNum()) - 1)) {
                                    playSound(R.raw.sucee);
                                    Toast.makeText(CaptureActivity.this, "扫描成功！", Toast.LENGTH_SHORT).show();
                                    userDao.updateUser(new User(DatasUtils.queryAll(this).get(i).getId(), DatasUtils.queryAll(this).get(i).getMergerSysNo(),
                                            DatasUtils.queryAll(this).get(i).getOuterIid(), DatasUtils.queryAll(this).get(i).getOuterSkuId(),
                                            DatasUtils.queryAll(this).get(i).getNum(), DatasUtils.queryAll(this).get(i).getWareHouseName(), "已全部扫描", DatasUtils.queryAll(this).get(i).getSuppname(), DatasUtils.queryAll(this).get(i).getYiSaoNum() + 1));
                                } else {
                                    playSound(R.raw.sucee);
                                    Toast.makeText(CaptureActivity.this, "扫描成功！", Toast.LENGTH_SHORT).show();
                                    userDao.updateUser(new User(DatasUtils.queryAll(this).get(i).getId(), DatasUtils.queryAll(this).get(i).getMergerSysNo(),
                                            DatasUtils.queryAll(this).get(i).getOuterIid(), DatasUtils.queryAll(this).get(i).getOuterSkuId(),
                                            DatasUtils.queryAll(this).get(i).getNum(), DatasUtils.queryAll(this).get(i).getWareHouseName(), "已扫描" + (DatasUtils.queryAll(this).get(i).getYiSaoNum() + 1) + "件", DatasUtils.queryAll(this).get(i).getSuppname(), DatasUtils.queryAll(this).get(i).getYiSaoNum() + 1));
                                }
                                codeDao.add(new Code(tagNo));
                            }
                            flag = 1;
                        }
                    }
                    if (flag == 0) {
                        playSound(R.raw.fail);
                        Toast.makeText(CaptureActivity.this, "扫描错误,批次中不存在该商品!", Toast.LENGTH_SHORT).show();
                    }
                }
                int flag2 = 0;
                for (int j = 0; j < DatasUtils.queryAll(this).size(); j++) {
                    if (DatasUtils.queryAll(this).get(j).getZhuangTai().equals("已全部扫描")) {
                        flag2++;
                    }
                }
                if (flag2 != DatasUtils.queryAll(this).size()) {
                    restartPreviewAfterDelay(2000L);
                } else {
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < DatasUtils.queryTagAll(this).size(); i++) {
                        if (i == (DatasUtils.queryTagAll(this).size() - 1)) {
                            stringBuffer.append(DatasUtils.queryTagAll(this).get(DatasUtils.queryTagAll(this).size() - 1).getWeiYiCode());
                        } else {
                            stringBuffer.append(DatasUtils.queryTagAll(this).get(i).getWeiYiCode());
                            stringBuffer.append(",");
                        }
                    }
                    ACache aCache = ACache.get(this);
                    MsgPresent msgPresent = new MsgPresent(this, 1);
                    msgPresent.getMsg(DatasUtils.finishShopUrl(this, aCache.getAsString("guid"), stringBuffer.toString()));
                }
            } else {
                Toast.makeText(CaptureActivity.this, tagMsg.getErrMsg().toString(), Toast.LENGTH_SHORT).show();
                restartPreviewAfterDelay(2000L);
            }
        }
    }

    @Override
    public void isError(String isError, int queneFlag) {
        Toast.makeText(CaptureActivity.this, "服务器或网络异常！", Toast.LENGTH_SHORT).show();
        restartPreviewAfterDelay(2000L);
    }

    private void playSound(int res) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        AssetFileDescriptor file = this.getResources().openRawResourceFd(
                res);//声音
        try {
            mediaPlayer.setDataSource(file.getFileDescriptor(),
                    file.getStartOffset(), file.getLength());
            file.close();
            mediaPlayer.setVolume(1f, 1f);
            mediaPlayer.prepare();
        } catch (IOException ioe) {

        }
        mediaPlayer.start();
    }
}