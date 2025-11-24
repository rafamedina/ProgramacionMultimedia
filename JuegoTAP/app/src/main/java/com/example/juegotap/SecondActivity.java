package com.example.juegotap;

// Import necessary Android and Firebase libraries
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch; // This import is unused
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull; // This import is unused
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
import java.util.Collections; // This import is unused
import java.util.Comparator;

import POJO.Usuario; // Import the User Plain Old Java Object

// Second activity class, inherits from AppCompatActivity
public class SecondActivity extends AppCompatActivity {
    // Declare class variables
    int contador; // Declared but not used, maybe for a future feature
    Button btnClick; // Button to increment the score
    String Nombreusuario; // Stores the username from the intent
    Usuario usuario; // A user object to hold all user data
    String password; // Stores the user's password from the intent
    String idUsuario; // Stores the user's unique ID from Firebase
    int Usuariopuntuaje; // Stores the user's current score
    boolean subida = false; // Flag to check if the score bonus has been applied

    // Static TextViews to display the ranking
    static TextView txtR1;
    static TextView txtR2;
    static TextView txtR3;
    // Get instance of Firebase Database
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
    // Get reference to the "usuarios" node in the database
    DatabaseReference myRef = database.getReference("usuarios");


    // Static list to hold user objects for ranking
    static ArrayList<Usuario> Listausuarios = new ArrayList<>();
    static int puntuaje = 0; // Declared but shadowed by Usuariopuntuaje, likely legacy code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass's onCreate method
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge display for a modern UI
        EdgeToEdge.enable(this);
        // Set the layout for this activity
        setContentView(R.layout.activity_second);
        // Add a listener to handle system window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Get the insets for the system bars (status bar, navigation bar)
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply padding to the main view to avoid overlapping with system bars
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            // Load the ranking data when the layout is ready
            cargarRanking();
            // Return the insets to the system
            return insets;
        });

        // Initialize TextViews for the ranking by finding them in the layout
        txtR1 = findViewById(R.id.textViewRank1);
        txtR2 = findViewById(R.id.textViewRank2);
        txtR3 = findViewById(R.id.textViewRank3);

        // Initialize the main score button
        btnClick = this.findViewById(R.id.btnScore);

        // Retrieve data passed from MainActivity via the intent
        Nombreusuario = getIntent().getStringExtra("Usuario");

        idUsuario = getIntent().getStringExtra("id");
        password = getIntent().getStringExtra("password");
        Usuariopuntuaje = getIntent().getIntExtra("Puntuaje", 0); // Default to 0 if not found

        // Create a new Usuario object with the retrieved data
        usuario = new Usuario(idUsuario,Nombreusuario, Usuariopuntuaje);

        // Log the username and score for debugging purposes
        Log.d("datitos", Nombreusuario);
        Log.d("datitos", String.valueOf(Usuariopuntuaje));

        // Set the initial text of the button to the user's score
        btnClick.setText(String.valueOf(Usuariopuntuaje));

        // Set a click listener for the score button
        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to increment the score
                if (Usuariopuntuaje < 100) {
                    // Increment score by 1 if below 100
                    Usuariopuntuaje += 1;
                } else if (!subida) {
                    // If score reaches 100, apply a permanent +2 bonus
                    Usuariopuntuaje += 2;
                    // Show a toast message for the level up
                    Toast.makeText(SecondActivity.this, "Mejora de Level", Toast.LENGTH_SHORT).show();
                    // Set the flag to true so this only happens once
                    subida = true;
                } else if (subida || Usuariopuntuaje > 100) {
                    // Continue incrementing by 2 after the bonus is applied
                    Usuariopuntuaje += 2;
                }

                // Update the button's text to show the new score
                btnClick.setText(String.valueOf(Usuariopuntuaje));
                // Save the new score to Firebase
                guardarPuntuajeEnFirebase();
            }
        });
    }

    // Method to save the updated score to Firebase
    private void guardarPuntuajeEnFirebase() {
        // Format the data as a string: "username;password;score"
        String valor = Nombreusuario + ";" + password + ";" + Usuariopuntuaje;
        // Update the value in Firebase for the specific user ID
        myRef.child(idUsuario).setValue(valor);
        // Refresh the ranking display
        cargarRanking();
    }

    // Static method to load and display the top 3 users
    @SuppressLint("SetTextI18n") // Suppress lint warning for concatenating text in setText
    public static void cargarRanking() {
        // Get Firebase database and reference again (needed for static context)
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference myRef = database.getReference("usuarios");

        // Add a listener to read the data from Firebase once
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot lista) {
                // Clear the list before populating it to avoid duplicates
                Listausuarios.clear();

                // Iterate through each user in the database snapshot
                for (DataSnapshot individual : lista.getChildren()) {
                    // Get the string value (e.g., "user;pass;123")
                    String valor = individual.getValue(String.class);
                    // Skip if the value is null
                    if (valor == null) continue;

                    // Split the string by the delimiter ";"
                    String[] partes = valor.split(";");
                    // Ensure the data has at least 3 parts (user, pass, score)
                    if (partes.length < 3) continue;

                    // Parse the data from the string parts
                    String id  = individual.getKey();
                    String usuariodb  = partes[0];
                    String passworddb = partes[1]; // Not used here, but parsed
                    int puntuaje  = Integer.parseInt(partes[2]);

                    // Add a new Usuario object to the list
                    Listausuarios.add(new Usuario(id, usuariodb, puntuaje));
                }

                // Sort the list of users by score in descending order
                Listausuarios.sort(Comparator.comparingInt(Usuario::getPuntuaje).reversed());

                // Update the TextViews with the top 3 users, if they exist
                if (Listausuarios.size() >= 1)
                    txtR1.setText("Top 1: " + Listausuarios.get(0).getUsuario() + " | Puntos: " + Listausuarios.get(0).getPuntuaje());

                if (Listausuarios.size() >= 2)
                    txtR2.setText("Top 2: " + Listausuarios.get(1).getUsuario() + " | Puntos: " + Listausuarios.get(1).getPuntuaje());

                if (Listausuarios.size() >= 3)
                    txtR3.setText("Top 3: " + Listausuarios.get(2).getUsuario() + " | Puntos: " + Listausuarios.get(2).getPuntuaje());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Log a warning if reading from the database fails or is cancelled
                Log.w("FIREBASE", "Error leyendo", databaseError.toException());
            }
        });
    }
}



