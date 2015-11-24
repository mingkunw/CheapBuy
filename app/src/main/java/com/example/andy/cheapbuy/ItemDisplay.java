package com.example.andy.cheapbuy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ItemDisplay extends AppCompatActivity {

    private String itemId;
    private int imageNum = 0;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_display);
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
                imageNum= 0;
            } else {
                imageNum= extras.getInt("imageNum");
            }
        } else {
            imageNum= (int) savedInstanceState.getSerializable("imageNum");
        }

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                userId = null;
            } else {
                userId= extras.getString("userId");
            }
        } else {
            userId= (String) savedInstanceState.getSerializable("userId");
        }

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
        query.getInBackground(itemId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    if (object.has("itemImage0")) {
                        ImageView displayImage = (ImageView)findViewById(R.id.imageDisplay);
                        ParseFile image = object.getParseFile("itemImage0");
                        try {
                            byte[] imageData = image.getData();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                            displayImage.setImageBitmap(bitmap);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                }
            }
        });
    }

    private int next(int num){

        if(num == 2) return 0;
        else{
            return num+1;
        }
    }

    public void moreImageFunction(View view){


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
        query.getInBackground(itemId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    imageNum = next(imageNum);
                    String item = "itemImage"+imageNum;
                    if (object.has(item)) {
                        ImageView displayImage = (ImageView)findViewById(R.id.imageDisplay);
                        ParseFile image = object.getParseFile(item);
                        try {
                            byte[] imageData = image.getData();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                            displayImage.setImageBitmap(bitmap);
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                }
            }
        });

    }

    public void buyItFunction(View view){

        Intent i = new Intent(ItemDisplay.this,DiscussionAndBuy.class);
        i.putExtra("userId",userId);
        i.putExtra("itemId",itemId);
        startActivity(i);
    }

}
