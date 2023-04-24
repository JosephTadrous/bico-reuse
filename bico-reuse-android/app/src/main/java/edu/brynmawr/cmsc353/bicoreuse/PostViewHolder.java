package edu.brynmawr.cmsc353.bicoreuse;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.os.Parcelable;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URL;

import edu.brynmawr.cmsc353.bicoreuse.info.PostInfo;
import org.json.JSONArray;


public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView sellerName;
    TextView title;
    TextView price;
    ImageView image;
    View view;

    private String postId;
    private String userId;
    private String imageURL;

    private JSONArray userBookmarks;



    PostViewHolder(View itemView, String userId, JSONArray userBookmarks) {
        super(itemView);
        sellerName = (TextView)itemView.findViewById(R.id.Name);
        // description = (TextView)itemView.findViewById(R.id.Desc);
        title = (TextView)itemView.findViewById(R.id.Title);
        price = (TextView)itemView.findViewById(R.id.Price);
        image = (ImageView)itemView.findViewById(R.id.Image);
        view  = itemView;
        this.userId= userId;
        this.userBookmarks = userBookmarks;
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
    }

    public void setPostId(String postId) {
        this.postId= postId;
    }

    public String getPostId() {
        return this.postId;
    }

    public void displayPhoto(String image) {
        imageURL = image;
        LoadImageURL loadImageURL = new LoadImageURL();
        loadImageURL.execute("");
    }
    @Override
    public void onClick(View v) {
        Intent i= new Intent (v.getContext(), PostActivity.class);
        i.putExtra("postId", postId);
        i.putExtra("userId", userId);
        i.putExtra("userBookmarks", userBookmarks.toString());
        v.getContext().startActivity(i);
    }

    private class LoadImageURL extends AsyncTask<String, Void, Bitmap> {
        protected void onPreExecute() {
            image.setVisibility(View.GONE);
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                URL url = new URL(imageURL);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                bmp = null;
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                Log.i("error", "can't get the photo");
                image.setImageBitmap(result);
            }

            image.setVisibility(View.VISIBLE);
        }
    }
}
