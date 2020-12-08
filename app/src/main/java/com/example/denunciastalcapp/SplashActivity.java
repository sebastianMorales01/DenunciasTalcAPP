package com.example.denunciastalcapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (hayInternet()){
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();

                }else{
                    setContentView(R.layout.empty_internet_activity);
                }
                /*
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                */

            }
        },1000);
    }
    public  boolean hayInternet(){
        boolean connected = false;
        ConnectivityManager connectivityManager;
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        Network[] networks = connectivityManager.getAllNetworks();
        //agregar un permiso en el manifest   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>

        //recorrer las redes de arreglos
        for (Network network : networks){
            NetworkInfo info = connectivityManager.getNetworkInfo(network);
            if (info.isConnected()){
                connected = true;
                break;
            }
        }
        return  connected;
    }

}