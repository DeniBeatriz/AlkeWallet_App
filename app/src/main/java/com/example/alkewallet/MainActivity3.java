package com.example.alkewallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    private EditText editTextTextEmailAddress;
    private EditText editTextTextPassword;
    private Button btnLogin;
    private TextView txtNewAccount;
    private TextView txtForgotAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtNewAccount = findViewById(R.id.txtNewAccount);
        txtForgotAccount = findViewById(R.id.txtForgotAccount);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarYRedirigir();
            }

        });

        txtNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
                startActivity(intent);
            }

        });

        txtForgotAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity3.this, R.string.alert_forgot_password, Toast.LENGTH_SHORT).show();
            }

        });

    }
    private void validarYRedirigir() {
        //Obtener los valores ingresados por el usuario
        String email = editTextTextEmailAddress.getText().toString().trim();
        String password = editTextTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            //Mostrar un mensaje de error si los campos están vacíos
            Toast.makeText(this, R.string.alert_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent(MainActivity3.this, MainActivity5.class);
            startActivity(intent);

            finish();

        }


    }


}










