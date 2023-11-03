package com.example.projectesupermercat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class MyComandesAdapter extends RecyclerView.Adapter<MyComandesViewHolder>{

    private Activity activity;
    List<Comanda> comandas;
    public MyComandesAdapter(Activity activity, List<Comanda> items){
        this.activity = activity;
        this.comandas = items;
    }
    @NonNull
    @Override
    public MyComandesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyComandesViewHolder(LayoutInflater.from(activity).inflate(R.layout.comanda_element_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyComandesViewHolder holder, int position) {
        Log.d("Comanda", "Nueva comanda");
        Comanda comanda = comandas.get(position);
        holder.nameView.setText("Comanda #"+(position+1));
        holder.preuTotalView.setText(String.valueOf(comanda.getPreuTotal())+"€");
        holder.estadoView.setText(comanda.getEstat().getEstado_text());
        holder.estadoView.setTextColor(comanda.getEstat().getColor_actual());
        holder.comandaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mostrarProductos(comanda, position);
            }
        });
    }

    private void mostrarProductos(Comanda comanda, int position) {
        if (activity != null && !activity.isFinishing()) {
            final Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.productes_list_view);
            dialog.setTitle("Comanda #" + (position + 1));

            RecyclerView recyclerView = dialog.findViewById(R.id.productesRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.setAdapter(new MyProductesAdapter(activity.getApplicationContext(),
                    comanda.getProductes()));
            CardView cerrarButton = dialog.findViewById(R.id.cerrarButton);
            cerrarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            CardView recollirButton = dialog.findViewById(R.id.recollirButton);
            recollirButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO cambiar estado de la comanda
                }
            });
            if(comanda.getEstat() != Estat.LLEST){
                recollirButton.setVisibility(View.INVISIBLE);
            }

            // Mostrar el diálogo
            dialog.show();
        }

    }

    @Override
    public int getItemCount() {
        return comandas.size();
    }

}
