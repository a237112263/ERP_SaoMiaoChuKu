package com.keyi.erp_saomiaochuku.application;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.keyi.erp_saomiaochuku.R;
import com.keyi.erp_saomiaochuku.utils.DatasUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/8/24.
 */
public class ShopShowAdapter extends BaseAdapter {

    Context context;

    public ShopShowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return DatasUtils.queryAll(context).size();
    }

    @Override
    public Object getItem(int i) {
        return DatasUtils.queryAll(context).get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.shopitem, viewGroup, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.textView1.setText("系统单号:" + DatasUtils.queryAll(context).get(i).getMergerSysNo());
        viewHolder.textView2.setText("商品编码:" + DatasUtils.queryAll(context).get(i).getOuterIid());
        viewHolder.textView3.setText("规格编码:" + DatasUtils.queryAll(context).get(i).getOuterSkuId());
        viewHolder.textView4.setText("数量:" + DatasUtils.queryAll(context).get(i).getNum());
        viewHolder.textView5.setText("仓库:" + DatasUtils.queryAll(context).get(i).getWareHouseName());
        viewHolder.textView7.setText("商品" + DatasUtils.numberZhuanHanzi(i + 1));
        viewHolder.textView8.setText("供应商:"+DatasUtils.queryAll(context).get(i).getSuppname());
        if (DatasUtils.queryAll(context).get(i).getZhuangTai().equals("已全部扫描")) {
            viewHolder.textView6.setTextColor(Color.RED);
        }
        if (DatasUtils.queryAll(context).get(i).getZhuangTai().contains("件")) {
            viewHolder.textView6.setTextColor(Color.rgb(255,115,2));
        }
        viewHolder.textView6.setText("状态：" + DatasUtils.queryAll(context).get(i).getZhuangTai());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_shopshow_xitongdanhao)
        TextView textView1;
        @Bind(R.id.tv_shopshow_guigebianm)
        TextView textView2;
        @Bind(R.id.tv_shopshow_shangpingbianma)
        TextView textView3;
        @Bind(R.id.tv_shopshow_shuliang)
        TextView textView4;
        @Bind(R.id.tv_shopshow_cangku)
        TextView textView5;
        @Bind(R.id.tv_shopshow_zhuangtai)
        TextView textView6;
        @Bind(R.id.tv_shopshow)
        TextView textView7;
        @Bind(R.id.tv_shopshow_gongyingshang)
        TextView textView8;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
