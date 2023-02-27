package com.example.foody2.View;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foody2.Controller.DangKyController;
import com.example.foody2.Model.ThanhVienModel;
import com.example.foody2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    TextView existsUser;
    EditText inputEmail,inputPassword,conFirminputPassword;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth= FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        mAuth.signOut();
        btnSignUp = findViewById(R.id.btnSignUp);
        existsUser = findViewById(R.id.existsUser);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        conFirminputPassword = findViewById(R.id.conFirminputPassword);

        existsUser.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),SignInActivity.class)));
        btnSignUp.setOnClickListener(view -> {
            if (identitySignUp()){
                progressDialog.setMessage("loading..");
                progressDialog.setIndeterminate(true);
                progressDialog.show();
                String email = inputEmail.getText().toString().trim();
                String pass = inputPassword.getText().toString().trim();
                mAuth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener(task -> {
                            progressDialog.dismiss();
                            ThanhVienModel thanhVienModel = new ThanhVienModel();
                            thanhVienModel.setHoten(email);
                            thanhVienModel.setHinhanh("user.png");

                            String uid = Objects.requireNonNull(task.getResult().getUser()).getUid();

                            DangKyController dangKyController = new DangKyController();
                            dangKyController.ThemThongTinThanhVienController(thanhVienModel,uid);

                            showToast("login success ..");
                        }).addOnFailureListener(e -> showToast(e.getMessage()));
            }
        });
    }

    private void showToast(String s){
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            showToast("add success .. ");
        }
    }
    private boolean identitySignUp(){
        if (inputEmail.getText().toString().trim().isEmpty()){
            showToast("enter email !");
            return false;
        }else if(inputPassword.getText().toString().trim().isEmpty()){
            showToast("enter password !");
            return false;
        }else if(conFirminputPassword.getText().toString().trim().isEmpty()){
            showToast("enter confirm password !");
            return false;
        }else if(!conFirminputPassword.getText().toString().trim().equals(inputPassword.getText().toString().trim())){
            showToast("confirm password not exists with password !");
            return false;
        }
        else {
            return true;
        }
    }
}
