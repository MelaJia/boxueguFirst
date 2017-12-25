package cn.edu.gdmec.android.boxuegu.bean;

/**
 * Created by student on 17/12/25.
 * 练习题实体类
 */

public class ExercisesBean {
    public int id;//每章习题id
    public String title;// 每章习题标题
    public String content;// 每章习题的数目
    public int background;//每章习题前边的序号背景
    public int subjectId;//每章习题的id
    public String subject;//每道习题的题干
    public String a;//每道题的a选项
    public String b;//每道题的b选项
    public String c;//每道题的c选项
    public String d;//每道题的d选项
    public int answer;//每道题的正确答案
    public int select;//用户选中的那项（0表示对，1表示a选项错，2是b错，3是c错，4是d错）
}
