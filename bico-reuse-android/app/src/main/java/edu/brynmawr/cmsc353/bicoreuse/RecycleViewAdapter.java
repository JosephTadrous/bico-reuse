package edu.brynmawr.cmsc353.bicoreuse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

import edu.brynmawr.cmsc353.bicoreuse.info.PostInfo;

public class RecycleViewAdapter extends RecyclerView.Adapter<PostViewHolder> {
    JSONArray dataArray = new JSONArray();
    private ImageView image;

    private String userId;
    private String postId;
    private String imageURL;
    private JSONArray userBookmarks;

    Context context;
//    ClickListener listiner;

    public RecycleViewAdapter(JSONArray dataArray, Context context, String userId, JSONArray userBookmarks) {
        this.dataArray = dataArray;
        this.context = context;
//        this.listiner = listiner;

        this.userId= userId;
        this.userBookmarks = userBookmarks;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout

        View photoView = inflater.inflate(R.layout.activity_individual_post_on_homepage, parent, false);

        PostViewHolder viewHolder = new PostViewHolder(photoView, userId, userBookmarks);
        return viewHolder;
    }

    @Override
    public void
    onBindViewHolder(final PostViewHolder viewHolder, final int position) {
//        final v = viewHolder.getAdapterPosition();
        try {
            JSONObject jo = dataArray.getJSONObject(position);
            viewHolder.setPostId(jo.getString("_id"));
            JSONObject seller = (JSONObject) jo.get("seller");
            String name = seller.get("name").toString();
            viewHolder.sellerName.setText(name);
            viewHolder.title.setText(dataArray.getJSONObject(position).get("title").toString());
            // viewHolder.description.setText(dataArray.getJSONObject(position).get("description").toString());
            viewHolder.price.setText("$" + dataArray.getJSONObject(position).get("price").toString());
//            postId = viewHolder.getPostId();
            Object imageArray = (Object) jo.get("photos");
            String image = (String) imageArray.toString();
            image = image.substring(1, image.length() - 1);
//                        Log.i("image URL", image);
            StringBuilder builder = new StringBuilder();


            for (char c : image.toCharArray()) {
                // 92 is the Ascii value of "\"
                if ((int) c != 92 && c != '"') {
//                                Log.i("char", c + "");
                    builder.append(c);
                }
            }

            imageURL = builder.toString();
            viewHolder.displayPhoto(imageURL);
//
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


//        viewHolder.OnClickListener clickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boolean isView = false;
//                for (View view : views) {
//
//                }
//            }
//        });
    }
    @Override
    public int getItemCount() {
        return dataArray.length();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
