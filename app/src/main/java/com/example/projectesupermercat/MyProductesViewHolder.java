package com.example.projectesupermercat;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyProductesViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView nameView,preuUnitatView,preuTotalView,quantitatView;
    Button minusButton,plusButton;

    public MyProductesViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        nameView = itemView.findViewById(R.id.name);
        preuUnitatView = itemView.findViewById(R.id.preu_unitat);
        preuTotalView = itemView.findViewById(R.id.preu_total);
        quantitatView = itemView.findViewById(R.id.editTextQuantity);
        minusButton = itemView.findViewById(R.id.minusButton);
        plusButton = itemView.findViewById(R.id.plusButton);

    }
}