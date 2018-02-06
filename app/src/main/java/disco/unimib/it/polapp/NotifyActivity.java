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
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class NotifyActivity extends AppCompatActivity {

    Bitmap image;

    String mCurrentPhotoPath;

    File photoFile=null;

    Uri photoUri;

    Intent TakePictureIntent;

    ImageView photoSaved;

    Bitmap rotatedimg;

    private String uploadUrl="http://192.168.1.228/PolApp/testupload.php";

    String zona;

    EditText problema;

    Button buttonsend;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        Bundle bundle=getIntent().getExtras();
        zona=bundle.getString("zona");


        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        setTitle(R.string.problem);

        buttonsend=(Button) findViewById(R.id.buttonsend);
        final Button buttonimage=(Button) findViewById(R.id.buttonphoto);
        photoSaved=(ImageView) findViewById(R.id.photoSaved);
        problema=(EditText) findViewById(R.id.problemdescr);


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
                uploadImage();
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
        if(rotatedimg!=null){
            photoSaved.setImageBitmap(rotatedimg);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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

    public String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte [] img=byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(img, Base64.DEFAULT);
    }

    private void uploadImage(){
        RequestQueue queue= Volley.newRequestQueue(NotifyActivity.this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String rsp=jsonObject.getString("response");
                            problema.setText("");
                            buttonsend.setVisibility(View.GONE);
                            Snackbar.make(findViewById(R.id.notify),rsp,Snackbar.LENGTH_INDEFINITE).setAction(R.string.snackbaraction, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent openMain = new Intent(NotifyActivity.this, MainActivity.class);
                                    startActivity(openMain);
                                }
                            })
                                    .show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("image",imgToString(rotatedimg));
                params.put("text",problema.getText().toString().trim());
                params.put("name",zona);
                return params;
            }
        };
        queue.add(stringRequest);
    }

}
