package cn.edu.gdmec.android.boxuegu;

import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdmec.android.boxuegu.bean.VideoBean;
import cn.edu.gdmec.android.boxuegu.utils.DBUtils;

public class VideoListItemAdapter extends BaseAdapter {

    private List<VideoBean> objects = new ArrayList<VideoBean>();
    private int setSelectPosition=-1;
    private DBUtils db;

    private Context context;
    private LayoutInflater layoutInflater;

    public VideoListItemAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        db=DBUtils.getInstance(context);


    }
    public void setData(List<VideoBean> vbl){
        this.objects=vbl;
        notifyDataSetChanged();

    }


    @Override
    public int getCount() {
        return objects.size();
    }
    public void setSelectPosition(int position){
        setSelectPosition=position;
    }

    @Override
    public VideoBean getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.video_list_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews((VideoBean)getItem(position), (ViewHolder) convertView.getTag(),position,convertView);
        return convertView;
    }

    private void initializeViews(final VideoBean object, ViewHolder holder,final int position,View convertView) {
        //TODO implement
        holder.tvVideoTitle.setTextColor(Color.parseColor("#333333"));
        holder.lvLeftIcon.setImageResource(R.drawable.course_bar_icon);
        if (object!=null){
            holder.tvVideoTitle.setText(object.secondTitle);
            if (setSelectPosition==position){
                holder.lvLeftIcon.setImageResource(R.drawable.course_intro_icon);
                holder.tvVideoTitle.setTextColor(Color.parseColor("#009958"));
            }else {

            }
        }
    }


    protected class ViewHolder {
        private ImageView lvLeftIcon;
    private TextView tvVideoTitle;

        public ViewHolder(View view) {
            lvLeftIcon = (ImageView) view.findViewById(R.id.lv_left_icon);
            tvVideoTitle = (TextView) view.findViewById(R.id.tv_video_title);
        }
    }
}
