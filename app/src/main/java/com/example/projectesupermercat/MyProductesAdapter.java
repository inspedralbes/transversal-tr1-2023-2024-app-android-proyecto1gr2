package com.example.projectesupermercat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyProductesAdapter extends RecyclerView.Adapter<MyProductesViewHolder> {

    TotalPriceListener priceListener;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    Context context;
    List<Producte> productes;
    private float precioTotal = 0.0f;
    private Map<Producte, Integer> cantidadPorProducto = new HashMap<>();

    public MyProductesAdapter(Context context, List<Producte> items, TotalPriceListener listener) {
        this.context = context;
        this.productes = items;
        this.priceListener = listener;

        for (Producte producto : productes) {
            cantidadPorProducto.put(producto, 0);
        }
    }

    public MyProductesAdapter(Context context, List<Producte> productes, TotalPriceListener priceListener, Map<Producte, Integer> cantidadPorProducto) {
        this.priceListener = priceListener;
        this.context = context;
        this.productes = productes;
        this.cantidadPorProducto = cantidadPorProducto;
    }

    public MyProductesAdapter(Context context, List<JsonProducte> jsonProductes) {
        this.context = context;
        this.productes = new ArrayList<>();
        jsonProductes.forEach(jsonProducte -> {

            this.productes.add(new Producte(jsonProducte.getId(),
                    jsonProducte.getNom(),
                    jsonProducte.getDescripcio(),
                    jsonProducte.getPreu(), jsonProducte.getQuantitat(), jsonProducte.getImatge(), 0,null));
            cantidadPorProducto.put(this.productes.get(this.productes.size()-1), jsonProducte.getQuantitat());
        });

    }

    @NonNull
    @Override
    public MyProductesViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new MyProductesViewHolder(LayoutInflater.from(context).inflate(R.layout.producte_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyProductesViewHolder holder, int position) {
        Producte producte = productes.get(position);
        Log.d("Debug","Producte modificat: "+producte.getNom()+" ID: "+producte.getId());

        holder.nameView.setText(productes.get(position).getNom());
        holder.preuUnitatView.setText(productes.get(position).getPreu()+"€/u");
        int cantidad = cantidadPorProducto.get(producte);
        holder.quantitatView.setText(String.valueOf(cantidad));

        int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
        if (quantitat>0){
            holder.preuTotalView.setText(decfor.format(Float.parseFloat(holder.preuUnitatView
                    .getText().toString().replace("€/u","")) * quantitat)+"€");
        }else{
            holder.preuTotalView.setText("");
        }

        holder.quantitatView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Solucion mientras el EditText esta deshabilitado
                /*if(holder.quantitatView.getText().toString().equals("")) holder.quantitatView.setText("0");
                int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());

                cantidadPorProducto.put(producte, quantitat);
                calcularPrecioTotal();
                priceListener.onPriceChanged(precioTotal);
                if (quantitat>0){
                    holder.preuTotalView.setText(decfor.format(Float.parseFloat(holder.preuUnitatView
                            .getText().toString().replace("€/u","")) * quantitat)+"€");
                }else{
                    holder.preuTotalView.setText("");
                }*/
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
                Log.d("Debug","Producto afegit: "+producte.getNom());
                if(holder.quantitatView.getText().toString().equals("")) holder.quantitatView.setText("0");

                //Solucion mientras el EditText esta deshabilitado
                quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
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
        });
        holder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
                if (quantitat>0){
                    cantidadPorProducto.put(producte, quantitat - 1);
                    holder.quantitatView.setText(String.valueOf(quantitat-1));
                    if(holder.quantitatView.getText().toString().equals("")) holder.quantitatView.setText("0");

                    //Solucion mientras el EditText esta deshabilitado
                    quantitat = Integer.parseInt(holder.quantitatView.getText().toString());
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


            }
        });
        calcularPrecioTotal();
        if(priceListener != null){
            priceListener.onPriceChanged(precioTotal);
        }else{
            holder.minusButton.setVisibility(View.INVISIBLE);
            holder.plusButton.setVisibility(View.INVISIBLE);
        }

        new DownloadImageTask(holder.imageView)
                .execute(producte.getImatge());
    }

    private void calcularPrecioTotal() {
        precioTotal = 0.0f;
        Set<Producte> producteSet = cantidadPorProducto.keySet();
        Iterator<Producte> producteIterator = producteSet.iterator();
        producteIterator.forEachRemaining(producte -> {
            int cantidad = cantidadPorProducto.get(producte);
            if (cantidad > 0) {
                precioTotal += producte.getPreu() * cantidad;
            }
        });
    }

    public float getPrecioTotal() {
        calcularPrecioTotal();
        return precioTotal;
    }

    @Override
    public int getItemCount() {
        return productes.size();
    }

    public Map<Producte, Integer> getCantidadPorProducto() {
        return cantidadPorProducto;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
