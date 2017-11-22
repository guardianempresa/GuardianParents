package com.controlderuta.guardianpadres;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.RingtonePreference;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.controlderuta.guardianpadres.model.AlertDistance;
import com.controlderuta.guardianpadres.model.DialogCall;
import com.controlderuta.guardianpadres.model.LocRoute;
import com.controlderuta.guardianpadres.model.NotifyAlert;
import com.controlderuta.guardianpadres.model.StateAlert;
import com.controlderuta.guardianpadres.model.UserTravel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


import es.dmoral.toasty.Toasty;

public class NewMapActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {


    AlertDialog alert = null;

    //------variablñes graficas---

    FloatingActionButton fabVoy;
    FloatingActionButton fabNoVoy;
    TextView txtDistance;
    SeekBar NewSeekBar;
    TextView txtSeekBar;
    Button btnPhone;

    DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private static final int RC_SIGN_IN=1;
    private GoogleApiClient googleApiClient;

    private static final String TAG ="NewMapActivity" ;
    public static final int NOTIFICACION_ID=1;

    double longitudRoute;
    double latitudRoute;
    double latitudUser;
    double longitudUser;
    String nameRoute;
    int alertRoute;
    String alertHour;
    String phone;
    String nameDriver;
    String lastnameDriver;

    LatLng coordenadasRoute;
    LatLng coordenadasUser;

    String PruUid;

    String Code;
    String Codificacion;
    String Avatar;
    String codeprueba;
    double distance;
    int distancemts;


    String one;
    String two;
    String three;
    String four;
    String five;
    int datastade;

    int StateAl;
    int Type;
    String HourInicial;
    String HourFinal;


    String state;

    int zoomCamera = 13;


    /// Control de alertas

    int ControlAlerta1;
    int ControlAlerta2;
    int ControlAlerta3;

    int alertDistanceOne;
    int alertDistanceTwo;
    int alertDistanceThree;

