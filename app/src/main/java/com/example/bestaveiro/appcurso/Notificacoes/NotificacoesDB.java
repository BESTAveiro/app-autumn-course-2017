package com.example.bestaveiro.appcurso.Notificacoes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.example.bestaveiro.appcurso.GlobalContext;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ricardo on 19/08/2016.
 */
public class NotificacoesDB extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "notificacao.db";
    private static final int DATABASE_VERSION = 1;
    private static String tableName = "notificacao";
    private static String className = "NotificacoesDB";

    public NotificacoesDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * vai à base de dados local buscar os dados de álcool e retorna uma ArrayList
     * @return ArrayList com os nomes e quantidades de álcool
     */
    public static ArrayList<Notificacao> retrieveAll()
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return null;
        }

        ArrayList<Notificacao> notArray = new ArrayList<>();

        ArrayList<StatusBarNotification> notificacaoArray = new ArrayList<>();
        Log.d(className, tableName);
        Cursor cursor = db.rawQuery("select * from notificacao order by data asc", null);
        Log.d(className, cursor.getCount()+"");
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            String data = cursor.getString(0);
            String texto = cursor.getString(1);
            String titulo = cursor.getString(2);

            Notificacao tmp = new Notificacao(new Date(Long.parseLong(data)), texto, titulo);
            Log.d(className, tmp.toString());
            notArray.add(tmp);
            cursor.moveToNext();
        }
        cursor.close();

        db.close();
        return notArray;
    }

    public static boolean insert(Notificacao not)
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return false;
        }

        ContentValues cv = new ContentValues();
        Log.d(className, not.data.toString());
        Log.d(className, not.data.getTime()+"");
        cv.put(ColumnNames.data.toString(), not.data.getTime());
        cv.put(ColumnNames.texto.toString(), not.texto);
        cv.put(ColumnNames.titulo.toString(), not.titulo);

        long tmp = db.insert(tableName, null, cv);
        Log.d(className, tmp + " inserted");
        db.close();
        return tmp!=-1;
    }

    /**
     * abrir a base de dados local e buscar o context
     * @param write se é para buscar writableDatabase (true) ou readableDatabase (false)
     * @return array de Object com a base de dados local na primeira posição e o context na segunda
     */
    private static Object[] openDatabaseAndGetContext(boolean write)
    {
        Context con = GlobalContext.getContext();
        NotificacoesDB aDB = new NotificacoesDB(con);
        SQLiteDatabase db = null;
        try
        {
            if(!write) db = aDB.getReadableDatabase();
            else db = aDB.getWritableDatabase();
        }
        catch(Exception ex)
        {
            //não foi possível abrir a base de dados
            Log.d(className, "não foi possível abrir a base de dados");
        }

        return new Object[]{db, con};
    }

    private enum ColumnNames
    {
        data, texto, titulo
    }
}
