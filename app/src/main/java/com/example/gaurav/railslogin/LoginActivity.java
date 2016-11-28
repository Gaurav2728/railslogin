package com.example.gaurav.railslogin;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

/**
 * Created by suchi on 27/11/16.
 */

public class LoginActivity extends Activity {
    private EditText email;
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
    }
}
