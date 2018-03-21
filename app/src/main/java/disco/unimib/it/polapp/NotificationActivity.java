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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class NotificationActivity extends AppCompatActivity {


    private Bitmap image;

    private String mCurrentPhotoPath;

    private File photoFile=null;

    private Uri photoUri;

    private Intent TakePictureIntent;

    private ImageView photoSaved, photoSaved2, photoSaved3, photoSaved4;

    private Button cancelButton, cancelButton2, cancelButton3, cancelButton4;

    private Bitmap rotatedimg;

    private String zona, indifferenziato, carta, plastica, vetro;

    private Button buttonimage;

    private EditText problema;

    private Button buttonsend;

    int photoCount=0;

    private RequestQueue queue;

    private ArrayList<String> encodedImages;

    private JSONObject jsonObject;

    private String imageFileName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Bundle bundle=getIntent().getExtras();
        zona=bundle.getString("zona");
        indifferenziato=bundle.getString("indifferenziato");
        carta=bundle.getString("carta");
        plastica=bundle.getString("plastica");
        vetro=bundle.getString("vetro");

        jsonObject=new JSONObject();

        encodedImages=new ArrayList<>();


        final Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        setTitle(R.string.problem);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonsend=(Button) findViewById(R.id.buttonsend);

        buttonimage=(Button) findViewById(R.id.buttonphoto);

        photoSaved=(ImageView) findViewById(R.id.photoSaved);
        photoSaved.setDrawingCacheEnabled(true);
        photoSaved.setImageDrawable(null);
        photoSaved2=(ImageView) findViewById(R.id.photoSaved2);
        photoSaved2.setDrawingCacheEnabled(true);
        photoSaved2.setImageDrawable(null);
        photoSaved3=(ImageView) findViewById(R.id.photoSaved3);
        photoSaved3.setDrawingCacheEnabled(true);
        photoSaved3.setImageDrawable(null);
        photoSaved4=(ImageView) findViewById(R.id.photoSaved4);
        photoSaved4.setDrawingCacheEnabled(true);
        photoSaved4.setImageDrawable(null);

        problema=(EditText) findViewById(R.id.problemdescr);

        cancelButton=(Button) findViewById(R.id.cancel_button);
        cancelButton.setVisibility(View.GONE);
        cancelButton2=(Button) findViewById(R.id.cancel_button2);
        cancelButton2.setVisibility(View.GONE);
        cancelButton3=(Button) findViewById(R.id.cancel_button3);
        cancelButton3.setVisibility(View.GONE);
        cancelButton4=(Button) findViewById(R.id.cancel_button4);
        cancelButton4.setVisibility(View.GONE);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoSaved.setImageBitmap(null);
                photoSaved.setImageDrawable(null);
                cancelButton.setVisibility(View.GONE);
                photoCount--;
                buttonimage.setVisibility(View.VISIBLE);
                if(photoCount==1 && photoSaved2.getDrawingCache()!=null){
                    photoSaved.setImageBitmap(photoSaved2.getDrawingCache());
                    photoSaved2.setImageBitmap(null);
                    photoSaved2.setImageDrawable(null);
                    cancelButton2.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.VISIBLE);
                }else if(photoCount==2 && photoSaved2.getDrawingCache()!=null && photoSaved3.getDrawingCache()!=null){
                    photoSaved.setImageBitmap(photoSaved2.getDrawingCache());
                    photoSaved2.setImageBitmap(photoSaved3.getDrawingCache());
                    photoSaved3.setImageBitmap(null);
                    photoSaved3.setImageDrawable(null);
                    cancelButton3.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.VISIBLE);
                }else if(photoCount==3 && photoSaved2.getDrawingCache()!=null && photoSaved3.getDrawingCache()!=null && photoSaved4.getDrawingCache()!=null){
                    photoSaved.setImageBitmap(photoSaved2.getDrawingCache());
                    photoSaved2.setImageBitmap(photoSaved3.getDrawingCache());
                    photoSaved3.setImageBitmap(photoSaved4.getDrawingCache());
                    photoSaved4.setImageBitmap(null);
                    photoSaved4.setImageDrawable(null);
                    cancelButton4.setVisibility(View.GONE);
                    cancelButton.setVisibility(View.VISIBLE);
                }

            }
        });

        cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoSaved2.setImageBitmap(null);
                photoSaved2.setImageDrawable(null);
                cancelButton2.setVisibility(View.GONE);
                photoCount--;
                buttonimage.setVisibility(View.VISIBLE);
                if(photoCount==2 && photoSaved3.getDrawingCache()!=null){
                    photoSaved2.setImageBitmap(photoSaved3.getDrawingCache());
                    photoSaved3.setImageBitmap(null);
                    photoSaved3.setImageDrawable(null);
                    cancelButton3.setVisibility(View.GONE);
                    cancelButton2.setVisibility(View.VISIBLE);
                }else if(photoCount==3 && photoSaved3.getDrawingCache()!=null && photoSaved4.getDrawingCache()!=null){
                    photoSaved2.setImageBitmap(photoSaved3.getDrawingCache());
                    photoSaved3.setImageBitmap(photoSaved4.getDrawingCache());
                    photoSaved4.setImageBitmap(null);
                    photoSaved4.setImageDrawable(null);
                    cancelButton4.setVisibility(View.GONE);
                    cancelButton2.setVisibility(View.VISIBLE);
                }

            }
        });

        cancelButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoSaved3.setImageBitmap(null);
                photoSaved3.setImageDrawable(null);
                cancelButton3.setVisibility(View.GONE);
                photoCount--;
                buttonimage.setVisibility(View.VISIBLE);
                if(photoCount==3 && photoSaved4.getDrawingCache()!=null){
                    photoSaved3.setImageBitmap(photoSaved4.getDrawingCache());
                    photoSaved4.setImageDrawable(null);
                    photoSaved4.setImageBitmap(null);
                    cancelButton4.setVisibility(View.GONE);
                    cancelButton3.setVisibility(View.VISIBLE);
                }

            }
        });

        cancelButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoSaved4.setImageBitmap(null);
                photoSaved4.setImageDrawable(null);
                cancelButton4.setVisibility(View.GONE);
                photoCount--;
                buttonimage.setVisibility(View.VISIBLE);

            }
        });


        buttonimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TakePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (TakePictureIntent.resolveActivity(getPackageManager()) != null) {
                    if (ContextCompat.checkSelfPermission(NotificationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(NotificationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                    } else {
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (photoFile != null) {
                            photoUri = FileProvider.getUriForFile(NotificationActivity.this, "com.example.android.fileprovider", photoFile);
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
                if (NetworkUtils.isNetworkAvaiable(NotificationActivity.this)) {
                    if (photoSaved.getDrawable() != null) {
                        encodedImages.add(imgToString(photoSaved.getDrawingCache()));
                    }
                    if (photoSaved2.getDrawable() != null) {
                        encodedImages.add(imgToString(photoSaved2.getDrawingCache()));
                    }
                    if (photoSaved3.getDrawable() != null) {
                        encodedImages.add(imgToString(photoSaved3.getDrawingCache()));
                    }
                    if (photoSaved4.getDrawable() != null) {
                        encodedImages.add(imgToString(photoSaved4.getDrawingCache()));
                    }
                    queue = Volley.newRequestQueue(NotificationActivity.this);
                    JSONArray jsonArrayImages = new JSONArray();
                    for (String encoded : encodedImages) {
                        jsonArrayImages.put(encoded);

                    }
                    try {
                        jsonObject.put("imagearray", jsonArrayImages);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    uploadData();
                    uploadNotification();
                    uploadImages();
                }else{
                    Snackbar.make(findViewById(R.id.notify),R.string.no_internet,Snackbar.LENGTH_SHORT).show();
                }
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
            Bitmap resized= Bitmap.createScaledBitmap(rotatedimg,(int)(rotatedimg.getWidth()*0.8),(int)(rotatedimg.getHeight()*0.8),true);
            photoCount++;
            if(photoCount==1) {
                photoSaved.setImageBitmap(resized);
                cancelButton.setVisibility(View.VISIBLE);
            }else if(photoSaved.getDrawable()==null){
                photoSaved.setImageBitmap(resized);
                cancelButton.setVisibility(View.VISIBLE);
            }else if(photoSaved2.getDrawable()==null){
                photoSaved2.setImageBitmap(resized);
                cancelButton2.setVisibility(View.VISIBLE);
            }else if(photoSaved3.getDrawable()==null){
                photoSaved3.setImageBitmap(resized);
                cancelButton3.setVisibility(View.VISIBLE);
            }else if(photoSaved4.getDrawable()==null){
                photoSaved4.setImageBitmap(resized);
                cancelButton4.setVisibility(View.VISIBLE);
            }
            if(photoCount==4){
                buttonimage.setVisibility(View.GONE);
            }else{
                buttonimage.setVisibility(View.VISIBLE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
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
                photoUri = FileProvider.getUriForFile(NotificationActivity.this, "com.example.android.fileprovider", photoFile);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void uploadNotification(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ApiContract.urlData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.cancelAll(new RequestQueue.RequestFilter() {
                    @Override
                    public boolean apply(Request<?> request) {
                        return true;
                    }
                });

            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("text",problema.getText().toString().trim());
                params.put("name",zona);
                return params;
            }
        };
        queue.add(stringRequest);
    }
    private void uploadData(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ApiContract.urlValues, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                buttonsend.setVisibility(View.GONE);
                problema.setText("");
                photoSaved.setImageBitmap(null);
                photoSaved2.setImageBitmap(null);
                photoSaved3.setImageBitmap(null);
                photoSaved4.setImageBitmap(null);
                cancelButton.setVisibility(View.GONE);
                cancelButton2.setVisibility(View.GONE);
                cancelButton3.setVisibility(View.GONE);
                cancelButton4.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String rsp=jsonObject.getString("response");
                    Snackbar.make(findViewById(R.id.notify),rsp,Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.snackbaraction, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent openMain = new Intent(NotificationActivity.this, QrCodeScannerActivity.class);
                                    startActivity(openMain);
                                }
                            }).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.cancelAll(new RequestQueue.RequestFilter() {
                    @Override
                    public boolean apply(Request<?> request) {
                        return true;
                    }
                });

            }
        })
        {
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String> params=new HashMap<>();
                params.put("zona",zona);
                params.put("indifferenziato",indifferenziato);
                params.put("carta",carta);
                params.put("plastica",plastica);
                params.put("vetro",vetro);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void uploadImages(){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, ApiContract.urlImages, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.cancelAll(new RequestQueue.RequestFilter() {
                    @Override
                    public boolean apply(Request<?> request) {
                        return true;
                    }
                });

            }
        });
        queue.add(jsonObjectRequest);
    }

}





