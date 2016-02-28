package com.example.praseedm.materialapp;


import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Navigation_Drawer extends Fragment {

    public static final  String PREF_FILE_NAME = "pref_file";
    public static final  String KEY_USER_LEARNED = "learned";
    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout  drawerLayout;
    private boolean userlearnedDrawer ;
    private boolean fromSavedInstance;

    private View drawerFrag;

    public Navigation_Drawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       userlearnedDrawer =
               Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED,"false"));
        if(savedInstanceState != null){
            fromSavedInstance = true;
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation__drawer, container, false);
    }

    public void setup(int fragmentId ,DrawerLayout drawer , Toolbar toolbar) {

        drawerFrag = getActivity().findViewById(fragmentId);
        drawerLayout = drawer;
        drawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
                if(!userlearnedDrawer){
                    userlearnedDrawer = true;
                    saveToPreferences(getActivity(),KEY_USER_LEARNED,userlearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }
        };
        if(!userlearnedDrawer && !fromSavedInstance){
            drawerLayout.openDrawer(drawerFrag);
        }
        drawerLayout.setDrawerListener(drawerToggle);

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                 drawerToggle.syncState();
            }
        });

    }


    private static void saveToPreferences(Context context, String preferenceName, String preferenceValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName,preferenceValue);
        editor.apply();
    }
    private static String readFromPreferences(Context context, String preferenceName, String defaultValue)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
       return sharedPreferences.getString(preferenceName,defaultValue);
    }
}
