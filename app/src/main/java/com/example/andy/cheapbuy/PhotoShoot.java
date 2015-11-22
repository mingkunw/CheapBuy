package com.example.andy.cheapbuy;

import android.content.Intent;
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

import java.io.File;

import static android.provider.MediaStore.*;
import static com.example.andy.cheapbuy.R.id.cameraClick;
import static com.example.andy.cheapbuy.R.id.start;

public class PhotoShoot extends AppCompatActivity {

    private SellInfo sellinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sellinfo = new SellInfo();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_shoot);
        ImageView cameraButton = (ImageView)findViewById(R.id.cameraClick);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File imagesFolder = new File(Environment.getExternalStorageDirectory(),"myImages");
                imagesFolder.mkdirs();
                File image = new File(imagesFolder, "image_001.jpg");
                Uri uriSavedImage = Uri.fromFile(image);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
                startActivityForResult(i, 0);
            }
        });

    }

    public void backToMain(View v){


        Intent i = new Intent(PhotoShoot.this, SellInterface.class);
        startActivity(i);

    }


}
