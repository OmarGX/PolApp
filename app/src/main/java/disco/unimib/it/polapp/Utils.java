package disco.unimib.it.polapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by omarg on 10/02/2018.
 */

public class Utils {
    public static final String urlData="http://192.168.1.228/PolApp/testupload.php";

    public static final String urlImages="http://192.168.1.228/PolApp/testuploadimages.php";

    public static final String urlValues="http://192.168.1.228/PolApp/testuploaddata.php";

    public static boolean isNetworkAvaiable(Context context){
        ConnectivityManager connectivityManager
                = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

