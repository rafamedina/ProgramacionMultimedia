package com.example.ejemploclasehilos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button variableQueGuardaUnBoton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        variableQueGuardaUnBoton = findViewById(R.id.buttonGuapardo);

        variableQueGuardaUnBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new HiloJ().start();
            }
        });
    }
    class HiloJ extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);

                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "TERMINE EL SLEEP", Toast.LENGTH_SHORT).show()
                );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


