package disco.unimib.it.polapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    SharedPreferences sharedp;
    public static final String MyPref = "MyPrefs" ;
    public static final String Nome = "nameKey";
    public static final String cognome = "surnameKey";
    public static final String isRegistered = "RegisteredKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedp=getSharedPreferences(MyPref,MODE_PRIVATE);
        if(sharedp.getBoolean(isRegistered,true)){
            Toast.makeText(LoginActivity.this,"Bentornato " + sharedp.getString(cognome,null),Toast.LENGTH_LONG).show();
            Intent openMain=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(openMain);
        }

        final Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        final EditText ed1 = (EditText)findViewById(R.id.editText);
        final EditText ed2 = (EditText)findViewById(R.id.editText2);

        final Button savedata= (Button) findViewById(R.id.button2);

        savedata.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String N=ed1.getText().toString();
                String C=ed2.getText().toString();
                SharedPreferences.Editor editor = sharedp.edit();

                editor.putString(Nome,N);
                editor.putString(cognome,C);
                editor.putBoolean(isRegistered,true);
                editor.apply();
                Toast.makeText(LoginActivity.this,"Grazie!",Toast.LENGTH_LONG).show();
                Intent openMain=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(openMain);
            }

        });
    }
}
