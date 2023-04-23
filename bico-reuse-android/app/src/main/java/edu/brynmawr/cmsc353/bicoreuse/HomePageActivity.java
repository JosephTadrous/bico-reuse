package edu.brynmawr.cmsc353.bicoreuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class HomePageActivity extends AppCompatActivity {
    RecycleViewAdapter adapter;
    RecyclerView recyclerView;
    private String userId;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        Intent intent= getIntent();

        userId= intent.getStringExtra("userId");
        

    }

    // method to inflate the options menu when
    // the user opens the menu for the first time
    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {

        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {

        switch (item.getItemId()){
            case R.id.profile:
                Intent i = new Intent(this, ProfileActivity.class);
                i.putExtra("id", userId);
                startActivity(i);
            case R.id.logOut:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCreateButtonClick(View v) {
        Intent i= new Intent(this, CreatePostActivity.class);
        i.putExtra("userId", userId);

        this.startActivity(i);
    }

    public void onBackPressed() {
        super.onBackPressed();
    }


    protected String message;

    public JSONArray connectToServer() {
        TextView tv = findViewById(R.id.statusField);
        JSONArray data = new JSONArray();
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/api");

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

    @Override
    public void onResume(){
        super.onResume();
        JSONArray dataArray = connectToServer();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        adapter = new RecycleViewAdapter(dataArray, getApplication(), this.userId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomePageActivity.this));

        ActionBar actionBar = getSupportActionBar();
    }
}