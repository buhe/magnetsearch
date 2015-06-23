package com.aoppp.magnetsearch;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aoppp.magnetsearch.connector.HttpConnnectManager;
import com.aoppp.magnetsearch.connector.MagnetBack;
import com.aoppp.magnetsearch.domain.Magnet;
import com.aoppp.magnetsearch.domain.MagnetAdapter;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.umeng.analytics.MobclickAgent;

import java.util.Arrays;
import java.util.List;


public class ResultTableActivity extends ActionBarActivity implements AbsListView.OnScrollListener {

    private ListView listView;
    private View moreView;
    private TextView tv_load_more;
    private ProgressBar pb_load_progress;
    private MagnetAdapter adapter;
    private int lastItem;
    private int page = 0;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_table);
        setTitle("搜索结果");
        findView();
        setListener();
    }


    private void findView() {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        moreView = inflater.inflate(R.layout.footer_more, null);

        tv_load_more = (TextView) moreView.findViewById(R.id.tv_load_more);
        pb_load_progress = (ProgressBar) moreView.findViewById(R.id.pb_load_progress);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.resultList);
        text = getIntent().getStringExtra("text");
        adapter = new MagnetAdapter(ResultTableActivity.this);
// ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Magnet  itemValue    = (Magnet) listView.getItemAtPosition(position);
                ClipboardManager cmb = (ClipboardManager)ResultTableActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(itemValue.getUrl());
//                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "已经复制磁力链到剪贴板", Toast.LENGTH_LONG)
                        .show();

            }

        });
        listView.addFooterView(moreView);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        HttpConnnectManager.instance().get(text, new MagnetBack() {
            @Override
            public void get(List<Magnet> magnets) {
                adapter.addNewList(magnets);
                ResultTableActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });


    }

    private void setListener() {
        listView.setOnScrollListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (lastItem == adapter.getCount()
                && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            loadMoreData();
        }
    }

    private void loadMoreData() {
        page++;
        HttpConnnectManager.instance().get(text,page,new MagnetBack() {
            @Override
            public void get(List<Magnet> magnets) {
                adapter.addNewList(magnets);
                ResultTableActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        lastItem = firstVisibleItem + visibleItemCount - 1;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);          //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
