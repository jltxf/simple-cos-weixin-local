package com.example.mywechat.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mywechat.ChatActiivty;
import com.example.mywechat.DAO.Information;
import com.example.mywechat.R;

import java.util.List;

public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.ViewHolder> {
    private List<Information> mInformationList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout left_layout;
        private LinearLayout right_layout;
        private TextView left_msg;
        private TextView right_msg;
        private ImageView left_image;
        private ImageView right_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            left_layout = (LinearLayout) itemView.findViewById(R.id.left_layout);
            right_layout = (LinearLayout) itemView.findViewById(R.id.right_layout);
            left_msg = (TextView) itemView.findViewById(R.id.left_msg);
            right_msg = (TextView) itemView.findViewById(R.id.right_msg);
            left_image =(ImageView)itemView.findViewById(R.id.left_image);
            right_image =(ImageView)itemView.findViewById(R.id.right_image);
        }
    }

    public InformationAdapter(List<Information> informationList) {
        mInformationList = informationList;

    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_information, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //获取单个msg
        Information information = mInformationList.get(position);
        if(ChatActiivty.getmMenu().getImageId()==0){
            ChatActiivty.getmMenu().setImageId(R.drawable.menu_mu);
        }

        //对holider的控件赋值
        if (information.getType() == Information.TYPE_RECEIVED) {
            // 如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
            viewHolder.left_layout.setVisibility(View.VISIBLE);//隐藏
            viewHolder.right_layout.setVisibility(View.GONE);
            viewHolder.left_msg.setText(
                    information.getContent());
            viewHolder.left_image.setImageResource(ChatActiivty.getmFriend().getImageId());
        } else if(information.getType() == Information.TYPE_SENT) {
            // 如果是发出的消息，则显示右边的消息布局，将左边的消息布局隐藏
            viewHolder.left_layout.setVisibility(View.GONE);//隐藏
            viewHolder.right_layout.setVisibility(View.VISIBLE);
            viewHolder.right_msg.setText(information.getContent());
            viewHolder.right_image.setImageResource(ChatActiivty.getmMenu().getImageId());
        }
    }





    @Override
    public int getItemCount() {
        return mInformationList.size();
    }

}

