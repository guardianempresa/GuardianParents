package com.controlderuta.guardianpadres;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.controlderuta.guardianpadres.model.DataListRoute;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ListRouteActivity extends AppCompatActivity {

    private static final String TAG = "ListRouteActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<DataListRoute> prueba;

    String PruUid;

    int ControlAlerta1=0;
    int ControlAlerta2=0;
    int ControlAlerta3=0;

    String fechapago;
    String fechainicio;

    AlertDialog alert = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_route);

        isNetDisponible();
        consultapago();


        lstArtist = (ListView)findViewById(R.id.lstArtistdos);
        artistNames = new ArrayList<>();
        prueba=new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,R.layout.fila_listas,R.id.nombre_fila_lista_route,artistNames);
        lstArtist.setAdapter(arrayAdapter);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        databaseReference.child("uservscode").child(PruUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistNames.clear();
                prueba.clear();

                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                        DataListRoute datalist = snapshot.getValue(DataListRoute.class);
                        Log.w(TAG,datalist.getNameroute());
                        artistNames.add(datalist.getNameroute());
                        prueba.add(datalist);

                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        lstArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Code = prueba.get(position).getCode();
                Intent intent = new Intent(ListRouteActivity.this, NewMapActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("alerta1",ControlAlerta1);
                intent.putExtra("alerta2",ControlAlerta2);
                intent.putExtra("alerta3",ControlAlerta3);
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

    private  void  consultapago(){

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("loginuser").child(PruUid).child("fechapago").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                fechapago   = dataSnapshot.getValue(String.class);

                Calendar fechaA = Calendar.getInstance();
                fechainicio=(String.valueOf(fechaA.get(Calendar.YEAR))+"-"+String.valueOf(+fechaA.get(Calendar.MONTH)+1)+"-"+String.valueOf(+fechaA.get(Calendar.DAY_OF_MONTH)));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                Date fechaInicial= null;
                try {
                    fechaInicial = dateFormat.parse(fechainicio);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Date fechaFinal= null;
                try {
                    fechaFinal = dateFormat.parse(fechapago);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);

                Toast.makeText(ListRouteActivity.this,"Tiene "+dias+" días para que su periodo de prueba termine.",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}