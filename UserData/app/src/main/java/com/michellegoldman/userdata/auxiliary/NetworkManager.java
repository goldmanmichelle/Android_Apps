//Michelle Goldman
//CPMD 1215
//November 30, 2015
//Updated December 17, 2015

package com.michellegoldman.userdata.auxiliary;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkManager {


    public static boolean isConnected(Context context){

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {

                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    //Alert user they're on WIFI connection
                    Toast.makeText(context, "WIFI connection detected!", Toast.LENGTH_LONG).show();

                } else if(activeNetwork != null) {
                    //Alert user they're on WIFI connection
                    //Toast.makeText(context, "Connected to network!", Toast.LENGTH_LONG).show();
                }
                return true;
            } else if (activeNetwork == null ){
                //Alert user there is no valid data connection
                Toast.makeText(context, "No network connection detected!", Toast.LENGTH_SHORT).show();

            }

        }
        return false;

    }


}
