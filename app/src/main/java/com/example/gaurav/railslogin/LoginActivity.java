package com.example.gaurav.railslogin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;

import cz.msebera.android.httpclient.Header;

import static android.R.attr.name;

/**
 * Created by suchi on 27/11/16.
 */

public class LoginActivity extends Activity {
    private EditText email;
    private EditText password;
    private Button email_sign_in_button;
    ProgressDialog pdLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        pdLoading = new ProgressDialog(LoginActivity.this);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        email_sign_in_button=(Button) findViewById(R.id.email_sign_in_button);
        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stremail=email.getText().toString();
                String strpwd=password.getText().toString();
                RequestParams params = new RequestParams();
                params.put("email", stremail);
                // Put Http parameter username with value of Email Edit View control
                params.put("password", strpwd);
                // Invoke RESTful Web Service with Http parameters

                //right now its get request make it post
                invokeWS(params);
            }
        });
    }
    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        pdLoading.setMessage("Loading...");
        pdLoading.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://www.mocky.io/v2/5843c1a5100000ed181a5712",params ,new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                pdLoading.hide();
                try {
                  String str = new String(responseBody, "UTF-8");

                    Gson gson = new Gson();

                    //convert java object to JSON format
                    LoginData data=gson.fromJson(str,LoginData.class);
                    if(data!=null)
                    {
                        if(data.getStatus().equals("200"))
                        {
                            Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
                            //login successfull here call another screen here

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                          //error occurr
                        }
                    }


                }  catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                pdLoading.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
