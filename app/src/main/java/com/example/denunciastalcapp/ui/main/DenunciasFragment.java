package com.example.denunciastalcapp.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.denunciastalcapp.R;
import com.example.denunciastalcapp.adapter.AdapterDenuncia;
import com.example.denunciastalcapp.model.Denuncias;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class DenunciasFragment extends Fragment {

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    RecyclerView recyclerView;
    String uid;
    List<Denuncias> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_denuncias, container, false);

        recyclerView= view.findViewById(R.id.recycler_denuncias);
        auth = FirebaseAuth.getInstance();
        list = new ArrayList<>();
        database= FirebaseDatabase.getInstance();
        uid=auth.getUid();
        myRef= database.getReference("denuncias");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    list.clear();

                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        for (DataSnapshot ds_denuncias: ds.getChildren()){
                            Denuncias denuncias = ds_denuncias.getValue(Denuncias.class);
                            denuncias.setId(ds_denuncias.getKey());
                            list.add(denuncias);
                        }
                    }
                    LinearLayoutManager lm = new LinearLayoutManager(getActivity());
                    lm.setOrientation(RecyclerView.VERTICAL);
                    AdapterDenuncia adapterDenuncia = new AdapterDenuncia(getActivity(),R.layout.item_denuncia,list);

                    recyclerView.setLayoutManager(lm);
                    recyclerView.setAdapter(adapterDenuncia);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}