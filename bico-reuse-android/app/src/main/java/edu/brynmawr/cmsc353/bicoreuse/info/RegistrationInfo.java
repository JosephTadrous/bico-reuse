package edu.brynmawr.cmsc353.bicoreuse.info;

import android.util.Log;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RegistrationInfo {
    private String name;
    private String college;
    private String email;
    private String password;
    private String phone;

    private boolean success= false;

    public RegistrationInfo(String name, String college, String email, String password, String phone) {
        this.name= name;
        this.college= college;
        this.email= email;
        this.password= password;
        this.phone= phone;
    }

    public void submitData() {
        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                        try {
                            URL url = new URL("http://10.0.2.2:3000/create_user");

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");

                            conn.setDoOutput(true);
                            Map<String, String> requestData= new HashMap<>();

                            requestData.put("name", name);
                            requestData.put("college", college);
                            requestData.put("email", email);
                            requestData.put("phone", phone);
                            requestData.put("password", password);

                            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                            out.writeBytes(getUrlEncodedData(requestData));
                            out.flush();
                            out.close();


                            int responseCode = conn.getResponseCode();
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                success= true;
                            } else {
                                success= false;
                            }


                        }
                        catch (Exception e) {
                            Log.v("RegistrationInfo", e.getMessage());
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

    public boolean isSuccess() {
        return success;
    }
}
