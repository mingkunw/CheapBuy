package com.example.andy.cheapbuy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.provider.MediaStore.*;
import static com.example.andy.cheapbuy.R.id.cameraClick;
import static com.example.andy.cheapbuy.R.id.start;

public class PhotoShoot extends AppCompatActivity {

    private SellInfo sellinfo;
    private int TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sellinfo = new SellInfo();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_shoot);
        ImageView cameraButton = (ImageView)findViewById(R.id.cameraClick);



    }

    public void backToMain(View v){


        Intent i = new Intent(PhotoShoot.this, SellInterface.class);
        startActivity(i);

    }

    /*

    public void cameraClick(View view){

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
*/
}
