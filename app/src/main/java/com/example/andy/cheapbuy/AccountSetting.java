package com.example.andy.cheapbuy;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AccountSetting extends AppCompatActivity {

    public String userId;
    public Uri selfieUri;
    private int TAKE_PHOTO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
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



        final TextView usernamePresent = (TextView)findViewById(R.id.username_changeable);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("PersonInfo");
        query.getInBackground(userId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    if (object.has("selfieimage")) {
                        ImageView profileImage = (ImageView)findViewById(R.id.personalInfoImage);
                        ParseFile image = object.getParseFile("selfieimage");
                        try {
                            byte[] imageData = image.getData();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                            profileImage.setImageBitmap(bitmap);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }

                    usernamePresent.setText(object.getString("username"));
                } else {
                }
            }
        });

    }

    public void takePhoto(View view){

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"selfie.jpg");

        selfieUri = Uri.fromFile(photo);



        startActivityForResult(i, TAKE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){


        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        final Bitmap imageData = (Bitmap)bundle.get("data");
        int width = imageData.getWidth();
        int height = imageData.getHeight();
        int crop;
        final Bitmap imageAfterCrop;
        if(width > height){
            crop = (width-height)/2;
            imageAfterCrop = Bitmap.createBitmap(imageData,crop,0,height,height);
        }
        else{
            crop = (height-width)/2;
            imageAfterCrop = Bitmap.createBitmap(imageData,0,crop,width,width);
        }
        ParseQuery<ParseObject> query = ParseQuery.getQuery("PersonInfo");
        query.getInBackground(userId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageAfterCrop.compress(Bitmap.CompressFormat.PNG, 4, stream);
                    byte[] byteArray = stream.toByteArray();
                    ParseFile file = new ParseFile("selfie.png",byteArray);
                    object.put("selfieimage", file);
                    object.saveInBackground();
                    Intent i = new Intent(AccountSetting.this,AccountSetting.class);
                    i.putExtra("userId",userId);
                    startActivity(i);
                }
            }
        });


    }

    public void pleaseInputName(View view){
        Intent i = new Intent(AccountSetting.this, NameSetting.class);
        startActivity(i);
    }

    public void passwordInputTrans(View view){
        Intent i = new Intent(AccountSetting.this, passwordSetting.class);
        i.putExtra("userId",userId);
        startActivity(i);

    }

}
