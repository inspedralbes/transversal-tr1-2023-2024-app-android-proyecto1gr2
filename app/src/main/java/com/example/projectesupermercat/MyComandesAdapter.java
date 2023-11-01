package com.example.projectesupermercat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyComandesAdapter extends RecyclerView.Adapter<MyComandesViewHolder>{

    Context context;
    List<Comanda> comandas;
    public MyComandesAdapter(Context context, List<Comanda> items){
        this.context = context;
        this.comandas = items;
    }
    @NonNull
    @Override
    public MyComandesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyComandesViewHolder(LayoutInflater.from(context).inflate(R.layout.comanda_element_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyComandesViewHolder holder, int position) {
        Log.d("Comanda", "Nueva comanda");
        Comanda comanda = comandas.get(position);
        holder.nameView.setText("#Comanda "+(position+1));
        holder.preuTotalView.setText(String.valueOf(comanda.getPreuTotal())+"€");
        holder.estadoView.setText(comanda.getEstat().getEstado_text());
    }

    @Override
    public int getItemCount() {
        return comandas.size();
    }

}
