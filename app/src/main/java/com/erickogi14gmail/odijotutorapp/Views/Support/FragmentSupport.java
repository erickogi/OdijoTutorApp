package com.erickogi14gmail.odijotutorapp.Views.Support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.erickogi14gmail.odijotutorapp.R;


/**
 * Created by Eric on 11/11/2017.
 */

public class FragmentSupport extends Fragment implements View.OnClickListener {
    private TextView txt_phone_1, txt_phone_2, txt_email;
    private View view;
    //private PrefManager prefManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_suppport, container, false);
        txt_phone_1 = view.findViewById(R.id.phone_no);
        txt_phone_2 = view.findViewById(R.id.phone_no_2);
        txt_email = view.findViewById(R.id.email);

        txt_email.setOnClickListener(this);
        txt_phone_2.setOnClickListener(this);
        txt_phone_1.setOnClickListener(this);
        //  prefManager=new PrefManager(getContext());

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone_no:
                String n0 = txt_phone_1.getText().toString();
                dialPhoneNumber(n0);
//                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse(n0));
//                startActivity(intent);

                break;

            case R.id.phone_no_2:
                String n1 = txt_phone_2.getText().toString();
                dialPhoneNumber(n1);
//                Intent intent1=new Intent(Intent.ACTION_CALL, Uri.parse(n1));
//                startActivity(intent1);

                break;


            case R.id.email:
                //String id=prefManager.getId();

                String email = txt_email.getText().toString();
                String[] emails = {email};
                composeEmail(emails, "Support -User Id ");
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
//                        "mailto",email, null));
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
//                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
//                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
        }

    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
