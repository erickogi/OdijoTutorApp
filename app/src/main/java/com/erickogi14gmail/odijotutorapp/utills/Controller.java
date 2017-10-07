package com.erickogi14gmail.odijotutorapp.utills;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import com.erickogi14gmail.odijotutorapp.R;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Created by Eric on 9/26/2017.
 */

public class Controller  {
    public void toast(String msg, Context context, int icon) {
        StyleableToast st = new StyleableToast(context, msg, Toast.LENGTH_SHORT);
        st.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));

        st.setTextColor(context.getResources().getColor(R.color.white));
        try {
            st.setIcon(icon);


            st.setMaxAlpha();
            st.show();
        }
        catch (Exception m){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
    public FloatingActionButton fab(Activity activity, boolean show, int image) {

        final FloatingActionButton fab = (FloatingActionButton) activity.findViewById(R.id.fab);

        if (show) {
            fab.setImageResource(image);
            fab.show();
        } else {
            fab.setImageResource(image);
            fab.hide();
        }
        return fab;
    }
}
