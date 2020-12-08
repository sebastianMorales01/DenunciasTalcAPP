package com.example.denunciastalcapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denunciastalcapp.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText register_email,register_name,register_celular,register_clave;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_email = findViewById(R.id.register_email);
        register_name = findViewById(R.id.register_name);
        register_celular = findViewById(R.id.register_celular);
        register_clave = findViewById(R.id.register_clave);

        auth = FirebaseAuth.getInstance();
    }
    public void createAccount(View view) {
        final String email = register_email.getText().toString();
        final String name = register_name.getText().toString();
        final String celular = register_celular.getText().toString();
        String pass = register_clave.getText().toString();

        if (!email.isEmpty() && !name.isEmpty() && !celular.isEmpty() && !pass.isEmpty()){
            //create account in FireBase
            auth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this,"cuenta creada con exito",
                                        Toast.LENGTH_SHORT).show();

                                //registrar en la bd al usuario
                                Usuario user = new Usuario();
                                user.setNombre(name);
                                user.setEmail(email);
                                user.setCelular(celular);
                                user.setUid(task.getResult().getUser().getUid());

                                //inicializar la BD
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("usuarios");

                                myRef.push().setValue(user); //.push() --> generar un id automatico

                                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this , msg , Toast.LENGTH_SHORT).show();
                                register_clave.setText("");
                            }
                        }
                    });
        }else{
            Toast.makeText(this,"complete los campos",Toast.LENGTH_SHORT).show();
            register_clave.setText("");
        }
    }
    public void launchLogin(View view) {
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();//destroy this activity (Register)
    }

}