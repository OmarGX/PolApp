package disco.unimib.it.polapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedp;
    public static final String MyPref = "MyPrefs" ;
    public static final String Nome = "nameKey";
    public static final String cognome = "surnameKey";
    public static final String firstTime = "RegisteredKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedp=getSharedPreferences(MyPref,MODE_PRIVATE);
        View root=findViewById(R.id.rootview);
        Boolean isFirstTime=sharedp.getBoolean(firstTime,true);
        if(isFirstTime==false){
            Intent openMain=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(openMain);
        }else {
            setContentView(R.layout.activity_login);

            final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
            setSupportActionBar(toolbar);
            setTitle("PolApp");

            final Spinner ed1 = (Spinner) findViewById(R.id.spinner10);
            final Spinner ed2 = (Spinner) findViewById(R.id.spinner11);

            ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.areeuniv,android.R.layout.simple_spinner_dropdown_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ed1.setAdapter(adapter1);

            ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.ruoli,android.R.layout.simple_spinner_dropdown_item);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ed2.setAdapter(adapter2);


            final Button savedata = (Button) findViewById(R.id.button2);

            savedata.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String N = ed1.getSelectedItem().toString();
                    String C = ed2.getSelectedItem().toString();
                    SharedPreferences.Editor editor = sharedp.edit();

                    editor.putString(Nome, N);
                    editor.putString(cognome, C);
                    editor.putBoolean(firstTime, false);
                    editor.apply();
                    Snackbar.make(findViewById(R.id.rootview),"Dati Salvati!",Snackbar.LENGTH_LONG).show();
                    Intent openMain = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(openMain);
                }

            });
        }
    }
}
