package com.example.juanm.practicau2_1_sqlitecon2tablas_juanmanueldelhoyo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText clave;
    Button buscar;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertar();
            }
        });

        lista = findViewById(R.id.lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                Poliza pol = new Poliza(MainActivity.this);
                final Poliza polizas[] = pol.consulta();
                final int pos = i;
                LinearLayout layout = new LinearLayout(MainActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                EditText idCoche, modelo, marca,año,fecha,precio,tipoPoliza, idDueño;
                idCoche=modelo=marca=año=fecha=precio=tipoPoliza=idDueño=new EditText(MainActivity.this);
                Button eliminar, actualizar;
                eliminar=actualizar=new Button(MainActivity.this);
                idCoche.setText(polizas[pos].idCoche+"");
                idCoche.setEnabled(false);
                modelo.setText(polizas[pos].modelo+"");
                marca.setText(polizas[pos].marca+"");
                año.setText(polizas[pos].año+"");
                fecha.setText(polizas[pos].fecha+"");
                precio.setText(polizas[pos].precio+"");
                tipoPoliza.setText(polizas[pos].tipoPoliza+"");
                idDueño.setText(polizas[pos].idDueño+"");
                LinearLayout layout2 = new LinearLayout(MainActivity.this);
                layout2.setOrientation(LinearLayout.HORIZONTAL);
                eliminar.setText("Eliminar");
                actualizar.setText("Actualizar");
                layout2.addView(eliminar);
                layout2.addView(actualizar);
                layout.addView(idCoche);
                layout.addView(modelo);
                layout.addView(marca);
                layout.addView(año);
                layout.addView(fecha);
                layout.addView(precio);
                layout.addView(tipoPoliza);
                layout.addView(idDueño);
                layout.addView(layout2);

                alerta.setTitle("Detalle de poliza no: "+polizas[pos].idCoche)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.dueños) {
            Intent i = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected void onStart(){
        super.onStart();
        Poliza pol = new Poliza(this);
        Poliza polizas[] = pol.consulta();
        if(polizas==null){
            Toast.makeText(this,"No hay ninguna poliza registrada",Toast.LENGTH_LONG).show();
        } else {
            String NoPol[] = new String[polizas.length];
            for (int i = 0; i < NoPol.length; i++) {
                NoPol[i] = polizas[i].idCoche + "\n" + polizas[i].fecha;
            }
            ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, NoPol);
            lista.setAdapter(adap);
        }
    }

    void insertar(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setPadding(10,0,10,0);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText modelo= new EditText(this);
        modelo.setHint("Modelo...");
        final EditText marca = new EditText(this);
        marca.setHint("Marca...");
        final EditText año = new EditText(this);
        año.setHint("Año...");
        final EditText fecha = new EditText(this);
        fecha.setHint("Fecha...");
        final EditText precio = new EditText(this);
        precio.setHint("Precio...");
        final EditText tipoPoliza = new EditText(this);
        tipoPoliza.setHint("Tipo poliza...");
        final Spinner idDueño=new Spinner(this);;
        Dueño d=new Dueño(this);
        Dueño[] dueños = d.consulta();
        ArrayList<String> options=new ArrayList<String>();
        if(dueños!=null){
            for(int i=0 ; i<dueños.length; i++){
                options.add(dueños[i].id+"");
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,options);
        idDueño.setAdapter(adapter);
        layout.addView(modelo);
        layout.addView(marca);
        layout.addView(año);
        layout.addView(fecha);
        layout.addView(precio);
        layout.addView(tipoPoliza);
        layout.addView(idDueño);
       final Poliza p = new Poliza(MainActivity.this);
        alerta.setTitle("Ingresar nueva poliza")
                .setView(layout)
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ift) {
                        Poliza pol = new Poliza(0,modelo.getText().toString(),
                                marca.getText().toString(),Integer.parseInt(año.getText().toString()),fecha.getText().toString(),
                                Float.parseFloat(precio.getText().toString()),tipoPoliza.getText().toString(),
                                idDueño.getSelectedItem().toString());
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
        });
        if (dueños == null) {
            Toast.makeText(this,"No hay ningun Dueño, no es posible crear polizas",Toast.LENGTH_LONG).show();
        }else{
            alerta.show();
        }
    }

    void mensaje(String m){
        Toast.makeText(this,m,Toast.LENGTH_LONG).show();
    }
}
