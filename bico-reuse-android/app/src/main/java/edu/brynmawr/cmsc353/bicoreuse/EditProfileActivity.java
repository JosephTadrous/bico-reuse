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

public class EditProfileActivity extends AppCompatActivity {

    private View ivSolidEdit;
    private TextView tvProfileEdit;
    private TextView tvNameEdit;
    private EditText etNameInputEdit;
    private TextView tvCollegeEdit;
    private EditText etCollegeInputEdit;
    private TextView tvEmailEdit;
    private TextView tvEmailInputEdit;
    private TextView tvPhoneEdit;
    private EditText etPhoneInputEdit;
    private Button btnSubmitEdit;
    private String id;
    private String name;
    private String college;
    private String email;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        id = getIntent().getStringExtra("ID");
        name = getIntent().getStringExtra("name");
        college = getIntent().getStringExtra("college");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");

        ivSolidEdit = findViewById(R.id.ivSolidEdit);
        tvProfileEdit = findViewById(R.id.tvProfileEdit);
        tvNameEdit = findViewById(R.id.tvNameEdit);
        etNameInputEdit = findViewById(R.id.etNameInputEdit);
        etNameInputEdit.setText(name);
        tvCollegeEdit = findViewById(R.id.tvCollegeEdit);
        etCollegeInputEdit = findViewById(R.id.etCollegeInputEdit);
        etCollegeInputEdit.setText(college);
        tvEmailEdit = findViewById(R.id.tvEmailEdit);
        tvEmailInputEdit = findViewById(R.id.tvEmailInputEdit);
        tvEmailInputEdit.setText(email);
        tvPhoneEdit = findViewById(R.id.tvPhoneEdit);
        etPhoneInputEdit = findViewById(R.id.etPhoneInputEdit);
        etPhoneInputEdit.setText(phone);
        btnSubmitEdit = findViewById(R.id.btnSubmitEdit);
        btnSubmitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean existsError= false;

                if (etNameInputEdit.getText().toString().isEmpty()) {
                    etNameInputEdit.setError("Name is required");
                    existsError= true;
                }
                if (etCollegeInputEdit.getText().toString().isEmpty()) {
                    etCollegeInputEdit.setError("College is required");
                    existsError= true;
                }
                if (etPhoneInputEdit.getText().toString().isEmpty()) {
                    etPhoneInputEdit.setError("Phone Number is required");
                    existsError= true;
                }

                if(existsError) {
                    Toast.makeText(getApplicationContext(),
                            "Please fill out all the fields!",
                            Toast.LENGTH_LONG).show();
                } else {
                    submitData();
                }
            }
        });
    }

    private void submitData() {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                            // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/update?id=" + id);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST"); // POST request
                            conn.setDoOutput(true);

                            Map<String, String> requestData = new HashMap<>();
                            requestData.put("name", etNameInputEdit.getText().toString());
                            requestData.put("college", etCollegeInputEdit.getText().toString());
                            requestData.put("phone", etPhoneInputEdit.getText().toString());

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
            this.finish();




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