package com.example.juanm.practicau2_1_sqlitecon2tablas_juanmanueldelhoyo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionBase extends SQLiteOpenHelper {
    public ConexionBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Dueño(Id VARCHAR(20) PRIMARY KEY NOT NULL, Nombre VARCHAR(500), Domicilio VARCHAR(500), Telefono VARCHAR(30))");
        db.execSQL("CREATE TABLE Poliza(IdCoche INTEGER PRIMARY KEY AUTOINCREMENT, Modelo VARCHAR(60), Marca VARCHAR(200), Año int,FechaInicio varchar(20), Precio float, TipoPoliza varchar(200), IdDueño INTEGER, FOREIGN KEY(IdDueño) REFERENCES Dueño(Id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
