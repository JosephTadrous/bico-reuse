package edu.brynmawr.cmsc353.bicoreuse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class ProfileActivity extends AppCompatActivity {

    private View ivSolid;
    private TextView tvProfile;
    private TextView tvName;
    private TextView tvNameInput;
    private TextView tvCollege;
    private TextView tvCollegeInput;
    private TextView tvEmail;
    private TextView tvEmailInput;
    private TextView tvPhone;
    private TextView tvPhoneInput;
    private TextView tvPostsHistory;
    private Button btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivSolid = findViewById(R.id.ivSolid);
        tvProfile = findViewById(R.id.tvProfile);
        tvName = findViewById(R.id.tvName);
        tvNameInput = findViewById(R.id.tvNameInput);
        tvCollege = findViewById(R.id.tvCollege);
        tvCollegeInput = findViewById(R.id.tvCollegeInput);
        tvEmail = findViewById(R.id.tvEmail);
        tvEmailInput = findViewById(R.id.tvEmailInput);
        tvPhone = findViewById(R.id.tvPhone);
        tvPhoneInput = findViewById(R.id.tvPhoneInput);
        tvPostsHistory = findViewById(R.id.tvPostsHistory);

        loadData();

    }


    protected String name;
    protected String college;
    protected String email;
    protected String phone;
    protected String id = "6418b08bddfa69be902df5f3";

    public void loadData() {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            // assumes that there is a server running on the AVD's host on port 3000
                            // and that it has a /test endpoint that returns a JSON object with
                             // a field called "message"

                            URL url = new URL("http://10.0.2.2:3000/profile?id=" + id);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("GET");
                            conn.connect();

                            Scanner in = new Scanner(url.openStream());
                            String response = in.nextLine();

                            JSONObject jo = new JSONObject(response);

                            // need to set the instance variable in the Activity object
                            // because we cannot directly access the TextView from here
                            name = jo.getString("name");
                            college = jo.getString("college");
                            email = jo.getString("email");
                            phone = jo.getString("phone");
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Log.i("ERR", e.toString());
                        }
                    }
            );

            // this waits for up to 2 seconds
            // it's a bit of a hack because it's not truly asynchronous
            // but it should be okay for our purposes (and is a lot easier)
            executor.awaitTermination(2, TimeUnit.SECONDS);

            // now we can set the status in the TextView
            tvNameInput.setText(name);
            tvCollegeInput.setText(college);
            tvEmailInput.setText(email);
            tvPhoneInput.setText(phone);
        }
        catch (Exception e) {
            // uh oh
            e.printStackTrace();
        }
    }

    // redirects to edit profile activity
    public void onEditButtonClick(View v) {
        Intent i = new Intent(this, EditProfileActivity.class);
        i.putExtra("ID", id);
        i.putExtra("name", name);
        i.putExtra("college", college);
        i.putExtra("email", email);
        i.putExtra("phone", phone);
        this.startActivity(i);
    }
}