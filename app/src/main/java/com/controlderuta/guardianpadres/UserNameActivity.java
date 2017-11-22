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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import es.dmoral.toasty.Toasty;

public class UserNameActivity extends AppCompatActivity {

    FloatingActionButton btnSigPerson;
    EditText edtNameDriver;
    EditText edtLastNameDriver;
    String nameconductor;
    String lastnameconductor;
    int a=0,b=0;
    int autovalidador;
    String Code;


    AlertDialog alert = null;


    //firebase var

    DatabaseReference databaseReference;
    String PruUid; //key no llave generada por google


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);

        Code=getIntent().getExtras().getString("parametro");

        isNetDisponible();

        btnSigPerson        = (FloatingActionButton) findViewById(R.id.btnSigPersonal);
        edtNameDriver       = (EditText)findViewById(R.id.namePerson);
        edtLastNameDriver   = (EditText)findViewById(R.id.lastnamePerson);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        btnSigPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nameconductor       = edtNameDriver.getText().toString();
                lastnameconductor   = edtLastNameDriver.getText().toString();

                if (nameconductor.equals("")){
                    a=0;
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(UserNameActivity.this,getText(R.string.validadorpersonalnamevacio), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    a=1;
                }

                if (lastnameconductor.equals("")){
                    b=0;
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(UserNameActivity.this,getText(R.string.validadorpersonallastnamevacio), Toast.LENGTH_SHORT, true).show();//info del toasty

                }else {
                    b=1;
                }

                autovalidador=a+b;




                if (autovalidador==2){

                    //como la base de datos ya aesta creada en el activity anterior solo refresacamos los datos de nombre y apellidosllegando al nodo

                    DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
                    DatabaseReference mensajeRef = ref.child("usersvstravel").child(Code).child(PruUid).child("lastname");
                    mensajeRef.setValue(lastnameconductor);

                    DatabaseReference mensajeRef2 = ref.child("usersvstravel").child(Code).child(PruUid).child("name");
                    mensajeRef2.setValue(nameconductor);

                    DatabaseReference mensajeRef3 = ref.child("usersvstravel").child(Code).child(PruUid).child("childlastname");
                    mensajeRef3.setValue(lastnameconductor);

                    DatabaseReference mensajeRef4= ref.child("usersvstravel").child(Code).child(PruUid).child("childname");
                    mensajeRef4.setValue(nameconductor);


                    Intent intent = new Intent(UserNameActivity.this, PhoneActivity.class);
                    intent.putExtra("parametro", Code);
                    startActivity(intent);
                    finish();

                }else {
                    Toasty.Config.getInstance() //Configuracion del toasty

                            .setInfoColor(ContextCompat.getColor(getApplicationContext(),R.color.colorBottonFloat)) //Color de relleno
                            .setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorWhite))  //Color de letra
                            .apply();

                    Toasty.info(UserNameActivity.this,getText(R.string.validadorpersonal), Toast.LENGTH_SHORT, true).show();//info del toasty
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

