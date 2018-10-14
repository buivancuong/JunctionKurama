package com.example.acsim.junction.ui.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.acsim.junction.R;
import com.example.acsim.junction.data.CoinRepo;
import com.example.acsim.junction.model.Coin;
import com.example.acsim.junction.model.Log;
import com.example.acsim.junction.ui.giveaway.GiveAwayActivity;
import com.example.acsim.junction.ui.log.LogActivity;
import com.example.acsim.junction.ui.owncode.OwnQRCodeActivity;
import com.example.acsim.junction.ui.qrscan.QRScanActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CoinAdapter.CoinClickListener, MainContractor.View {

    MainPresenter mainPresenter;
    RecyclerView coinRecyclerView;
    CoinAdapter coinAdapter;
    ImageView avatar;

    TextView tv_CustomerName, tv_IdCard, tv_ValueIDCard, tv_ValueUserID;
    Button btn_GetOwnQRCode, btn_ShowLog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Log.d("Customer Name: ", mainPresenter.getCustomerName());
//        Log.d("ID Card: ", String.valueOf(mainPresenter.getCustomerIDCard()));
//        Log.d("User ID: ", String.valueOf(mainPresenter.getCustomerUserID()));

        avatar = findViewById(R.id.imageViewAvatar);
        tv_CustomerName = findViewById(R.id.textViewCustomerName);
        tv_IdCard = findViewById(R.id.textViewIDCard);
//        tv_UserID = findViewById(R.id.textViewUserID);
        tv_ValueIDCard = findViewById(R.id.textViewStringIDCard);
//        tv_ValueUserID = findViewById(R.id.textViewStringUserID);
        btn_GetOwnQRCode = findViewById(R.id.buttongetownqrcode);
        btn_ShowLog = findViewById(R.id.buttonShowLog);
        coinRecyclerView = findViewById(R.id.recyclerViewCoinList);

        coinAdapter = new CoinAdapter();
        coinAdapter.setCoinClickListener(this);
        coinRecyclerView.setAdapter(coinAdapter);
        coinRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coinRecyclerView.addItemDecoration(new CoinDecorator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        coinRecyclerView.addItemDecoration(dividerItemDecoration);

        mainPresenter = new MainPresenter(this, CoinRepo.getInstance());
        mainPresenter.getCoinList();

        tv_ValueIDCard.setText(mainPresenter.getCustomerIDCard());
//        tv_ValueUserID.setText(mainPresenter.getCustomerUserID() + "");
        tv_CustomerName.setText(mainPresenter.getCustomerName());
        avatar.setImageResource(R.drawable.avatar);

        btn_GetOwnQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getOwnQRCode = new Intent(MainActivity.this, OwnQRCodeActivity.class);
                startActivity(getOwnQRCode);
            }
        });

        btn_ShowLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.util.Log.i("UserID", CoinRepo.getInstance().getCustomUserID());
                String responseBody = String.valueOf(new GetRequestBody().execute());
                android.util.Log.i("Response", responseBody);

                Intent showLong = new Intent(MainActivity.this, LogActivity.class);
                startActivity(showLong);
            }
        });
    }

    @Override
    public void onCoinItemClick(Coin coin) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setPositiveButton("Get Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent getItemIntent = new Intent(MainActivity.this, QRScanActivity.class);
                        startActivity(getItemIntent);
                    }
                }).setNegativeButton("Give Away", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent giveAwayIntent = new Intent(MainActivity.this, GiveAwayActivity.class);
                        startActivity(giveAwayIntent);
                    }
                }).create();
        alertDialog.setMessage("You want to use this coin for ...");
        alertDialog.show();
    }

    @Override
    public void onCoinItemLongClick(Coin coin) {

    }

    @Override
    public void showCoinList(List<Coin> coinList) {
        coinAdapter.setCoinList(coinList);
    }

    @Override
    public void showCustomerName() {
        tv_CustomerName.setText(mainPresenter.getCustomerName());
    }

    @Override
    public void showCustomerIDCard() {
        tv_ValueIDCard.setText(mainPresenter.getCustomerIDCard());
    }

    @Override
    public void showCustomerUserID() {
        tv_ValueUserID.setText(mainPresenter.getCustomerUserID());
    }

    @SuppressLint("StaticFieldLeak")
    private class GetRequestBody extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String getURL = "http://10.79.1.213:8080/customer/history/" + CoinRepo.getInstance().getCustomUserID();
            StringBuilder stringBuffer = new StringBuilder();
            try {
                URL url = new URL(getURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");

                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String inputLine;


                    while ((inputLine = bufferedReader.readLine()) != null) {
                        stringBuffer.append(inputLine);
                    }
                    bufferedReader.close();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return String.valueOf(stringBuffer);
        }

    }
}
