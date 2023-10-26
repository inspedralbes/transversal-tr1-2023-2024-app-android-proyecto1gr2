package com.example.projectesupermercat;

import static com.example.projectesupermercat.MainActivity.getApiService;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectesupermercat.databinding.ActivityBasicBinding;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicActivity extends AppCompatActivity implements TotalPriceListener{

    private AppBarConfiguration appBarConfiguration;
    private ActivityBasicBinding binding;

    private List<Producte> producteList = new ArrayList<>();
    private TextView precioTotalTextView;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    MyAdapter adapter;
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {

            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityBasicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_basic);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        RelativeLayout comandaView = findViewById(R.id.comandaButton);
        precioTotalTextView = comandaView.findViewById(R.id.preu_total_final);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        comandaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ComandaActivity.class);
                Map<Producte,Integer> cantidadPorProducto = ((MyAdapter)recyclerView.getAdapter()).getCantidadPorProducto();
                intent.putExtra("cantidadPorProducto", (Serializable) cantidadPorProducto);
                launcher.launch(intent);
            }
        });

        Call<List<Producte>> call = getApiService().getProductes();
        call.enqueue(new Callback<List<Producte>>() {
            @Override
            public void onResponse(Call<List<Producte>> call, Response<List<Producte>> response) {
                if(response.isSuccessful()){
                    Log.d("Response", "Success");
                    producteList = response.body();
                    Intent intent = getIntent();
                    if (intent != null && intent.getSerializableExtra("cantidadPorProducto")!=null) {
                        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),
                                producteList,BasicActivity.this::onPriceChanged,
                                (Map<Producte, Integer>) intent.getSerializableExtra("cantidadPorProducto")));
                    }else{
                        recyclerView.setAdapter(new MyAdapter(getApplicationContext(),producteList,BasicActivity.this::onPriceChanged));
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Producte>> call, Throwable t) {
                Log.d("Response", "Failure");
                Toast.makeText(getApplicationContext(),"Server error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public void onPriceChanged(float totalPrice) {
        precioTotalTextView.setText(decfor.format(totalPrice) + "€");
    }



    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_basic);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

}