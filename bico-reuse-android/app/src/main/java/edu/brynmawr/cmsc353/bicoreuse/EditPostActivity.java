package edu.brynmawr.cmsc353.bicoreuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class EditPostActivity extends AppCompatActivity {

    private View ivSolidEdit;
    private TextView PostEdit;
    private TextView TitleEdit;
    private EditText TitleInputEdit;
    private TextView DescriptionEdit;
    private EditText DescriptionInputEdit;
    private TextView PriceEdit;
    private EditText PriceInputEdit;
    private TextView ImageEdit;
    private EditText ImageInputEdit;
    private TextView StatusEdit;
    private EditText StatusInputEdit;
    private Button SubmitEditButton;
    private Button CancelEditButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        ivSolidEdit = findViewById(R.id.ivSolidEdit);
        PostEdit = findViewById(R.id.PostEdit);
        TitleEdit = findViewById(R.id.TitleEdit);
        TitleInputEdit = findViewById(R.id.TitleInputEdit);
        DescriptionEdit = findViewById(R.id.DescriptionEdit);
        DescriptionInputEdit = findViewById(R.id.DescriptionInputEdit);
        PriceEdit = findViewById(R.id.PriceEdit);
        PriceInputEdit = findViewById(R.id.PriceInputEdit);
        ImageEdit = findViewById(R.id.ImageEdit);
        ImageInputEdit = findViewById(R.id.ImageInputEdit);

        StatusEdit = findViewById(R.id.StatusEdit);
        StatusInputEdit = findViewById(R.id.StatusInputEdit);

        CancelEditButton = findViewById(R.id.CancelEditButton);
        SubmitEditButton = findViewById(R.id.SubmitEditButton);
        SubmitEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitEditedPost();
            }
        });
    }

    private void submitEditedPost() {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/edit_post");

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST"); // POST request
                            conn.setDoOutput(true);

                            Map<String, String> requestData = new HashMap<>();
                            requestData.put("id", "6418b08bddfa69be902df5f4");
                            requestData.put("title", TitleInputEdit.getText().toString());
                            requestData.put("description", DescriptionInputEdit.getText().toString());
                            requestData.put("price", PriceInputEdit.getText().toString());
                            requestData.put("image", ImageInputEdit.getText().toString());
                            requestData.put("status", StatusInputEdit.getText().toString());

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
