package disco.unimib.it.polapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotifyActivity extends AppCompatActivity {

    Bitmap image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);


        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        setTitle(R.string.problem);

        final Button button3=(Button) findViewById(R.id.button3);
        final Button button4=(Button) findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TakePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(TakePictureIntent, 50);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        image= (Bitmap) data.getExtras().get("data");
        Log.d("ok", "onActivityResult: photo taken");
        Snackbar.make(findViewById(R.id.notify),R.string.photosaved,Snackbar.LENGTH_LONG).show();
    }
}
