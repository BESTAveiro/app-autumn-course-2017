package com.example.bestaveiro.appcurso.Inventario;

/**
 * Created by Ricardo on 26/06/2016.
 */
public class ItemInventario
{
    public String nome;
    public Integer foto; // opcional
    public float quantidade;
    public GrupoInventario grupo; // opcional

    public ItemInventario(String nome, Integer foto, float quantidade, GrupoInventario grupo)
    {
        this.nome = nome;
        if(foto != null) this.foto = foto;
        this.quantidade = quantidade;
        if(grupo != null) this.grupo = grupo;
    }

    public ItemInventario()
    {

    }

    @Override
    public String toString()
    {
        return String.format("%s - %f", nome, quantidade);
    }
}
