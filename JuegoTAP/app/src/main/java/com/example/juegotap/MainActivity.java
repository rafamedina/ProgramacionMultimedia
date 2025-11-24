package com.example.juegotap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

// Main activity class, inherits from AppCompatActivity
public class MainActivity extends AppCompatActivity {
    // Declare UI elements
    Button btnIniciar; // Button for login
    Button btnRegistrar; // Button for registration

    // These TextViews are declared but not initialized, maybe they are legacy code
    TextView TvUsu;
    TextView TvPass;

    // Input fields for username and password
    TextInputEditText TiUsu;
    TextInputEditText TiPass;
    // Variables to store user input
    String usuario;
    String password;

    // Variable to store the user's score
    int puntuaje;

    // Intent to start the next activity
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the superclass's onCreate method
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge display
        EdgeToEdge.enable(this);
        // Set the layout for this activity
        setContentView(R.layout.activity_main);

        // Listener to handle system window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            // Get system bar insets
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Apply padding to the view
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            // Return the insets
            return insets;
        });

        // Initialize input fields by finding them in the layout
        TiPass = findViewById(R.id.txtInputPass);
        TiUsu = findViewById(R.id.txtInputUsuario);

        // Initialize buttons by finding them in the layout
        btnIniciar = findViewById(R.id.btnIniciarSesion);
        btnRegistrar = findViewById(R.id.btnCrearCuenta);


        // Set a click listener for the register button
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear previous values
                usuario = "";
                password = "";

                // Get text from input fields and trim whitespace
                usuario = TiUsu.getText().toString().trim();
                password = TiPass.getText().toString().trim();

                // Check if fields are empty
                if (usuario.isEmpty() || password.isEmpty()) {
                    // Show a toast message if fields are empty
                    Toast.makeText(MainActivity.this, "Rellena usuario y contraseña", Toast.LENGTH_SHORT).show();
                    // Stop further execution
                    return;
                }

                // Get a reference to the Firebase database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
                // Get a reference to the "usuarios" node
                DatabaseReference myRef = database.getReference("usuarios");

                // Add a listener to read the data once
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot lista) {
                        // Flag to check if the user exists
                        boolean existe = false;

                        // Iterate through all children under "usuarios"
                        for (DataSnapshot individual : lista.getChildren()) {
                            // Get the value as a String
                            String valor = individual.getValue(String.class);
                            // Skip if the value is null
                            if (valor == null) continue;

                            // Split the string into parts using ";" as a delimiter
                            String[] partes = valor.split(";");

                            // Ensure there are at least two parts (user and password)
                            if (partes.length < 2) continue;

                            // Extract username and password from the parts
                            String usuariodb = partes[0];
                            String passworddb = partes[1];
                            // The score would be in partes[2], but it's ignored here

                            // Check only if the username already exists
                            if (usuariodb.equals(usuario)) {
                                existe = true;
                                // Exit the loop since the user was found
                                break;
                            }
                        }

                        // Check if the user already exists
                        if (existe) {
                            // Show a toast message indicating the user exists
                            Toast.makeText(MainActivity.this, "Usuario Ya existe", Toast.LENGTH_SHORT).show();
                        } else {
                            // Add the new user to the database with a default score of 0
                            myRef.push().setValue(usuario + ";" + password + ";" + 0);
                            // Show a confirmation toast
                            Toast.makeText(MainActivity.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Log a warning if there's an error reading from the database
                        Log.w("FIREBASE", "Error leyendo", databaseError.toException());
                    }
                });
            }
        });
        i = new Intent(MainActivity.this, SecondActivity.class);
//        imagen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(editTex != null){
//                    String valorEditText = editTex.getText().toString().trim();
//                    i.putExtra("editText",valorEditText);
//
//                    startActivity(i);
//                }
//
//            }
//        });



        // Set a click listener for the login button
        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear previous values
                usuario = "";
                password = "";
                // Get text from input fields and trim whitespace
                usuario = TiUsu.getText().toString().trim();
                password = TiPass.getText().toString().trim();
                // Get a reference to the Firebase database
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://juegotap-1ad8c-default-rtdb.europe-west1.firebasedatabase.app/");
                // Get a reference to the "usuarios" node
                DatabaseReference myRef = database.getReference("usuarios");

                // Add a listener to read the data once
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot lista) {
                        // Iterate through all children under "usuarios"
                        for (DataSnapshot individual : lista.getChildren()) {
                            // Get the value as a string
                            String valor = individual.getValue(String.class);
                            // Example format: "pepito;1234;100"

                            // Split the string into parts
                            String[] partes = valor.split(";");

                            // Extract user, password, score, and ID
                            String usuariodb = partes[0];
                            String passworddb = partes[1];
                            puntuaje = Integer.parseInt(partes[2]);
                            String id = individual.getKey();

                            // Check for nulls before comparing
                            if (usuario != null && password != null && usuariodb != null && passworddb != null) {
                                // Check if the credentials match
                                if (usuariodb.equals(usuario) && passworddb.equals(password)) {
                                    // Show a success message
                                    Toast.makeText(MainActivity.this, "Usuario Encontrado", Toast.LENGTH_SHORT).show();

                                    // Add user data to the intent
                                    i.putExtra("Usuario", usuario);
                                    i.putExtra("id", id);
                                    i.putExtra("password", passworddb);
                                    i.putExtra("Puntuaje", puntuaje);

                                    // Start the next activity
                                    startActivity(i);
                                }else {
                                    Toast.makeText(MainActivity.this, "El usuario no esta registrado o la contraseña es erronea", Toast.LENGTH_SHORT).show();
                            }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Log a warning if reading is cancelled or fails
                        Log.w("FIREBASE", "Error leyendo", databaseError.toException());
                    }


                });


            }

        });



    }


}

