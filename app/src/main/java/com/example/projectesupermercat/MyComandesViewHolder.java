package com.example.projectesupermercat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyComandesViewHolder extends RecyclerView.ViewHolder{

    TextView nameView, preuTotalView,estadoView;
    public MyComandesViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.name_comanda);
        preuTotalView = itemView.findViewById(R.id.preu_total_comanda);
        estadoView = itemView.findViewById(R.id.estado);
    }
}
