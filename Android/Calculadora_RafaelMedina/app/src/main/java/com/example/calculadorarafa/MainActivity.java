package com.example.calculadorarafa;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private String operador = "";
    private double num1 = 0;

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

        // Asignar listeners a los botones numéricos y el punto
        int[] ids = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnPunto, R.id.btnRes, R.id.btnSum, R.id.btnMul,R.id.btnDiv,R.id.btnIgu
        };

        for (int id : ids) {
            findViewById(id).setOnClickListener(this::presionarDigito);
        }
        // Asignar listener al botón igual
        findViewById(R.id.btnIgu).setOnClickListener(this::presionarIgual);

        // Asignar listeners a los botones de operación (+, -, *, /)
        findViewById(R.id.btnSum).setOnClickListener(v -> presionarOperacion("+"));
        findViewById(R.id.btnRes).setOnClickListener(v -> presionarOperacion("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> presionarOperacion("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> presionarOperacion("/"));
    }

    public void presionarDigito(View view) {
        TextView tv_num2 = findViewById(R.id.tv_num2);
        String num2 = tv_num2.getText().toString();

        int id = view.getId();

        if (id == R.id.btn0) {
            tv_num2.setText(num2 + "0");
        } else if (id == R.id.btn1) {
            tv_num2.setText(num2 + "1");
        } else if (id == R.id.btn2) {
            tv_num2.setText(num2 + "2");
        } else if (id == R.id.btn3) {
            tv_num2.setText(num2 + "3");
        } else if (id == R.id.btn4) {
            tv_num2.setText(num2 + "4");
        } else if (id == R.id.btn5) {
            tv_num2.setText(num2 + "5");
        } else if (id == R.id.btn6) {
            tv_num2.setText(num2 + "6");
        } else if (id == R.id.btn7) {
            tv_num2.setText(num2 + "7");
        } else if (id == R.id.btn8) {
            tv_num2.setText(num2 + "8");
        } else if (id == R.id.btn9) {
            tv_num2.setText(num2 + "9");
        } else if (id == R.id.btnPunto) {
            tv_num2.setText(num2 + ".");
        } else if (id == R.id.btnRes) {
            tv_num2.setText(num2 + "-");
        } else if (id == R.id.btnSum) {
            tv_num2.setText(num2 + "+");
        } else if (id == R.id.btnMul) {
            tv_num2.setText(num2 + "*");
        } else if (id == R.id.btnDiv) {
            tv_num2.setText(num2 + "/");
        }
    }
    public void presionarOperacion(String op) {
        TextView tv_num2 = findViewById(R.id.tv_num2);
        try {
            num1 = Double.parseDouble(tv_num2.getText().toString());
        } catch (NumberFormatException e) {
            num1 = 0;
        }
        operador = op;
        tv_num2.setText(""); // Vacía para el siguiente número
    }

    public void presionarIgual(View view) {
        TextView tv_num2 = findViewById(R.id.tv_num2);
        TextView tvRes = findViewById(R.id.tvRes);

        double num2;
        try {
            num2 = Double.parseDouble(tv_num2.getText().toString());
        } catch (NumberFormatException e) {
            tvRes.setText("Error");
            return;
        }

        double resultado = 0;
        switch (operador) {
            case "+": resultado = num1 + num2; break;
            case "-": resultado = num1 - num2; break;
            case "*": resultado = num1 * num2; break;
            case "/": resultado = num2 != 0 ? num1 / num2 : Double.NaN; break;
            default: tvRes.setText("Sin operación"); return;
        }
        tvRes.setText(String.valueOf(resultado));
        tv_num2.setText(""); // Opcional: limpiar el campo de entrada
    }
}

