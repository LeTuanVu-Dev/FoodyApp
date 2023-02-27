package com.example.foody2.View;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.foody2.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    TextView createUser;
    Button btnSendEmail;
    EditText inputEmail;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        createUser = findViewById(R.id.createUser);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        inputEmail = findViewById(R.id.inputEmail);

        createUser.setOnClickListener(this);
        btnSendEmail.setOnClickListener(this);
    }
    private void showToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.createUser:
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                break;
            case R.id.btnSendEmail:
                String email = inputEmail.getText().toString().trim();
                boolean checkEmail = checkEmail(email);
                if (checkEmail){
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            showToast("send Email successfully !");
                            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                        }
                    });
                }
                else {
                    showToast("Your Email not valid ");
                }
                break;
        }
    }

    private boolean checkEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}