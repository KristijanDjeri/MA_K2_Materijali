package com.example.kolokvijum2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView textDetail = findViewById(R.id.textDetail);

        String naslov = getIntent().getStringExtra("naslov");
        if (naslov != null) {
            textDetail.setText(naslov);
        } else {
            textDetail.setText("Nema prosleđenih podataka");
        }
    }
}
