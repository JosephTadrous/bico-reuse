package edu.brynmawr.cmsc353.bicoreuse;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {
    TextView sellerName;
    TextView date;
    TextView title;
    // TextView description;
    TextView status;
    TextView price;
    View view;

    PostViewHolder(View itemView) {
        super(itemView);
        sellerName = (TextView)itemView.findViewById(R.id.Name);
        date = (TextView)itemView.findViewById(R.id.Date);
        // description = (TextView)itemView.findViewById(R.id.Desc);
        title = (TextView)itemView.findViewById(R.id.Title);
        status = (TextView)itemView.findViewById(R.id.Status);
        price = (TextView)itemView.findViewById(R.id.Price);
        view  = itemView;
    }
}
