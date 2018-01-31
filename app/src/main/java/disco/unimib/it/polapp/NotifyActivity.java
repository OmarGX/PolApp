package disco.unimib.it.polapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

import static android.os.Environment.getExternalStoragePublicDirectory;

public class NotifyActivity extends AppCompatActivity {

    Bitmap image;

    String mCurrentPhotoPath;

    File photoFile=null;

    Uri photoUri;




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
                Intent TakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (TakePictureIntent.resolveActivity(getPackageManager()) != null) {
                    if (ContextCompat.checkSelfPermission(NotifyActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(NotifyActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    } else {
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (photoFile != null) {
                            photoUri = FileProvider.getUriForFile(NotifyActivity.this, "com.example.android.fileprovider", photoFile);
                            TakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                            startActivityForResult(TakePictureIntent, 50);
                        }

                    }


                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.notify),R.string.notificationsent,Snackbar.LENGTH_LONG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        image= BitmapFactory.decodeFile(photoUri.getPath());
        Log.d("ok", "onActivityResult: photo taken");
        Snackbar.make(findViewById(R.id.notify),R.string.photosaved,Snackbar.LENGTH_LONG).show();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions,int [] grantResults){
        if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            try{
                photoFile = createImageFile();
            } catch(IOException ex){
                ex.printStackTrace();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
