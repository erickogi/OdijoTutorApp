package com.erickogi14gmail.odijotutorapp.Messaging;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.erickogi14gmail.odijotutorapp.R;

import java.util.ArrayList;

public class MessagingActivity extends AppCompatActivity {
    public static Fragment fragment = null;



    private ArrayList<ChatsListPojo> testData(){
        ArrayList<ChatsListPojo> chatsListPojos=new ArrayList<>();
        ArrayList<ChatThreadPojo> chatThreadPojos=new ArrayList<>();
        for(int a=0;a<14;a++){
            ChatThreadPojo chatThreadPojo1=new ChatThreadPojo();
            chatThreadPojo1.setMessage_id(0);
            chatThreadPojo1.setMessage_id(a);
            chatThreadPojo1.setMessage_from(a);
            chatThreadPojo1.setMessage_text(this.getResources().getString(R.string.lorem_ipsum));
            chatThreadPojos.add(chatThreadPojo1);
        }



        ChatsListPojo chatsListPojo=new ChatsListPojo();
        chatsListPojo.setOdijo_key("odijo1");
        chatsListPojo.setStudent_key("student1");
        chatsListPojo.setOdijo_name("John Mark");
        chatsListPojo.setLast_chat(getString(R.string.lorem_ipsum));
        chatsListPojo.setNo_of_chats(1);
        chatsListPojo.setChatThreadPojos(chatThreadPojos);
        chatsListPojo.setLast_chat_date("9/24/2017");

        ChatsListPojo chatsListPojo0=new ChatsListPojo();
        chatsListPojo0.setOdijo_key("odijo1");
        chatsListPojo0.setStudent_key("student1");
        chatsListPojo0.setOdijo_name("Mr Clinton Juma");
        chatsListPojo0.setLast_chat(getString(R.string.lorem_ipsum));
        chatsListPojo0.setNo_of_chats(0);
        chatsListPojo0.setLast_chat_date("9/24/2017");
        chatsListPojo0.setChatThreadPojos(chatThreadPojos);

        ChatsListPojo chatsListPojo1=new ChatsListPojo();
        chatsListPojo1.setOdijo_key("odijo1");
        chatsListPojo1.setStudent_key("student1");
        chatsListPojo1.setOdijo_name("Robert M");
        chatsListPojo1.setLast_chat(getString(R.string.lorem_ipsum));
        chatsListPojo1.setNo_of_chats(1);
        chatsListPojo1.setLast_chat_date("9/24/2017");
        chatsListPojo1.setChatThreadPojos(chatThreadPojos);

        ChatsListPojo chatsListPojo2=new ChatsListPojo();
        chatsListPojo2.setOdijo_key("odijo1");
        chatsListPojo2.setLast_chat_date("9/24/2017");
        chatsListPojo2.setStudent_key("student1");
        chatsListPojo2.setOdijo_name("M.P patel");
        chatsListPojo2.setLast_chat(getString(R.string.lorem_ipsum));
        chatsListPojo2.setNo_of_chats(1);
        chatsListPojo2.setChatThreadPojos(chatThreadPojos);


        ChatsListPojo chatsListPojo3=new ChatsListPojo();
        chatsListPojo3.setOdijo_key("odijo1");
        chatsListPojo3.setLast_chat_date("9/24/2017");
        chatsListPojo3.setStudent_key("student1");
        chatsListPojo3.setOdijo_name("Justin M mwilu");
        chatsListPojo3.setLast_chat(getString(R.string.lorem_ipsum));
        chatsListPojo3.setNo_of_chats(0);
        chatsListPojo3.setChatThreadPojos(chatThreadPojos);







        for(int a=0;a<4;a++) {
            chatsListPojos.add(chatsListPojo);
            chatsListPojos.add(chatsListPojo0);
            chatsListPojos.add(chatsListPojo1);
            chatsListPojos.add(chatsListPojo2);
            chatsListPojos.add(chatsListPojo3);
        }









       return chatsListPojos;

    }











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        fragment=new FragmentChatsList();
        Bundle args = new Bundle();
        args.putSerializable("data",testData());


        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment, "fragmentChatsLists").commit();
       // popOutFragments();
       // setUpView();
    }

    void setUpView() {
        if (fragment != null) {
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                    .addToBackStack(null).commit();
        }

    }

    void popOutFragments() {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
            fragmentManager.popBackStack();
        }
    }

}
