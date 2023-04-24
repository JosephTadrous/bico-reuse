package edu.brynmawr.cmsc353.bicoreuse;

import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.brynmawr.cmsc353.bicoreuse.info.RegistrationInfo;


public class RegisterActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etCollege;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etPasswordConfirm;

    RegistrationInfo registrationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.etName);
        etCollege = findViewById(R.id.etCollege);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        etPhone = findViewById(R.id.etPhone);

        Intent intent= getIntent();
        String email= intent.getStringExtra("email");

        etEmail.setText(email);

        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    // gets the information inputted by the user
    private String getInformation(EditText text) {
        return text.getText().toString();
    }

    public void onCancelButtonClick(View v) {
        this.finish();
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

    public void onRegisterButtonClick(View v) {
        String name= getInformation(etName);
        String college= getInformation(etCollege);
        String email= getInformation(etEmail);
        String password= getInformation(etPassword);
        String passwordReconfirm= getInformation(etPasswordConfirm);
        String phone= getInformation(etPhone);


        boolean existsError= false;

        if (name.isEmpty()) {
            etName.setError("Name is required");
            existsError= true;
        }
        if (college.isEmpty()) {
            etCollege.setError("College is required");
            existsError= true;
        }
        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            existsError= true;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            existsError= true;
        }
        if (passwordReconfirm.isEmpty()) {
            etPassword.setError("Reconfirm your password");
            existsError= true;
        }
        if (phone.isEmpty()) {
            etPhone.setError("Phone Number is required");
            existsError= true;
        }
        if (!passwordReconfirm.equals(password)) {
            etPasswordConfirm.setError("Passwords do not match");
            etPassword.setError("Passwords do not match");
            existsError= true;
        }

        if (!existsError) {
            RegistrationInfo registrationInfo= new RegistrationInfo(name, college, email, password, phone);

            registrationInfo.submitData();
            int registrationStatus= registrationInfo.getSuccessStatus();
            if (registrationStatus == 0) {
                Toast.makeText(getApplicationContext(),
                        "You have successfully registered!\n You can now login!",
                        Toast.LENGTH_LONG).show();

                this.finish();
            } else if (registrationStatus == 1) {
                Toast.makeText(getApplicationContext(),
                        "This email is already registered!",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Something went wrong. Try again!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}