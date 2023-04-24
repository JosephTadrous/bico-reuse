package edu.brynmawr.cmsc353.bicoreuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONArray;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BookmarksActivity extends AppCompatActivity {
    BookmarkViewAdapter adapter;
    RecyclerView recyclerView;
    private String userId;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarks);
        Intent intent= getIntent();

        userId = intent.getStringExtra("userId");
        type = intent.getStringExtra("type");


    }



    public void onBackPressed() {
        super.onBackPressed();
    }


    protected String message;

    public JSONArray getPosts() {
        TextView tv = findViewById(R.id.statusField);
        JSONArray data = new JSONArray();
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/" + type + "?id=" + userId);
                            Log.i("aywaa", url.toString());

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.connect();

                            Scanner in = new Scanner(url.openStream());
                            String response = in.nextLine();

                            JSONArray dataArray = new JSONArray(response);

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jo = dataArray.getJSONObject(i);
                                data.put(jo);
                                System.out.println("");
                                String title = jo.get("title").toString();
                                Log.i("A7AA", title);
                                message = jo.get("title").toString();
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            message = e.toString();
                        }
                    }
            );

            // this waits for up to 2 seconds
            // it's a bit of a hack because it's not truly asynchronous
            // but it should be okay for our purposes (and is a lot easier)
            executor.awaitTermination(2, TimeUnit.SECONDS);

            // now we can set the status in the TextView
            tv.setText(message);
        } catch (Exception e) {
            // uh oh
            e.printStackTrace();
//            tv.setText(e.toString());
        }
        return data;
    }

    private JSONArray bookmarked;

    public JSONArray isBookmarked() {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/profile?id=" + userId);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.connect();

                            Scanner in = new Scanner(url.openStream());
                            String response = in.nextLine();

                            JSONObject jo = new JSONObject(response);
                            // need to set the instance variable in the Activity object
                            // because we cannot directly access the TextView from here
                            bookmarked = jo.getJSONArray("bookmarked");
                        }
                        catch (Exception e) {
                            e.printStackTrace(); }
                    }
            );

            // this waits for up to 2 seconds
            // it's a bit of a hack because it's not truly asynchronous
            // but it should be okay for our purposes (and is a lot easier)
            executor.awaitTermination(2, TimeUnit.SECONDS);
            return bookmarked;

        }
        catch (Exception e) {
            // uh oh
            Log.e("aA", e.getMessage());
            e.printStackTrace();
        }
        return bookmarked;
    }

    @Override
    public void onResume(){
        super.onResume();
        JSONArray dataArray = getPosts();

        JSONArray userBookmarks = isBookmarked();


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new BookmarkViewAdapter(dataArray, getApplication(), this.userId, userBookmarks);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookmarksActivity.this));

        ActionBar actionBar = getSupportActionBar();
    }
}