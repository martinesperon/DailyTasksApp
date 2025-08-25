package com.example.dailytasksapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import android.graphics.Color;
import android.widget.CheckBox;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;


public class TaskListActivity extends AppCompatActivity {

    private LinearLayout taskContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        taskContainer = findViewById(R.id.taskContainer);
        Button btnAddTask = findViewById(R.id.btnAddTask);

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarTarea();
            }
        });

        TextView welcomeText = findViewById(R.id.welcomeText);

        TextView nuevaTarea = new TextView(this);
        nuevaTarea.setTextColor(Color.parseColor("#212121"));

        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "Usuario");

        welcomeText.setText("Hola " + username + ", estas son tus tareas:");

        cargarTareasGuardadas();

        Button btnResumen = findViewById(R.id.btnResumen);
        btnResumen.setOnClickListener(v -> {
            int tareasCompletadas = 0;


            List<CheckBox> todasLasTareas = new ArrayList<>();
            CheckBox tarea1 = findViewById(R.id.task1);
            CheckBox tarea2 = findViewById(R.id.task2);
            CheckBox tarea3 = findViewById(R.id.task3);

            if (tarea1 != null) todasLasTareas.add(tarea1);
            if (tarea2 != null) todasLasTareas.add(tarea2);
            if (tarea3 != null) todasLasTareas.add(tarea3);


            for (int i = 0; i < taskContainer.getChildCount(); i++) {
                View view = taskContainer.getChildAt(i);
                if (view instanceof CheckBox) {
                    todasLasTareas.add((CheckBox) view);
                }
            }


            for (CheckBox tarea : todasLasTareas) {
                if (tarea.isChecked()) {
                    tareasCompletadas++;
                }
            }


            Intent intent = new Intent(TaskListActivity.this, SummaryActivity.class);
            intent.putExtra("TAREAS_COMPLETADAS", tareasCompletadas);
            startActivity(intent);
        });
    }



    private void mostrarDialogoAgregarTarea() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nueva tarea");

        final EditText input = new EditText(this);
        input.setHint("Escribí tu tarea...");
        builder.setView(input);

        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tarea = input.getText().toString().trim();
                if (!tarea.isEmpty()) {
                    agregarTareaEnPantalla(tarea);
                    guardarTarea(tarea);
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void agregarTareaEnPantalla(String tarea) {
        CheckBox nuevaTarea = new CheckBox(this);
        nuevaTarea.setText(tarea);
        nuevaTarea.setTextSize(16);
        nuevaTarea.setTextColor(Color.WHITE);
        nuevaTarea.setBackgroundColor(Color.TRANSPARENT);
        nuevaTarea.setPadding(0, 10, 0, 10);
        nuevaTarea.setEnabled(true);
        taskContainer.addView(nuevaTarea);
    }

    private void guardarTarea(String tarea) {
        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        try {
            String tareasJSON = prefs.getString("tareas", "[]");
            JSONArray tareasArray = new JSONArray(tareasJSON);

            tareasArray.put(tarea);

            editor.putString("tareas", tareasArray.toString());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarTareasGuardadas() {
        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String tareasJSON = prefs.getString("tareas", "[]");

        try {
            JSONArray tareasArray = new JSONArray(tareasJSON);
            for (int i = 0; i < tareasArray.length(); i++) {
                String tarea = tareasArray.getString(i);
                agregarTareaEnPantalla(tarea);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



