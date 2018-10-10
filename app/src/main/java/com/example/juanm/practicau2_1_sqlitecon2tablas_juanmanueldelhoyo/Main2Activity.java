package com.example.juanm.practicau2_1_sqlitecon2tablas_juanmanueldelhoyo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    EditText clave;
    Button buscar;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
            }
        });

        lista = findViewById(R.id.lista_dueños);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(Main2Activity.this);
                Dueño due = new Dueño(Main2Activity.this);
                final Dueño dueños[] = due.consulta();
                final int pos = i;
                LinearLayout layout = new LinearLayout(Main2Activity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText idDueño =new EditText(Main2Activity.this);
                final EditText nombre =new EditText(Main2Activity.this);
                final EditText domicilio =new EditText(Main2Activity.this);
                final EditText telefono =new EditText(Main2Activity.this);
                Button eliminar, actualizar;
                eliminar=new Button(Main2Activity.this);
                actualizar=new Button(Main2Activity.this);
                idDueño.setText(dueños[pos].id+"");
                idDueño.setEnabled(false);
                nombre.setText(dueños[pos].nombre+"");
                domicilio.setText(dueños[pos].domicilio+"");
                telefono.setText(dueños[pos].telefono+"");
                LinearLayout layout2 = new LinearLayout(Main2Activity.this);
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                eliminar.setText("Eliminar");
                actualizar.setText("Actualizar");
                layout2.addView(eliminar);
                layout2.addView(actualizar);
                layout.addView(idDueño);
                layout.addView(nombre);
                layout.addView(domicilio);
                layout.addView(telefono);
                layout.addView(layout2);

                eliminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dueño dueño = new Dueño(Main2Activity.this);
                        boolean respuesta = dueño.eliminar(new Dueño(idDueño.getText().toString(),nombre.getText().toString(),
                                domicilio.getText().toString(), telefono.getText().toString()));
                        if(respuesta){
                            mensaje("Se elimino con exito");
                        } else {
                            mensaje("Error no se pudo eliminar");
                        }
                    }
                });

                alerta.setTitle("Detalle de dueño: "+dueños[pos].nombre)
                        .setView(layout)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int ift) {
                                dialogInterface.dismiss();
                            }
                        }).show();
            }
        });
    }

    protected void onStart(){
        super.onStart();
        Dueño due = new Dueño(this);
        Dueño dueños[] = due.consulta();
        if(dueños==null){
            Toast.makeText(this,"No hay ningun dueño registrado",Toast.LENGTH_LONG).show();
        } else {
            String NoPol[] = new String[dueños.length];
            for (int i = 0; i < NoPol.length; i++) {
                NoPol[i] = dueños[i].id + "\n" + dueños[i].nombre;
            }
            ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, NoPol);
            lista.setAdapter(adap);
        }
    }

    void insertar(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(Main2Activity.this);
        LinearLayout layout = new LinearLayout(Main2Activity.this);
        layout.setPadding(10,0,10,0);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText idDueño= new EditText(this);
        idDueño.setHint("Id dueño...");
        final EditText nombre= new EditText(this);
        nombre.setHint("Nombre...");
        final EditText direccion = new EditText(this);
        direccion.setHint("Direccion...");
        final EditText telefono = new EditText(this);
        telefono.setHint("Telefono...");
        layout.addView(idDueño);
        layout.addView(nombre);
        layout.addView(direccion);
        layout.addView(telefono);
        final Dueño p = new Dueño(Main2Activity.this);
        alerta.setTitle("Ingresar nuevo dueño")
                .setView(layout)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ift) {
                        Dueño pol = new Dueño(idDueño.getText().toString(),nombre.getText().toString(),
                                direccion.getText().toString(),telefono.getText().toString());
                        if(p.insertar(pol))
                            mensaje("Se inserto correctamente");
                        else
                            mensaje("error al insertar");
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    void mensaje(String m){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show();
    }

}
