package com.example.mei.rate;

import android.app.ListActivity;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;



import java.util.HashMap;

import static android.support.constraint.Constraints.TAG;


public class MyList2Activity extends ListActivity implements AdapterView.OnItemClickListener{
    //...
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list2);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Log.i(TAG, "onItemClick: parent=" + parent);
        Log.i(TAG, "onItemClick: view=" + view);
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: id=" + id);
        // 从ListView中获取选中数据
        HashMap<String,String> map = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = map.get("ItemTitle");
        String detailStr = map.get("ItemDetail");
        Log.i(TAG, "onItemClick: titleStr=" + titleStr);
        Log.i(TAG, "onItemClick: detailStr=" + detailStr);
        //从View中获取选中数据
        TextView title = (TextView) view.findViewById(R.id.itemTitle);
        //将币种文本和汇率数据传入到RateCalcActivity页面，附RateCalcActivity布局和代码
        TextView detail = (TextView) view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());

    }
}