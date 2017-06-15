package com.example.bestaveiro.appcurso.Notificacoes;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Ricardo on 19/08/2016.
 */
public class Notificacao
{
    Date data;
    String texto;
    String titulo;

    public Notificacao(Date data, String texto, String titulo)
    {
        this.data = data;
        this.texto = texto;
        this.titulo = titulo;
    }

    public String getNicelyFormattedDate()
    {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dt.format(data);
    }

    @Override
    public String toString()
    {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return String.format("Data : %s \n" +
                "Texto : %s\n" +
                "Título : %s", dt.format(data), texto, titulo);
    }
}
