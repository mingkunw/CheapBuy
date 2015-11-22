package com.example.andy.cheapbuy;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SignUpInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_interface);
        Button completeButton = (Button) findViewById(R.id.completeButton);

    }

    public void gotoMain(View view) throws ParseException {

        EditText signupUsername = (EditText)findViewById(R.id.signupUsername);

        String username = signupUsername.getText().toString();


        final ParseQuery<ParseObject> userObject = ParseQuery.getQuery("PersonInfo");
        userObject.whereEqualTo("username", username);
        userObject.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null) {
                    TextView failureText = new TextView(getApplicationContext());
                    failureText.setText("Username Has Been Used");
                    failureText.setTextColor(Color.BLACK);
                    failureText.setTextSize(20);
                    RelativeLayout layout = (RelativeLayout) findViewById(R.id.signupInterfaceId);
                    RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    relativeParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, failureText.getId());
                    relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL, failureText.getId());

                    layout.addView(failureText, relativeParams);


                    } else {
                    if (e.getCode() == ParseException.OBJECT_NOT_FOUND) {
                        EditText signupUsername = (EditText) findViewById(R.id.signupUsername);
                        EditText signupPassword = (EditText) findViewById(R.id.singupPasswordInput);
                        EditText signupRepeat = (EditText) findViewById(R.id.signupRepeat);

                        String username = signupUsername.getText().toString();
                        String password = signupPassword.getText().toString();
                        String repeat = signupRepeat.getText().toString();

                        if (password.equals(repeat)) {

                            ParseObject Personinfo = new ParseObject("PersonInfo");
                            Personinfo.put("username", username);
                            Personinfo.put("password", password);
                            Personinfo.saveInBackground();
                            Intent i = new Intent(SignUpInterface.this, PasswordInput.class);
                            startActivity(i);

                        } else {

                            TextView failureText = new TextView(getApplicationContext());
                            failureText.setText("Password Incorrect");
                            failureText.setTextColor(Color.BLACK);
                            failureText.setTextSize(20);
                            RelativeLayout layout = (RelativeLayout) findViewById(R.id.signupInterfaceId);
                            RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT);
                            relativeParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, failureText.getId());
                            relativeParams.addRule(RelativeLayout.CENTER_HORIZONTAL, failureText.getId());

                            layout.addView(failureText, relativeParams);
                        }
                    }
                }
            }
        });




    }

}


