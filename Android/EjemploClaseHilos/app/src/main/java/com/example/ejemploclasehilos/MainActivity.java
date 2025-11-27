package com.example.ejemploclasehilos;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.jetbrains.annotations.Async;

public class MainActivity extends AppCompatActivity {

    private Button variableQueGuardaUnBoton;
    Handler handler = new Handler(Looper.getMainLooper());

    TextView viewContador ;

    ImageView imagealfa;

     int  concurrencia = 0;

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
        viewContador = findViewById(R.id.textView_contador);
        imagealfa = findViewById(R.id.imageViewAlfa);
        variableQueGuardaUnBoton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //new HiloJ().start();
                //new HiloContador().start();
                //AsyncTask a = new MiClaseAsincrona().execute();
                int cpu_cores = Runtime.getRuntime().availableProcessors();

                if(cpu_cores>concurrencia){
                    imagealfa.setImageAlpha(0);

                    AsyncTask a = new MiClaseAsincronaAlfa().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                    concurrencia++;
                }

            }
        });
    }

    private class MiClaseAsincrona extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewContador.setText("Comienza la cuenta atras");
        }

        @Override
        protected String doInBackground(String... strings) {
            int i = 0;
            while(i<=20){
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                    i++;
                } catch ( InterruptedException e) {
                    return e.getLocalizedMessage();
                }
            }
            return "Se acabo la cuenta atras";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(viewContador!=null){
                viewContador.setText("contador " + values[0] + " el valor x1000 es: " + values[0] * 1000);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            viewContador.setText(s);
            concurrencia--;

        }
    }
    private class MiClaseAsincronaAlfa extends AsyncTask<String, Integer, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            viewContador.setText("Empieza a cambiar");
        }

        @Override
        protected String doInBackground(String... strings) {
            int i = 0;

            while(i<=1000){
                publishProgress(i);
                try {
                    Thread.sleep(500);
                    i++;
                } catch ( InterruptedException e) {
                    return e.getLocalizedMessage();
                }

            }
            return "Se acabo la cuenta atras";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if(viewContador!=null && imagealfa != null){
                imagealfa.setImageAlpha(values[0]);
                viewContador.setText("El alfa es de:  " + values[0]);
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            viewContador.setText(s);
            concurrencia--;
        }
    }
    class HiloJ extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(10000);

                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(MainActivity.this, "TERMINE EL SLEEP", Toast.LENGTH_SHORT).show()
                );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class HiloContador extends Thread {
        @Override
        public void run() {



            for (int i = 0; i <= 1500; i++) {

                int finalI = i; // necesario porque i se usa dentro del Runnable

                handler.post(() -> {
                    viewContador.setText(String.valueOf(finalI));
                });

                try {
                    Thread.sleep(1000); // retardo para ver el conteo (10ms)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}


