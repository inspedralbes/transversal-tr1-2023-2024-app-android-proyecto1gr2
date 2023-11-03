package com.example.projectesupermercat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PaymentActivity extends AppCompatActivity {

    CardView comandesView, iniciView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initializeViews();
        setupListeners();

    }

    private void initializeViews() {
        comandesView = findViewById(R.id.comandes);
        iniciView = findViewById(R.id.inici);
    }

    private void setupListeners() {
        comandesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleComandesOnClick();
            }
        });

        iniciView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleIniciOnClick();
            }
        });
    }

    private void handleComandesOnClick() {
        Intent intent = new Intent(getApplicationContext(), LlistaComandesActivity.class);
        startActivity(intent);
    }

    private void handleIniciOnClick() {
        Intent intent = new Intent(getApplicationContext(), BasicActivity.class);
        startActivity(intent);
    }
}