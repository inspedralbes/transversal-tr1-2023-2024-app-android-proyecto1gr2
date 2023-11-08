package com.example.projectesupermercat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity {
    TextView nom;
    TextView cognoms;
    TextView email;
    CardView editar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();
        Usuari user = new Usuari();
        user.setEmail(settings.getString("Email","").toString());

        nom = findViewById(R.id.nom);
        cognoms = findViewById(R.id.cognoms);
        email = findViewById(R.id.email);
        editar  = findViewById(R.id.edit);

        nom.setText(settings.getString("Nom","").toString());
        cognoms.setText(settings.getString("Cognoms","").toString());
        email.setText(settings.getString("Email","").toString());
        Log.d("Values","concat of "+nom.getText()+cognoms.getText()+email.getText());

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEditPressed();
            }
        });

    }
    public void onEditPressed() {
        Log.d("Back", "Voy de PerfilActivity a EditarActivity");
        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
        startActivity(intent);

    }
}
