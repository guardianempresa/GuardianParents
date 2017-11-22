package com.controlderuta.guardianpadres;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import android.support.v7.app.AppCompatActivity;

import com.controlderuta.guardianpadres.model.LenguajeListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class UserAvatarActivity extends AppCompatActivity {

    private String lenguajeProgramacion[]=new String[]{"Teresa","Camila","Alicia","Monica","Alejo","Cristian",
            "Diego","Mateo"};

    private String description[]=new String[]{"La intelectual","La trabajadora","La espiritual","La divertida","El independiente","El alternativo",
            "El genio","El trabajador"};

    private Integer[] imgid={
            R.drawable.avatarcir1,
            R.drawable.avatarcir2,
            R.drawable.avatarcir3,
            R.drawable.avatarcir4,
            R.drawable.avatarcir5,
            R.drawable.avatarcir6,
            R.drawable.avatarcir7,
            R.drawable.avatarcir8,
    };

    private ListView lista;

    //firebase var

    DatabaseReference databaseReference;
    String PruUid; //key no llave generada por google

    String Code;
    String Up;
    String Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_avatar);

        Code=getIntent().getExtras().getString("parametro");

        databaseReference = FirebaseDatabase.getInstance().getReference();//obtiene el enlace de la db "ejemplos-android:"
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//Para extraeerr el Uid del cliente
        PruUid=user.getUid(); //Guardamos Uid en variable


        LenguajeListAdapter adapter=new LenguajeListAdapter(this,lenguajeProgramacion,imgid,description);
        lista=(ListView)findViewById(R.id.milista);
        lista.setAdapter(adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Slecteditem= lenguajeProgramacion[+position];


                if (Slecteditem.equals("Teresa")){
                    Up="R.drawable.avatarcir1";
                    Icon="avatar1";
                }else if(Slecteditem.equals("Camila")){
                    Up="R.drawable.avatarcir2";
                    Icon="avatar2";
                }else if(Slecteditem.equals("Alicia")){
                    Up="R.drawable.avatarcir3";
                    Icon="avatar3";
                }else if(Slecteditem.equals("Monica")){
                    Up="R.drawable.avatarcir4";
                    Icon="avatar4";
                }else if(Slecteditem.equals("Alejo")){
                    Up="R.drawable.avatarcir5";
                    Icon="avatar5";
                }else if(Slecteditem.equals("Cristian")){
                    Up="R.drawable.avatarcir6";
                    Icon="avatar6";
                }else if(Slecteditem.equals("Diego")){
                    Up="R.drawable.avatarcir7";
                    Icon="avatar7";
                }else {
                    Up ="R.drawable.avatarcir8";
                    Icon="avatar8";
                }


                DatabaseReference ref =FirebaseDatabase.getInstance().getReference();
                DatabaseReference mensajeRef = ref.child("usersvstravel").child(Code).child(PruUid).child("iconface");
                mensajeRef.setValue(Up);


                DatabaseReference ref2 =FirebaseDatabase.getInstance().getReference();
                DatabaseReference mensajeRef2 = ref2.child("usersvstravel").child(Code).child(PruUid).child("icon");
                mensajeRef2.setValue(Icon);

                Intent intent = new Intent(UserAvatarActivity.this,PointEndActivity.class);
                intent.putExtra("parametro", Code);
                intent.putExtra("avatar",Icon);
                startActivity(intent);
                finish();
            }
        });
    }
}
