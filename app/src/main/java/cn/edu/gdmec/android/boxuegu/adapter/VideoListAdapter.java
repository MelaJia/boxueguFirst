package cn.edu.gdmec.android.boxuegu.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.boxuegu.R;
import cn.edu.gdmec.android.boxuegu.bean.VideoBean;

/**
 * Created by student on 17/12/27.
 */

public class VideoListAdapter extends BaseAdapter {

    private Context context;
    private List<VideoBean> vbl;  //视频列表数据
    private int selectedPosition = -1; //点击时选中位置
    private OnSelectListener onSelectListener;


    public VideoListAdapter(Context context, OnSelectListener onSelectListener) {
        this.context = context;
        this.onSelectListener = onSelectListener;

    }

    /**
     * 设置数据，更新界面
     */
    public void setSelectedPosition(int position) {
        this.selectedPosition = position;

    }

    public void setData(List<VideoBean> vbl) {
        this.vbl = vbl;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return vbl == null ? 0 : vbl.size();
    }

    @Override
    public VideoBean getItem(int i) {
        return vbl == null ? null : vbl.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.video_list_item, null);
            vh.tv_title = convertView.findViewById(R.id.tv_video_title);
            vh.iv_icon = convertView.findViewById(R.id.lv_left_icon);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final VideoBean bean = getItem(position);
        vh.iv_icon.setImageResource(R.drawable.course_bar_icon);
        vh.tv_title.setTextColor(Color.parseColor("#333333"));
        if (bean != null) {
            vh.tv_title.setText(bean.secondTitle);
            if (selectedPosition == position) {
                vh.iv_icon.setImageResource(R.drawable.course_intro_icon);
                vh.tv_title.setTextColor(Color.parseColor("#009958"));
            } else {
                vh.iv_icon.setImageResource(R.drawable.course_bar_icon);
                vh.tv_title.setTextColor(Color.parseColor("#333333"));
            }
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bean == null) {
                    return;
                }
                //播放视频
                onSelectListener.onSelect(position, vh.iv_icon);
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView tv_title;
        public ImageView iv_icon;

    }

    /**
     * 创建OnSelectListener接口把位置控件传递activity实例
     */
    public interface OnSelectListener {
        void onSelect(int position, ImageView iv);
    }
}
