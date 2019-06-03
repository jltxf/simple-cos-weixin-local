package com.example.mywechat.util;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.DAO.Friend;
import com.example.mywechat.DAO.Information;
import com.example.mywechat.DAO.Msg;
import com.example.mywechat.R;

import java.util.List;
import java.util.TimeZone;

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    private List<Msg> mMsgList;
    private List<Friend> mFriendList;
    private ClickInterface clickInterface;
    private int imageId = 0;
    private String dateTime;

    private long current = System.currentTimeMillis();//当前时间毫秒数
    private long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();//今天零点零分零秒的毫秒数
    private long twelve = zero + 24 * 60 * 60 * 1000 - 1;//今天23点59分59秒的毫秒数

    //--------------------点击事件-------------------------------------------//
    public void setOnclick(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    //回调接口
    public interface ClickInterface {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name, tv_introduce, tv_time;
        private ImageView iv_photo;
        View msgView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msgView = itemView;
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
            tv_introduce = (TextView) itemView.findViewById(R.id.tv_introduce);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
        }
    }


    public MsgAdapter(List<Msg> msgList, List<Friend> friendList) {
        mMsgList = msgList;
        mFriendList = friendList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup
                                                 parent, int viewType) {
        final Msg msg = mMsgList.get(viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_msg, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (position == 8) {
            return;
        }
        Msg msg = mMsgList.get(position);
        if (msg.getM_time() <= twelve || msg.getM_time() >= zero) {
            dateTime = msg.getM_date();
        } else if (msg.getM_time() <= zero || msg.getM_time() >= zero - 86400000) {
            dateTime = "昨天";
        } else {
            dateTime = msg.getM_date2();
        }
        viewHolder.tv_name.setText(msg.getM_friend());
        viewHolder.tv_time.setText(dateTime);
        viewHolder.tv_introduce.setText(msg.getM_msg());
        viewHolder.iv_photo.setImageResource(mFriendList.get(position).getImageId());
        viewHolder.msgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickInterface != null) {
                    clickInterface.onItemClick(v, position);
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }

}
