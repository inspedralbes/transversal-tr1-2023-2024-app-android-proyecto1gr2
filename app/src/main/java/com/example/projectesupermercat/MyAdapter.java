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
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    TotalPriceListener priceListener;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    Context context;
    List<Producte> productes;
    private float precioTotal = 0.0f;

    public MyAdapter(Context context, List<Producte> items, TotalPriceListener listener) {
        this.context = context;
        this.productes = items;
        this.priceListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.producte_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.nameView.setText(productes.get(position).getNom());
        holder.preuUnitatView.setText(productes.get(position).getPreu()+"€/u");
        holder.quantitatView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("Total",String.valueOf(precioTotal));
                priceListener.onPriceChanged(precioTotal);
                if(holder.quantitatView.getText().toString().equals("")) holder.quantitatView.setText("0");
                int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
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
                precioTotal += Float.parseFloat(holder.preuUnitatView.getText().toString().replace("€/u",""));
                holder.quantitatView.setText(String.valueOf(quantitat+1));

            }
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
                if (quantitat>0){
                    precioTotal -= Float.parseFloat(holder.preuUnitatView.getText().toString().replace("€/u",""));
                    holder.quantitatView.setText(String.valueOf(quantitat-1));

                }


            }
        });
        //holder.imageView.setImageResource(productes.get(position).getImatge());
    }

    @Override
    public int getItemCount() {
        return productes.size();
    }

}
