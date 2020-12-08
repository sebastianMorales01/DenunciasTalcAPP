package com.example.denunciastalcapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.denunciastalcapp.R;
import com.example.denunciastalcapp.model.Denuncias;
import java.util.List;

public class AdapterDenuncia extends RecyclerView.Adapter<AdapterDenuncia.DenunciaHolder> {
    public Activity activity;
    private int layout;
    public List<Denuncias> list;

    public AdapterDenuncia(Activity activity, int layout, List<Denuncias> list) {
        this.activity = activity;
        this.layout = layout;
        this.list = list;
    }
    @NonNull
    @Override
    public DenunciaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        return new DenunciaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DenunciaHolder holder, int position) {
        Denuncias denuncias = list.get(position);
        holder.titulo.setText(denuncias.getTitulo());
        holder.direccion.setText(denuncias.getDireccion());
        //holder.id = denuncias.getId(); //en caso de necesitar el id para eliminar una denuncia

        int e = Integer.parseInt(denuncias.getEstado());
        if (e == 1){
            holder.item_estado.setImageResource(R.drawable.estadoa);
        }
        if (e == 0){
            holder.item_estado.setImageResource(R.drawable.estadob);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class DenunciaHolder extends RecyclerView.ViewHolder{

        public TextView titulo , direccion , id;
        ImageView item_estado;

        public DenunciaHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.item_denuncia_id);
            titulo = itemView.findViewById(R.id.item_denuncia_titulo);
            direccion = itemView.findViewById(R.id.item_denuncia_direccion);
            item_estado = itemView.findViewById(R.id.item_estado);

        }
    }
}
