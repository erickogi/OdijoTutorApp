package com.erickogi14gmail.odijotutorapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.Data.Models.RequestPojo;
import com.erickogi14gmail.odijotutorapp.Main.ClickListenerRequestCard;
import com.erickogi14gmail.odijotutorapp.R;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Eric on 9/27/2017.
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<RequestPojo> modelList;
    private int status;
    private ClickListenerRequestCard listener;

    public RequestAdapter(Context context, ArrayList<RequestPojo> modelList, int status) {
        this.context = context;
        this.modelList = modelList;
        // this.listener = listener;
        this.status = status;
    }

    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(RequestAdapter.MyViewHolder holder, int position) {
        RequestPojo requestPojo=modelList.get(position);
        holder.txtClientName.setText(requestPojo.getStudent_name());
        holder.txtClientZone.setText(requestPojo.getZone());
        holder.txtRequestDate.setText(requestPojo.getDate());
        holder.txtRequestTime.setText(requestPojo.getTime());
        holder.txtSubject.setText(requestPojo.getSubject_name());
        if (status == 2) {
            if (requestPojo.getStatus() == 2) {
                holder.txtClientName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.rejecteds, 0);

            } else {
                holder.txtClientName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.completeds, 0);
            }
        }

        if (requestPojo.getSession_status() > 0) {
            holder.txtSession.setVisibility(View.VISIBLE);
        }
        Picasso.with(context).load(requestPojo.getStudent_image()).resize(60, 60).centerCrop().error(R.drawable.ic_person_black_24dp).into(holder.imgClientProfile);
        if(requestPojo.getStatus()==0){
            holder.btnAccept.setEnabled(true);
            holder.btnDecline.setEnabled(true);
        }else if(requestPojo.getStatus()==1){
            holder.btnAccept.setText("ACCEPTED");
            holder.btnDecline.setVisibility(View.GONE);
        }else if(requestPojo.getStatus()==2){
            holder.btnAccept.setVisibility(View.GONE);
            holder.btnDecline.setText("DECLINED");
        }






    }

    public void updateList(ArrayList<RequestPojo> list) {
        modelList = list;
        notifyDataSetChanged();

    }
    @Override
    public int getItemCount() {
        return (null != modelList ? modelList.size() : 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtClientName, txtClientZone, txtRequestDate, txtRequestTime, txtSubject, txtSession;
        private ImageView imgClientProfile,imgMore;
        private Button btnAccept,btnDecline;
        private WeakReference<ClickListenerRequestCard> listenerWeakReference;


        public MyViewHolder(View itemView) {
            super(itemView);
            // listenerWeakReference=new WeakReference<ClickListenerRequestCard>(listener);
            txtSubject = itemView.findViewById(R.id.txt_subject);

            txtSession = itemView.findViewById(R.id.session);
            txtClientName = itemView.findViewById(R.id.txt_client_name);
            txtClientZone = itemView.findViewById(R.id.txt_location);
            txtRequestDate = itemView.findViewById(R.id.txt_date);
            txtRequestTime = itemView.findViewById(R.id.txt_time);

            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnDecline = itemView.findViewById(R.id.btn_decline);

            imgClientProfile = itemView.findViewById(R.id.img_clent_image);
            imgMore = itemView.findViewById(R.id.icon_more);


//
//            btnDecline.setOnClickListener(this);
//            btnAccept.setOnClickListener(this);
//
//            imgClientProfile.setOnClickListener(this);
//            imgMore.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_accept:
                    if(btnAccept.getText().toString().equals("ACCEPTED")){
                        //  btnAccept.setEnabled(false);
                        listenerWeakReference.get().onBtnAcceptClicked(getAdapterPosition(), btnAccept, btnDecline);

                    }else {
                        int a = modelList.get(getAdapterPosition()).getId();

                        listenerWeakReference.get().onBtnAcceptClicked(getAdapterPosition(), btnAccept, btnDecline);
                    }
                    break;
                case R.id.btn_decline:
                    if(btnDecline.getText().toString().equals("DECLINED")){
                        // btnDecline.setEnabled(false);
                    }else {
                        listenerWeakReference.get().onBtnDeclineClicked(getAdapterPosition(), btnDecline, btnAccept);
                    }
                    break;
                case R.id.img_clent_image:
                    listenerWeakReference.get().onClientImageClicked(getAdapterPosition());
                    break;
                case R.id.icon_more:
                    listenerWeakReference.get().onIconMoreClicked(getAdapterPosition(),v);
                    break;
            }

        }
    }
}
