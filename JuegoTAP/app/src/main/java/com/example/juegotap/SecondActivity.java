package com.example.juegotap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import POJO.Usuario;

public class SecondActivity extends AppCompatActivity {
    int contador;
    Button btnClick;
    String Nombreusuario;
    Usuario usuario;
    String password;
    String idUsuario;
    int Usuariopuntuaje;
    boolean subida = false;

    static TextView txtR1;
    static TextView txtR2;
    static TextView txtR3;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference myRef = database.getReference("usuarios");

    static ArrayList<Usuario> Listausuarios = new ArrayList<>();
    static int puntuaje = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            cargarRanking();
            return insets;
        });


        txtR1 = findViewById(R.id.textViewRank1);
        txtR2 = findViewById(R.id.textViewRank2);
        txtR3 = findViewById(R.id.textViewRank3);


        btnClick = this.findViewById(R.id.btnScore);

        Nombreusuario = getIntent().getStringExtra("Usuario");
        idUsuario = getIntent().getStringExtra("id");
        password = getIntent().getStringExtra("password");

        Usuariopuntuaje = getIntent().getIntExtra("Puntuaje", 0);


        usuario = new Usuario(idUsuario,Nombreusuario, Usuariopuntuaje);


        Log.d("datitos", Nombreusuario);
        Log.d("datitos", String.valueOf(Usuariopuntuaje));

        btnClick.setText(String.valueOf(Usuariopuntuaje));


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Usuariopuntuaje < 100) {
                    Usuariopuntuaje += 1;

                } else if (!subida) {
                    Usuariopuntuaje += 2;
                    Toast.makeText(SecondActivity.this, "Mejora de Level", Toast.LENGTH_SHORT).show();
                    subida = true;


                } else if (subida || Usuariopuntuaje > 100) {
                    Usuariopuntuaje += 2;
                }


                btnClick.setText(String.valueOf(Usuariopuntuaje));
                guardarPuntuajeEnFirebase();
            }
        });


    }
    private void guardarPuntuajeEnFirebase() {
        String valor = Nombreusuario + ";" + password + ";" + Usuariopuntuaje;

        myRef.child(idUsuario).setValue(valor);
        cargarRanking();

    }



    @SuppressLint("SetTextI18n")
    public static void cargarRanking() {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("usuarios");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot lista) {

                Listausuarios.clear();

                for (DataSnapshot individual : lista.getChildren()) {

                    String valor = individual.getValue(String.class);
                    if (valor == null) continue;

                    String[] partes = valor.split(";");
                    if (partes.length < 3) continue;

                    String id  = individual.getKey();
                    String usuariodb  = partes[0];
                    String passworddb = partes[1];
                    int puntuaje  = Integer.parseInt(partes[2]);

                    Listausuarios.add(new Usuario(id, usuariodb, puntuaje));
                }

                Listausuarios.sort(Comparator.comparingInt(Usuario::getPuntuaje).reversed());

                if (Listausuarios.size() >= 1)
                    txtR1.setText("Top 1: " + Listausuarios.get(0).getUsuario() + " | Puntos: " + Listausuarios.get(0).getPuntuaje());

                if (Listausuarios.size() >= 2)
                    txtR2.setText("Top 2: " + Listausuarios.get(1).getUsuario() + " | Puntos: " + Listausuarios.get(1).getPuntuaje());

                if (Listausuarios.size() >= 3)
                    txtR3.setText("Top 3: " + Listausuarios.get(2).getUsuario() + " | Puntos: " + Listausuarios.get(2).getPuntuaje());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FIREBASE", "Error leyendo", databaseError.toException());
            }
        });
    }


}