    private GoogleMap mMap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_map);

        isNetDisponible();

        Code=getIntent().getExtras().getString("parametro");
        ControlAlerta1=getIntent().getExtras().getInt("alerta1");
        ControlAlerta2=getIntent().getExtras().getInt("alerta2");
        ControlAlerta3=getIntent().getExtras().getInt("alerta3");

        NewSeekBar  = (SeekBar)findViewById(R.id.seekBarNewMap);
        txtSeekBar  = (TextView)findViewById(R.id.textseekBar);
        txtDistance = (TextView)findViewById(R.id.distanceText);
        btnPhone    = (Button)findViewById(R.id.btnphone);

        txtSeekBar.setText("Zoom: 56%");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(R.string.title_activity_new_map);
        setSupportActionBar(toolbar);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable

        firebaseAuth = FirebaseAuth.getInstance();//Firebase Ultimo
        firebaseAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){

                FirebaseUser user =firebaseAuth.getCurrentUser();
            }
        };


        one=getText(R.string.alertone).toString();
        two=getText(R.string.alerttwo).toString();
        three=getText(R.string.alertthree).toString();
        four=getText(R.string.alertfoue).toString();
        five=getText(R.string.alertfive).toString();

        /// Valida inicio del recorrido

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("starandfinish").child(Code).child("estado").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                datastade = dataSnapshot.getValue(int.class);

                if (datastade==1){

                    pruebabus();


                }else{

                    stateDialog();
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

// Cierre valdador inicio del recorrido


        phoneData();


        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertCall();
            }
        });

        fabVoy = (FloatingActionButton) findViewById(R.id.fabVoy);
        fabNoVoy = (FloatingActionButton) findViewById(R.id.fabNoVoy);

        ///----------------------Controlador de floating action buttom

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("usersvstravel").child(Code).child(PruUid).child("messageuser").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                state = dataSnapshot.getValue(String.class);

                if (state.equals("go")){

                fabNoVoy.setVisibility(View.VISIBLE);
                fabVoy.setVisibility(View.INVISIBLE);


                }else{

                fabNoVoy.setVisibility(View.INVISIBLE);
                fabVoy.setVisibility(View.VISIBLE);

            }}

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //--------------Barraa zoom----------

        NewSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                zoomCamera = progress;
                int porc= (100/24)*(progress+1);
                txtSeekBar.setText("Zoom: "+porc+"%");
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadasRoute,zoomCamera));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        fabVoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fabNoVoy.setVisibility(View.VISIBLE);
                fabVoy.setVisibility(View.INVISIBLE);

                Toasty.Config.getInstance() //Configuracion del toasty

                        .setInfoColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)) //Color de relleno
                        .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite))  //Color de letra
                        .apply();

                Toasty.info(NewMapActivity.this, getText(R.string.textGo), Toast.LENGTH_LONG, true).show();//info del toasty

                databaseReference.child("usersvstravel").child(Code).child(PruUid).child("messageuser").setValue("go");

            }
        });


        fabNoVoy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fabNoVoy.setVisibility(View.INVISIBLE);
                fabVoy.setVisibility(View.VISIBLE);

                Toasty.Config.getInstance() //Configuracion del toasty

                        .setInfoColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)) //Color de relleno
                        .setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite))  //Color de letra
                        .apply();

                Toasty.info(NewMapActivity.this, getText(R.string.textNoGo), Toast.LENGTH_LONG, true).show();//info del toasty

                databaseReference.child("usersvstravel").child(Code).child(PruUid).child("messageuser").setValue("notgo");


            }
        });

        extraeAlert();
        extraeEstado();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //Cierre del Create

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_perfil) {
            return true;
        }*/

        if (id == R.id.action_config) {

            Intent intent = new Intent(NewMapActivity.this, ConfigAlertActivity.class);
            intent.putExtra("parametro",Code);
            //intent.putExtra("alerta1",ControlAlerta1);
            //intent.putExtra("alerta2",ControlAlerta2);
            //intent.putExtra("alerta3",ControlAlerta3);
            startActivity(intent);
            //finish();


            return true;
        }

        if (id == R.id.action_sesion) {

            logOut();//Firebase Ultimo

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_call) {
            // Handle the camera action

            AlertCall();


        } else if (id == R.id.nav_addroute) {

            Intent intent = new Intent(NewMapActivity.this, NewRouteActivity.class);
            intent.putExtra("parametro", Code);
            //intent.putExtra("alerta1",ControlAlerta1);
            //intent.putExtra("alerta2",ControlAlerta2);
            //intent.putExtra("alerta3",ControlAlerta3);
            startActivity(intent);
            //finish();

        } else if (id == R.id.nav_changeroute) {

            Intent intent = new Intent(NewMapActivity.this, NewListRouteActivity.class);
            intent.putExtra("parametro", Code);
            //intent.putExtra("alerta1",ControlAlerta1);
            //intent.putExtra("alerta2",ControlAlerta2);
            //intent.putExtra("alerta3",ControlAlerta3);
            startActivity(intent);
            //finish();

        } else if (id == R.id.nav_remove) {

            Intent intent = new Intent(NewMapActivity.this, NewTypeRouteActivity.class);
            intent.putExtra("parametro", Code);
           // intent.putExtra("alerta1",ControlAlerta1);
            //intent.putExtra("alerta2",ControlAlerta2);
            //intent.putExtra("alerta3",ControlAlerta3);
            startActivity(intent);
            //finish();



        } //else if (id == R.id.nav_remove) {

        //} else if (id == R.id.nav_call) {

        //}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void pruebabus() {//Traemos cordenadas del bus y nombre

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("travel").child(Code).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                LocRoute locRoute = dataSnapshot.getValue(LocRoute.class);

                longitudRoute   = locRoute.getLongitud();
                latitudRoute    = locRoute.getLatitud();
                nameRoute       = locRoute.getNombre();


                coordenadasRoute = new LatLng(latitudRoute, longitudRoute); ///Concateno para hacer marca

                mMap.clear();//limpia el mapa
                mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marcaruta))
                        .position(coordenadasRoute)
                        .zIndex(1.0f)
                        .draggable(true));

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadasRoute,zoomCamera));
                usuario();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void usuario() {//Cordenadas de usuario

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("usersvstravel").child(Code).child(PruUid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                UserTravel userTravel= dataSnapshot.getValue(UserTravel.class);

                //Extraigo la variables

                longitudUser = userTravel.getLongitud();
                latitudUser  = userTravel.getLatitud();
                Avatar       = userTravel.getIcon();


                coordenadasUser = new LatLng(latitudUser, longitudUser); ///Concateno para hacer marca

                if (Avatar.equals("avatar1")) {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar1)));


                }else if (Avatar.equals("avatar2")) {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar2)));


                }else if (Avatar.equals("avatar3")) {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar3)));


                }else if (Avatar.equals("avatar4")) {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar4)));


                }else if (Avatar.equals("avatar5")) {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar5)));


                }else if (Avatar.equals("avatar6")) {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar6)));


                }else if (Avatar.equals("avatar7")) {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar7)));


                }else {
                    mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar8)));
                }

                extraedistance();
                distancia();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void distancia(){        //Calculo de distancia

        Location locationA = new Location("punto A");
        locationA.setLatitude(latitudUser);
        locationA.setLongitude(longitudUser);
        Location locationB = new Location("punto B");
        locationB.setLatitude(latitudRoute);
        locationB.setLongitude(longitudRoute);

        distance    = locationA.distanceTo(locationB);
        distancemts = (int)distance;
        txtDistance.setText("Distancia aprox: "+distancemts+" metros");

        if ((distance<=alertDistanceOne)&&ControlAlerta1==0)
        {
            starAlarm();
            ControlAlerta1=1;

        }else if((distance<=alertDistanceTwo)&&ControlAlerta2==0){

            starAlarm();
            ControlAlerta2=1;

        }else if((distance<=alertDistanceThree)&&(ControlAlerta3==0)){
            starAlarm();
            ControlAlerta3=1;

        }
    }



    private void phoneData(){//Descarga la informacion de la tabla phoneroute

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("phoneroute").child(Code).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                DialogCall dialogCall = dataSnapshot.getValue(DialogCall.class);
                phone           = dialogCall.getMobileconductor();
                nameDriver      = dialogCall.getNameconductor();
                lastnameDriver  = dialogCall.getLastnameconductor();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void extraedistance(){//Descarga la informacion de la tabla phoneroute

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("uservscode").child(PruUid).child(Code).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                AlertDistance alertDistance = dataSnapshot.getValue(AlertDistance.class);
                alertDistanceOne        = alertDistance.getAlertone();
                alertDistanceTwo        = alertDistance.getAlerttwo();
                alertDistanceThree      = alertDistance.getAlertthree();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void extraeAlert(){

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("alert").child(Code).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                NotifyAlert notifyAlert = dataSnapshot.getValue(NotifyAlert.class);
                alertRoute=notifyAlert.getType();
                alertHour=notifyAlert.getHour();

                alertaseguridad();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    private void alertaseguridad() {///selector de alerta 0. nada, 1 Trafico, 2. Accediente 3.Daño mecanico

        switch (alertRoute){
            case 0:
                break;

            case 1:
                AlertOne();

                break;

            case 2:
                AlertTwo();
                break;

            case 3:
                AlertThree();
                break;
        }}

    private void AlertCall(){//Dialogo de alerta
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage("Esta marcando a "+nameDriver+" "+lastnameDriver+" responsable de la ruta")
                .setTitle(getResources().getString(R.string.calldialog))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yesGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        llamada();
                    }})
                .setNegativeButton(getResources().getString(R.string.noGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") int which) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }


    private void extraeEstado(){

        databaseReference = FirebaseDatabase.getInstance().getReference(); ///Raiz
        databaseReference.child("starandfinish").child(Code).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                StateAlert stateAlert = dataSnapshot.getValue(StateAlert.class);
                StateAl=stateAlert.getEstado();
                Type=stateAlert.getTipo();
                HourInicial=stateAlert.getHourini();
                HourFinal=stateAlert.getHourfin();

                if (StateAl==1){
                    AlertFour();
                }else if(StateAl==2){
                    AlertFive();
                }else{
                }

                if (StateAl==1){

                }else if(StateAl==2){

                }else{
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }





    private void llamada(){//Metodo de llamar

        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(phone)); //Aui toca definir variable para cambiar numero
        if(ActivityCompat.checkSelfPermission(NewMapActivity.this, android.Manifest.permission.CALL_PHONE)!=
                PackageManager.PERMISSION_GRANTED)
            return;
        startActivity(i);
    }


    private void starAlarm() {//Metodo alerta distancia

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logowhitenew);
        builder.setAutoCancel(true);
        builder.setContentTitle("Guardian");
        builder.setContentText("Tu ruta esta a "+ distancemts+ " metros");
        builder.setSubText("Pulsa para ver la ubicación");
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_LIGHTS| RingtonePreference.DEFAULT_ORDER);
        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());

    }


    private void AlertOne() {//Metodo alerta uno


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logowhitenew);
        builder.setAutoCancel(true);
        builder.setContentTitle("Alerta de ruta Guardian");
        builder.setContentText(""+one);
        builder.setSubText("Hora de alerta: "+alertHour);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_LIGHTS| RingtonePreference.DEFAULT_ORDER);
        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());
    }

    private void AlertTwo() {//Metodo alerta dos

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logowhitenew);
        builder.setAutoCancel(true);
        builder.setContentTitle("Alerta de ruta Guardian");
        builder.setContentText(""+two);
        builder.setSubText("Hora de alerta: "+alertHour);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_LIGHTS| RingtonePreference.DEFAULT_ORDER);

        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());
    }

    private void AlertThree() {//Metodo alerta tres


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logowhitenew);
        builder.setAutoCancel(true);
        builder.setContentTitle("Alerta de ruta Guardian");
        builder.setContentText(""+three);
        builder.setSubText("Hora de alerta: "+alertHour);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_LIGHTS| RingtonePreference.DEFAULT_ORDER);

        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());
    }

    private void AlertFour() {//Metodo alerta tres


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logowhitenew);
        builder.setAutoCancel(true);
        builder.setContentTitle("Inicio de recorrido");
        builder.setContentText(four+" "+HourInicial);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_LIGHTS| RingtonePreference.DEFAULT_ORDER);

        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());
    }

    private void AlertFive() {//Metodo alerta tres

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.drawable.logowhitenew);
        builder.setAutoCancel(true);
        builder.setContentTitle("Final de recorrido Guardian");
        builder.setContentText(five+" "+HourFinal);
        builder.setDefaults(Notification.DEFAULT_ALL);
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_LIGHTS| RingtonePreference.DEFAULT_ORDER);

        NotificationManager notificationManager =(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICACION_ID, builder.build());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {//metodo generacion mapa
        mMap = googleMap;
    }




    //-----------------------LogOut

    private void goLogInScreen(){///Firebase ultimo para logOut
        //Intent intent=new Intent(this,SplasScreenActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);

        finish();
        System.exit(0);
    }


    public void logOut(){///Firebase ultimo para logOut

        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                }else{
                    Toast.makeText(NewMapActivity.this,"La cague",Toast.LENGTH_SHORT);
                }
            }
        });
    }


    public void revoke(View view){///Firebase ultimo para logOut

        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()){
                    goLogInScreen();
                }else{
                    Toast.makeText(NewMapActivity.this,"La cague mas revocado",Toast.LENGTH_SHORT);
                }
            }
        });
    }

    @Override///Firebase ultimo para logOut
    protected void onStart() {
        super.onStart();

        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override///Firebase ultimo para logOut

    protected void onStop() {
        super.onStop();

        if (firebaseAuthListener!=null){
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override///Firebase ultimo para logOut
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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

    private void stateDialog(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.estadoruta))
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
