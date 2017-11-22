package com.controlderuta.guardianpadres;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.controlderuta.guardianpadres.model.LocRoute;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import es.dmoral.toasty.Toasty;

public class NewRouteActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;

    FloatingActionButton btnSigNewCode;
    Button btnBackNewRoute;
    EditText edtCodeStar;
    String txtCodeStar;
    String PruUid;
    String NameRoute;
    String Code;
    int ControlAlerta1;
    int ControlAlerta2;
    int ControlAlerta3;

    AlertDialog alert = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_route);

        Code=getIntent().getExtras().getString("parametro");
        //ControlAlerta1=getIntent().getExtras().getInt("alerta1");
        //ControlAlerta2=getIntent().getExtras().getInt("alerta2");
        //ControlAlerta3=getIntent().getExtras().getInt("alerta3");

        isNetDisponible();


        btnBackNewRoute     = (Button) findViewById(R.id.btnBackNewRoute);
        btnSigNewCode       = (FloatingActionButton) findViewById(R.id.fabSigNewRoute);
        edtCodeStar         = (EditText) findViewById(R.id.edtNewCode);


        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

        btnBackNewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(NewRouteActivity.this, NewMapActivity.class);
                //intent.putExtra("parametro", Code);
                //ControlAlerta1=getIntent().getExtras().getInt("alerta1");
                //ControlAlerta2=getIntent().getExtras().getInt("alerta2");
                //ControlAlerta3=getIntent().getExtras().getInt("alerta3");
                //startActivity(intent);
                finish();
            }
        });


        btnSigNewCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCodeStar = edtCodeStar.getText().toString();

                if (txtCodeStar.equals("")) {

                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(NewRouteActivity.this, getText(R.string.validadorcodestar), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else{

                    databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
                    databaseReference.child("travel").child(txtCodeStar).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.exists()) {

                                LocRoute locRoute = dataSnapshot.getValue(LocRoute.class);
                                NameRoute = locRoute.getNombre();

                                databaseReference.child("uservscode").child(PruUid).child(txtCodeStar).child("code").setValue(txtCodeStar);
                                databaseReference.child("uservscode").child(PruUid).child(txtCodeStar).child("nameroute").setValue(NameRoute);
                                databaseReference.child("uservscode").child(PruUid).child(txtCodeStar).child("alertone").setValue(200);
                                databaseReference.child("uservscode").child(PruUid).child(txtCodeStar).child("alerttwo").setValue(1000);
                                databaseReference.child("uservscode").child(PruUid).child(txtCodeStar).child("alertthree").setValue(3000);

                                Intent intent = new Intent(NewRouteActivity.this, TypeRouteActivity.class);
                                intent.putExtra("parametro", txtCodeStar);
                                startActivity(intent);
                                finish();


                            } else {
                                Toasty.Config.getInstance() //Configuracion del toasty

                                        .setInfoColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)) //Color de relleno
                                        .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite))  //Color de letra
                                        .apply();

                                Toasty.info(NewRouteActivity.this, getText(R.string.validadorcodestarexist), Toast.LENGTH_LONG, true).show();//info del toasty

                                Intent intent = new Intent(NewRouteActivity.this, NewRouteActivity.class);
                                intent.putExtra("parametro", Code);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
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
