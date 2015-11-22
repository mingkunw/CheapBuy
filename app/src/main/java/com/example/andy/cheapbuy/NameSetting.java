package com.example.andy.cheapbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NameSetting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_setting);

    }

    public void GoBacktoAccountSetting(View view){

        Intent i = new Intent(NameSetting.this, AccountSetting.class);
        startActivity(i);
        
    }


}
