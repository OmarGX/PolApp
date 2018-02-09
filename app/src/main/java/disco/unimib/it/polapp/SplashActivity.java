package disco.unimib.it.polapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.SplashTheme);
        final SharedPreferences mSharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs",MODE_PRIVATE);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Boolean isFirstTime;
                    isFirstTime = mSharedPreferences.getBoolean("RegisteredKey",true);
                    if (isFirstTime) {
                        Intent openLogin = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(openLogin);
                        finish();
                    } else {
                        Intent openMain = new Intent(SplashActivity.this, MainActivity.class);
                        openMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        openMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        openMain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(openMain);
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}
