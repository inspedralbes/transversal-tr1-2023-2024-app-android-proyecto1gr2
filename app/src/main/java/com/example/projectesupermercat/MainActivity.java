package com.example.projectesupermercat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Usuari user = new Usuari();
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
                user.setMail(emailText.getText().toString());
                user.setPasswordCypher(passwordText.getText().toString());

            }
        });
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"EEEEYY", Toast.LENGTH_SHORT).show();

            }
        });
    }
}