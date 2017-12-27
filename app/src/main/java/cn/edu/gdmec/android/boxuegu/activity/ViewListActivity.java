package cn.edu.gdmec.android.boxuegu.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class ViewListActivity extends AppCompatActivity {

    private String intro;
    private int chapterId;
    private DBUtils db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        //设置界面为视频
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //从课程界面传过来的章节ID
        chapterId = getIntent().getIntExtra("id", 0);
        //从课程界面传过来的章节简介
        intro = getIntent().getStringExtra("info");
        db = DBUtils.getInstance(ViewListActivity.this);
        initData();
        initView();

    }

    private void initView() {

    }

    /**
     * 设置视频列表本地数据
     */
    private void initData() {
        JSONArray jsonArray;
        InputStream is;
        try {
            is = getResources().getAssets().open("data.json");
            jsonArray = new JSONArray(read(is));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /*
    读取数据流，参数in就是数据流
     */
    private String read(InputStream in) {
        BufferedReader reader = null;
        StringBuilder sb = null;
        String line =null;
        sb = new StringBuilder();
        //InoutStreamReader把in字节流转换成字符流
        reader = new BufferedReader(new InputStreamReader(in));
        try {
            while ((line = reader.readLine())!=null){
                sb.append(line);
                sb.append("\n");


            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
}
