package com.example.acsim.junction.ui.giveaway;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acsim.junction.R;
import com.example.acsim.junction.data.CoinRepo;
import com.example.acsim.junction.model.GetItemInfo;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GiveAwayActivity extends AppCompatActivity {

    SurfaceView scanSurfaceView;
    TextView textViewResult;
    Button btn_GiveAway;
    EditText edt_ValueGA;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(scanSurfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_away);

        scanSurfaceView = findViewById(R.id.surfaceViewGiveAway);
        textViewResult = findViewById(R.id.textViewGWIDCard);
        btn_GiveAway = findViewById(R.id.buttonGiveAway);
        edt_ValueGA = findViewById(R.id.textViewValueCoinGA);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(400, 400)
                .build();
        scanSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(GiveAwayActivity.this, new String[]{android.Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(scanSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrCode = detections.getDetectedItems();
                if (qrCode.size() != 0) {
                    textViewResult.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            assert vibrator != null;
                            vibrator.vibrate(1000);
                            textViewResult.setText(qrCode.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });

        btn_GiveAway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetItemInfo getItemInfo = new GetItemInfo(textViewResult.getText().toString(), CoinRepo.getInstance().getCustomerIDCard());
                try {
                    setPOST(getApplicationContext(),getItemInfo.Ob2Json(getItemInfo));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void setPOST(Context context, String json) throws IOException {
        URL obj = new URL("10.79.1.173:8080/customer/getItemUsingToken");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Toast.makeText(context, "You get item success.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "You get item fail.", Toast.LENGTH_LONG).show();
        }

//        InputStream is = new InputStream()
    }
}
