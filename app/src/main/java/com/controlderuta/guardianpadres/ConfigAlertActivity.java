package com.controlderuta.guardianpadres;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.controlderuta.guardianpadres.model.AlertDistance;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConfigAlertActivity extends AppCompatActivity {


    private DatabaseReference databaseReference;
    String PruUid;

    String Code;
    int ControlAlerta1;
    int ControlAlerta2;
    int ControlAlerta3;

    int alertaOne;
    int alertaTwo;
    int alertaThree;

    TextView txtOne;
    TextView txtTwo;
    TextView txtThree;


    //-----------Componentes

    SeekBar barOne;
    SeekBar barTwo;
    SeekBar barThree;
    Button btnBackConfig;
    FloatingActionButton btnSigConfig;



    AlertDialog alert = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_alert);

        isNetDisponible();

        barOne          = (SeekBar)findViewById(R.id.seekBarConfigOne);
        barTwo          = (SeekBar)findViewById(R.id.seekBarConfigTwo);
        barThree        = (SeekBar)findViewById(R.id.seekBarConfigThree);
        btnBackConfig   = (Button)findViewById(R.id.btnBackConfig);
        btnSigConfig    = (FloatingActionButton)findViewById(R.id.fabSigConfig);

        txtOne          = (TextView)findViewById(R.id.textlayout1);
        txtTwo          = (TextView)findViewById(R.id.textlayout2);
        txtThree        = (TextView)findViewById(R.id.textlayout3);



        Code=getIntent().getExtras().getString("parametro");
        //ControlAlerta1=getIntent().getExtras().getInt("alerta1");
        //ControlAlerta2=getIntent().getExtras().getInt("alerta2");
        //ControlAlerta3=getIntent().getExtras().getInt("alerta3");

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        databaseReference.child("uservscode").child(PruUid).child(Code).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    AlertDistance alertDistance = dataSnapshot.getValue(AlertDistance.class);
                    alertaOne   = alertDistance.getAlertone();
                    alertaTwo   = alertDistance.getAlerttwo();
                    alertaThree = alertDistance.getAlertthree();



                    txtOne.setText(alertaOne+" mts");
                    txtTwo.setText(alertaTwo+" mts");
                    txtThree.setText(alertaThree+" mts");

                    barOne.setProgress(alertaOne);
                    barTwo.setProgress(alertaTwo);
                    barThree.setProgress(alertaThree);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        barOne.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                alertaOne = progress;

                if (alertaOne>0){
                    txtOne.setText(alertaOne+" mts");

                }else {
                    txtOne.setText(R.string.desactive);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        barTwo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                alertaTwo =progress;

                if (alertaTwo>0){
                    txtTwo.setText(alertaTwo+" mts");

                }else {
                    txtTwo.setText(R.string.desactive);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        barThree.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                alertaThree =progress;

                if (alertaThree>0){
                    txtThree.setText(alertaThree+" mts");

                }else {
                    txtThree.setText(R.string.desactive);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSigConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ControlAlerta1=0;
                //ControlAlerta2=0;
                //ControlAlerta3=0;

                //Intent intent = new Intent(ConfigAlertActivity.this, NewMapActivity.class);
                //intent.putExtra("parametro",Code);
                //intent.putExtra("alerta1",ControlAlerta1);
                //intent.putExtra("alerta2",ControlAlerta2);
                //intent.putExtra("alerta3",ControlAlerta3);


                databaseReference.child("uservscode").child(PruUid).child(Code).child("alertone").setValue(alertaOne);
                databaseReference.child("uservscode").child(PruUid).child(Code).child("alerttwo").setValue(alertaTwo);
                databaseReference.child("uservscode").child(PruUid).child(Code).child("alertthree").setValue(alertaThree);

                //startActivity(intent);
                finish();

            }
        });

        btnBackConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(ConfigAlertActivity.this, NewMapActivity.class);
                //intent.putExtra("parametro",Code);
                //intent.putExtra("alerta1",ControlAlerta1);
                //intent.putExtra("alerta2",ControlAlerta2);
                //intent.putExtra("alerta3",ControlAlerta3);
                //startActivity(intent);
                finish();
            }
        });

    }

    public void extraerData(){

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("uservscode").child(PruUid).child(Code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    AlertDistance alertDistance = dataSnapshot.getValue(AlertDistance.class);
                    alertaOne   = alertDistance.getAlertone();
                    alertaTwo   = alertDistance.getAlerttwo();
                    alertaThree = alertDistance.getAlertthree();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void internetDialog(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.internetfailen))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {

                    }
                });

        alert = builder.create();
        alert.show();
    }

    private void isNetDisponible() {//valida internet

        ConnectivityManager cm;
        NetworkInfo ni;
        cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        ni = cm.getActiveNetworkInfo();
        boolean tipoConexion1 = false;
        boolean tipoConexion2 = false;

        if (ni != null) {
            ConnectivityManager connManager1 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager1.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            ConnectivityManager connManager2 = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobile = connManager2.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mWifi.isConnected()) {
                tipoConexion1 = true;


            }
            if (mMobile.isConnected()) {
                tipoConexion2 = true;

            }

            if (tipoConexion1 == true || tipoConexion2 == true) {

        /* Estas conectado a internet usando wifi o redes moviles, puedes enviar tus datos */
            }
        }
        else {
            internetDialog();

/* No estas conectado a internet */
        }

    }

}
