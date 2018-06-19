package com.example.vazquez.proyectouaq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuTwo extends AppCompatActivity {
 //quiz lucy
    public void nextActivityCase2(View view){
        startActivity(new Intent(this, QuizTwoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_two);
    }
}

