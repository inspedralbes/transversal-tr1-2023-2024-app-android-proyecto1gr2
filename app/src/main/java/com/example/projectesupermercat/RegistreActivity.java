package com.example.projectesupermercat;

import static com.example.projectesupermercat.MainActivity.getApiService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class RegistreActivity extends AppCompatActivity {

    private static ApiService apiService;

    EditText nomText, cognomsText, contrasenyaText, emailText;

    CardView cancelar, registre;

    Usuari nouUsuari = new Usuari();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);

        initializeViews();
        setupListeners();

    }



    private void initializeViews() {
        nomText = findViewById(R.id.nom);
        emailText = findViewById(R.id.email);
        contrasenyaText = findViewById(R.id.contrasenya);
        cancelar = findViewById(R.id.cancelar);
        registre = findViewById(R.id.registre);
    }

    private void setupListeners() {
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCancelarOnClick();
            }
        });

        registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegistreOnClick();
            }
        });
    }

    private void handleRegistreOnClick() {

        nouUsuari.setNom(nomText.getText().toString());
        nouUsuari.setCognom(cognomsText.getText().toString());
        nouUsuari.setPasswordCypher(contrasenyaText.getText().toString());
        nouUsuari.setEmail(emailText.getText().toString());
        Call<Void> call = getApiService().registrarUsuari(nouUsuari);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Aquest mail ja està en ús", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleCancelarOnClick() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    private void handleApiFailure() {
        Log.d("Response", "Failure");
        Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
    }

}