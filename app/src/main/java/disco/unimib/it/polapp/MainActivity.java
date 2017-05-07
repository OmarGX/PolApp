package disco.unimib.it.polapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SurfaceView cameraView = (SurfaceView) findViewById(R.id.camera_view);
        final TextView barcodeInfo = (TextView) findViewById(R.id.code_info);

        BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                //.setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();

        final CameraSource cameraSource = new CameraSource.Builder(this, detector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 50);
                } else {
                    //start your camera
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                /*try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }*/
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                            barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            );
                        }
                    });
                }
            }
        });

        /*Button btn = (Button) findViewById(R.id.button);
        final TextView txtView = (TextView) findViewById(R.id.txtContent);
        final ImageView myImageView = (ImageView) findViewById(R.id.imgview);

        final Bitmap myBitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.puppy);
        myImageView.setImageBitmap(myBitmap);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                        .build();

                if(!detector.isOperational()){
                    txtView.setText("Could not set up the detector!");
                    return;
                }

                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);

                Barcode thisCode = barcodes.valueAt(0);

                txtView.setText(thisCode.rawValue);
            }
        });*/
    }
}