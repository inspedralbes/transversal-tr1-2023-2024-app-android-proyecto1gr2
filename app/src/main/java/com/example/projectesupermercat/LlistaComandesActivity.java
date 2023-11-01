package com.example.projectesupermercat;

import static com.example.projectesupermercat.MainActivity.getApiService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LlistaComandesActivity extends AppCompatActivity {

    RecyclerView comandesRecyclerView;
    List<Comanda> llistaComandes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llista_comandes);

        comandesRecyclerView = findViewById(R.id.comandes_recycler_view);
        comandesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        Usuari user = new Usuari();
        user.setEmail(settings.getString("Email","").toString());

        Call<List<Comanda>> call = getApiService().rebreComandes(user);

        call.enqueue(new Callback<List<Comanda>>() {
            @Override
            public void onResponse(Call<List<Comanda>> call, Response<List<Comanda>> response) {
                if(response.isSuccessful()){
                    Log.d("Response", "Success");
                    llistaComandes = response.body();
                    llistaComandes.forEach(comanda -> comanda.setEstat(Comanda.recibirEstat(comanda.getEstado())));
                    comandesRecyclerView.setAdapter(new MyComandesAdapter(getApplicationContext(),llistaComandes));
                    Log.d("Comandes", "Lista: "+llistaComandes.size());
                    Log.d("Comandes","RecyclerView: "+comandesRecyclerView.getAdapter().getItemCount());

                }
            }

            @Override
            public void onFailure(Call<List<Comanda>> call, Throwable t) {
                Log.d("Response", "Failure");
                Toast.makeText(getApplicationContext(),"Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}