// SummaryActivity.java
package com.example.dailytasksapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class SummaryActivity extends AppCompatActivity {
    TextView summaryText;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        summaryText = findViewById(R.id.summaryText);
        prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);

        int tasksDone = prefs.getInt("tasksDone", 0);
        String name = prefs.getString("username", "Usuario");

        int tareasCompletadas = getIntent().getIntExtra("TAREAS_COMPLETADAS", 0);

        summaryText.setText(name + ", completaste " + tareasCompletadas + " tarea(s) en total.");

        Button volverBtn = findViewById(R.id.btnBackToTasks);
        volverBtn.setOnClickListener(v -> {
            finish();
        });
    }
}
