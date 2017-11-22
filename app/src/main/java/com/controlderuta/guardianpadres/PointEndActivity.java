package com.controlderuta.guardianpadres;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class PointEndActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Variables diego viejas

    private static final String TAG ="" ;
    Location location;//objeto localition
    LocationManager locationManager;// objeto location manager
    LocationListener locationListener;
    double latitud=0.0;
    double longitud=0.0;

    //Variables Geolocalizacion
    private GoogleMap mMap;

    //Alert

    AlertDialog alert = null;

    //Variables FireBase
    private DatabaseReference databaseReference;
    private String PruUid; //Para traer token del otro activity
    private DatabaseReference mensajeRef;
    private double latitudUser;
    private  double longitudUser;



    //Button btnSigPoint;

    String Code;
    String Avatar;
    FloatingActionButton btnSigPoint;
    String keyfecha;
    String fechapago;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_end);

        isNetDisponible();


        Code=getIntent().getExtras().getString("parametro");
        Avatar=getIntent().getExtras().getString("avatar");


        btnSigPoint = (FloatingActionButton)findViewById(R.id.fabSigPoint);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapstar);
        mapFragment.getMapAsync(this);

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        btnSigPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (latitud==0.0&&longitud==0.0){

                    AlertConfirm();

                }else{

                    Calendar fechaA = Calendar.getInstance();
                    int a=fechaA.get(Calendar.MONTH)+2;
                    if (a>12){

                       fechapago=(String.valueOf(fechaA.get(Calendar.YEAR))+"-"+1+"-"+String.valueOf(+fechaA.get(Calendar.DAY_OF_MONTH)));

                    }else{

                       fechapago=(String.valueOf(fechaA.get(Calendar.YEAR))+"-"+String.valueOf(+fechaA.get(Calendar.MONTH)+2)+"-"+String.valueOf(+fechaA.get(Calendar.DAY_OF_MONTH)));

                    }



                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    mensajeRef = databaseReference.child("usersvstravel").child(Code).child(PruUid).child("latitud");//Nodo
                    mensajeRef.setValue(latitudUser);
                    mensajeRef = databaseReference.child("usersvstravel").child(Code).child(PruUid).child("longitud");//Nodo
                    mensajeRef.setValue(longitudUser);

                    databaseReference.child("loginuser").child(PruUid).child("login").setValue("1");
                    databaseReference.child("loginuser").child(PruUid).child("fechapago").setValue(fechapago);



                    Intent intent = new Intent(PointEndActivity.this, NewMapActivity.class);
                    intent.putExtra("parametro", Code);
                    startActivity(intent);
                    finish();
                }

            }
        });

        AlertHelp();


}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);//se asigna el servicio de localizacion

        //Alerta de GPS no encendido

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertNoGps();
        }


        locationListener =new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                escribirposicion(location);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                // Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//en caso que el proveedor este desactivado ejecuta para que el usuario lo active
                //startActivity(intent);//se ejecuta en cualquier momento

            }
        };
        geolocalizar();
    }

    //Gps permisos
    @Override
    public void onRequestPermissionsResult (int requestCode,String[] permission,int[] grantResult){
        switch (requestCode){
            case 10:
                geolocalizar();
                break;
            default:return;
        }
    }

    //Metodo alerta gps no encendido


    private void AlertNoGps(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.alertGps))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yesGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })

                .setNegativeButton(getResources().getString(R.string.noGps), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") int which) {
                        dialog.cancel();
                    }
                });
        alert = builder.create();
        alert.show();
    }

    private void AlertHelp(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.helpPoint))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        dialog.cancel();
                        AlertHelpTwo();
                    }

                });
        alert = builder.create();
        alert.show();
    }

    private void AlertHelpTwo(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.helpPointTwo))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        dialog.cancel();
                        AlertHelpThree();
                    }

                });
        alert = builder.create();
        alert.show();
    }

    private void AlertHelpThree(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.helpPointThree))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        dialog.cancel();
                    }

                });
        alert = builder.create();
        alert.show();
    }

    private void AlertConfirm(){
        final AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.confirpoint))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.helpConfirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@SuppressWarnings("unused")final DialogInterface dialog, @SuppressWarnings("unused") final int which) {
                        dialog.cancel();
                    }

                });
        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(alert!=null)
        {
            alert.dismiss();
        }
    }



    //geolocalizar

    private void geolocalizar() {


            /*si a sdk es mayor a la 23 que en varcion code es M cheque permisos y si no es mayor no es requerido
            chequeo de permisos de locacalizacion no es solicitado en verciones menores a sdk 23
             si los permisos fueron denegados no retorna nada de lo contracion entrega la lastlocation*/
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED&&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET},10);

            }else{
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                return;}
        }
        else{
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000000, 0, locationListener);//actualizacion de ubicacion cada 15seg
        }
    }

    //Escribe los datos en firebase travel


    private void escribirposicion(Location location) {
        if (location!=null){
            //verifica que no se null
            //lee la variable location para que no sea null y obtiene la latitud y longitud
            //pilas no puede existir un valor null para obtener el latitud y longitud
            longitud = location.getLongitude();
            latitud=location.getLatitude();
            LatLng coordenadasUser =new LatLng(latitud,longitud);
            //mMap.clear();//limpia el mapa
            //mMap.addMarker(new MarkerOptions().position(actual).draggable(true).title("mi pocicion").snippet(latitud+","+longitud));
            //se va actualizando la lat y long en la vase de datos

            if (Avatar.equals("avatar1")) {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar1)).draggable(true));

            }else if (Avatar.equals("avatar2")) {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar2)).draggable(true));

            }else if (Avatar.equals("avatar3")) {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar3)).draggable(true));

            }else if (Avatar.equals("avatar4")) {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar4)).draggable(true));

            }else if (Avatar.equals("avatar5")) {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar5)).draggable(true));

            }else if (Avatar.equals("avatar6")) {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar6)).draggable(true));

            }else if (Avatar.equals("avatar7")) {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar7)).draggable(true));

            }else {
                mMap.addMarker(new MarkerOptions().position(coordenadasUser).icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar8)).draggable(true));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordenadasUser,14));

            latitudUser = coordenadasUser.latitude;
            longitudUser = coordenadasUser.longitude;

            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.clear();//limpia el mapa

                    if (Avatar.equals("avatar1")) {
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar1))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                    } else if (Avatar.equals("avatar2")) {

                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar2))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                    } else if (Avatar.equals("avatar3")) {

                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar3))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                    }else if (Avatar.equals("avatar4")) {

                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar4))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                    }else if (Avatar.equals("avatar5")) {

                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar5))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                    }else if (Avatar.equals("avatar6")) {

                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar6))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                    }else if (Avatar.equals("avatar7")) {

                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar7))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                    }else if (Avatar.equals("avatar8")) {

                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.avatar8))
                                .position(latLng))
                                .isDraggable();

                        latitudUser = latLng.latitude;
                        longitudUser = latLng.longitude;

                }}
            });

        }
    }

    //liberacion de los servicios*
    protected void onPause(Bundle savedInstanceState) {
        super.onPause();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;}
            else {locationManager.removeUpdates(locationListener);}}
        else { locationManager.removeUpdates(locationListener);}
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










