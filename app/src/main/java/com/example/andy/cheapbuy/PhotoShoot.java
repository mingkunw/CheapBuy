package com.example.andy.cheapbuy;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

import static android.provider.MediaStore.*;
import static com.example.andy.cheapbuy.R.id.cameraClick;
import static com.example.andy.cheapbuy.R.id.start;

public class PhotoShoot extends AppCompatActivity {

    private SellInfo sellinfo;
    private int TAKE_PHOTO = 1;
    private String userId;
    private Uri itemImageUri;

    private String itemId;
    private int photoNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sellinfo = new SellInfo();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_shoot);

        //ImageView cameraButton = (ImageView)findViewById(R.id.cameraClick);

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

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                itemId= null;
            } else {
                itemId= extras.getString("itemId");
            }
        } else {
            itemId= (String) savedInstanceState.getSerializable("itemId");
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                photoNum= 0;
            } else {
                photoNum = extras.getInt("photoNum");
            }
        } else {
            photoNum= (int) savedInstanceState.getSerializable("photoNum");
        }

        if(itemId != null){

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
            query.getInBackground(itemId, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        if(object.has("itemImage0")){
                            ImageButton imageButton1 = (ImageButton)findViewById(R.id.imagebutton1);
                            ParseFile image = object.getParseFile("itemImage0");
                            try {
                                byte[] imageData = image.getData();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                imageButton1.setBackgroundColor(-1);
                                imageButton1.setImageBitmap(bitmap);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                        }
                        if(object.has("itemImage1")){
                            ImageButton imageButton2 = (ImageButton)findViewById(R.id.imagebutton2);
                            ParseFile image = object.getParseFile("itemImage1");
                            try {
                                byte[] imageData = image.getData();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                imageButton2.setBackgroundColor(-1);
                                imageButton2.setImageBitmap(bitmap);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                        }
                        if(object.has("itemImage2")){
                            ImageButton imageButton3 = (ImageButton)findViewById(R.id.imagebutton3);
                            ParseFile image = object.getParseFile("itemImage2");
                            try {
                                byte[] imageData = image.getData();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                imageButton3.setBackgroundColor(-1);
                                imageButton3.setImageBitmap(bitmap);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }
                }
            });

        }


    }

    public void backToMain(View v){

        if(itemId == null)return;
        Log.d("nani",itemId);
        final EditText descriptionText = (EditText)findViewById(R.id.descriptionInput);
        final EditText priceText = (EditText)findViewById(R.id.priceInput);
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
        query.getInBackground(itemId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    Log.d("Description", descriptionText.getText().toString());
                    Log.d("Price", priceText.getText().toString());
                    object.put("Description",descriptionText.getText().toString());
                    object.put("Price",Integer.parseInt(priceText.getText().toString()));
                    //object.put("UserId",userId);
                    object.saveInBackground();
                }
            }
        });
        Intent i = new Intent(PhotoShoot.this, SellInterface.class);
        startActivity(i);

    }



    public void cameraClick(View view){

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"itemImage.jpg");

        itemImageUri = Uri.fromFile(photo);

        startActivityForResult(i, TAKE_PHOTO);

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){


        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        final Bitmap imageData = (Bitmap)bundle.get("data");

        Log.d("PHOTONUM","is"+photoNum);
        if(photoNum == 0){

            photoNum++;
            final ParseObject newItem = new ParseObject("Item");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageData.compress(Bitmap.CompressFormat.PNG, 4, stream);
            byte[] byteArray = stream.toByteArray();
            ParseFile file = new ParseFile("itemImage0",byteArray);
            newItem.put("itemImage0", file);
            newItem.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Intent i = new Intent(PhotoShoot.this, PhotoShoot.class);
                        itemId = newItem.getObjectId();

                        i.putExtra("userId", userId);
                        i.putExtra("itemId", itemId);
                        i.putExtra("photoNum", photoNum);
                        startActivity(i);
                    } else {

                    }
                }
            });

        }
        else if(photoNum == 1||photoNum == 2){
            Log.d("itemID",itemId+"111111");


            ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
            query.getInBackground(itemId, new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {


                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imageData.compress(Bitmap.CompressFormat.PNG, 4, stream);
                        byte[] byteArray = stream.toByteArray();
                        String name = "itemImage"+photoNum;

                        photoNum++;
                        ParseFile file = new ParseFile(name,byteArray);
                        object.put(name, file);
                        object.saveInBackground();

                        Intent i = new Intent(PhotoShoot.this,PhotoShoot.class);
                        i.putExtra("userId",userId);
                        i.putExtra("itemId",itemId);
                        i.putExtra("photoNum",photoNum);
                        startActivity(i);
                    }
                }
            });
        }
        else{}




    }

}
