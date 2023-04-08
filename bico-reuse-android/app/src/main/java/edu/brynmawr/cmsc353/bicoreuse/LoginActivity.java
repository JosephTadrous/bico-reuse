package edu.brynmawr.cmsc353.bicoreuse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import edu.brynmawr.cmsc353.bicoreuse.info.LoginInfo;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;

    private String email;
    private String password;

    LoginInfo loginInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
    }

    private String getInformation(EditText text) {
        return text.getText().toString();
    }

    // redirects to register activity
    public void onRegisterButtonClick(View v) {

    }

    public void onLoginButtonClick(View v) {
        email= getInformation(etEmail);
        password= getInformation(etPassword);

        boolean existsError= false;

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            existsError= true;
        }
        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            existsError= true;
        }

        if (existsError) {
            Toast.makeText(getApplicationContext(),
                    "One or more fields are incorrect/missing",
                    Toast.LENGTH_LONG).show();
        } else {
            loginInfo= new LoginInfo(email, password);
            loginInfo.getData();


            Toast.makeText(getApplicationContext(),
                    "Successfully logged in!",
                    Toast.LENGTH_LONG).show();
        }

    }
}