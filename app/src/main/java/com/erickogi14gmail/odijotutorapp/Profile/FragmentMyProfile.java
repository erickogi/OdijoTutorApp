package com.erickogi14gmail.odijotutorapp.Profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.Helper.PrefManager;
import com.erickogi14gmail.odijotutorapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Eric on 9/15/2017.
 */

public class FragmentMyProfile extends Fragment {
    public static Fragment fragment = null;
    PrefManager prefManager;
    private View view;
    private Button buttonPhone, buttonMail, btnZone, btnSubjects, btnEdit;
    private TextView txtName;
    private ImageView img;
    private ArrayList<Subjects> subjectses;
    private String[] zones = {"Select Zone", "Westlands", "Karen", "Rongai", "Ngara"};
    private int zoneId = 2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my_profile,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


        prefManager = new PrefManager(getContext());
        final HashMap<String, String> hashMap = prefManager.getUserDetails();

        populate();
        buttonMail = (Button) view.findViewById(R.id.btn_mail);
        buttonPhone = (Button) view.findViewById(R.id.btn_phone);
        btnZone=(Button)view.findViewById(R.id.btn_zone);
        btnSubjects=(Button)view.findViewById(R.id.btn_course);
        btnEdit = (Button) view.findViewById(R.id.btn_profile_edit_profile);

        img = (ImageView) view.findViewById(R.id.imageView);
        String image = prefManager.getImg();
        //Toast.makeText(getContext(), ""+image, Toast.LENGTH_SHORT).show();

        if (!image.equals("null")) {
            img.setImageBitmap(getThumbnail(image));
        } else {

        }
        String subjects = "";
        try {
            for (int a = 0; a < subjectses.size(); a++) {
                subjects = (subjects + "/" + subjectses.get(a).getSubject_name());
            }
        } catch (NullPointerException nm) {

        }

        zoneId = 2;
        for (int a = 0; a < zones.length; a++) {
            if (hashMap.get("zone").equals(zones[a])) {
                zoneId = a;
            }
        }

        String zone = "Zone Not Set";

        btnSubjects.setText(subjects);
        btnZone.setText(hashMap.get("zone"));


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new FragmentEditProfile();

                Bundle args = new Bundle();
                args.putString("Name", hashMap.get("name"));
                args.putString("Email", hashMap.get("email"));
                args.putStringArray("Zones", zones);
                args.putInt("zone", zoneId);
                args.putString("mobile", hashMap.get("mobile"));
                args.putString("description", hashMap.get("description"));
                args.putString("work_hours", hashMap.get("work_hours"));
                args.putSerializable("data", subjectses);


                fragment.setArguments(args);
                // popOutFragments();
                setUpView();
            }
        });


        txtName = (TextView) view.findViewById(R.id.txt_name);


        buttonMail.setText(hashMap.get("email"));
        buttonPhone.setText(hashMap.get("mobile"));
        txtName.setText(hashMap.get("name"));



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

    public Bitmap getThumbnail(String filename) {
        Bitmap thumnail = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_person_black_24dp);
        try {
            File filepath = getContext().getFileStreamPath(filename);
            FileInputStream fi = new FileInputStream(filepath);
            thumnail = BitmapFactory.decodeStream(fi);

        } catch (Exception m) {
            m.printStackTrace();
        }
        return thumnail;
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

    private void populate() {
        try {
            subjectses = prefManager.getSubjects();
        } catch (NullPointerException nm) {
            subjectses = null;
        }
    }
}
