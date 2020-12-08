package com.example.denunciastalcapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.denunciastalcapp.R;
import com.example.denunciastalcapp.model.Denuncias;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class DenunciarFragment extends Fragment {

    EditText txt_titulo,txt_direccion;
    Button button;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_denunciar, container, false);

        auth = FirebaseAuth.getInstance();
        txt_titulo = view.findViewById(R.id.nuevo_titulo);  //se le pone el view. ya q estamos en un fragmento. para q reconozca la vista
        txt_direccion = view.findViewById(R.id.nuevo_direccion);
        button = view.findViewById(R.id.nuevo_btnGuardar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titulo = txt_titulo.getText().toString();
                String direccion = txt_direccion.getText().toString();
                String uid = auth.getCurrentUser().getUid();

                if (!titulo.isEmpty() && !direccion.isEmpty()){
                    FirebaseDatabase database = FirebaseDatabase.getInstance();  //inicializar la BD
                    DatabaseReference myRef = database.getReference("denuncias").child(uid);  //al agregarle el child(uid),
                    // asociamos la tarea creada al usuario de la sesion
                    Denuncias denuncias = new Denuncias();
                    denuncias.setTitulo(titulo);
                    denuncias.setDireccion(direccion);
                    ///////
                    denuncias.setEstado("1");
                    myRef.push().setValue(denuncias); //.push() --> generar un id automatico
                    Toast.makeText(getActivity(),"Denuncia Creada",Toast.LENGTH_SHORT).show();
                    txt_titulo.setText("");
                    txt_direccion.setText("");
                }else {
                    Toast.makeText(getActivity(),"complete los campos",Toast.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }
}