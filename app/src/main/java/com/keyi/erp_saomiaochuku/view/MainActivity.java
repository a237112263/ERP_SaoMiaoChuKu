package com.keyi.erp_saomiaochuku.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.keyi.erp_saomiaochuku.R;
import com.keyi.erp_saomiaochuku.db.Code;
import com.keyi.erp_saomiaochuku.db.CodeDao;
import com.keyi.erp_saomiaochuku.db.User;
import com.keyi.erp_saomiaochuku.db.UserDao;
import com.keyi.erp_saomiaochuku.scanner.CaptureActivity;
import com.keyi.erp_saomiaochuku.utils.DatasUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.bt_sms1)
    Button button;
    @Bind(R.id.iv_main1)
    ImageView imageView;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.activity = this;
        button.setOnClickListener(this);
        imageView.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_sms1:
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_main1:
                Intent intent3 = new Intent();
                intent3.setClass(MainActivity.this, RegisterActivity.class);
                intent3.putExtra("smsactivity", true);
                startActivity(intent3);
                break;
        }
        UserDao userDao = new UserDao(this);
        List<User> users = DatasUtils.queryAll(this);
        userDao.delete(users);

        CodeDao codeDao = new CodeDao(this);
        List<Code> codes = DatasUtils.queryTagAll(this);
        codeDao.delete(codes);
    }
}
