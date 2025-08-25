// MainActivity.java
package com.example.dailytasksapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;
    Button continueBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.nameInput);
        continueBtn = findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(view -> {
            String name = nameInput.getText().toString().trim();
            if (!name.isEmpty()) {
                SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                prefs.edit().putString("username", name).apply();

                Intent intent = new Intent(MainActivity.this, TaskListActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Ingresá tu nombre", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
