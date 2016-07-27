package com.example.bestaveiro.appcurso.Inventario.Alcool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bestaveiro.appcurso.GlobalContext;
import com.example.bestaveiro.appcurso.Inventario.ItemInventario;
import com.example.bestaveiro.appcurso.R;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Ricardo on 15/07/2016.
 */
public class AlcoolLocalDB  extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "alcool.db";
    private static final int DATABASE_VERSION = 1;
    private static String tableName = "alcool";
    private static String columnsName[] = new String[]{"nome", "quantidade"};
    private static String className = "AlcoolLocalDB";

    public AlcoolLocalDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * vai à base de dados local buscar os dados de álcool e retorna uma ArrayList
     * @return ArrayList com os nomes e quantidades de álcool
     */
    public static ArrayList<ItemInventario> retrieveAll()
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        Context con = (Context) tmpArray[1];
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return null;
        }

        //buscar string array dos resources com o nome do alcoól que tem foto
        String alcoolComImagem[] = con.getResources().getStringArray(R.array.alcool_com_imagem);
        ArrayList<String> alcoolComImagemString = new ArrayList<>(Arrays.asList(alcoolComImagem));

        ArrayList<ItemInventario> alcoolArray = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from alcool", null);
        Log.d(className, cursor.getCount()+"");
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            String nome = cursor.getString(0);
            float quantidade = cursor.getFloat(1);
            Integer b = null;
            if(alcoolComImagemString.contains(nome))
            {
                b = con.getResources().getIdentifier(nome, "mipmap",con.getPackageName());
            }
            ItemInventario tmp = new ItemInventario(nome, b, quantidade, null);
            Log.d(className, tmp.toString());
            alcoolArray.add(tmp);
            cursor.moveToNext();
        }
        cursor.close();

        db.close();
        return alcoolArray;
    }

    /**
     * vai à base de dados local e muda a quantidade do álcool cujo nome é @name
     * @param name nome do álcool cuja quantidade queremos mudar
     * @param quantity a nova quantidade
     * @return se a operação foi bem sucedida
     */
    public static boolean update(String name, float quantity)
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        Context con = (Context) tmpArray[1];
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(ColumnNames.quantidade.toString(), quantity);

        //db.rawQuery("update ? set quantidade = ? where nome = ?", new String[]{tableName, quantity, name});
        return db.update(tableName, cv, "nome = ?", new String[]{name}) == 1;
    }

    /**
     * abrir a base de dados local e buscar o context
     * @param write se é para buscar writableDatabase (true) ou readableDatabase (false)
     * @return array de Object com a base de dados local na primeira posição e o context na segunda
     */
    private static Object[] openDatabaseAndGetContext(boolean write)
    {
        Context con = GlobalContext.getContext();
        AlcoolLocalDB aDB = new AlcoolLocalDB(con);
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
        nome,
        quantidade;
    }
}
