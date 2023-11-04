package com.example.projectesupermercat;

import static com.example.projectesupermercat.MainActivity.getApiService;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
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

public class BasicActivity extends AppCompatActivity implements TotalPriceListener{

    private AppBarConfiguration appBarConfiguration;
    private ActivityBasicBinding binding;

    private List<Producte> producteList = new ArrayList<>();

    private RecyclerView recyclerView;

    private List<Categoria> categoriaList = new ArrayList<>();
    private Categoria selectedCategoria;
    private TextView precioTotalTextView;

    private List<CardView> categoriasCardViews = new ArrayList<>();
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    MyProductesAdapter adapter;
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
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        comandaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ComandaActivity.class);
                Map<Producte,Integer> cantidadPorProducto = ((MyProductesAdapter)recyclerView.getAdapter()).getCantidadPorProducto();
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
                        recyclerView.setAdapter(new MyProductesAdapter(getApplicationContext(),
                                producteList,BasicActivity.this::onPriceChanged,
                                (Map<Producte, Integer>) intent.getSerializableExtra("cantidadPorProducto")));
                    }else{
                        recyclerView.setAdapter(new MyProductesAdapter(getApplicationContext(),producteList,BasicActivity.this::onPriceChanged));
                    }

                }
            }

            @Override
            public void onFailure(Call<List<Producte>> call, Throwable t) {
                Log.d("Response", "Failure");
                Toast.makeText(getApplicationContext(),"Server error", Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<Categoria>> categoriaCall = getApiService().getCategories();
        categoriaCall.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if(response.isSuccessful()){
                    Log.d("Response", "Success");
                    categoriaList = response.body();
                    categoriaList.add(0,new Categoria(0,"Totes"));
                    createCategoriasCardViews();
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });

    }

    private void createCategoriasCardViews() {
        LinearLayout categoriaLayout = findViewById(R.id.layout_categoria);
        HorizontalScrollView horizontalScrollView = findViewById(R.id.horizontal_scroll_view);
        horizontalScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        categoriaList.forEach(categoria -> {
            CardView cardView = new CardView(getApplicationContext()); // Reemplaza 'this' con el contexto adecuado
            CardView.LayoutParams cardLayoutParams = new CardView.LayoutParams(
                    CardView.LayoutParams.WRAP_CONTENT,
                    CardView.LayoutParams.WRAP_CONTENT
            );
            cardLayoutParams.setMargins(0, 0, dpToPx(10), 0); // Ajusta los valores según tus necesidades
            cardView.setLayoutParams(cardLayoutParams);
            cardView.setCardBackgroundColor(Color.parseColor("#D3D3D3"));
            cardView.setRadius(dpToPx(7));
            categoriasCardViews.add(cardView);
            TextView textView = new TextView(getApplicationContext()); // Reemplaza 'this' con el contexto adecuado
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(dpToPx(10), 0, dpToPx(10), 0); // Establecer márgenes horizontal (izquierda y derecha) de 10dp
            textView.setLayoutParams(layoutParams);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textView.setTextSize(20);
            textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));
            textView.setText(categoria.getNom());

            cardView.addView(textView);
            categoriaLayout.addView(cardView);
        });
        categoriasCardViews.get(0).setCardBackgroundColor(Color.parseColor("#FAB333"));
        categoriasCardViews.forEach(cardView -> {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    categoriaList.forEach(categoria -> {
                        if(((TextView)cardView.getChildAt(0)).getText().toString() == categoria.getNom()){
                            selectedCategoria = categoria;
                        }
                    });
                    updateSelectedCardView(cardView);
                    recyclerViewByCategoria();
                }
            });
        });
    }

    private void recyclerViewByCategoria() {
        Map<Producte,Integer> cantidadPorProducto = ((MyProductesAdapter)recyclerView.getAdapter()).getCantidadPorProducto();
        if(selectedCategoria == categoriaList.get(0)){
            recyclerView.setAdapter(new MyProductesAdapter(getApplicationContext(),producteList,BasicActivity.this::onPriceChanged, cantidadPorProducto));
        }else {
            List<Producte> categoriaProducteList = new ArrayList<>();
            producteList.forEach(producte -> {
                if (producte.getId_categoria() == selectedCategoria.getId()) {
                    categoriaProducteList.add(producte);
                }
            });
            recyclerView.setAdapter(new MyProductesAdapter(getApplicationContext(), categoriaProducteList, BasicActivity.this::onPriceChanged, cantidadPorProducto));
        }
    }

    private void updateSelectedCardView(CardView selectedCardView) {
        categoriasCardViews.forEach(cardView -> {
            if(cardView == selectedCardView){
                cardView.setCardBackgroundColor(Color.parseColor("#FAB333"));
            }else{
                cardView.setCardBackgroundColor(Color.parseColor("#D3D3D3"));
            }
        });
    }

    public int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Intent intent = new Intent(getApplication(), LlistaComandesActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPriceChanged(float totalPrice) {
        precioTotalTextView.setText(decfor.format(totalPrice) + "€");
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_basic);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }*/

}