package cn.edu.gdmec.android.boxuegu.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by student on 17/12/26.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "bxg.db";
    public static final String U_USERINFO = "userinfo";
    public SQLiteHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    //创建数据库
    @Override
    public void onCreate(SQLiteDatabase db){
        //创建用户信息表
        db.execSQL("CREATE TABLE IF NOT EXISTE " + U_USERINFO + "( " +
         "_id INTEGER PRIMARY KEY AUTOINREMENT, "
         + "userName VARCHAR,"//用户名
         + "nickName VARCHAR," //昵称
         + "sex VARCHAR,"       //性bie
                + "signature VARCHAR"//签名
                + ")" );
    }
    //数据库升级 版本号增加 升级调用此方法

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        db.execSQL("DROP TABLE IF NOT EXISTS " + U_USERINFO);
        onCreate(db);

    }
}
