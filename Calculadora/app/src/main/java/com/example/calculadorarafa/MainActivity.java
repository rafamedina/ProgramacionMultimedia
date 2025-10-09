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
        }
    }
}

