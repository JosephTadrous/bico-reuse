package edu.brynmawr.cmsc353.bicoreuse;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import edu.brynmawr.cmsc353.bicoreuse.info.PostInfo;
import edu.brynmawr.cmsc353.bicoreuse.info.UserInfo;

@SuppressWarnings("ALL")
public class PostActivity extends AppCompatActivity {
    private TextView tvTitle;
    private TextView tvSeller;
    private TextView tvDate;
    private TextView tvDescription;
    private TextView tvEmail;
    private TextView tvPhone;
    private Button btnDelete;
    private Button btnEdit;

    private UserInfo curUser;

    private PostInfo postInfo= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);


        Intent intent= getIntent();
        String postId= intent.getStringExtra("postId");
        String curUserId= intent.getStringExtra("curUserId");
        //curUser= new UserInfo("6418b08bddfa69be902df5f3");
        curUser= new UserInfo("64186a1d476ee3e3a7151640");
        postInfo= new PostInfo("6418b08bddfa69be902df5f4");



        tvTitle= findViewById(R.id.tvTitle);
        tvSeller= findViewById(R.id.tvSeller);
        tvDate= findViewById(R.id.tvDate);
        tvDescription= findViewById(R.id.tvDescription);
        tvEmail= findViewById(R.id.tvEmail);
        tvPhone= findViewById(R.id.tvPhone);
        btnDelete= findViewById(R.id.btnDelete);
        btnEdit= findViewById(R.id.btnEdit);

        tvTitle.setText(String.format("%s - $%.2f", postInfo.getTitle(), postInfo.getPrice()));
        tvSeller.setText(String.format("Seller: %s", postInfo.getSeller().getName()));
        tvEmail.setText(String.format("Email: %s", postInfo.getSeller().getEmail()));
        tvPhone.setText(String.format("Phone: %s", postInfo.getSeller().getPhone()));
        tvDate.setText(String.format("Posted: %s", postInfo.getDate()));
        tvDescription.setText(postInfo.getDescription());

        // set button visibilities if the user == seller
        if (postInfo.getSeller().getId().equals(curUser.getId())) {
            btnDelete.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.VISIBLE);
        } else {
            btnDelete.setVisibility(View.GONE);
            btnEdit.setVisibility(View.GONE);
        }
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDeleteButtonClick(View v) {

    }

    public void onEditButtonClick(View v) {

    }


}