package edu.brynmawr.cmsc353.bicoreuse.info;

import android.util.Log;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class UserInfo {
    private String id;
    private String name;
    private String college;
    private String email;
    private String phone;

    private boolean success;

    public UserInfo(String id, String name, String college, String email, String phone) {
        this.id = id;
        this.name = name;
        this.college = college;
        this.email = email;
        this.phone = phone;
    }

    public UserInfo(JSONObject user) {
        this.id= (String) user.get("_id");
        this.name= (String) user.get("name");
        this.email= (String) user.get("email");
        this.phone= (String) user.get("phone");
        this.college= (String) user.get("college");
    }

    public UserInfo(String id) {
        this.id= id;
        this.updateData();
    }

    public boolean updateData() {
        success= false;
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                try {

                    URL url = new URL("http://10.0.2.2:3000/profile?id=" + id);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream in = conn.getInputStream();
                        JSONParser jsonParser= new JSONParser();
                        JSONObject jsonObject= (JSONObject)jsonParser.parse(
                                new InputStreamReader(in, "UTF-8"));

                        this.name= (String) jsonObject.get("name");
                        this.email= (String) jsonObject.get("email");
                        this.college= (String) jsonObject.get("college");
                        this.phone= (String) jsonObject.get("phone");

                        success= true;

                    } else {
                        success= false;
                        // Handle error response from server
                    }
                }
                catch (Exception e) {
                    Log.v("LoginInfo", e.getMessage());
                    e.printStackTrace();
                }
            });

            // this waits for up to 2 seconds
            // it's a bit of a hack because it's not truly asynchronous
            // but it should be okay for our purposes (and is a lot easier)
            executor.awaitTermination(2, TimeUnit.SECONDS);

        }
        catch (Exception e) {
            // uh oh
            e.printStackTrace();
        }
        return success;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCollege() {
        return college;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
