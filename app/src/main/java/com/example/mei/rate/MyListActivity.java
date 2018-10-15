package com.example.mei.rate;

import android.app.ListActivity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MyListActivity extends ListActivity implements Runnable, AdapterView.OnItemClickListener {

    private String[] list_data={"one","two","three","four"};
    private int msgWhat=3;
    Handler handler;
    //    存放文字，汇率
    private ArrayList<HashMap<String,String>> listItems;
    //    适配器
    private SimpleAdapter listItemAdapter;

    //多列显示
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        GridView listView = (GridView) findViewById(R.id.mylist);
        //init data
        List<String > data=new ArrayList<String>();

        for(int i=0;i<100;i++){
            data.add("item" + i);//为其添加数据
        }
        ListAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data) ;
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.nodata));
        listView.setOnItemClickListener(this);

    }




    //长按：在事件处理里添加删除语句
    @Override
    public void onItemClick(AdapterView<?> listview, View view, int position, long id){
        Log.i(TAG, "onItemClick: position=" + position);
        Log.i(TAG, "onItemClick: parent" + listview);

        //simpleArray 方法
        listItems.remove(position);//删除数据项
        listItemAdapter.notifyDataSetChanged();// 更新适配器

        //arrayadapter 方法
        //adapter.remove(listview.getItemAtPosition(position));
        //adapter.notifyDataSetChanged();
    }
//    @Override//自动生成的
//    public void onPointerCaptureChanged(boolean hasCapture) {//长按
//    }

    //短按：更新数据
    public void onclick(){



    }

    public void run(){
        //获取网络数据，放入List带回
        Log.i("thread","run……");
        List<String> rateList=new ArrayList<String>();
        try{
            Document doc= Jsoup.connect("http://www.usd-cny.com/icbc.htm").get();
            Elements tbs=doc.getElementsByClass("tableDataTable");
            Element table=tbs.get(0);

            Elements tds=table.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=5){
                Element td=tds.get(i);
                Element td2=tds.get(i+3);

                String tdStr=td.text();
                String pStr=td2.text();
                rateList.add(tdStr+"==>"+pStr);
                Log.i("td",tdStr+"==>"+pStr);
            }

        }catch (MalformedURLException e){
            Log.e("www",e.toString());
            e.printStackTrace();
        }catch (IOException e){
            Log.e("www",e.toString());
            e.printStackTrace();
        }

        //获取Msg对象，用于返回主线程
        Message msg=handler.obtainMessage(5);
        msg.obj=rateList;
        handler.sendMessage(msg);

        Log.i("thread","sendMessage……");
    }



}
