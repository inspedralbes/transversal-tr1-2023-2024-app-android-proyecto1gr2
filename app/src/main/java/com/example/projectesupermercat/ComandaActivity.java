package com.example.projectesupermercat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComandaActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView emptyTextView;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda);

        CardView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        emptyTextView = findViewById(R.id.emptyTextView);
        Intent intent = getIntent();
        if (intent != null) {
            Map<Producte, Integer> cantidadPorProducto = (Map<Producte, Integer>) intent.getSerializableExtra("cantidadPorProducto");
            if (cantidadPorProducto != null) {
                // Utiliza el Map en esta actividad
                List<Producte> producteList = new ArrayList<>();
                for (Map.Entry<Producte, Integer> entry : cantidadPorProducto.entrySet()) {
                    Producte producto = entry.getKey();
                    int cantidad = entry.getValue();

                    if (cantidad > 0) {
                        producteList.add(producto);
                    }
                }
                if(producteList.isEmpty()) {emptyTextView.setText("No hi ha cap producte a la lista");}
                else {
                    recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setAdapter(new MyAdapter(getApplicationContext(), producteList, ComandaActivity.this::onPriceChanged,cantidadPorProducto));
                    CardView payButton = findViewById(R.id.payButton);
                    payButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }else{
                emptyTextView.setText("S'ha produit un error");
            }
        }
    }

    private void onPriceChanged(float totalPrice) {
        emptyTextView.setText("Total: "+decfor.format(totalPrice) + "€");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.d("Back", "Voy de ComandaActivity a BasicActivity");
        Intent intent = new Intent(this, BasicActivity.class);
        if(recyclerView != null){
            Log.d("Debug", "RecyclerView is not null");
            Map<Producte, Integer> cantidadPorProducto = ((MyAdapter) recyclerView.getAdapter()).getCantidadPorProducto();
            intent.putExtra("cantidadPorProducto", new HashMap<>(cantidadPorProducto)); // Usar HashMap en lugar de Serializable
        }

        setResult(RESULT_OK, intent);
        finish();

    }

}