package com.example.calculadorarafa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button b = findViewById(R.id.button2);
        TextView t3 = findViewById(R.id.textView5);
        ConstraintLayout m = findViewById(R.id.main);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t3.setText("On Click");
            }

        });
        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                t3.setText("Long Click");
                return false;
            }
        });

        m.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                HandleTouchEvent(v, event);
                return true;
            }
        });
    }
    public void HandleTouchEvent(View v, MotionEvent me)
    {
        TextView t1 = findViewById(R.id.textView);
        TextView t2 = findViewById(R.id.textView4);

        int pointerCount = me.getPointerCount();

        for(int i = 0; i <pointerCount; i++)
        {
            int x = (int) me.getX();
            int y = (int) me.getY();

            int id = me.getPointerId(i);

            int action = me.getActionMasked();

            int actionIndex = me.getActionIndex();

            String actionName;

            switch(action)
            {
                case MotionEvent.ACTION_UP:
                    actionName = "UP";
                    break;
                case MotionEvent.ACTION_DOWN:
                    actionName = "DOWN";
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    actionName = "PTR_UP";
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    actionName = "PTR_DOWN";
                    break;
                case MotionEvent.ACTION_MOVE:
                    actionName = "MOVE";
                    break;
                default:
                    actionName = "";
                    break;
            }
            String touchStatus = "Action: " + actionName + " Index: " + actionIndex + " ID: " + id + " X: " + x + " Y: " + y;
            if(id == 0)
            {
                t1.setText(touchStatus);
            }
            else
            {
                t2.setText(touchStatus);
            }

        }
    }
}


