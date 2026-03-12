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

public class MainActivity4 extends AppCompatActivity {

    private EditText editTextTextName;
    private EditText editTextTextEmailAddress2;
    private EditText editTextTextPassword2;
    private EditText editTextTextPassword3;
    private Button btnCreateAccount2;
    private TextView txtAccount;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextTextName = findViewById(R.id.editTextTextName);
        editTextTextEmailAddress2 = findViewById(R.id.editTextTextEmailAddress2);
        editTextTextPassword2 = findViewById(R.id.editTextPassword1);
        editTextTextPassword3 = findViewById(R.id.editTextPassword2);
        btnCreateAccount2 = findViewById(R.id.btnCreateAccount2);
        txtAccount = findViewById(R.id.txtAccount);


        btnCreateAccount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarYRedirigir();
            }

        });

        txtAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity3.class);
                startActivity(intent);
            }

        });

    }
    private void validarYRedirigir() {
        //Obtener los valores ingresados por el usuario
        String name = editTextTextName.getText().toString().trim();
        String email = editTextTextEmailAddress2.getText().toString().trim();
        String password = editTextTextPassword2.getText().toString().trim();
        String confirmPassword = editTextTextPassword3.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            //Mostrar un mensaje de error si los campos están vacíos
            Toast.makeText(this, R.string.alert_empty_fields, Toast.LENGTH_SHORT).show();
            return;
        }
        else if (!password.equals(confirmPassword)) {
            Toast.makeText(this, R.string.alert_coincide_password, Toast.LENGTH_SHORT).show();
            return;
        } else {
            Intent intent = new Intent(MainActivity4.this, MainActivity5.class);
            startActivity(intent);

            finish();

        }


    }






    }
