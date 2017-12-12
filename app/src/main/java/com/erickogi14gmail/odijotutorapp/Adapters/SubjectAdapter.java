package com.erickogi14gmail.odijotutorapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.Data.Models.Subjects;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.Interfaces.clickListenerSubject;
import com.erickogi14gmail.odijotutorapp.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Eric on 10/11/2017.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.MyViewHolder> {

    PrefManager prefManager;
    private Context context;
    private ArrayList<Subjects> modelList;
    private clickListenerSubject listener;
    private int view;

    public SubjectAdapter(Context context, int view, ArrayList<Subjects> modelList, clickListenerSubject listener) {
        this.context = context;
        this.modelList = modelList;
        this.listener = listener;
        this.view = view;
        prefManager = new PrefManager(context);
    }

    @Override
    public SubjectAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_item, parent, false);
        return new MyViewHolder(itemView, view, listener);
    }

    @Override
    public void onBindViewHolder(SubjectAdapter.MyViewHolder holder, int position) {
        Subjects subjects = modelList.get(position);


        holder.txtSubject.setText(subjects.getSubject_name());
        if (view == 1) {
            //ArrayList<Subjects> subjectses=prefManager.getSubjects();
            //for(int a=0;a<subjectses.size();a++){
            //    if(subjectses.get(a).getSubject_id()==subjects.getSubject_id()&&subjectses.get(a).getSubject_name().equals(subjects.getSubject_name())){
            // holder.btnRemove.setText("Added");
            //       holder.btnRemove.setEnabled(false);
            //   }
            //  else {
            holder.btnRemove.setText("Add");
            //  }
            // }


        }


    }

    public void updateList(ArrayList<Subjects> list) {
        modelList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return (null != modelList ? modelList.size() : 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtSubject;
        private Button btnRemove;
        private WeakReference<clickListenerSubject> listenerWeakReference;
        private int view;

        public MyViewHolder(View itemView, int view, clickListenerSubject listener) {
            super(itemView);
            listenerWeakReference = new WeakReference<clickListenerSubject>(listener);

            txtSubject = itemView.findViewById(R.id.txt_subject);
            btnRemove = itemView.findViewById(R.id.btn_remove);

            this.view = view;

            btnRemove.setOnClickListener(this);


        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            if (this.view == 0) {
                listenerWeakReference.get().onBtnDeleteClicked(getAdapterPosition());
            } else {
                listenerWeakReference.get().onBtnAddClicked(getAdapterPosition());
            }

        }
    }
}
