package com.erickogi14gmail.odijotutorapp.Views.Profile;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.erickogi14gmail.odijotutorapp.Adapters.SubjectAdapter;
import com.erickogi14gmail.odijotutorapp.Data.Models.Subjects;
import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.Interfaces.clickListenerSubject;
import com.erickogi14gmail.odijotutorapp.R;

import java.util.ArrayList;

/**
 * Created by Eric on 11/27/2017.
 */

public class DialogSubjects extends android.support.v4.app.DialogFragment {


    private PrefManager prefManager;

    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private SubjectAdapter subjectAdapter;
    private int type = 1;
    private Button buttondismiss;

    public DialogSubjects() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DialogSubjects newInstance(String title, int type, ArrayList<Subjects> subjectses1) {
        DialogSubjects frag = new DialogSubjects();
        Bundle args = new Bundle();
        // args.putSerializable("data", productModels);
        args.putString("title", title);
        args.putInt("type", type);
        args.putSerializable("data", subjectses1);
        frag.setArguments(args);
        return frag;
    }

    public void sendBackResult(Subjects model) {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        DialogSearchListener listener = (DialogSearchListener) getActivity();
        listener.onSelected(model);
        dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);

        // inflate and adjust layout
        // LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // View layout = inflater.inflate(R.layout.your_dialog_layout, null);
        View view = inflater.inflate(R.layout.dialog_subject, container);
        view.setBackgroundColor(getContext().getResources().getColor(R.color.white));
        view.setMinimumWidth((int) (displayRectangle.width() * 0.8f));
        view.setMinimumHeight((int) (displayRectangle.height() * 0.8f));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefManager = new PrefManager(getActivity());
        type = getArguments().getInt("type");

        buttondismiss = view.findViewById(R.id.btn_back);
        buttondismiss.setOnClickListener(v -> dismiss());
        // Get field from view
        recyclerView = view.findViewById(R.id.recycler_view);
        ArrayList<Subjects> productModels = (ArrayList<Subjects>) getArguments().getSerializable("data");
        for (Subjects model : productModels) {
            // Log.d("dla", "" + productModel.getProduct_name());
        }


        subjectAdapter = new SubjectAdapter(getActivity(), 1, productModels, new clickListenerSubject() {
            @Override
            public void onBtnDeleteClicked(int pos) {


            }

            @Override
            public void onBtnAddClicked(int adapterPosition) {


                sendBackResult(productModels.get(adapterPosition));
            }
        });

        subjectAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(subjectAdapter);

//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView,
//                new RecyclerTouchListener.ClickListener() {
//                    @Override
//                    public void onClick(View view, int position) {
//
//                        sendBackResult(productModels.get(position));
//                    }
//
//                    @Override
//                    public void onLongClick(View view, int position) {
//
//                    }
//                }));


        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Search");
        getDialog().setTitle(title);

    }

    public interface DialogSearchListener {
        void onSelected(Subjects model);
    }
}
