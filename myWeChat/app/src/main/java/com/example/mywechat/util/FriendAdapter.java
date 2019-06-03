package com.example.mywechat.util;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mywechat.DAO.Friend;
import com.example.mywechat.DAO.Msg;
import com.example.mywechat.R;

import java.util.List;
import java.util.TimeZone;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {
    private List<Friend> mFriendList;
    private ClickInterface clickInterface;

    //--------------------点击事件-------------------------------------------//
    public void setOnclick(ClickInterface clickInterface) {
        this.clickInterface = clickInterface;
    }

    //回调接口
    public interface ClickInterface {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fri;
        private ImageView img_fri;
        View FriendView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FriendView = itemView;
            img_fri = (ImageView) itemView.findViewById(R.id.img_fri);
            tv_fri = (TextView) itemView.findViewById(R.id.tv_fri);
        }
    }


    public FriendAdapter(List<Friend> friendList) {
        mFriendList = friendList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Friend friend = mFriendList.get(viewType);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        Friend friend = mFriendList.get(position);
        viewHolder.tv_fri.setText(friend.getWeChatName());
        viewHolder.img_fri.setImageResource(friend.getImageId());
        viewHolder.FriendView.setOnClickListener(new View.OnClickListener() {
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
        return mFriendList.size();
    }

}
