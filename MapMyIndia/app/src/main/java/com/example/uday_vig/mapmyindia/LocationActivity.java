package com.example.uday_vig.mapmyindia;

import android.content.Intent;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        EditText location1, location2;
        MaterialButton fab = findViewById(R.id.fabCont);

        location1 = findViewById(R.id.location1EditText);
        location2 = findViewById(R.id.location2EditText);

        location1.setText("Prestige Ferns Galaxy Bellandur Gate Bengaluru Karnataka");
        location2.setText("Block B Eatery Shop Outer Ring Road Sobha Hibiscus Bengaluru Karnataka");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LocationActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
