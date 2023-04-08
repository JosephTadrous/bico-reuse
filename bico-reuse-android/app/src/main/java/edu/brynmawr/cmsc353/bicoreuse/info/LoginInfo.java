package edu.brynmawr.cmsc353.bicoreuse.info;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class LoginInfo {
    private String email;
    private String password;
    private UserInfo userInfo= null;


    public LoginInfo(String email, String password) {
        this.email= email;
        this.password= password;
    }

    public UserInfo getData() {

        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                try {

                    URL url = new URL("http://10.0.2.2:3000/login_user");

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");

                    conn.setDoOutput(true);
                    Map<String, String> requestData= new HashMap<>();


                    requestData.put("email", email);
                    requestData.put("password", password);

                    DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                    out.writeBytes(getUrlEncodedData(requestData));
                    out.flush();
                    out.close();


                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // should only be an id
                        InputStream in = conn.getInputStream();
                        JSONParser jsonParser= new JSONParser();
                        JSONObject jsonObject= (JSONObject)jsonParser.parse(
                                new InputStreamReader(in, "UTF-8"));

                        String id= (String) jsonObject.get("_id");
                        String name= (String) jsonObject.get("name");
                        String college= (String) jsonObject.get("college");
                        String phone= (String) jsonObject.get("phone");

                        // this will now be set as our current user
                        userInfo= new UserInfo(id, name, college, email, phone);


                    } else {
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

        return userInfo;
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

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
