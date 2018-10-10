package com.example.juanm.practicau2_1_sqlitecon2tablas_juanmanueldelhoyo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class Dueño {
    String id, nombre,domicilio, telefono;
    ConexionBase base;

    public Dueño(String id, String nombre, String domicilio, String telefono){
        this.id = id;
        this.nombre = nombre;
        this.domicilio=domicilio;
        this.telefono = telefono;
    }
    public Dueño(Activity activity){
        base = new ConexionBase(activity,"segurocoche",null,1);
    }

    public boolean insertar(Dueño dueño){
        try{
            SQLiteDatabase tabla = base.getWritableDatabase();
            ContentValues data = new ContentValues();
            data.put("Id",dueño.id);
            data.put("Nombre",dueño.nombre);
            data.put("Domicilio",dueño.domicilio);
            data.put("Telefono",dueño.telefono);
            long res = tabla.insert("Dueño",null, data);
            tabla.close();
            if(res<0){
                return false;
            }
        }catch (SQLiteException e){
            Log.e("ERROR: ",e.getMessage());
            return false;
        }
        return true;
    }

    public Dueño consultar(String id){
        Dueño d=null;
        try{
            SQLiteDatabase tabla = base.getReadableDatabase();
            String SQL = "SELECT * FROM ABOGADO WHERE TELEFONO=?";
            String claves[] = {telefono};

            Cursor c = tabla.rawQuery(SQL,claves);
            if(c.moveToFirst()){
                d = new Dueño(c.getString(0),c.getString(1),c.getString(2),c.getString(3));
            }
            tabla.close();
        }catch (SQLiteException e){
            return null;
        }
        return d;
    }

    public Dueño[] consulta(){
        Dueño[] resultado=null;
        try{
            SQLiteDatabase tabla = base.getReadableDatabase();
            String SQL = "SELECT * FROM Dueño";

            Cursor c = tabla.rawQuery(SQL,null);
            if(c.moveToFirst()){
                resultado = new Dueño[c.getCount()];
                int i=0;
                do {
                    resultado[i++] = new Dueño(c.getString(0), c.getString(1), c.getString(2), c.getString(3));
                }while(c.moveToNext());
            }
            tabla.close();
        }catch (SQLiteException e){
            return null;
        }
        return resultado;
    }

    public boolean eliminar(Dueño dueño){
        try{
            SQLiteDatabase tabla = base.getWritableDatabase();
            String[] data = {""+dueño.id};
            long res = tabla.delete("Dueño","Id=?",data);
            tabla.close();
            if(res==0){
                return false;
            }
        }catch (SQLiteException e){
            return false;
        }
        return true;
    }

    public boolean actualizar(Dueño dueño){
        try{
            SQLiteDatabase tabla = base.getWritableDatabase();
            ContentValues data = new ContentValues();
            data.put("Nombre",dueño.nombre);
            data.put("Domicilio",dueño.domicilio);
            data.put("Telefono",dueño.telefono);
            String[] clave = {""+dueño.id};
            long res = tabla.update("Dueño",data,"Id=?",clave);
            tabla.close();
            if(res<0){
                return false;
            }
        }catch (SQLiteException e){
            return false;
        }
        return true;
    }
}
