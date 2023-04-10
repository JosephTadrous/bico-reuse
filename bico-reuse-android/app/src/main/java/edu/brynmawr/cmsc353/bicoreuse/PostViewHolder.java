package edu.brynmawr.cmsc353.bicoreuse;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView sellerName;
    TextView title;
    // TextView description;
    TextView price;
    View view;

    private String postId;
    private String userId;



    PostViewHolder(View itemView, String userId) {
        super(itemView);
        sellerName = (TextView)itemView.findViewById(R.id.Name);
        // description = (TextView)itemView.findViewById(R.id.Desc);
        title = (TextView)itemView.findViewById(R.id.Title);
        price = (TextView)itemView.findViewById(R.id.Price);
        view  = itemView;
        this.userId= userId;
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

    }

    public void setPostId(String postId) {
        this.postId= postId;
    }

    @Override
    public void onClick(View v) {
        Intent i= new Intent (v.getContext(), PostActivity.class);
        i.putExtra("postId", postId);
        i.putExtra("userId", userId);
        v.getContext().startActivity(i);

    }
}
