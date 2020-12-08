package com.example.denunciastalcapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.denunciastalcapp.ui.main.SectionsPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        auth = FirebaseAuth.getInstance();
        //obtener el email del usuario para ponerlo como titulo en la app
        title = findViewById(R.id.title);
        title.setText(auth.getCurrentUser().getEmail());

    }
    public void signOff(View view) {
        //cerrar sesion
        auth.signOut();
        //cerrar sesion face
        com.facebook.login.LoginManager.getInstance().logOut();
        //redireccionar
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();//destroy this activity

    }

    public void btn_aboutUs(View view) {
        Intent i = new Intent(this, AboutUsActivity.class);
        startActivity(i);
    }
}