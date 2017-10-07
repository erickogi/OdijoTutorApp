package com.erickogi14gmail.odijotutorapp.Messaging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Eric on 9/27/2017.
 */

public class ChatThreadAdapter extends RecyclerView.Adapter<ChatThreadAdapter.MyViewHolder>{
    private Context context;
    private ArrayList<ChatThreadPojo> modelList;
    private ChatThredClickListener listener;
    private int viewType;

    public ChatThreadAdapter(Context context, ArrayList<ChatThreadPojo> modelList, ChatThredClickListener listener) {
        this.context = context;
        this.modelList = modelList;
        this.listener = listener;
    }

    @Override
    public ChatThreadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;

        if (viewType == 1){
            this.viewType=viewType;
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_message_friend, parent, false);
        }
        else{
            this.viewType=viewType;
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item_message_user, parent, false);

        }
        return new MyViewHolder(itemView,listener);
    }


    @Override
    public void onBindViewHolder(ChatThreadAdapter.MyViewHolder holder, int position) {
        ChatThreadPojo chatPojo=modelList.get(position);

        holder.txtMessage.setText(chatPojo.getMessage_text());
    }


    @Override
    public int getItemCount() {
        return (null != modelList ? modelList.size() : 0);
    }
    @Override
    public int getItemViewType(int position) {

        if((modelList.get(position).getMessage_from()&1)==0){
            return 0;
        }else {
            return 1;
        }


      //  return modelList.get(position).getMessage_from();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {

        TextView txtMessage;
        private WeakReference<ChatThredClickListener> listenerWeakReference;
        private LinearLayout linearLayout;
        public MyViewHolder(View itemView,ChatThredClickListener listener) {
            super(itemView);
            listenerWeakReference=new WeakReference<ChatThredClickListener>(listener);
            txtMessage=(TextView)itemView.findViewById(R.id.textContent);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear_layout);
            linearLayout.setOnClickListener(this);
            linearLayout.setOnLongClickListener(this);

        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            listenerWeakReference.get().onChatItemClicked(getAdapterPosition(),v);
        }

        /**
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         * @return true if the callback consumed the long click, false otherwise.
         */
        @Override
        public boolean onLongClick(View v) {
            listenerWeakReference.get().onChatItemLongClicked(getAdapterPosition(),v);
            return false;
        }
    }
}
