package com.yc.appmonitor.crash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.yc.appmonitor.R;
import com.yc.monitortimelib.TimeMonitorHelper;
import com.yc.toollib.crash.CrashListActivity;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2020/7/10
 *     desc  : 制造异常测试类
 *     revise:
 * </pre>
 */
public class CrashTestActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash_test);

        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
        findViewById(R.id.tv_5).setOnClickListener(this);
        findViewById(R.id.tv_6).setOnClickListener(this);
        findViewById(R.id.tv_7).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TimeMonitorHelper.end("startActivity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        TimeMonitorHelper.start("onStop");
    }

    @Override
    protected void onDestroy() {
        TimeMonitorHelper.start("finish");
        super.onDestroy();
        TimeMonitorHelper.end("onStop");
    }

    @Override
    public void finish() {
        super.finish();
        TimeMonitorHelper.end("finish");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_1) {
            TimeMonitorHelper.start("Click");
            Integer.parseInt("12.3");
        } else if (id == R.id.tv_2) {
            ArrayList<String> list = new ArrayList<>();
            list.get(5);
            TimeMonitorHelper.end("Click");
        } else if (id == R.id.tv_3) {
            Activity activity = null;
            activity.isDestroyed();
        } else if (id == R.id.tv_4){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CrashTestActivity.this,"吐司",Toast.LENGTH_SHORT).show();
                }
            }).start();
        } else if ( id == R.id.tv_5){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    throw new RuntimeException("handler异常");
                }
            });
        } else if (id == R.id.tv_6){
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int i=0 ; i<100 ; i++){
                list.add(i);
            }
            Iterator<Integer> iterator = list.iterator();
            while(iterator.hasNext()){
                Integer integer = iterator.next();
                if(integer%2==0){
                    list.remove(integer);
                }
            }
        } else if ( id == R.id.tv_7){
            CrashListActivity.startActivity(this);
        }
    }

}
