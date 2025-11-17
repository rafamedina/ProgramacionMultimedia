package com.example.juegotap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button btnIniciar;
    Button btnRegistrar;

    TextView TvUsu;
    TextView TvPass;

    TextInputEditText TiUsu;
    TextInputEditText TiPass;
    String usuario;
    String password;

    int puntuaje;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Write a message to the database

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            return insets;


        });

        TiPass = findViewById(R.id.txtInputPass);
        TiUsu = findViewById(R.id.txtInputUsuario);

        btnIniciar = findViewById(R.id.btnIniciarSesion);
        btnRegistrar = findViewById(R.id.btnCrearCuenta);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                usuario = "";
                password = "";

                usuario = TiUsu.getText().toString().trim();
                password = TiPass.getText().toString().trim();

                if (usuario.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("usuarios");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot lista) {

                        boolean existe = false;

                        // Recorremos todos los hijos dentro de "usuarios"
                        for (DataSnapshot individual : lista.getChildren()) {

                            String valor = individual.getValue(String.class);
                            if (valor == null) continue;

                            String[] partes = valor.split(";");

                            if (partes.length < 2) continue;

                            String usuariodb = partes[0];
                            String passworddb = partes[1];
                            // Si hay puntuaje sería partes[2], pero puedes ignorarlo aquí

                            // Solo controlamos que NO se repita el nombre
                            if (usuariodb.equals(usuario) && passworddb.equals(password)) {
                                existe = true;
                                break;  // ya sabemos que está, no hace falta seguir
                            }
                        }

                        if (existe) {
                            Toast.makeText(MainActivity.this, "Usuario Ya existe", Toast.LENGTH_SHORT).show();
                        } else {
                            myRef.push().setValue(usuario + ";" + password + ";" + 0);
                            Toast.makeText(MainActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("FIREBASE", "Error leyendo", databaseError.toException());
                    }
                });
            }
        });




        i = new Intent(MainActivity.this, SecondActivity.class);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = "";
                password = "";
                usuario = TiUsu.getText().toString().trim();
                password = TiPass.getText().toString().trim();
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference myRef = database.getReference("usuarios");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot lista) {

                        // Recorremos todos los hijos dentro de "usuarios"
                        for (DataSnapshot individual : lista.getChildren()) {

                            String valor = individual.getValue(String.class);
                            // Ej: "pepito;1234"

                            // Separar usuario y contraseña
                            String[] partes = valor.split(";");

                            String usuariodb = partes[0];
                            String passworddb = partes[1];
                            puntuaje =  Integer. parseInt(partes[2]);
                            String id = individual.getKey();

                            if (usuario != null && password != null && usuariodb != null && passworddb != null) {
                                if (usuariodb.equals(usuario) && passworddb.equals(password)) {

                                    Toast.makeText(MainActivity.this, "Usuario Encontrado", Toast.LENGTH_SHORT).show();

                                    i.putExtra("Usuario", usuario);
                                    i.putExtra("id", id);
                                    i.putExtra("password", passworddb);
                                    i.putExtra("Puntuaje", puntuaje);

                                    startActivity(i);


                                }
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("FIREBASE", "Error leyendo", databaseError.toException());

                    }
                });

            }
        });



    }
}


//
//        btn = findViewById(R.id.btnIniciarSesion);
//
//        btn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v){
//
//
//
//            }
//
//        });


