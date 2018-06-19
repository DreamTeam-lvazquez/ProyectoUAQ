package com.example.vazquez.proyectouaq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

public class MenuMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        CircleMenu circleMenu = (CircleMenu) findViewById(R.id.circle_menu);
        circleMenu.setMainMenu(Color.parseColor("#FFFFFF"), R.drawable.logo, R.drawable.logo)
                .addSubMenu(Color.parseColor("#E6E4E3"), R.drawable.user)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        if(index==0) {
                            Toast.makeText(MenuMain.this, "Usuario", Toast.LENGTH_SHORT).show();
                            Intent newFor = new Intent(MenuMain.this,UserActivity.class);
                            startActivity(newFor);
                        }else if(index==2) {
                            Toast.makeText(MenuMain.this, "Casos Clínicos", Toast.LENGTH_SHORT).show();
                            Intent newFor = new Intent(MenuMain.this,MenuActivity.class);
                            startActivity(newFor);
                        }
                    }
                })  .addSubMenu(Color.parseColor("#3DA4A6"), R.drawable.theory)
                    .addSubMenu(Color.parseColor("#A6C3CC"), R.drawable.cuestionary)
                    .addSubMenu(Color.parseColor("#FF7AC9CF"),R.drawable.logo)
                    .addSubMenu(Color.parseColor("#FF79C793"),R.drawable.info);

        }
    }