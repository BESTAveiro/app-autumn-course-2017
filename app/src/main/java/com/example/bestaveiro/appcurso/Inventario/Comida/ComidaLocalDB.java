package com.example.bestaveiro.appcurso.Inventario.Comida;

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
public class ComidaLocalDB extends SQLiteAssetHelper
{
    private static final String DATABASE_NAME = "comida.db";
    private static final int DATABASE_VERSION = 1;
    private static String tableName = "comida";
    private static String className = "ComidaLocalDB";

    public ComidaLocalDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * vai à base de dados local buscar os dados de comida e retorna uma ArrayList
     * @return ArrayList com os nomes e quantidades de comida
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

        //buscar string array dos resources com o nome das comidas que têm foto
        String comidaComImagem[] = con.getResources().getStringArray(R.array.comida_com_imagem);
        ArrayList<String> comidaComImagemString = new ArrayList<>(Arrays.asList(comidaComImagem));

        ArrayList<ItemInventario> comidaArray = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from comida order by nome asc", null);
        Log.d(className, cursor.getCount()+"");
        cursor.moveToFirst();

        while(!cursor.isAfterLast())
        {
            String nome = cursor.getString(0);
            float quantidade = cursor.getFloat(1);
            Integer b = null;
            if(comidaComImagemString.contains(nome))
            {
                b = con.getResources().getIdentifier(nome, "mipmap",con.getPackageName());
            }
            ItemInventario tmp = new ItemInventario(nome, b, quantidade, null);
            Log.d(className, tmp.toString());
            comidaArray.add(tmp);
            cursor.moveToNext();
        }
        cursor.close();

        db.close();
        return comidaArray;
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
     * vai à base de dados local e muda a quantidade da comida cujo nome é @name
     * @param name nome da comida cuja quantidade queremos mudar
     * @param quantity a nova quantidade
     * @return se a operação foi bem sucedida
     */
    public static boolean update(String name, float quantity)
    {
        Object[] tmpArray = openDatabaseAndGetContext(false);
        SQLiteDatabase db = (SQLiteDatabase) tmpArray[0];
        if(db == null)
        {
            Log.d(className, "não foi possível abrir a base de dados");
            return false;
        }

        ContentValues cv = new ContentValues();
        cv.put(ColumnNames.quantidade.toString(), quantity);

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
        ComidaLocalDB aDB = new ComidaLocalDB(con);
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
