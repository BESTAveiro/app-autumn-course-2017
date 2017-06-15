package com.example.bestaveiro.appcurso.Inventario;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Ricardo on 16/08/2016.
 */
public class FirebaseEvent
{
    public DataSnapshot dataSnapshot;
    public Tipe tipe;

    public FirebaseEvent(DataSnapshot dataSnapshot, Tipe tipe)
    {
        this.dataSnapshot = dataSnapshot;
        this.tipe = tipe;
    }

    public enum Tipe
    {
        insert, delete, update
    }
}
