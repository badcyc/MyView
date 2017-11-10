package com.example.myview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myview.View.MyView;

public class MainActivity extends AppCompatActivity {

    private MyView myView;
    private int percent=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myView=(MyView)findViewById(R.id.myview);
        Button button=(Button)findViewById(R.id.button);
        myView.setCurrentPercent(percent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                percent+=10;
                myView.setCurrentPercent(percent);
            }
        });
    }
}
