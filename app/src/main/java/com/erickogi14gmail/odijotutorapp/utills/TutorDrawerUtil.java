package com.erickogi14gmail.odijotutorapp.utills;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;

import com.erickogi14gmail.odijotutorapp.Interfaces.DrawerItemListener;
import com.erickogi14gmail.odijotutorapp.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialize.holder.ColorHolder;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

/**
 * Created by Eric on 10/25/2017.
 */

public class TutorDrawerUtil {
    static Drawer result;
    private Activity activity;

    private static Bitmap getBitmap(Activity activity, String img) {


        Bitmap thumnail = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_person_black_24dp);
        try {
            File filepath = activity.getFileStreamPath(img);
            FileInputStream fi = new FileInputStream(filepath);
            thumnail = BitmapFactory.decodeStream(fi);

        } catch (Exception m) {
            m.printStackTrace();
        }
        return thumnail;

    }

    // private static Typeface getTypeface(Context context){
    // Typeface typeface;
    // AssetManager am = context.getApplicationContext().getAssets();

    // typeface = Typeface.createFromAsset(am,
    //      String.format(Locale.US, "fonts/%s", "UbuntuMono-B.ttf"));
    //  return typeface;
    // }
    public static void getDrawer(final Activity activity, Toolbar toolbar, int id, HashMap<String, String> details, String img, DrawerItemListener itemListener) {
        //if you want to update the items at a later time it is recommended to keep it in a variable
        Bitmap image = getBitmap(activity, img);
        PrimaryDrawerItem drawerEmptyItem = new PrimaryDrawerItem().withIdentifier(0).withName("");
        drawerEmptyItem.withEnabled(false);

        //  Typeface typeface=getTypeface(activity.getApplicationContext());


        PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(1)
                .withName("Home").withTypeface(Typeface.MONOSPACE);//.withIcon(R.drawable.ic_home_black_24dp);
        PrimaryDrawerItem account = new PrimaryDrawerItem().withIdentifier(2)
                .withName("My Profile").withTypeface(Typeface.MONOSPACE);//.withIcon(R.drawable.ic_account_circle_black_24dp);
//        PrimaryDrawerItem messages = new PrimaryDrawerItem().withIdentifier(3)
//                .withName("Messages").withIcon(R.drawable.ic_message_black_24dp)
//                // .withBadge("3")
//                ;
//        PrimaryDrawerItem favorites = new PrimaryDrawerItem().withIdentifier(4)
//                .withName("Favorites").withIcon(R.drawable.ic_favorite_black_24dp)
//                .withBadge("4");


        PrimaryDrawerItem payments = new PrimaryDrawerItem().withIdentifier(5)
                .withName("Payments").withTypeface(Typeface.MONOSPACE);//.withIcon(R.drawable.ic_attach_money_black_24dp);
        PrimaryDrawerItem settingd = new PrimaryDrawerItem().withIdentifier(6)
                // .withIcon(R.drawable.ic_settings_black_24dp)
                .withName("Settings").withTypeface(Typeface.MONOSPACE);

//        PrimaryDrawerItem upcoming = new PrimaryDrawerItem().withIdentifier(10)
//                .withIcon(R.drawable.ic_move_to_inbox_black_24dp)
//                .withName("Upcoming Lessons")
//                // .withBadge("4");
//                ;

        PrimaryDrawerItem logout = new PrimaryDrawerItem().withIdentifier(7)
                .withName("Log Out").withTypeface(Typeface.MONOSPACE);
        PrimaryDrawerItem help = new PrimaryDrawerItem().withIdentifier(8)
                .withName("Help").withTypeface(Typeface.MONOSPACE);
        PrimaryDrawerItem about = new PrimaryDrawerItem().withIdentifier(9)
                .withName("About").withTypeface(Typeface.MONOSPACE);

//

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withOnProfileClickDrawerCloseDelay(2)
                .withTextColorRes(R.color.primary_dark)

                //.withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName(details.get("name")).withEmail(details.get("email"))
                                .withTextColor(ColorHolder.fromColorRes(R.color.colorPrimaryDark).getColorInt())
                                .withTextColor(activity.getResources().getColor(R.color.primary_dark))
                                .withSelectedTextColorRes(R.color.colorPrimaryDark)
                                //.withSelectedTextColorRes(R.color.colorPrimaryDark)
                                .withIcon(image)


                                .withDisabledTextColor(activity.getResources().getColor(R.color.colorPrimaryDark))


                )
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();


        result = new DrawerBuilder()

                .withActivity(activity)
                //.withFooter(R.layout.footer)

                //.withGenerateMiniDrawer(true)
                .withFooterDivider(false)


                .withToolbar(toolbar)

                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withCloseOnClick(true)
                .withSelectedItem(-1)
                .addDrawerItems(
                        // favorites,
                        home, payments, //new DividerDrawerItem(),
                        account,
                        //  settingd,
                        //new DividerDrawerItem(),
                        logout,
                        help, about


                )
                .withFooterClickable(true)
                //.withStickyFooter(R.layout.footer)


                .withOnDrawerItemClickListener((view, position, drawerItem) -> {

                            switch ((int) drawerItem.getIdentifier()) {
                                case 1:
                                    itemListener.homeClicked();
                                    result.closeDrawer();
                                    break;
                                case 2:
                                    itemListener.profileClicked();
                                    result.closeDrawer();
                                    break;
                                case 3:
                                    itemListener.messageClicked();
                                    result.closeDrawer();
                                    break;
                                case 4:
                                    itemListener.favoritesClicked();
                                    result.closeDrawer();
                                    break;
                                case 5:
                                    itemListener.paymentsClicked();
                                    result.closeDrawer();
                                    break;
                                case 6:
                                    itemListener.settingsClicked();
                                    result.closeDrawer();
                                    break;
                                case 7:
                                    itemListener.logOutClicked();
                                    result.closeDrawer();
                                    break;
                                case 8:
                                    itemListener.helpClicked();
                                    result.closeDrawer();
                                    break;
                                case 9:
                                    itemListener.aboutClicked();
                                    result.closeDrawer();
                                    break;
                                case 10:
                                    itemListener.upcomingClicked();
                                    result.closeDrawer();

                            }
                            return true;
                        }
                )

                .build();
    }


}
