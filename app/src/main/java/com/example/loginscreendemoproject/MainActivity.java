package com.example.loginscreendemoproject;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginscreendemoproject.connectivity.CheckNetwork;
import com.example.loginscreendemoproject.service.RetrofitClient;
import com.example.loginscreendemoproject.utility.AppConstants;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,TextWatcher {
    private String username, password,device_type,device_token,header;
    private boolean validate = false;
    private TextView login;
    private EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    /**
     *
     */
    private void initView() {
        login = (TextView) findViewById(R.id.login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        login.setOnClickListener(this);
        et_username.addTextChangedListener(this);
        et_password.addTextChangedListener(this);

        device_type=getString(R.string.device_type);
        device_token=getString(R.string.device_token);
        header=getString(R.string.header);
    }


    @Override
    public void onClick(View v) {
        login.setBackgroundColor(ResourcesCompat.getColor(getResources(),R.color.colorPrimaryDark,null));
        getDataFromUser();
    }

    private void getDataFromUser() {
        username = et_username.getText().toString();
        password = et_password.getText().toString();


        if (TextUtils.isEmpty(et_username.getText().toString())) {
            et_username.setError(getString(R.string.username_error));
            et_username.requestFocus();
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
            et_password.setError(getString(R.string.password_error));
            et_password.requestFocus();
        } else {
            validate = true;
            if (validate) {
                if (CheckNetwork.isNetworkAvailable(MainActivity.this))
                    attemptLogin();
                else
                    CheckNetwork.showAlert(getString(R.string.internet_connectivity_failure), MainActivity.this);
            } else {
                getDataFromUser();
            }
        }
    }

    /**
     *
     */
    private void attemptLogin() {
        RetrofitClient.getApiService().login(header, username, password, device_type, device_token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("in","success");
                if (response != null && response.body() != null) {
                    Snackbar.make(findViewById(R.id.content), "Login Successfully", Snackbar.LENGTH_SHORT)
                    .show();
                }
                else{
                    Snackbar.make(findViewById(R.id.content), "Username and Password not Exist!", Snackbar.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("in", "Failure");

            }
        });
    }






    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        et_username.setError(null);
        et_password.setError(null);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
