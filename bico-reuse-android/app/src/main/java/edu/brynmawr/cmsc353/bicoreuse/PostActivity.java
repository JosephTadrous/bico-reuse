package edu.brynmawr.cmsc353.bicoreuse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import edu.brynmawr.cmsc353.bicoreuse.databinding.ActivityPostBinding;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ActivityPostBinding binding= DataBindingUtil.setContentView(this, R.layout.activity_post);
    }


}