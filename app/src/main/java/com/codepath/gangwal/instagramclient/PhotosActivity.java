package com.codepath.gangwal.instagramclient;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.gangwal.instagramclient.adapters.InstagramPhotoAdapter;
import com.codepath.gangwal.instagramclient.fragments.TaskDialog;
import com.codepath.gangwal.instagramclient.pojo.Comment;
import com.codepath.gangwal.instagramclient.pojo.InstagramPhoto;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PhotosActivity extends ActionBarActivity {

    private static final String CLIENT_ID="abd3ed67ab1d4d508e753f1b5787175d";
    private static final String TAG = PhotosActivity.class.getSimpleName();
    private ArrayList<InstagramPhoto> photos;
    InstagramPhotoAdapter aPhotos;
    private ListView lvPhotos;
    private ImageView ivLike;
    boolean liked =false;
    JSONArray photosJSON = null;
    private SwipeRefreshLayout swipeContainer;
    private TextView tvViewAllComments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchpopularPhotos(0);
            }
        });
        fetchpopularPhotos(0);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light);


        lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        tvViewAllComments = (TextView) findViewById(R.id.tvViewAllComment);

//        //Display something if the View is empty for some reason.
//        RelativeLayout empty=(RelativeLayout)findViewById(R.id.empty);
//        lvPhotos.setEmptyView(empty);

        photos = new ArrayList<InstagramPhoto>();
        aPhotos = new InstagramPhotoAdapter(this, 0, photos);

        lvPhotos.setAdapter(aPhotos);
//        fetchpopularPhotos(0);
//        setupSingleClickListner();
    }
    public void clickNew(View v)
    {
        //TODO update this logic
        ivLike = (ImageView)findViewById(R.id.ivLike);
            ivLike.setImageResource(R.drawable.feed_button_like_active);

    }


    /**
     * Edit the task on Single click
     */
//    private void setupSingleClickListner() {
//        lvPhotos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String text = photos.get(position).getComment1().toString();
//                try {
//                    JSONArray comments = photosJSON.getJSONObject(position).getJSONObject("comments").getJSONArray("data");
//                    showAddDialog(position, photos.get(position),comments);
////                Toast.makeText(this,"Toast ID " ,Toast.LENGTH_LONG).show();
//                    Log.i(TAG,"SClicked id" + id + " position " + position+ " "  +text);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }

//    private void setupViewAllCommentsListener() {
//        tvViewAllComments.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                String text = photos.get(position).getComment1().toString();
//                try {
//                    JSONArray comments = photosJSON.getJSONObject(position).getJSONObject("comments").getJSONArray("data");
//                    showAddDialog(position, photos.get(position), comments);
////                Toast.makeText(this,"Toast ID " ,Toast.LENGTH_LONG).show();
//                    Log.i(TAG, "SClicked id" + id + " position " + position + " " + text);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }

    private void showAddDialog(int index, InstagramPhoto item,JSONArray comments) {
        TaskDialog dailog = TaskDialog.
                getInstance(item, comments,getSupportFragmentManager());
        dailog.show(getSupportFragmentManager(),"");
    }
    private void fetchpopularPhotos(int page) {
        final Geocoder gCode = new Geocoder(this, Locale.ENGLISH);
    /* Client Id abd3ed67ab1d4d508e753f1b5787175d
    Media Popular  https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
    Response
    Type - {“data”=>[ JSONArray]=>”type”} (image or video)
    Caption - {“data”=>[ JSONArray]=>”caption”==>”text"}
        Url - {“data”=>[ JSONArray]=>”images”=>”standard_resolution”=>”url"}
        Author - {“data”=>[ JSONArray]=>”user”=>”username"}*/


        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        final String DATA= "data";
        client.get(url, null, new JsonHttpResponseHandler() {
            //On Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                int commentCount;
                try {
                    photosJSON = response.getJSONArray(DATA);
                    writeToFile(photosJSON.toString(2));
                    for (int i = 0; i < photosJSON.length(); i++) {
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
                        photo.setUsername(photoJSON.getJSONObject("user").getString("username"));
                        photo.setImageUrl(photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));

                        commentCount = photoJSON.getJSONObject("comments").getInt("count");

                        photo.setCommentsCount(commentCount);
                        photo.setCommentJson(photoJSON.getJSONObject("comments").getJSONArray("data"));
                        int availableCommet = photoJSON.getJSONObject("comments").getJSONArray("data").length();
                        //TODO Need to optimize this logic
                        if(commentCount>0)
                        {
                            String username = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(availableCommet - 1).getJSONObject("from").getString("username");
                            String commentText = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(availableCommet-1).getString("text");

                            photo.setComment1(new Comment(username,commentText));
                        }
                        if(commentCount>1)
                        {
                            String username = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(availableCommet - 2).getJSONObject("from").getString("username");
                            String commentText = photoJSON.getJSONObject("comments").getJSONArray("data").getJSONObject(availableCommet-2).getString("text");

                            photo.setComment2(new Comment(username, commentText));
                        }

                        photo.setProfilePicUrl(photoJSON.getJSONObject("user").getString("profile_picture"));
                        //Setting the relative time
                        String createdTime = photoJSON.getString("created_time");

                        photo.setCreateTime(getRelativeTime(createdTime));

                        //Convert lat and long to address
                        if(!photoJSON.isNull("location"))
                        {
                            double latitude = photoJSON.getJSONObject("location").getDouble("latitude");
                            double longitude = photoJSON.getJSONObject("location").getDouble("longitude");
                            Log.i(TAG,"Latitude " + latitude);
                            Log.i(TAG,"Longitude " + longitude);

                            List<Address> locations = null;
                            try {
                                locations = gCode.getFromLocation(latitude, longitude, 1);
                                if(locations.size()>0) {
                                    Address location = locations.get(0);
                                    Log.i(TAG, "Location "  + location.getFeatureName());
                                    StringBuffer printableAddress = new StringBuffer();
                                    for(int idx=0;idx<location.getMaxAddressLineIndex()-1;idx++)
                                    {
                                     printableAddress.append(location.getAddressLine(idx)) ;
                                    }
                                    Log.i(TAG,"Complete address " + printableAddress.toString());
                                    photo.setLocation(printableAddress.toString());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }


                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.getLocalizedMessage());
                }

                aPhotos.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);

//                super.onSuccess(statusCode, headers, response);
            }

            //On Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "Error " + responseString);
//                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });

    }
    public String getRelativeTime(String createdTime)
    {
        if(createdTime!=null) {
            CharSequence refCreatedTime = DateUtils.getRelativeTimeSpanString(Long.parseLong(createdTime)*1000);
            String  refCratedTimeString = refCreatedTime.toString().replace(" ago","");
            refCratedTimeString = refCratedTimeString.replace(" days","d");
            refCratedTimeString = refCratedTimeString.replace(" day","d");
            refCratedTimeString = refCratedTimeString.replace(" hours","h");
            refCratedTimeString = refCratedTimeString.replace(" hour","h");
            refCratedTimeString = refCratedTimeString.replace(" minutes","m");
            refCratedTimeString = refCratedTimeString.replace(" minute","m");
            //,System.currentTimeMillis(),DateUtils.MINUTE_IN_MILLIS,DateUtils.FORMAT_ABBREV_RELATIVE);
//                            Log.i(TAG, refCreatedTime.toString());
            return refCratedTimeString;
        }
        else
            return "";
    }
    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            Log.i(TAG,"File Written");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
