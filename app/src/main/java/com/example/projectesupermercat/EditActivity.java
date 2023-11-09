package com.example.projectesupermercat;

import static com.example.projectesupermercat.MainActivity.getApiService;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    int id;
    EditText nom, cognoms, email;
    CardView save;

    Usuari editedUsuari = new Usuari();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        Usuari user = new Usuari();
        user.setEmail(settings.getString("Email","").toString());

        nom = findViewById(R.id.nomText);
        cognoms = findViewById(R.id.cognomsText);
        email = findViewById(R.id.emailText);
        save  = findViewById(R.id.save);

        id = settings.getInt("Id",0);
        nom.setText(settings.getString("Nom","").toString());
        cognoms.setText(settings.getString("Cognoms","").toString());
        email.setText(settings.getString("Email","").toString());
        Log.d("ValuesWhenEdit","concat of "+id+nom.getText()+cognoms.getText()+email.getText());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSavePressed();
            }
        });

    }
    public void onSavePressed() {
        editedUsuari.setNom(nom.getText().toString());
        editedUsuari.setCognom(cognoms.getText().toString());
        editedUsuari.setEmail(email.getText().toString());

        Log.d("Usuario","Info usuario: "+editedUsuari);
        Call<Void> call = getApiService().updateUsuari(editedUsuari);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("Response",""+response.body());
                if (response.isSuccessful()) {
                    SharedPreferences settings = getSharedPreferences("UserInfo", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("Nom",editedUsuari.getNom());
                    editor.putString("Cognoms",editedUsuari.getCognom());
                    editor.putString("Email",editedUsuari.getEmail());
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "error de update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
