package com.example.andy.cheapbuy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class passwordSetting extends AppCompatActivity {

    public String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_setting);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userId= null;
            } else {
                userId= extras.getString("userId");
            }
        } else {
            userId= (String) savedInstanceState.getSerializable("userId");
        }


        Toast.makeText(this,userId,Toast.LENGTH_LONG);


    }

    public void GoBacktoAccountSettingPassword(View view){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("PersonInfo");
        query.getInBackground(userId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    EditText newPassword = (EditText) findViewById(R.id.passwordInputFirst);
                    EditText newRepeat = (EditText) findViewById(R.id.passwordInputTwice);

                    if (newPassword.getText().toString().equals(newRepeat.getText().toString())) {
                        object.put("password", newPassword.getText().toString());
                        object.saveInBackground();
                        Intent i = new Intent(passwordSetting.this, AccountSetting.class);
                        i.putExtra("userId",userId);
                        startActivity(i);
                    } else {


                        TextView failureText = new TextView(getApplicationContext());
                        failureText.setText("Password Does Not Match");
                        failureText.setTextColor(Color.BLACK);
                        failureText.setTextSize(20);
                        RelativeLayout layout = (RelativeLayout) findViewById(R.id.passwordSettingInterface);
                        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        relativeParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, failureText.getId());
                        relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL, failureText.getId());

                        layout.addView(failureText, relativeParams);

                    }

                } else {
                }
            }
        });



    }

}
