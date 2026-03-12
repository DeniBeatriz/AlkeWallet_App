package com.example.alkewallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity5 extends AppCompatActivity {

    private ImageView imgProfile;
    private Button btnSend;
    private Button btnReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main5);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgProfile = findViewById(R.id.imgProfile);
        btnSend = findViewById(R.id.btnSend);
        btnReceive = findViewById(R.id.btnReceive);

        imgProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity5.this, MainActivity9.class);
                startActivity(intent);
            }
        });


        btnSend.setOnClickListener(v -> {
            // Acciones al hacer clic en btnSend
            Intent intent = new Intent(MainActivity5.this, MainActivity8.class);
            startActivity(intent);
        });

        btnReceive.setOnClickListener(v -> {
            // Acciones al hacer clic en btnReceive
            Intent intent = new Intent(MainActivity5.this, MainActivity7.class);
            startActivity(intent);
        });

    }
}