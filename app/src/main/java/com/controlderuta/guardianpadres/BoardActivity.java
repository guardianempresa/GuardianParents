package com.controlderuta.guardianpadres;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class BoardActivity extends AppCompatActivity {

    String Code;
    int ControlAlerta1;
    int ControlAlerta2;
    int ControlAlerta3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Code=getIntent().getExtras().getString("parametro");

        ControlAlerta1=getIntent().getExtras().getInt("alerta1");
        ControlAlerta2=getIntent().getExtras().getInt("alerta2");
        ControlAlerta3=getIntent().getExtras().getInt("alerta3");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(BoardActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("alerta1",ControlAlerta1);
                intent.putExtra("alerta2",ControlAlerta2);
                intent.putExtra("alerta3",ControlAlerta3);
                startActivity(intent);
                finish();

            }
        },500);

    }
}
