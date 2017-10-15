package com.erickogi14gmail.odijotutorapp.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private ClickListenerRequestCard listener;

    public RequestAdapter(Context context, ArrayList<RequestPojo> modelList, ClickListenerRequestCard listener) {
        this.context = context;
        this.modelList = modelList;
        this.listener = listener;
    }

    @Override
    public RequestAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item,parent, false);
        return new MyViewHolder(itemView,listener);
    }


    @Override
    public void onBindViewHolder(RequestAdapter.MyViewHolder holder, int position) {
        RequestPojo requestPojo=modelList.get(position);
        holder.txtClientName.setText(requestPojo.getStudent_name());
        holder.txtClientZone.setText(requestPojo.getZone());
        holder.txtRequestDate.setText(requestPojo.getDate());
        holder.txtRequestTime.setText(requestPojo.getTime());
        holder.txtSubject.setText(requestPojo.getSubject_name());

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
        private TextView txtClientName, txtClientZone, txtRequestDate, txtRequestTime, txtSubject;
        private ImageView imgClientProfile,imgMore;
        private Button btnAccept,btnDecline;
        private WeakReference<ClickListenerRequestCard> listenerWeakReference;


        public MyViewHolder(View itemView,ClickListenerRequestCard listener) {
            super(itemView);
            listenerWeakReference=new WeakReference<ClickListenerRequestCard>(listener);
            txtSubject = (TextView) itemView.findViewById(R.id.txt_subject);

            txtClientName=(TextView)itemView.findViewById(R.id.txt_client_name);
            txtClientZone=(TextView)itemView.findViewById(R.id.txt_location);
            txtRequestDate=(TextView)itemView.findViewById(R.id.txt_date);
            txtRequestTime=(TextView)itemView.findViewById(R.id.txt_time);

            btnAccept=(Button)itemView.findViewById(R.id.btn_accept);
            btnDecline=(Button)itemView.findViewById(R.id.btn_decline);

            imgClientProfile=(ImageView)itemView.findViewById(R.id.img_clent_image);
            imgMore=(ImageView)itemView.findViewById(R.id.icon_more);



            btnDecline.setOnClickListener(this);
            btnAccept.setOnClickListener(this);

            imgClientProfile.setOnClickListener(this);
            imgMore.setOnClickListener(this);
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
