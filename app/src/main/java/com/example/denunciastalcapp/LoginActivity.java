package com.example.denunciastalcapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButton;
    private CallbackManager callbackManager;  //mCallbackManager
    EditText login_email,login_clave;
    FirebaseAuth auth; //mFirebaseAuth

    /*metodo 2
    private TextView textViewUser;
    private static  final String TAG = "FacebookAurhentication";
    private FirebaseAuth.AuthStateListener authStateListener;
    private AccessTokenTracker accessTokenTracker;
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        //-----------start login face------------------//
        FacebookSdk.sdkInitialize(getApplicationContext());
        loginButton = findViewById(R.id.login_facebook);
        //loginButton.setReadPermissions("email","public_profile");
        callbackManager = CallbackManager.Factory.create();

        /*
         Nuevo metodo 2
        textViewUser = findViewById(R.id.text_user);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"onSuccess"+loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"onError"+error);
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    updateUI(user);
                }else {
                    updateUI(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken == null){
                    auth.signOut();
                }
            }
        };

         Fin nuevo metodo 2
        */

        /* metodo 1
        loginButton.setOnClickListener(new View.OnClickListener() {
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
          fin metodo 1*/

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
        //Log.d(TAG,"handleFacebook"+token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    //Log.d(TAG,"sign in with credential: succesful");

                    /*metodo 2
                    FirebaseUser user = auth.getCurrentUser();
                    updateUI(user);
                    */
                    goToMainMenu();
                } else {
                    /*metodo 2
                    Log.d(TAG,"sign in with credential: failire",task.getException());
                    Toast.makeText(LoginActivity.this,"Authentication Failed",Toast.LENGTH_LONG).show();
                    updateUI(null);
                    */
                    Toast.makeText(LoginActivity.this,"no puede ingresar con esta cuenta",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /*metodo 2
     public void updateUI(FirebaseUser user){
        if (user != null){
            textViewUser.setText(user.getDisplayName());
            /* obtener la foto de perfil de facebook
            if (user.getPhotoUrl() != null){
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?type=large";

                //min 20
            }

        }
     }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

    fin metodo 2
    */
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