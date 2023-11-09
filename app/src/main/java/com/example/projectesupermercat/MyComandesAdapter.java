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

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyComandesAdapter extends RecyclerView.Adapter<MyComandesViewHolder> {

    SocketManager socketManager;
    private Activity activity;
    List<Comanda> comandas;
    Map<Comanda, MyComandesViewHolder> comandaToViewHolderMap = new HashMap<>();
    public MyComandesAdapter(Activity activity, List<Comanda> items){
        this.activity = activity;
        this.comandas = items;
        socketManager = new SocketManager();
        socketManager.connect();
        socketManager.sendAutentification(activity);
        socketManager.setOnMessageReceivedListener(new SocketManager.SocketEventListener() {
            @Override
            public void onMessageReceived(String message) {
                Log.d("Message", message);
                Gson gson = new Gson();
                JsonComanda jsonComanda = gson.fromJson(message,JsonComanda.class);
                comandas.forEach(comanda -> {
                    if(comanda.getId() == jsonComanda.getId()){
                        comanda.setEstat(Comanda.recibirEstat(jsonComanda.getEstado()));

                        MyComandesViewHolder viewHolder = comandaToViewHolderMap.get(comanda);
                        if (viewHolder != null) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    viewHolder.estadoView.setText(comanda.getEstat().getEstado_text());
                                    viewHolder.estadoView.setTextColor(comanda.getEstat().getColor_actual());
                                }
                            });

                            Log.d("Comanda updateada", String.valueOf(comanda.getId()));
                        }
                    }

                });




            }
        });
    }
    @NonNull
    @Override
    public MyComandesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyComandesViewHolder viewHolder = new MyComandesViewHolder(LayoutInflater.from(activity).inflate(R.layout.comanda_element_view,parent,false));
        return viewHolder;
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
        comandaToViewHolderMap.put(comandas.get(position), holder);
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
                    socketManager.sendRecollitEstat(comanda.getId());
                    dialog.dismiss();
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
