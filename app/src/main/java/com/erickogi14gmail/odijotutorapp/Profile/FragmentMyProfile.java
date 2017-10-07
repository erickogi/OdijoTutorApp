package com.erickogi14gmail.odijotutorapp.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.erickogi14gmail.odijotutorapp.R;

/**
 * Created by Eric on 9/15/2017.
 */

public class FragmentMyProfile extends Fragment {
    private View view;
    private Button buttonPhone, buttonMail,btnZone,btnSubjects;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my_profile,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        buttonMail = (Button) view.findViewById(R.id.btn_mail);
        buttonPhone = (Button) view.findViewById(R.id.btn_phone);
        btnZone=(Button)view.findViewById(R.id.btn_zone);
        btnSubjects=(Button)view.findViewById(R.id.btn_course);


        //buttonMail.s

        try {
            buttonPhone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_phone_iphone_black_24dp, 0, 0, 0);
            buttonMail.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_email_black_24dp, 0, 0, 0);
            btnZone.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_my_location_black_24dp, 0, 0, 0);
            btnSubjects.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_import_contacts_black_24dp, 0, 0, 0);

        } catch (Exception nm) {

        }
        return view;
    }
}
