package com.example.bestaveiro.appcurso.Inventario;

import android.graphics.drawable.Drawable;

/**
 * Created by Ricardo on 26/06/2016.
 */
public class GrupoInventario
{
    String nome;
    Drawable foto; // opcional

    public GrupoInventario(String nome, Drawable foto)
    {
        this.nome = nome;
        if(foto != null) this.foto = foto;
    }
}
