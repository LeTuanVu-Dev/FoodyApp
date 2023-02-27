package com.example.foody2.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foody2.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener ,FirebaseAuth.AuthStateListener{
    Button login_Google,btnLogin;
    TextView forGotPassword , signUp;
    LoginButton login_Facebook;
    GoogleApiClient apiClient;
    GoogleSignInClient gsc;
    EditText inputEmail,inputPassword;
    public static int REQUEST_CODE_LOGIN_GOOGLE = 99;
    public static int CHECK_PROVIDER_LOGIN = 0;
    FirebaseAuth firebaseAuth;
    CallbackManager mcallbackFacebook;
    GoogleSignInOptions signInOptions;
    List<String > permissionFacebook = Arrays.asList("email","public_profile");
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in);

        mcallbackFacebook = CallbackManager.Factory.create();
        inputPassword = findViewById(R.id.inputPassword);
        inputEmail = findViewById(R.id.inputEmail);
        login_Google = findViewById(R.id.login_Google);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        forGotPassword = findViewById(R.id.forgotPassword);
        signUp  = findViewById(R.id.signUp);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        //LoginManager.getInstance().logOut();
        login_Google.setOnClickListener(this);
        signUp.setOnClickListener(this);
        forGotPassword.setOnClickListener(this);
        login_Facebook = findViewById(R.id.login_Facebook);
        login_Facebook.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("luudangnhap",MODE_PRIVATE);

        CreateClientLoginGoogle();
       // printHashKey(this);


    }
    public static void printHashKey(Context pContext) {
        try {
            @SuppressLint("PackageManagerGetSignatures")
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("123123123", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (Exception e) {
            Log.e("TAG", "printHashKey()", e);
        }
    }
    private void CreateClientLoginGoogle(){
        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.facebook_client_token))
                .requestEmail()
                .build();
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();
        gsc = GoogleSignIn.getClient(this,signInOptions);

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    // open form login google
    private void LoginGoogle(){
        CHECK_PROVIDER_LOGIN = 1;
        Intent intent = gsc.getSignInIntent();
        startActivityForResult(intent,REQUEST_CODE_LOGIN_GOOGLE);
    }
    // end form login google

    // open form login facebook
    private void LoginFacebook(){
        login_Facebook.setReadPermissions(permissionFacebook);
        login_Facebook.registerCallback(mcallbackFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                CHECK_PROVIDER_LOGIN = 2;
                String tokenId = loginResult.getAccessToken().getToken();
                authenticationLoginFirebase(tokenId);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });
    }
    //end form login fb

    private void Login(){
        String email = inputEmail.getText().toString();
        String matkhau = inputPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,matkhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "login not success ..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // get tokenId đã login google để login on firebase
    private void authenticationLoginFirebase(String tokenId){
        if(CHECK_PROVIDER_LOGIN == 1){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenId,null);
            firebaseAuth.signInWithCredential(authCredential);
        }
        else if (CHECK_PROVIDER_LOGIN == 2){
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenId);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }
    // end tokenId đã login google để login on firebase


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_LOGIN_GOOGLE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToMainActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            mcallbackFacebook.onActivityResult(requestCode,resultCode,data);
        }
    }

    // điều hướng tới main
    private void navigateToMainActivity() {
        finish();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    // listener event user click in button login google , facebook and email account
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.login_Google:
                LoginGoogle();
                break;
            case R.id.login_Facebook:
                LoginFacebook();
                break;
            case R.id.btnLogin:
                Login();
                break;
            case R.id.signUp:
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
                break;
        }
    }
    //end

    // event check user login success or logout
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mauser",user.getUid());
            editor.commit();

            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
            Intent iTrangChu = new Intent(this,MainActivity.class);
            startActivity(iTrangChu);
        }
    }
}