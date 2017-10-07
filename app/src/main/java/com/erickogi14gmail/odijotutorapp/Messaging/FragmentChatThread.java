package com.erickogi14gmail.odijotutorapp.Messaging;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erickogi14gmail.odijotutorapp.R;

import java.util.ArrayList;

/**
 * Created by Eric on 9/27/2017.
 */

public class FragmentChatThread extends Fragment {
    private View view;
    private Fragment fragment = null;
    private ArrayList<ChatsListPojo> chatsListPojo=new ArrayList<>();

    RecyclerView recyclerView;
    private int pos;

    private ChatThreadAdapter chatThreadAdapter;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    void setView(){
        getActivity().setTitle(Html.fromHtml("<font color ='#142170'>'"+chatsListPojo.get(pos).getOdijo_name()+"' </font>"));
       // getActivity().setTitle(chatsListPojo.get(pos).getOdijo_name());
        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_person_black_24dp);
        //getActivity().getActionBar().setLogo(R.drawable.p);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerChat);
        chatThreadAdapter=new ChatThreadAdapter(getActivity(), chatsListPojo.get(pos).getChatThreadPojos(), new ChatThredClickListener() {
            @Override
            public void onChatItemClicked(int position, View view) {

            }

            @Override
            public void onChatItemLongClicked(int position, View view) {

            }
        });
        chatThreadAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatThreadAdapter);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_chat_thread,container,false);
      FloatingActionButton floatingActionButton=(FloatingActionButton) getActivity().findViewById(R.id.fab);
        floatingActionButton.hide();

        chatsListPojo=(ArrayList<ChatsListPojo>) getArguments().getSerializable("data");
        pos=getArguments().getInt("position");

        setView();



        return view;
    }
}
