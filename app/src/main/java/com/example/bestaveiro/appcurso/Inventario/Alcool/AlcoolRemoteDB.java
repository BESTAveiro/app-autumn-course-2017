package com.example.bestaveiro.appcurso.Inventario.Alcool;

import android.util.Log;

import com.example.bestaveiro.appcurso.Inventario.FirebaseEvent;
import com.example.bestaveiro.appcurso.Inventario.ItemInventario;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Ricardo on 15/07/2016.
 */
public class AlcoolRemoteDB
{
    static String className = "AlcoolRemoteDB";
    static DatabaseReference firebaseDB;
    static ChildEventListener list = new ChildEventListener()
    {
        /**
         * adicionar à base de dados local
         * este vai ser chamado uma vez por cada child logo que se regista e vai dar exceção
         * @param dataSnapshot
         * @param s
         */
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s)
        {
            Log.d(className, "onChildAdded");
            AlcoolLocalDB.insert(dataSnapshot.getKey(), Float.parseFloat(dataSnapshot.getValue().toString()));
            EventBus.getDefault().post(new FirebaseEvent(dataSnapshot, FirebaseEvent.Tipe.insert));
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s)
        {
            Log.d(className, "onChildChanged");
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot)
        {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s)
        {

        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {

        }
    };

    static void startListeningForEvents()
    {
        if(firebaseDB == null) firebaseDB = FirebaseDatabase.getInstance().getReference();

        firebaseDB.child("alcool").addChildEventListener(list);
    }

    static void stopListeningForEvents()
    {
        if(firebaseDB == null) firebaseDB = FirebaseDatabase.getInstance().getReference();

        firebaseDB.child("alcool").removeEventListener(list);
    }

    /**
     * vai buscar as informações mais recentes ao firebase e atualiza a base de dados local
     * eu acho que deve apagar o que está na bd local e escrever aquilo que vem
     * @return
     */
   static ArrayList<ItemInventario> retrieveAll()
   {
       Log.d(className, "retrieveAll começou");
       if(firebaseDB == null) firebaseDB = FirebaseDatabase.getInstance().getReference();

       new Thread(new Runnable()
       {
           @Override
           public void run()
           {
               firebaseDB.child("alcool").addListenerForSingleValueEvent(new ValueEventListener()
               {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot)
                   {
                       Log.d(className, "onDataChange called");
                       Log.d(className, "Adapter - "+Thread.currentThread().toString());
                       // apagar todos os registos da base de dados
                       AlcoolLocalDB.deleteAll();

                       Iterable<DataSnapshot> arr = dataSnapshot.getChildren();
                       for(DataSnapshot tmp : arr)
                       {
                           Log.d(className, String.format("%s - %s", tmp.getKey(), tmp.getValue()));
                           AlcoolLocalDB.insert(tmp.getKey(), Float.parseFloat(tmp.getValue().toString()));
                       }
                       Log.d(className, "acabou onDataChange");

                       new Thread(new Runnable()
                       {
                           @Override
                           public void run()
                           {
                               synchronized (Alcool.lock)
                               {
                                   Alcool.wait = false;
                                   Alcool.lock.notifyAll();
                                   Log.d(className, "notificou");
                               }
                           }
                       }).start();

                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError)
                   {

                   }
               });


           }
       }).start();
       Log.d(className, "saiu do retrieveAll");

       return null;
   }

    static void update(ItemInventario item, float quantidade, DatabaseReference.CompletionListener listener)
    {
        if(firebaseDB == null) firebaseDB = FirebaseDatabase.getInstance().getReference();

        firebaseDB.child("alcool").child(item.nome).setValue(quantidade, listener);
    }

    static void delete(ItemInventario item, DatabaseReference.CompletionListener listener)
    {
        if(firebaseDB == null) firebaseDB = FirebaseDatabase.getInstance().getReference();

        firebaseDB.child("alcool").child(item.nome).removeValue(listener);
    }

    static void insert(ItemInventario item, DatabaseReference.CompletionListener listener)
    {
        if(firebaseDB == null) firebaseDB = FirebaseDatabase.getInstance().getReference();

        firebaseDB.child("alcool").child(item.nome).setValue(item.quantidade, listener);
    }
}
