package com.keyi.erp_saomiaochuku.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.keyi.erp_saomiaochuku.R;

/**
 * Created by Administrator on 2016/6/16.
 */
public class YanZhengMaAtivity extends Activity implements View.OnClickListener {
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanzhengma);
        button= (Button) findViewById(R.id.bt_yanzheng);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(YanZhengMaAtivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
