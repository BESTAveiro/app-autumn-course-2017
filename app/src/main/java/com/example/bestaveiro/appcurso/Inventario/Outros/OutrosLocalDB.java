package com.example.bestaveiro.appcurso.Inventario.Outros;

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
public class OutrosLocalDB extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "outros.db";
    private static final int DATABASE_VERSION = 1;
    private static String tableName = "outros";
    private static String className = "OutrosLocalDB";

    public OutrosLocalDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * vai à base de dados local buscar os dados de outros e retorna uma ArrayList
     * @return ArrayList com os nomes e quantidades de outros
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

        //buscar string array dos resources com o nome dos outros que têm foto
        String outrosComImagem[] = con.getResources().getStringArray(R.array.outros_com_imagem);
        ArrayList<String> outrosComImagemString = new ArrayList<>(Arrays.asList(outrosComImagem));

        ArrayList<ItemInventario> outrosArray = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from outros order by nome asc", null);
        Log.d(className, cursor.getCount()+"");
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            String nome = cursor.getString(0);
            float quantidade = cursor.getFloat(1);
            Integer b = null;
            if(outrosComImagemString.contains(nome))
            {
                b = con.getResources().getIdentifier(nome, "mipmap",con.getPackageName());
            }
            ItemInventario tmp = new ItemInventario(nome, b, quantidade, null);
            Log.d(className, tmp.toString());
            outrosArray.add(tmp);
            cursor.moveToNext();
        }
        cursor.close();

        db.close();
        return outrosArray;
    }

    public static boolean insert(String nome, float quantidade)
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(ColumnNames.nome.toString(), nome);
        cv.put(ColumnNames.quantidade.toString(), quantidade);

        long tmp = db.insert(tableName, null, cv);
        Log.d(className, tmp + " inserted");
        return tmp!=-1;
    }

    /**
     * apagar todos os registos da base de dados
     * @return true para sucesso, false para fracasso
     */
    public static boolean deleteAll()
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return false;
        }

        Log.d(className,db.delete(tableName,"1",null)+ " deleted");

        return true;
    }

    /**
     * apaga um determinado registo da base de dados
     * @return true para sucesso, false para fracasso
     */
    public static boolean delete(String name)
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return false;
        }

        Log.d(className,db.delete(tableName, "nome = ?" ,new String[]{name})+ " deleted");

        return true;
    }

    /**
     * vai à base de dados local e muda a quantidade dos outros cujo nome é @name
     * @param name nome do outro cuja quantidade queremos mudar
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

        //db.rawQuery("retrieve ? set quantidade = ? where nome = ?", new String[]{tableName, quantity, name});
        int retuirn = db.update(tableName, cv, "nome = ?", new String[]{name});
        db.close();
        return retuirn == 1;
    }

    /**
     * abrir a base de dados local e buscar o context
     * @param write se é para buscar writableDatabase (true) ou readableDatabase (false)
     * @return array de Object com a base de dados local na primeira posição e o context na segunda
     */
    private static Object[] openDatabaseAndGetContext(boolean write)
    {
        Context con = GlobalContext.getContext();
        OutrosLocalDB aDB = new OutrosLocalDB(con);
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
        quantidade
    }
}
