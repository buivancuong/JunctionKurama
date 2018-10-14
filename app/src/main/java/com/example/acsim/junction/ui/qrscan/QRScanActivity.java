package com.example.acsim.junction.ui.qrscan;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.acsim.junction.R;
import com.example.acsim.junction.data.CoinRepo;
import com.example.acsim.junction.model.GetItemInfo;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class QRScanActivity extends AppCompatActivity {

    SurfaceView cameraSurfaceView;
    TextView textViewResult;
    Button btn_GetItem;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int ReQuestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ReQuestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraSurfaceView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class GetRequestBody extends AsyncTask<String, Void, String> {

        @SuppressLint("ShowToast")
        @Override
        protected String doInBackground(String... strings) {
//            GetItemInfo getItemInfo = new GetItemInfo("50891448", "1");
//            String json = getItemInfo.Ob2Json(getItemInfo);
//            Log.i("JSON", json);
            try {
                setPOST();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan);

        cameraSurfaceView = findViewById(R.id.surfaceViewQRScan);
        textViewResult = findViewById(R.id.textViewResult);
        btn_GetItem = findViewById(R.id.buttonGetItem);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(400, 400)
                .build();
        cameraSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(QRScanActivity.this, new String[]{Manifest.permission.CAMERA}, ReQuestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
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

        btn_GetItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                GetItemInfo getItemInfo = new GetItemInfo(textViewResult.getText().toString(), CoinRepo.getInstance().getCustomerIDCard());
                GetItemInfo getItemInfo = new GetItemInfo("50891448", "1");
                Log.i("Click: ", getItemInfo.Ob2Json(getItemInfo));
                new GetRequestBody().execute("http://10.79.1.173:8080/customer/getItemUsingToken");
            }
        });
    }

    @SuppressLint("ShowToast")
    private void setPOST() throws IOException {

        AndroidNetworking.post("http://10.79.1.213:8080/customer/getItemUsingToken")
                .addBodyParameter("idCard", CoinRepo.getInstance().getCustomerIDCard())
                .addBodyParameter("itemID",textViewResult.getText().toString())
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getApplicationContext(), "Ko co get dc j", Toast.LENGTH_LONG).show();
                    }
                });

//        String postURL = "http://10.79.1.173:8080/customer/getItemUsingToken";
//        URL url = new URL(postURL);
//        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//
//        httpURLConnection.setRequestMethod("POST");
//        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
////        httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//
//        httpURLConnection.setDoOutput(true);
//
////        OutputStream outputStream = httpURLConnection.getOutputStream();
////        outputStream.write(json.getBytes());
////        outputStream.flush();
////        outputStream.close();
//        DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
//        dataOutputStream.writeBytes(json);
//        Log.i("FJSON", json);
//        dataOutputStream.close();
//        dataOutputStream.flush();
//
//        int responseCode = 0;
//        responseCode = httpURLConnection.getResponseCode();
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
//        String inputLine;
//        StringBuffer stringBuffer = new StringBuffer();
//        while ((inputLine = bufferedReader.readLine()) != null) {
//            stringBuffer.append(inputLine);
//        }
//        bufferedReader.close();
//
//        if (responseCode == 200) {
//            Toast.makeText(getApplicationContext(), stringBuffer.toString(), Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_LONG).show();
//        }

    }
}
