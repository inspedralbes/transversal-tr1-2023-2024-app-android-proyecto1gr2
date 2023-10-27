package com.example.projectesupermercat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView signUpTextView = findViewById(R.id.signUpTextView);
        CardView cardView = findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailText = findViewById(R.id.emailEditText);
                EditText passwordText = findViewById(R.id.passwordEditText);
                user.setEmail(emailText.getText().toString());
                user.setPasswordCypher(passwordText.getText().toString());
                Log.d("user", user.getPassword());
                configurarApi();
                Call<Usuari> call = getApiService().login(user);
                call.enqueue(new Callback<Usuari>() {
                    @Override
                    public void onResponse(Call<Usuari> call, Response<Usuari> response) {
                        if(response.isSuccessful()){
                            Log.d("Response", "Success");
                            Usuari responseUser = response.body();
                            Log.d("Response",responseUser.getEmail());
                            if(responseUser.getEmail().equals("")){
                                Toast.makeText(getApplicationContext(),"Incorrect user or pass", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Welcome "+responseUser.getNom(),Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplication(), BasicActivity.class);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuari> call, Throwable t) {
                        Log.d("Response", "Failure");
                        Toast.makeText(getApplicationContext(),"Server error", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"On development", Toast.LENGTH_SHORT).show();

            }
        });
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