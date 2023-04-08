package edu.brynmawr.cmsc353.bicoreuse;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.brynmawr.cmsc353.bicoreuse.info.RegistrationInfo;


public class RegistrationActivity extends AppCompatActivity {
    private EditText etName;
    private EditText etCollege;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPhone;

    RegistrationInfo registrationInfo;

    private String name;
    private String college;
    private String email;
    private String password;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.name);
        etCollege = findViewById(R.id.college);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etPhone = findViewById(R.id.phone);
    }



    // gets the information inputted by the user
    private String getInformation(EditText text) {
        return text.getText().toString();
    }

    public void onRegistrationButtonClick(View v) {
        name= getInformation(etName);
        college= getInformation(etCollege);
        email= getInformation(etEmail);
        password= getInformation(etPassword);
        phone= getInformation(etPhone);

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
        if (phone.isEmpty()) {
            etPhone.setError("Phone Number is required");
            existsError= true;
        }

        if (existsError) {
            Toast.makeText(getApplicationContext(),
                    "One or more fields are incorrect/missing",
                    Toast.LENGTH_LONG).show();
        } else {
            RegistrationInfo registrationInfo= new RegistrationInfo(name, college, email, password, phone);

            registrationInfo.submitData();
            Toast.makeText(getApplicationContext(),
                    "Successfully created a user!\n You can now login!",
                    Toast.LENGTH_LONG).show();
            
        }
    }

}