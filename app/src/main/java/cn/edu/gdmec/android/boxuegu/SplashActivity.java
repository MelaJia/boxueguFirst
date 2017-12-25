package cn.edu.gdmec.android.boxuegu;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }
    private void init(){
       TextView tv_version = (TextView) findViewById(R.id.tv_splash_version);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(),0);
            tv_version.setText("V"+info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText("V");
        }
        //利用timer使界面延迟三秒后跳转，timer有一个线程，这个线程不断执行task
        Timer timer = new Timer();
        //实timertask现runable接口， TimerTask表示在一个指定时间内执行的task
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(task,3000);//手中这个task在延迟三秒后自动执行
    }
}
