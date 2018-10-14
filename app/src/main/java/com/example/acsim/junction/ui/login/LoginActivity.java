package com.example.acsim.junction.ui.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.acsim.junction.R;
import com.example.acsim.junction.data.CoinRepo;
import com.example.acsim.junction.model.Coin;
import com.example.acsim.junction.model.Customer;
import com.example.acsim.junction.model.JSONLogin;
import com.example.acsim.junction.ui.main.MainActivity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText edt_IDCard;
    EditText edt_Password;
    Button btn_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_IDCard = findViewById(R.id.editTextIDCard);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.showSoftInput(edt_IDCard, InputMethodManager.SHOW_IMPLICIT);

        edt_Password = findViewById(R.id.editTextPassword);
        btn_Login = findViewById(R.id.buttonLogin);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post("http://10.79.1.213:8080/customer/login")
                        .addBodyParameter("username", edt_IDCard.getText().toString())
                        .addBodyParameter("password", edt_Password.getText().toString())
                        .setTag("LOGIN")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("Response", String.valueOf(response));
                                String jsonResponse = response.toString();
                                try {
                                    JSONObject jsonObjectResponse = new JSONObject(response.toString());
                                    String jsonCustomer = jsonObjectResponse.getString("user");
                                    JSONObject jsonObjectCustomer = new JSONObject(jsonCustomer);
//                                    String idCard = jsonObjectCustomer.getString("IdCard");
                                    String customerName = jsonObjectCustomer.getString("UserName");
                                    String userID = jsonObjectCustomer.getString("UserID");
                                    CoinRepo.getInstance().setCustomer(new Customer(userID, customerName, userID));
                                    CoinRepo.getInstance().getAllCoin().add(new Coin("HCoin", 1000));
                                    CoinRepo.getInstance().getAllCoin().add(new Coin("CCoin", 2000));
                                    CoinRepo.getInstance().getAllCoin().add(new Coin("MCoin", 3000));

                                    Log.d("status", jsonObjectResponse.getString("status"));

                                } catch (Throwable t) {
                                    Log.e("My App", "Could not parse malformed JSON: \"" + jsonResponse + "\"");
                                }
                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(loginIntent);
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(getApplicationContext(), "Wrong, Please Relogin!", Toast.LENGTH_LONG).show();
                            }
                        });
//                if (edt_IDCard.getText().toString().equals("1") && edt_Password.getText().toString().equals("1")) {
//                    Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(loginIntent);
//                } else {
//                    Toast.makeText(getApplicationContext(), "Wrong, Please Relogin!", Toast.LENGTH_LONG).show();
//                }
            }
        });
    }
}
