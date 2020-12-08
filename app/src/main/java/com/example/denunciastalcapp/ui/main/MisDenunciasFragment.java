package com.example.denunciastalcapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.denunciastalcapp.R;
import com.example.denunciastalcapp.adapter.AdapterDenuncia;
import com.example.denunciastalcapp.adapter.AdapterMisDenuncias;
import com.example.denunciastalcapp.model.Denuncias;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MisDenunciasFragment extends Fragment {

    FirebaseAuth auth;
    List<Denuncias> list;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mis_denuncias, container, false);

        auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        list = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler_denuncia);

        FirebaseDatabase database = FirebaseDatabase.getInstance();  //inicializar la BD
        DatabaseReference myRef = database.getReference("denuncias").child(uid);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    list.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        Denuncias denuncias = ds.getValue(Denuncias.class); //inicialiaza la descripcion y el nombre y los guarda en el objeto denuncias
                        denuncias.setId(ds.getKey()); // inicializa el id, q es el codigo largo (padre)
                        list.add(denuncias); // agregar la denuncia a la lista para poder mostrarla
                    }
                    /*recorrer la lista
                    String res="";
                    for (Denuncias d : list){
                        res +=t.getId() + " || "+ d.getTitulo()+"\n";
                    }
                    txt.setText(res);
                    */
                    //Aqui iria el recycler
                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());
                    lm.setOrientation(RecyclerView.VERTICAL);
                    AdapterMisDenuncias adapterMisDenuncias = new AdapterMisDenuncias(getActivity(),R.layout.item_mis_denuncias,list);

                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapterMisDenuncias);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {


            }
        });



        return view;
    }
}