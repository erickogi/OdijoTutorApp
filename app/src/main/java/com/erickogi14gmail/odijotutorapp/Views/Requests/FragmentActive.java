package com.erickogi14gmail.odijotutorapp.Views.Requests;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.erickogi14gmail.odijotutorapp.Adapters.RequestAdapter;
import com.erickogi14gmail.odijotutorapp.Data.Models.RequestPojo;
import com.erickogi14gmail.odijotutorapp.Interfaces.RequestClickListener;
import com.erickogi14gmail.odijotutorapp.R;

import java.util.ArrayList;

/**
 * Created by Eric on 11/25/2017.
 */

public class FragmentActive extends Fragment {
    SwipeRefreshLayout swipe_refresh_layout;
    LinearLayout linearLayoutEmpty;
    private ArrayList<RequestPojo> requestpojos;
    private RequestAdapter requestAdapter;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_active_request, container, false);

    }

    public void refresh(ArrayList<RequestPojo> requestPojos) {
        requestAdapter.updateList(requestPojos);
    }

    public void updateList(ArrayList<RequestPojo> requestPojos) {
        requestAdapter.updateList(requestPojos);
        this.requestpojos = requestPojos;
        //linearLayoutEmpty.setVisibility(View.GONE);
        //recyclerView.setVisibility(View.VISIBLE);
        initViews();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestpojos = (ArrayList<RequestPojo>) getArguments().getSerializable("data");

        recyclerView = view.findViewById(R.id.recycler_view);
        linearLayoutEmpty = view.findViewById(R.id.empty_layout);

        initViews();

    }

    private void initViews() {
        requestAdapter = new RequestAdapter(getContext(), requestpojos, 1, new RequestClickListener() {
            @Override
            public void onCardClicke(int position) {
                Intent intent = new Intent(getActivity(), RequestDetails.class);
                intent.putExtra("data", requestpojos.get(position));
                startActivity(intent);
            }
        });
        requestAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(requestAdapter);

        if (requestAdapter.getItemCount() > 0) {
            linearLayoutEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            linearLayoutEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        try {
            final FloatingActionButton fab = getActivity().findViewById(R.id.fab);
            fab.hide();

        } catch (Exception nm) {

        }
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView,
//                new RecyclerTouchListener.ClickListener() {
//                    @Override
//                    public void onClick(View view, int position) {
//
//
//                        Intent intent = new Intent(getActivity(), RequestDetails.class);
//                        intent.putExtra("data", requestpojos.get(position));
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onLongClick(View view, int position) {
//
//                    }
//                }));


    }

    public void sendBackResult() {
        DialogSearchListener listener = (DialogSearchListener) getActivity();
        listener.onSelected();

    }

    public interface DataChangedListener {
        void onSelected(ArrayList<RequestPojo> requestpojos);
    }

    public interface DialogSearchListener {
        void onSelected();
    }
}
