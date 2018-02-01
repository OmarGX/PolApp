package disco.unimib.it.polapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
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
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;

public class NotifyActivity extends AppCompatActivity {

    Bitmap image;

    String mCurrentPhotoPath;

    File photoFile=null;

    Uri photoUri;

    Intent TakePictureIntent;

    ImageView photoSaved;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);


        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        setTitle(R.string.problem);

        final Button buttonsend=(Button) findViewById(R.id.button3);
        final Button buttonimage=(Button) findViewById(R.id.button4);
        photoSaved=(ImageView) findViewById(R.id.photoSaved);

        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
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

        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.notify),R.string.notificationsent,Snackbar.LENGTH_LONG);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        InputStream is = null;
        try {
            is = getContentResolver().openInputStream(photoUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        image= BitmapFactory.decodeStream(is);
        ExifInterface ei= null;
        try {
            ei = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation=ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
        Bitmap rotatedimg=null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedimg = rotateImage(image, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedimg = rotateImage(image, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedimg = rotateImage(image, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedimg = image;
        }
        if(image!=null){
            Snackbar.make(findViewById(R.id.notify),R.string.photosaved,Snackbar.LENGTH_LONG).show();
            photoSaved.setImageBitmap(rotatedimg);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        Log.d("ciao", String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
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
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(NotifyActivity.this, "com.example.android.fileprovider", photoFile);
                TakePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(TakePictureIntent, 50);
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
}
