package com.example.projectesupermercat;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Usuari user = new Usuari();

    private static ApiService apiService;

    TextView signUpTextView;
    CardView cardView;
    EditText emailText;
    EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupListeners();
    }

    private void setupListeners() {
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignUpOnClick();
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCardOnClick();
            }
        });
    }

    private void handleSignUpOnClick() {
        Intent intent = new Intent(getApplication(), RegistreActivity.class);
        startActivity(intent);
    }

    private void handleCardOnClick() {
        user.setEmail(emailText.getText().toString());
        user.setPasswordCypher(passwordText.getText().toString());
        Log.d("user", user.getPassword());
        configurarApi();
        Call<Usuari> call = getApiService().login(user);
        call.enqueue(new Callback<Usuari>() {
            @Override
            public void onResponse(Call<Usuari> call, Response<Usuari> response) {
                handleApiResponse(response.body());
            }
            @Override
            public void onFailure(Call<Usuari> call, Throwable t) {
                handleApiFailure();
            }
        });
    }

    private void handleApiFailure() {
        Log.d("Response", "Failure");
        Toast.makeText(getApplicationContext(), "Server error", Toast.LENGTH_SHORT).show();
    }

    private void handleApiResponse(Usuari responseUser) {
        if (responseUser != null) {
            Log.d("Response", "Success");
            Toast.makeText(getApplicationContext(), "Welcome " + responseUser.getNom(), Toast.LENGTH_SHORT).show();
            SharedPreferences settings = getSharedPreferences("UserInfo", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Email",responseUser.getEmail());
            editor.putString("Nom",responseUser.getNom());
            editor.putString("Cognom",responseUser.getCognom());
            editor.commit();
            Intent intent = new Intent(getApplication(), BasicActivity.class);
            startActivity(intent);
        } else {
            Log.d("Response", "Success");
            Toast.makeText(getApplicationContext(), "Incorrect user or pass", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews() {
        signUpTextView = findViewById(R.id.signUpTextView);
        cardView = findViewById(R.id.cardView);
        emailText = findViewById(R.id.emailEditText);
        passwordText = findViewById(R.id.passwordEditText);
    }

    private void configurarApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://dam.inspedralbes.cat:3593")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

    }

    public static ApiService getApiService() {
        return apiService;
    }
}