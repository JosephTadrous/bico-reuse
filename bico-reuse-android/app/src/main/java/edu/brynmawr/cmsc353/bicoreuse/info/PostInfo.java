package edu.brynmawr.cmsc353.bicoreuse.info;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PostInfo {


    public final String id;
    private String title;
    private String description;
    private Double price;
    private String date;
    private String status;
    private UserInfo seller;

    private boolean success;

    public PostInfo(String id) {
        this.id= id;
        this.updateData();

    }


    public boolean updateData() {
        success= false;

        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute( () -> {
                try {

                    URL url = new URL("http://10.0.2.2:3000/post?pid=" + id);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int responseCode = conn.getResponseCode();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream in = conn.getInputStream();
                        JSONParser jsonParser= new JSONParser();
                        JSONObject jsonObject= (JSONObject)jsonParser.parse(
                                new InputStreamReader(in, "UTF-8"));

                        this.title= (String) jsonObject.get("title");
                        this.description= (String) jsonObject.get("description");
                        this.price= ((Number) jsonObject.get("price")).doubleValue();
                        this.date= (String) jsonObject.get("date");
                        this.status= (String) jsonObject.get("status");



                        JSONObject sellerObject= (JSONObject) jsonObject.get("seller");
                        this.seller= new UserInfo(sellerObject);

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

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getDate() {
        String d= String.format("%s/%s/%s", date.substring(5,7), date.substring(8,10), date.substring(0, 4));
        return d;
    }

    public String getStatus() {
        return status;
    }

    public UserInfo getSeller() {
        return seller;
    }
}
