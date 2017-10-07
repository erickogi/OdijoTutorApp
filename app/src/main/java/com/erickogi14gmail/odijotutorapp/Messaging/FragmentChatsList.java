package com.erickogi14gmail.odijotutorapp.Messaging;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

public class FragmentChatsList extends Fragment {
    private View view;
    private Fragment fragment = null;
    private ArrayList<ChatsListPojo> chatsListPojo=new ArrayList<>();

    RecyclerView recyclerView;

    private ChatListAdapter chatsListAdapter;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    private void setView(){
        getActivity().setTitle(Html.fromHtml("<font color ='#142170'>Messages </font>"));
        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbar.setLogo(null);
       // getActivity().setTitle("Chats");
        recyclerView=(RecyclerView)view.findViewById(R.id.recycleView);
        chatsListAdapter=new ChatListAdapter(getContext(), chatsListPojo, new ChatListItemClickListener() {
            @Override
            public void onProfileImage(int position) {

            }

            @Override
            public void onChatClicked(int position) {
                fragment=new FragmentChatThread();
                Bundle args = new Bundle();
                args.putSerializable("data",chatsListPojo);
                args.putInt("position",position);


                fragment.setArguments(args);
                popOutFragments();
                setUpView();
            }

            @Override
            public void onChatLongClicked(int position, View view) {

            }
        });
        chatsListAdapter.notifyDataSetChanged();
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mStaggeredLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(chatsListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_chat_list,container,false);


        chatsListPojo=(ArrayList<ChatsListPojo>) getArguments().getSerializable("data");

        setView();


        return view;






    }

    void setUpView() {
        if (fragment != null) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                    .addToBackStack(null).commit();
        }

    }

    void popOutFragments() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }
}
