package edu.brynmawr.cmsc353.bicoreuse;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.net.MalformedURLException;
import java.net.URL;
import android.widget.ProgressBar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import edu.brynmawr.cmsc353.bicoreuse.info.PostInfo;
import edu.brynmawr.cmsc353.bicoreuse.info.UserInfo;

@SuppressWarnings("ALL")
public class PostActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvSeller;
    private TextView tvDate;
    private ImageView image;
    private TextView tvDescription;
    private TextView tvEmail;
    private TextView tvPhone;
    private Button btnDelete;
    private Button btnEdit;
    private ToggleButton btnBookmark;

    private UserInfo curUser;
    private PostInfo postInfo= null;
    private JSONArray bookmarkedPosts = null;
    private List<String> bookmarkedIds = new ArrayList<>();

    private String postId;
    private String userId;
    private String imageURl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent intent= getIntent();
        postId= intent.getStringExtra("postId");
        userId= intent.getStringExtra("userId");
        String getBookmarks = intent.getStringExtra("userBookmarks");
        try {
            bookmarkedPosts = new JSONArray(getBookmarks);
            for (int i = 0; i < bookmarkedPosts.length(); i++) {
                bookmarkedIds.add(bookmarkedPosts.getJSONObject(i).getString("_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        curUser= new UserInfo(userId);
        postInfo= new PostInfo(postId);

        tvTitle= findViewById(R.id.tvTitle);
        tvSeller= findViewById(R.id.tvSeller);
        tvDate= findViewById(R.id.tvDate);
        tvDescription= findViewById(R.id.tvDescription);
        tvEmail= findViewById(R.id.tvEmail);
        tvPhone= findViewById(R.id.tvPhone);
        btnDelete= findViewById(R.id.btnDelete);
        btnEdit= findViewById(R.id.btnEdit);
        btnBookmark = findViewById(R.id.btnBookmark);
    }

    public void onDeleteButtonClick(View v) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/delete_post");

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST"); // POST request
                            conn.setDoOutput(true);

                            Map<String, String> requestData = new HashMap<>();
                            requestData.put("id", postId);
                            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                            out.writeBytes(getUrlEncodedData(requestData));
                            out.flush();
                            out.close();


                            int responseCode = conn.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                InputStream in = conn.getInputStream();
                            } else {
                                // Handle error response from server
                            }
                        }
                        catch (Exception e) {
                            Log.i("ERR", e.getMessage());
                            e.printStackTrace();
                        }
                    }
            );

            // this waits for up to 2 seconds
            // it's a bit of a hack because it's not truly asynchronous
            // but it should be okay for our purposes (and is a lot easier)
            executor.awaitTermination(2, TimeUnit.SECONDS);

        }
        catch (Exception e) {
            // uh oh
            e.printStackTrace();
        }


        this.finish();
    }

    private String getUrlEncodedData(Map<String, String> data) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (String key : data.keySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(key).append("=").append(data.get(key));
        }
        return builder.toString();
    }

    public void viewSellerInformation(View v) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("id", postInfo.getSeller().getId());
        i.putExtra("curUserId", curUser.getId());

        startActivity(i);
    }

    public void onEditButtonClick(View v) {
        Intent i= new Intent(this, EditPostActivity.class);
        i.putExtra("postId", postId);
        i.putExtra("title", postInfo.getTitle());
        i.putExtra("description", postInfo.getDescription());
        i.putExtra("price", postInfo.getPrice().toString());
        i.putExtra("status", postInfo.getStatus());
        i.putExtra("image", postInfo.getImage());
        this.startActivity(i);
    }

    public void updateBookmark(boolean add) {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"
                            URL url;
                            if (add) {
                                url = new URL("http://10.0.2.2:3000/add_bookmark");
                            } else {
                                url = new URL("http://10.0.2.2:3000/delete_bookmark");
                            }


                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST"); // POST request
                            conn.setDoOutput(true);

                            Map<String, String> requestData = new HashMap<>();
                            requestData.put("postId", postId);
                            requestData.put("userId", userId);
                            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                            out.writeBytes(getUrlEncodedData(requestData));
                            out.flush();
                            out.close();


                            int responseCode = conn.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                InputStream in = conn.getInputStream();
                            } else {
                                // Handle error response from server
                            }
                        }
                        catch (Exception e) {
                            Log.i("ERR", e.getMessage());
                            e.printStackTrace();
                        }
                    }
            );

            // this waits for up to 2 seconds
            // it's a bit of a hack because it's not truly asynchronous
            // but it should be okay for our purposes (and is a lot easier)
            executor.awaitTermination(2, TimeUnit.SECONDS);

        }
        catch (Exception e) {
            // uh oh
            e.printStackTrace();
        }

    }

    public void isBookmarked() {
        for (int i = 0; i < bookmarkedIds.size(); i++) {
            if (bookmarkedIds.get(i).equals(postId)) {
                btnBookmark.setChecked(true);
            }
        }
    }


    @Override
    public void onResume(){
        super.onResume();

        Intent intent= getIntent();
        postId= intent.getStringExtra("postId");
        userId= intent.getStringExtra("userId");

        curUser= new UserInfo(userId);
        postInfo= new PostInfo(postId);
        imageURl = postInfo.getImage();

        image = findViewById(R.id.image);
        tvTitle= findViewById(R.id.tvTitle);
        tvSeller= findViewById(R.id.tvSeller);
        tvDate= findViewById(R.id.tvDate);
        tvDescription= findViewById(R.id.tvDescription);
        tvEmail= findViewById(R.id.tvEmail);
        tvPhone= findViewById(R.id.tvPhone);
        btnDelete= findViewById(R.id.btnDelete);
        btnEdit= findViewById(R.id.btnEdit);

        tvTitle.setText(String.format("%s - $%.2f", postInfo.getTitle(), postInfo.getPrice()));
        tvSeller.setText(String.format("Seller: %s", postInfo.getSeller().getName()));
        tvEmail.setText(String.format("Email: %s", postInfo.getSeller().getEmail()));
        tvPhone.setText(String.format("Phone: %s", postInfo.getSeller().getPhone()));
        tvDate.setText(String.format("Posted: %s", postInfo.getDate()));
        tvDescription.setText(postInfo.getDescription());

        LoadImageURL loadImageURL = new LoadImageURL();
        loadImageURL.execute("");



        // set button visibilities if the user == seller
        if (postInfo.getSeller().getId().equals(curUser.getId())) {
            btnDelete.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        }

        this.isBookmarked();
        btnBookmark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    updateBookmark(true);
                } else {
                    updateBookmark(false);
                }
            }
        });


    }

    private class LoadImageURL extends AsyncTask<String, Void, Bitmap> {
        protected void onPreExecute() {
            image.setVisibility(View.GONE);
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;

            try {
                // imageURl is a global String variable, used to store the string URL of the image
                Log.i("Error", imageURl);
                URL url = new URL(imageURl);

                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                Log.e("Do In Background: ", e.getMessage());
                e.printStackTrace();
                bmp = null;
            }
            return bmp;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                Log.i("error", "can't get the photo");
                image.setImageBitmap(result);
            }

            image.setVisibility(View.VISIBLE);
        }
    }

}