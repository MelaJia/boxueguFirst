package cn.edu.gdmec.android.boxuegu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.UserBean;
import cn.edu.gdmec.android.boxuegu.utils.AnalysisUtils;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private String spUserName;
    private TextView tv_back;
    private TextView tv_nickName;
    private TextView tv_user_name;
    private TextView tv_sex;
    private TextView tv_signature;
    private TextView tv_qq;
    private RelativeLayout rl_nickName;
    private RelativeLayout rl_sex;
    private RelativeLayout rl_signature;
    private RelativeLayout rl_read;
    private RelativeLayout rl_qq;

    private static final int CHANGE_NICKNAME = 1;//修改昵称的自定义常量
    private static final int CHANGE_SIGNATURE = 2;//修改签名的自定义常量
    private static final int CHANGE_QQ = 3;//修改签名的自定义常量

    private ImageView mImageView; //用于显示图片
    private String mPhotoPath;
    private File mPhotoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        spUserName = AnalysisUtils.readLoginUserName(this);
        init();
        initData();
        setListener();
    }

    public void enterActivityForResult(Class<?> to, int requestCode, Bundle b) {
        Intent i = new Intent(this, to);
        i.putExtras(b);
        startActivityForResult(i, requestCode);
    }


    private void init() {
        tv_back = (TextView) findViewById(R.id.tv_back);
        TextView tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("个人资料");
        RelativeLayout rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));


        rl_nickName = (RelativeLayout) findViewById(R.id.rl_nickname);
        tv_nickName = (TextView) findViewById(R.id.tv_nickName);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);

        rl_sex = (RelativeLayout) findViewById(R.id.rl_sex);
        tv_sex = (TextView) findViewById(R.id.tv_sex);

        rl_signature = (RelativeLayout) findViewById(R.id.rl_signature);
        tv_signature = (TextView) findViewById(R.id.tv_signature);

        rl_qq=findViewById(R.id.rl_qq);
        tv_qq=findViewById(R.id.qq);

        rl_read = (RelativeLayout) findViewById(R.id.rl_read);
        mImageView = (ImageView) findViewById(R.id.iv_head_icon);

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoActivity.this.finish();
            }
        });


    }

    //从数据库中获取数据
    private void initData() {
        UserBean bean = null;
        bean = DBUtils.getInstance(this).getUserInfo(spUserName);
        //首先判断一下数据库中是否有数据
        if (bean == null) {
            bean = new UserBean();
            bean.userName = spUserName;
            bean.nickName = "问答精灵";
            bean.sex = "男";
            bean.signature = "问答精灵";
            bean.qq="8888888";
            //保存用户信息到数据库
            DBUtils.getInstance(this).saveUserInfo(bean);
        }
        setValue(bean);
    }

    //为界面控件设置值
    private void setValue(UserBean bean) {
        tv_nickName.setText(bean.nickName);
        tv_user_name.setText(bean.userName);
        tv_sex.setText(bean.sex);
        tv_signature.setText(bean.signature);
        tv_qq.setText(bean.qq);

    }

    private void setListener() {
        rl_nickName.setOnClickListener(this);
        tv_user_name.setOnClickListener(this);
        rl_sex.setOnClickListener(this);
        rl_signature.setOnClickListener(this);
        rl_read.setOnClickListener(this);
        rl_qq.setOnClickListener(this);

    }


    //设置性别的弹出框
    private void sexDialog(String sex) {

        int sexFlag = 0;
        if ("男".equals(sex)) {
            sexFlag = 0;
        } else if ("女".equals(sex)) {
            sexFlag = 1;
        }
        final String items[] = {"男", "女"};
        AlertDialog.Builder bulider = new AlertDialog.Builder(this);
        bulider.setTitle("性别");//设置标题
        //单选
        bulider.setSingleChoiceItems(items, sexFlag, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(UserInfoActivity.this, items[which], Toast.LENGTH_SHORT).show();
                setSex(items[which]);
            }

        });
        bulider.show();
    }

    /**
     * 更新界面上的性别数据
     *
     * @param sex
     */
    private void setSex(String sex) {
        tv_sex.setText(sex);
        //最后更新数据库的性别数据
        DBUtils.getInstance(this).updateUserInfo("sex", sex, spUserName);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_back:
                this.finish();
                break;
            case R.id.rl_nickname://昵称的点击事件
                String name = tv_nickName.getText().toString();//获取昵称控件上的数据
                //Bundle将数据传递给下一个修改页面
                Bundle bdName = new Bundle();
                bdName.putString("content", name);//传递界面上的昵称数据
                bdName.putString("title", "昵称");
                bdName.putInt("flag", 1);//flag传递1表示修改昵称
                enterActivityForResult(ChangeUserInfoActivity.class, CHANGE_NICKNAME, bdName);
                break;
            case R.id.rl_sex:
                String sex = tv_sex.getText().toString();
                sexDialog(sex);
                break;
            case R.id.rl_signature:// 签名的点击事件
                String signature = tv_signature.getText().toString();//获取签名控件上的数据
                Bundle bdSignature = new Bundle();
                bdSignature.putString("content", signature);//传递界面上的签名数据
                bdSignature.putString("title", "签名");
                bdSignature.putInt("flag", 2);//flag传递2表示修改 签名
                enterActivityForResult(ChangeUserInfoActivity.class, CHANGE_SIGNATURE, bdSignature);
                break;
            case R.id.rl_qq:// 签名的点击事件
                String qq = tv_qq.getText().toString();//获取签名控件上的数据
                Bundle bdQq = new Bundle();
                bdQq.putString("content", qq);//传递界面上的签名数据
                bdQq.putString("title", "qq");
                bdQq.putInt("flag", 3);//flag传递2表示修改 签名
                enterActivityForResult(ChangeUserInfoActivity.class, CHANGE_QQ, bdQq);
                break;
            case R.id.rl_read:
                takePhoto();
                break;

            default:
                break;
        }
    }

    public void takePhoto() {
//        AlertDialog.Builder bulider = new AlertDialog.Builder(this);
//        bulider.setTitle("选择上传");//设置标题
//        //单选
//        final String items[] = {"系统相机", "手机相册"};
//        bulider.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//        bulider.show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setPositiveButton("拍照上传", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.media.action.IMAGE_CAPTURE");
                intent.addCategory("android.intent.category.DEFAULT");
                mPhotoPath = getSDPath()+"/"+ getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面

                mPhotoFile = new File(mPhotoPath);
                if (!mPhotoFile.exists()){
                    try {
                        mPhotoFile.createNewFile();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
             //   startActivityForResult(intent,REQ_CODE_CAMERA);
//                File file = new File(Environment.getExternalStorageDirectory() + "/000.3gp");
//                Uri uri = Uri.fromFile(file);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("本地相册", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //  dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivity(intent);
            }
        });

        builder.create().show();

    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date)  +".jpg";
    }

    private String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);   //判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CHANGE_NICKNAME:
                if (data != null) {
                    String new_info = data.getStringExtra("nickName");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_nickName.setText(new_info);
                    //更新数据库中的昵称字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("nickName", new_info, spUserName);
                }

                break;
            case CHANGE_SIGNATURE://个人资料修改界面回传过来的数据
                if (data != null) {
                    String new_info = data.getStringExtra("signature");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_signature.setText(new_info);
                    //更新数据库中的签名 字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("signature", new_info, spUserName);
                }

                break;
            case CHANGE_QQ://个人资料修改界面回传过来的数据
                if (data != null) {
                    String new_info = data.getStringExtra("qq");
                    if (TextUtils.isEmpty(new_info)) {
                        return;
                    }
                    tv_qq.setText(new_info);
                    //更新数据库中的签名 字段
                    DBUtils.getInstance(UserInfoActivity.this).updateUserInfo("qq", new_info, spUserName);
                }

                break;
        }
    }
}
