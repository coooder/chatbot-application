package com.example.jiehui.chatrobot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiehui on 6/9/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ListData> lists = new ArrayList<>();

    private Context context;

    private RelativeLayout relativeLayout;

    private LayoutInflater inflater;



    public RecyclerViewAdapter(Context context, List<ListData> lists){
        this.context = context;
        this.lists = lists;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getItemViewType(int position) {
        if(lists.get(position).getFlag() == ListData.RECEIVER){
            return 0;
        } else{
            return 1;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == 0){
            relativeLayout = (RelativeLayout) LayoutInflater.from(context)
                    .inflate(R.layout.leftitem,parent,false);
            ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.image_left);
            String string = PreferenceUtils.getString(context,"image","");
            if(string.equals("")){}
            else if(string.equals("小灵真身")){
                imageView.setImageResource(R.mipmap.girlone);
            }
            else if(string.equals("绝世美女")){
                imageView.setImageResource(R.mipmap.boy);
            }
            else if(string.equals("少女")){
                imageView.setImageResource(R.mipmap.katong);
            }

            LeftHolder leftHolder = new LeftHolder(relativeLayout);
            return leftHolder;

        }
        if(viewType == 1){
            relativeLayout = (RelativeLayout) LayoutInflater.from(context)
                    .inflate(R.layout.rightitem,parent,false);
            ImageView imageView = (ImageView)relativeLayout.findViewById(R.id.image_right);
            String string = PreferenceUtils.getString(context,"key","");
            if(string.equals("")){}
            else if(string.equals("选择一")){
                imageView.setImageResource(R.mipmap.boytwo);
            }
            else if(string.equals("选择二")){
                imageView.setImageResource(R.mipmap.girltwo);
            }
            else if(string.equals("选择三")){
                imageView.setImageResource(R.mipmap.ic_rightitem);
            }

            RightHolder rightHolder = new RightHolder(relativeLayout);
            return rightHolder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof LeftHolder){
            LeftHolder leftHolder = (LeftHolder) holder;
            if(lists != null){
                ((LeftHolder) holder).LeftMessage.setText(lists.get(position).getContent());
            }

        } else if(holder instanceof RightHolder){
            RightHolder rightHolder = (RightHolder) holder;
            if(lists != null){
                ((RightHolder) holder).RightMessage.setText(lists.get(position).getContent());
            }
        }

    }

    @Override
    public int getItemCount() {
        if(lists != null){
            return lists.size();
        } else {
            return 0;
        }
    }

    public void addMessage(ListData listData){
        lists.add(listData);
        notifyItemInserted(lists.size());
    }


    class LeftHolder extends RecyclerView.ViewHolder {

        private TextView LeftMessage;

        public LeftHolder(View itemView) {
            super(itemView);
            LeftMessage = (TextView) itemView.findViewById(R.id.tv_left_message);
        }

    }

    class RightHolder extends RecyclerView.ViewHolder {

        private TextView RightMessage;

        public RightHolder(View itemView) {
            super(itemView);
            RightMessage = (TextView) itemView.findViewById(R.id.tv_right_message);
        }
    }


}
