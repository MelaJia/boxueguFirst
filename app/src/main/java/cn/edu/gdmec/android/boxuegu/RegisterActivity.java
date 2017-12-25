package cn.edu.gdmec.android.boxuegu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    TextView tv_main_title;
    TextView tv_back;
    RelativeLayout rl_title_bar;
    Button btn_register;
    EditText et_psw;
    EditText et_psw_again;
    EditText et_user_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }
    private void init(){
        tv_main_title= (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back = (TextView) findViewById(R.id.tv_back);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);


        btn_register= (Button) findViewById(R.id.btn_register);
        et_user_name = (EditText) findViewById(R.id.et_username);
        et_psw = (EditText) findViewById(R.id.et_pwd);
        et_psw_again = (EditText) findViewById(R.id.et_pwd_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

    }
}
