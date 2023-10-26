package com.example.projectesupermercat;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    TotalPriceListener priceListener;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    Context context;
    List<Producte> productes;
    private float precioTotal = 0.0f;
    private Map<Producte, Integer> cantidadPorProducto = new HashMap<>();

    public MyAdapter(Context context, List<Producte> items, TotalPriceListener listener) {
        this.context = context;
        this.productes = items;
        this.priceListener = listener;

        for (Producte producto : productes) {
            cantidadPorProducto.put(producto, 0);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.producte_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        Producte producte = productes.get(position);

        holder.nameView.setText(productes.get(position).getNom());
        holder.preuUnitatView.setText(productes.get(position).getPreu()+"€/u");

        int cantidad = cantidadPorProducto.get(producte);
        holder.quantitatView.setText(String.valueOf(cantidad));
        holder.quantitatView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(holder.quantitatView.getText().toString().equals("")) holder.quantitatView.setText("0");
                int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());

                cantidadPorProducto.put(producte, quantitat);
                calcularPrecioTotal();
                priceListener.onPriceChanged(precioTotal);
                if (quantitat>0){
                    holder.preuTotalView.setText(decfor.format(Float.parseFloat(holder.preuUnitatView
                            .getText().toString().replace("€/u","")) * quantitat)+"€");
                }else{
                    holder.preuTotalView.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        holder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
                cantidadPorProducto.put(producte, quantitat + 1);
                holder.quantitatView.setText(String.valueOf(quantitat+1));

            }
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
                if (quantitat>0){
                    cantidadPorProducto.put(producte, quantitat - 1);
                    holder.quantitatView.setText(String.valueOf(quantitat-1));

                }


            }
        });
        holder.imageView.setImageResource(R.drawable.fastmarket_logos_black);
    }

    private void calcularPrecioTotal() {
        precioTotal = 0.0f;

        for (Producte producto : productes) {
            int cantidad = cantidadPorProducto.get(producto);
            if (cantidad > 0) {
                precioTotal += producto.getPreu() * cantidad;
            }
        }
    }

    @Override
    public int getItemCount() {
        return productes.size();
    }

}
