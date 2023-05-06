package com.example.chatgptapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessegeAdapter extends  RecyclerView.Adapter<MessegeAdapter.MyViewHolder> {

    List<Messege> messegeList;

    public MessegeAdapter(List<Messege> messegeList) {
        this.messegeList = messegeList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatview = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,null);
        MyViewHolder myViewHolder= new MyViewHolder(chatview);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Messege messege=messegeList.get(position);
        if (messege.getSentBy().equals(Messege.SENT_BY_ME)){
            holder.leftchatview.setVisibility(View.GONE);
            holder.rightchatview.setVisibility(View.VISIBLE);
            holder.righttextview.setText(messege.getMessege());
        }else {
            holder.rightchatview.setVisibility(View.GONE);
            holder.leftchatview.setVisibility(View.VISIBLE);
            holder.lefttextview.setText(messege.getMessege());
        }
    }

    @Override
    public int getItemCount() {
        return messegeList.size();
    }

    public  class  MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftchatview,rightchatview;
        TextView lefttextview,righttextview;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            leftchatview=itemView.findViewById(R.id.left_chat);
            rightchatview=itemView.findViewById(R.id.right_chat);
            lefttextview=itemView.findViewById(R.id.tvReceiver);
            righttextview=itemView.findViewById(R.id.tvSender);
        }
    }
}
