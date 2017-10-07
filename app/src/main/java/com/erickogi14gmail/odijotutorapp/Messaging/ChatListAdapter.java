package com.erickogi14gmail.odijotutorapp.Messaging;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created by Eric on 9/27/2017.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {
    private Context context;
    private QBadgeView qBadgeView;
    private ArrayList<ChatsListPojo> modelList;
    private ChatListItemClickListener listener;

    public ChatListAdapter(Context context, ArrayList<ChatsListPojo> modelList,ChatListItemClickListener listener) {
        this.context = context;
        this.modelList = modelList;
        this.listener=listener;
    }

    @Override
    public ChatListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item ,parent, false);
        return new MyViewHolder(itemView,listener);
    }


    @Override
    public void onBindViewHolder(ChatListAdapter.MyViewHolder holder, int position) {
      ChatsListPojo chatsListPojo=modelList.get(position);
        holder.txtName.setText(chatsListPojo.getOdijo_name()+"         ");
        holder.txtDate.setText(chatsListPojo.getLast_chat_date());
        holder.txtLastMessage.setText(chatsListPojo.getLast_chat()+"    ");
        qBadgeView=new QBadgeView(context);

        qBadgeView.bindTarget(holder.txtName).setBadgeNumber(chatsListPojo.getNo_of_chats())
                .setBadgeGravity(Gravity.CENTER| Gravity.END)
                .setBadgeBackgroundColor(context.getResources().getColor(R.color.colorPrimaryP));


    }


    @Override
    public int getItemCount() {
        return (null != modelList ? modelList.size() : 0);
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener,View.OnLongClickListener{

        private TextView txtName,txtDate,txtLastMessage;
        private ImageView img_profile;
        private RelativeLayout relativeLayoutChat;
        private WeakReference<ChatListItemClickListener> listenerWeakReference;

        public MyViewHolder(View itemView,ChatListItemClickListener listener) {
            super(itemView);
            listenerWeakReference=new WeakReference<ChatListItemClickListener>(listener);

            txtName=(TextView)itemView.findViewById(R.id.txt_odijo_name);
            txtDate=(TextView)itemView.findViewById(R.id.txt_last_date);
            txtLastMessage=(TextView)itemView.findViewById(R.id.txt_last_message);
            img_profile=(ImageView)itemView.findViewById(R.id.profile_image);
            relativeLayoutChat=(RelativeLayout)itemView.findViewById(R.id.relative_chat);
            relativeLayoutChat.setOnClickListener(this);
            relativeLayoutChat.setOnLongClickListener(this);
            img_profile.setOnClickListener(this);
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.profile_image){
                listenerWeakReference.get().onProfileImage(getAdapterPosition());
            }else if(v.getId()==R.id.relative_chat){
                listenerWeakReference.get().onChatClicked(getAdapterPosition());
            }
        }

        /**
         * Called when a view has been clicked and held.
         *
         * @param v The view that was clicked and held.
         * @return true if the callback consumed the long click, false otherwise.
         */
        @Override
        public boolean onLongClick(View v) {
            if(v.getId()==R.id.relative_chat){
                listenerWeakReference.get().onChatLongClicked(getAdapterPosition(),v);
            }
            return false;
        }
    }
}
