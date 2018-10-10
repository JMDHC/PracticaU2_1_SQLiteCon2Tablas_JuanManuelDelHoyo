package com.example.juanm.practicau2_1_sqlitecon2tablas_juanmanueldelhoyo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

public class Poliza {
    int idCoche, año;
    String modelo,marca, fecha, tipoPoliza, idDueño;
    float precio;
    ConexionBase base;

    public Poliza(int idCoche, String modelo, String marca, int año, String fecha, float precio, String tipoPoliza, String idDueño){
        this.idCoche = idCoche;
        this.modelo=modelo;
        this.marca=marca;
        this.año=año;
        this.fecha=fecha;
        this.precio=precio;
        this.tipoPoliza=tipoPoliza;
        this.idDueño=idDueño;
    }
    public Poliza(Activity activity){
        base = new ConexionBase(activity,"segurocoche",null,1);
    }

    public boolean insertar(Poliza poliza){
        try{
            SQLiteDatabase tabla = base.getWritableDatabase();
            ContentValues data = new ContentValues();
            data.put("Modelo",poliza.modelo);
            data.put("Marca",poliza.marca);
            data.put("Año",poliza.año);
            data.put("Fecha",poliza.fecha);
            data.put("Precio",poliza.precio);
            data.put("TipoPoliza",poliza.tipoPoliza);
            data.put("IdDueño",poliza.idDueño);
            long res = tabla.insert("Poliza","IdCoche",data);
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

    public Poliza[] consulta(){
        Poliza[] resultado=null;
        try{
            SQLiteDatabase tabla = base.getReadableDatabase();
            String SQL = "SELECT * FROM Poliza";
            Cursor c = tabla.rawQuery(SQL,null);
            if(c.moveToFirst()){
                resultado = new Poliza[c.getCount()];
                int i=0;
                do {
                    resultado[i++] = new Poliza(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3),c.getString(4), c.getFloat(5), c.getString(6), c.getString(7));
                }while(c.moveToNext());
            }
            tabla.close();
        }catch (SQLiteException e){
            return null;
        }
        return resultado;
    }

    public boolean eliminar(Poliza poliza){
        try{
            SQLiteDatabase tabla = base.getWritableDatabase();
            String[] data = {poliza.idCoche+""};
            long res = tabla.delete("Poliza","IdCoche=?",data);
            tabla.close();
            if(res==0){
                return false;
            }
        }catch (SQLiteException e){
            return false;
        }
        return true;
    }

    public boolean actualizar(Poliza poliza){
        try{
            SQLiteDatabase tabla = base.getWritableDatabase();
            ContentValues data = new ContentValues();
            data.put("Modelo",poliza.modelo);
            data.put("Marca",poliza.marca);
            data.put("Año",poliza.año);
            data.put("Fecha",poliza.fecha);
            data.put("Precio",poliza.precio);
            data.put("TipoPoliza",poliza.tipoPoliza);
            data.put("IdDueño",poliza.idDueño);
            String[] clave = {poliza.idCoche+""};
            long res = tabla.update("Poliza",data,"IdCoche=?",clave);
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
