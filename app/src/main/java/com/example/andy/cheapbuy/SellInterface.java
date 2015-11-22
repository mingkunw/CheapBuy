package com.example.andy.cheapbuy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SellInterface extends AppCompatActivity {

    GridView mGrid;
    SellItemImageAdapter adapter;
    ArrayList<String> image_url = new ArrayList<String>();
    ArrayList<Bitmap> images = new ArrayList<Bitmap>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_interface);



        adapter = new SellItemImageAdapter(getLayoutInflater(), this, R.layout.sell_interface_unit, image_url);

        refreshContent();

        //mGrid = (GridView) findViewById(R.id.sell_item_grid);
       // mGrid.setAdapter(adapter);
//        mGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            }
//        });
//        final int endTrigger = 2;
//        mGrid.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if (mGrid.getCount() != 0
//                        && mGrid.getLastVisiblePosition() >= (mGrid.getCount() - 1) - endTrigger) {
//                    loadMore();
//                }
//            }
//        });

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent();
                mSwipeRefreshLayout.setRefreshing(false);
            }


        });

    }

    private void renderImage() {
        RelativeLayout sellLayout = (RelativeLayout) findViewById(R.id.sellInterface);

        ImageView leftDown, rightDown;


    }

    private void refreshContent() {
        images.clear();
        image_url.clear();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
        query.orderByDescending("updateAt");
        query.setLimit(8);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    for (ParseObject object : objects) {
                        String id = object.getObjectId();
                        if (object.has("ItemImage")) {
                            ParseFile image = object.getParseFile("ItemImage");
                            try {
                                byte[] imageData = image.getData();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                images.add(bitmap);
                                image_url.add("");
                                //adapter.notifyDataSetChanged();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else {

                }
            }
        });
    }

    private void loadMore() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Item");
        query.orderByDescending("updateAt");
        query.setSkip(images.size());
        query.setLimit(8);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    Log.d("Pull", "" + objects.size());
                    for (ParseObject object : objects) {
                        String id = object.getObjectId();
                        if (object.has("ItemImage")) {
                            ParseFile image = object.getParseFile("ItemImage");
                            try {
                                byte[] imageData = image.getData();
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                                images.add(bitmap);
                                image_url.add("");
                                //adapter.notifyDataSetChanged();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                } else {

                }
            }
        });
    }

    private class SellItemImageAdapter extends ArrayAdapter<String> {
        LayoutInflater inflater;
        Context context;
        ArrayList<String> image_url;
        int layoutResID;

        public SellItemImageAdapter(LayoutInflater lf_in, Context context_in, int layoutResourceID_in,
                                    ArrayList<String> listItemsImage_in) {
            super(context_in, layoutResourceID_in, listItemsImage_in);
            this.inflater = lf_in;
            this.context = context_in;
            this.image_url = listItemsImage_in;
            this.layoutResID = layoutResourceID_in;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v;

            if(convertView == null){

                LayoutInflater cellLayout = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(layoutResID, parent, false);
            }
            else {
                v = convertView;
            }



            ImageView image = (ImageView) v.findViewById(R.id.sell_box_image);

            if (images.size() > position) {
                image.setImageBitmap(images.get(position));
            }

            return v;
        }
    }


}
