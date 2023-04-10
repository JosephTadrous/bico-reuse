package edu.brynmawr.cmsc353.bicoreuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CreatePostActivity extends AppCompatActivity {
    
    private EditText TitleInput;
    private EditText DescriptionInput;
    private EditText PhotoInput;
    private EditText PriceInput;
    private Button btnSubmitCreatePost;
    
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post);
        
        Intent intent= getIntent();
        userId= intent.getStringExtra("userId");
        
        TitleInput = findViewById(R.id.TitleInput);
        DescriptionInput = findViewById(R.id.DescriptionInput);
        PhotoInput = findViewById(R.id.PhotoInput);
        PriceInput = findViewById(R.id.PriceInput);

        btnSubmitCreatePost = findViewById(R.id.btnSubmitCreatePost);
        btnSubmitCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDataCreatePost();
            }
        });
        
        
    }

    private void submitDataCreatePost() {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/create_post");

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST"); // POST request
                            conn.setDoOutput(true);

                            Map<String, String> requestData = new HashMap<>();
                            requestData.put("title", TitleInput.getText().toString());
                            requestData.put("user_id", userId);
                            requestData.put("description", DescriptionInput.getText().toString());
                            requestData.put("price", PriceInput.getText().toString());
                            requestData.put("photo", PhotoInput.getText().toString());

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

    // format data to be sent in the request body
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
}
