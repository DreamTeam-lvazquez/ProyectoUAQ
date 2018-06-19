package com.example.vazquez.proyectouaq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuOne extends AppCompatActivity {
 //quiz lucy
    public void nextActivity1(View view){
        startActivity(new Intent(this, QuizActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_one);
    }
}