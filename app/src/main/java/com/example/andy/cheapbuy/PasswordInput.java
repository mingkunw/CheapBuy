package com.example.andy.cheapbuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;

public class PasswordInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_input);

    }

    public  void gotoSignUp(View view){

        Intent i = new Intent(PasswordInput.this,SignUpInterface.class);
        startActivity(i);

    }

    public void enterMain(View view){

        EditText usernameInput = (EditText)findViewById(R.id.usernameInput);
        final EditText passwordInput = (EditText)findViewById(R.id.passwordInput);

        final ParseQuery<ParseObject> userObject = ParseQuery.getQuery("PersonInfo");
        userObject.whereEqualTo("username", usernameInput.getText().toString());
        userObject.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (object == null) {
                    TextView failureText = new TextView(getApplicationContext());
                    failureText.setText("Wrong Password");
                    failureText.setTextSize(20);
                    RelativeLayout layout = (RelativeLayout)findViewById(R.id.passwordInputID);
                    RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL,failureText.getId());
                    relativeParams.addRule(RelativeLayout.CENTER_VERTICAL,failureText.getId());
                    layout.addView(failureText, relativeParams);
                } else {

                    String passwordSaved = object.getString("password");

                    if (passwordSaved.equals( passwordInput.getText().toString())) {
                        Intent i = new Intent(PasswordInput.this, AccountSetting.class);
                        i.putExtra("userId", object.getObjectId());
                        startActivity(i);
                    } else {
                        TextView failureText = new TextView(getApplicationContext());
                        failureText.setText("Wrong Password");
                        failureText.setTextSize(20);
                        RelativeLayout layout = (RelativeLayout)findViewById(R.id.passwordInputID);
                        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                RelativeLayout.LayoutParams.WRAP_CONTENT);
                        relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL,failureText.getId());
                        relativeParams.addRule(RelativeLayout.CENTER_VERTICAL,failureText.getId());
                        layout.addView(failureText,relativeParams);

                    }
                }
            }
        });




    }
}
