package edu.brynmawr.cmsc353.bicoreuse;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

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
    }



    // gets the information inputted by the user
    private String getInformation(EditText text) {
        return text.getText().toString();
    }

    public void onCancelButtonClick(View v) {
        this.finish();
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
            if (registrationInfo.isSuccess()) {
                Toast.makeText(getApplicationContext(),
                        "You have successfully registered!\n You can now login!",
                        Toast.LENGTH_LONG).show();

                this.finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Something went wrong. Try again!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

}