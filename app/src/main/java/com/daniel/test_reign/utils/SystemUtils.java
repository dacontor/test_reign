package com.daniel.test_reign.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class SystemUtils {

    public static void replaceFragment(FragmentActivity activity,
                                       final int container,
                                       final String fragmentName,
                                       final boolean addToBackStack,
                                       final Bundle b,
                                       Fragment currentFragment) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (b != null)
            currentFragment.setArguments(b);

        //if the fragment is already there
        if (fragmentManager.findFragmentByTag(fragmentName) != null) {
            Log.e("SystemUtils", fragmentName + " fragment is already there");
            //fragmentTransaction.remove(fragmentManager.findFragmentByTag(fragmentName));
            fragmentTransaction.replace(container, currentFragment, fragmentName);
        } else {
            Log.e("SystemUtils", fragmentName + " new fragment");
            fragmentTransaction.add(container, currentFragment, fragmentName);
        }

        if (addToBackStack)
            fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commitAllowingStateLoss();
    }

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        assert connectivityManager != null;
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

}
