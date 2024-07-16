package com.example.eventdemo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String[] mobileArray ={"Iphone", "Samsung", "OnePlus", "Nothing", "Poco"};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        Button button = (Button) findViewById(R.id.button);
        TextView textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                textView.setText("Button Clicked");
            }
        });

        ListView listView1 = findViewById(R.id.listView);
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mobileArray);
        listView1.setAdapter(adapter);
        listView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long id) {
                Toast.makeText(getApplicationContext(),"Long Clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ConstraintLayout myView = (ConstraintLayout) findViewById(R.id.main);
        myView.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(MainActivity.this,"Touch Event", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        myView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int actionPeformed = event.getAction();

                switch (actionPeformed) {
                    case MotionEvent.ACTION_DOWN: {
                        Toast.makeText(MainActivity.this, "Multi Touch Detected", Toast.LENGTH_SHORT).show();
                        break;
                    }

//                    case MotionEvent.ACTION_MOVE: {
//                        Toast.makeText(MainActivity.this, "Multi Touch Detected", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
                }
                return true;
            }
        });
    }

}