package edu.brynmawr.cmsc353.bicoreuse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecycleViewAdapter extends RecyclerView.Adapter<PostViewHolder> {
    JSONArray dataArray = new JSONArray();

    private String userId;
    private String postId;

    Context context;
//    ClickListener listiner;

    public RecycleViewAdapter(JSONArray dataArray, Context context, String userId) {
        this.dataArray = dataArray;
        this.context = context;
//        this.listiner = listiner;

        this.userId= userId;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the layout

        View photoView = inflater.inflate(R.layout.activity_individual_post_on_homepage, parent, false);

        PostViewHolder viewHolder = new PostViewHolder(photoView, userId);
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
