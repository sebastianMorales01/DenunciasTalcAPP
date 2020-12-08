package com.example.denunciastalcapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    EditText login_email,login_clave;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        //-----------start login face------------------//
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_facebook);
        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                LoginManager.getInstance().logInWithPublishPermissions(LoginActivity.this, Arrays.asList("email","public_profile"));
                LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                });
            }
        });
         */
        //---------end login Face----------//

        //----------login email----------//
        if (auth.getCurrentUser() == null){
            setContentView(R.layout.activity_login);
            login_email = findViewById(R.id.login_email);
            login_clave = findViewById(R.id.login_clave);
        }else{
            Intent i = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,@Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    goToMainMenu();
                } else {
                    Toast.makeText(LoginActivity.this,"no puede ingresar con esta cuenta",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void goToMainMenu() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void launchRegister(View view) {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
        finish();//destroy this activity (Login)
    }

    public void signIn(View view) {
        String email = login_email.getText().toString();
        String pass = login_clave.getText().toString();

        if (!email.isEmpty() && !pass.isEmpty()){
            //create account in FireBase
            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this , msg , Toast.LENGTH_LONG).show();
                                login_clave.setText("");
                            }
                        }
                    });
        }else{
            Toast.makeText(this,"complete los campos",Toast.LENGTH_SHORT).show();
            login_clave.setText("");
        }
    }

    public void login_facebook(View view) {
        callbackManager = CallbackManager.Factory.create();
        loginButton = findViewById(R.id.login_facebook);
        LoginManager.getInstance().logInWithPublishPermissions(LoginActivity.this, Arrays.asList("email","public_profile"));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
}