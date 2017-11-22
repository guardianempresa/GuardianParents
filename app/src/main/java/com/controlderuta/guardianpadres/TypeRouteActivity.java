package com.controlderuta.guardianpadres;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.controlderuta.guardianpadres.model.UserTravel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TypeRouteActivity extends AppCompatActivity {

    private String name="";

    private String lastname="";
    private float latitud= (float) 0.0;
    private float longitud= (float) 0.0;
    private String phone="";
    private String messageuser="go";
    private float distance=100000;
    private String check="n";
    private String iconface="";
    private String childname="";
    private String childlastname="";
    private String icon="";

    Button btnScolar;
    Button btnEnterprise;

    String Code;

    DatabaseReference databaseReference;
    String PruUid; //key no llave generada por google
    String keyfecha;


    AlertDialog alert = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_route);

        Code = getIntent().getExtras().getString("parametro");

        isNetDisponible();


        btnEnterprise = (Button) findViewById(R.id.btnAddEnterprise);
        btnScolar = (Button) findViewById(R.id.btnAddScolar);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid = user.getUid(); //Guardamos Uid en variable


        btnScolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz

                UserTravel userTravel = new UserTravel(PruUid, Code, latitud, longitud, messageuser, distance, check, iconface, childname, childlastname,icon,name,lastname,phone);
                databaseReference.child("usersvstravel").child(Code).child(PruUid).setValue(userTravel);


                Intent intent = new Intent(TypeRouteActivity.this, UserChildrenActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();

            }
        });


        btnEnterprise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                UserTravel userTravel = new UserTravel(PruUid, Code, latitud, longitud, messageuser, distance, check, iconface, childname, childlastname,icon,name,lastname,phone);
                //como la base de datos ya aesta creada en el activity anterior solo refresacamos los datos de nombre y apellidosllegando al nod
                databaseReference.child("usersvstravel").child(Code).child(PruUid).setValue(userTravel);

                //Sube datos distancia



                Intent intent = new Intent(TypeRouteActivity.this, UserNameActivity.class);
                intent.putExtra("parametro", Code);
                startActivity(intent);
                finish();
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
