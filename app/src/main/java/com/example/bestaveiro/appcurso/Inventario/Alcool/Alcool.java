package com.example.bestaveiro.appcurso.Inventario.Alcool;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bestaveiro.appcurso.Inventario.FirebaseEvent;
import com.example.bestaveiro.appcurso.Inventario.Inventario_versao_tap_swipe;
import com.example.bestaveiro.appcurso.Inventario.ItemInventario;
import com.example.bestaveiro.appcurso.R;
import com.example.bestaveiro.appcurso.StaticMethods;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Alcool extends Fragment
{
    public static String className = "Álcool";
    // nome a usar no SharedPreferences
    private static String alcoolDateName = "alcool data";
    static CoordinatorLayout coordinatorLayout;
    SwipeRefreshLayout srl;
    RecyclerView rv;
    AlcoolAdapter alcoolAdapter;
    static Snackbar snackbar;
    static FloatingActionButton fab;
    static Toolbar toolbar;
    static TabLayout tabLayout;
    static TextView dataUltimaAtu;
    private SharedPreferences sharedPref;
    ConnectivityManager conManager;

    static int positionInViewPager = 0;

    static Boolean wait = true;
    static final Object lock = new Object();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(className, "onCreate");
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        conManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        Log.d(className, "onCreateView");
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_alcool, container, false);

        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout_appbar);
        fab = StaticMethods.showFAB(getActivity());
        dataUltimaAtu = (TextView) myView.findViewById(R.id.dataUltimaAtualização);

        srl = (SwipeRefreshLayout) myView.findViewById(R.id.alcool_swipe);

        rv = (RecyclerView) myView.findViewById(R.id.alcool_RecyclerView);

        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        String data;
        if(!sharedPref.contains(alcoolDateName)) data = "Ainda Não Foi Buscado!";
        else data = new Date(sharedPref.getLong(alcoolDateName, 0)).toString();
        dataUltimaAtu.setText("Última Atualização : "+data);

        return myView;
    }



    @Override
    public void onStart()
    {
        super.onStart();

        Log.d(className, "onStart chamado");
        alcoolAdapter = new AlcoolAdapter();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);
        Log.d(className, "setAdapter");
        rv.setAdapter(alcoolAdapter);



        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Log.d(className, "onRefresh chamado");
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        alcoolAdapter.retrieve();
                    }
                }).start();
            }
        });

        AlcoolRemoteDB.startListeningForEvents();

        // registar para receber eventos
        EventBus.getDefault().register(alcoolAdapter);
    }

    @Override
    public void onStop()
    {
        AlcoolRemoteDB.stopListeningForEvents();
        EventBus.getDefault().unregister(alcoolAdapter);
        super.onStop();

        Log.d(className, "onStop chamado");
    }



    class AlcoolAdapter extends RecyclerView.Adapter<AlcoolAdapter.CustomViewHolder>
    {
        ArrayList<ItemInventario> alcoolArr = new ArrayList<>();
        NetworkInfo activeNetwork;

        @Subscribe(threadMode = ThreadMode.ASYNC)
        public void registerFABonClick(Integer a)
        {
            if(a==positionInViewPager) registerFABonClick();
            Log.d(className, "register fab");
        }

        @Subscribe(threadMode = ThreadMode.ASYNC)
        public void atualizarRecyclerView(FirebaseEvent event)
        {
            Log.d(className, "Adapter - event");
            alcoolArr = AlcoolLocalDB.retrieveAll();

            rv.post(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.d(className, "Adapter - notifyDataSetChanged");
                    AlcoolAdapter.this.notifyDataSetChanged();
                }
            });

            String snackbarMessage = "";
            switch (event.tipe)
            {
                case delete:
                    snackbarMessage = String.format("%s deleted", event.dataSnapshot.getKey());
                    break;
                case update:
                    snackbarMessage = String.format("%s updated", event.dataSnapshot.getKey());
                    break;
                case insert:
                    snackbarMessage = String.format("%s inserted", event.dataSnapshot.getKey());
                    break;
            }

            snackbar = Snackbar.make(fab, snackbarMessage, Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        public class CustomViewHolder extends RecyclerView.ViewHolder
        {
            ImageView imageView;
            TextView textViewNome;
            TextView textViewQuantidade;
            ImageButton changeButton;

            public CustomViewHolder(View v)
            {
                super(v);
            }
        }

        public void orderArraylist()
        {
            Collections.sort(alcoolArr, new Comparator<ItemInventario>()
            {
                @Override
                public int compare(ItemInventario a, ItemInventario b)
                {
                    return a.nome.compareTo(b.nome);
                }
            });
        }

        public AlcoolAdapter()
        {
            //ir buscar as cenas à base de dados e preencher a ArrayList
            //primeiro ir buscar à bd local e se estiver ligado à net ir buscar à remota e atualizar
            // a arrayList e a bd local
            //mostrar um aviso se não conseguir buscar info da remota
            Log.d(className, "construtor Adapter chamado");
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    alcoolArr = AlcoolLocalDB.retrieveAll();
                    retrieve();
                    rv.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            notifyDataSetChanged();
                        }
                    });
                }
            }).start();

            if(Inventario_versao_tap_swipe.viewPager.getCurrentItem() == positionInViewPager)
            {
                registerFABonClick();
            }
        }

        void registerFABonClick()
        {
            fab.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // criar um alert dialog com duas caixas de texto, uma para o nome e outra para a quantidade
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Insere um Item");
                    LayoutInflater layInf = LayoutInflater.from(getActivity());
                    final View convertView = layInf.inflate(R.layout.insert_item_db, null);
                    builder.setView(convertView);
                    builder.setPositiveButton("Inserir", null);
                    builder.setNegativeButton("Cancelar", null);
                    final AlertDialog ad = builder.create();
                    // isto é para o dialog não fechar ao carregar numa das opções
                    ad.setOnShowListener(new DialogInterface.OnShowListener()
                    {
                        @Override
                        public void onShow(DialogInterface dialog)
                        {
                            // para mostrar logo o teclado
                            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(convertView.findViewById(R.id.insert_nome), 0);

                            ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    int errorCount = 0;

                                    final EditText editNome = (EditText) convertView.findViewById(R.id.insert_nome);
                                    final EditText editQuantidade = (EditText) convertView.findViewById(R.id.insert_quantidade);
                                    TextInputLayout editNomeTIL = (TextInputLayout) convertView.findViewById(R.id.insert_nome_TIL);
                                    TextInputLayout editQuantidadeTIL = (TextInputLayout) convertView.findViewById(R.id.insert_quantidade_TIL);
                                    if(editNome.getText().toString().length() == 0)
                                    {
                                        editNomeTIL.setError("Tens de escrever nome!");
                                        editNomeTIL.setErrorEnabled(true);
                                        errorCount++;
                                    }
                                    else
                                    {
                                        editNomeTIL.setErrorEnabled(false);
                                    }

                                    try
                                    {
                                        Float.parseFloat(editQuantidade.getText().toString());
                                        editQuantidadeTIL.setErrorEnabled(false);
                                    }
                                    catch (Exception ex)
                                    {
                                        editQuantidadeTIL.setError("Tens de escrever número!");
                                        editQuantidadeTIL.setErrorEnabled(true);
                                        errorCount++;
                                    }

                                    if(errorCount > 0) return;

                                    new Thread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            Error error = insert(new ItemInventario(editNome.getText().toString(),
                                                    null, Float.parseFloat(editQuantidade.getText().toString()), null));

                                            String errMsg = "";
                                            try
                                            {
                                                switch(error)
                                                {
                                                    case net:
                                                        errMsg = "liga-te à net para inserir";
                                                        throw new Exception();
                                                    case insertError:
                                                        errMsg = "erro na inserção";
                                                        throw new Exception();
                                                    case noError:
                                                        ad.dismiss();
                                                        break;
                                                }
                                            }
                                            catch(Exception ex)
                                            {
                                                //esconder o teclado
                                                if(getActivity().getCurrentFocus()!=null)
                                                {
                                                    Log.d(className, "vai esconder teclado");
                                                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                                                    inputMethodManager.hideSoftInputFromWindow(convertView.findViewById(R.id.insert_nome).getWindowToken(), 0);
                                                }

                                                snackbar = Snackbar.make(fab, errMsg, Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }
                                    }).start();
                                }
                            });
                        }
                    });
                    ad.show();
                }
            });
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layInf = LayoutInflater.from(getActivity().getBaseContext());
            View convertView = layInf.inflate(R.layout.inventario_child, null);

            CustomViewHolder customViewHolder = new CustomViewHolder(convertView);
            customViewHolder.imageView = (ImageView) convertView.findViewById(R.id.inventario_child_image_view);
            customViewHolder.textViewNome = (TextView) convertView.findViewById(R.id.inventario_child_text_view_nome);
            customViewHolder.textViewQuantidade = (TextView) convertView.findViewById(R.id.inventario_child_text_view_quantidade);
            customViewHolder.changeButton = (ImageButton) convertView.findViewById(R.id.change_button);

            return customViewHolder;
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, final int position)
        {
            ItemInventario tmpItem = alcoolArr.get(position);

            // ir buscar bitmaps
            if(tmpItem.foto != null)
            {
                //holder.imageView.setImageResource(tmpItem.foto);
                Glide.with(Alcool.this).load(tmpItem.foto).into(holder.imageView);
                Log.d(className, String.format("posição %d tem foto", position));
            }
            else
            {
                String a = null;
                Glide.with(Alcool.this).load(a).into(holder.imageView);
            }
            holder.textViewNome.setText(tmpItem.nome);
            holder.textViewQuantidade.setText(String.format("%.2f",tmpItem.quantidade));
            holder.changeButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    // criar um alert dialog em que dê para alterar a quantidade e atualizar isso nas bases de dados
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Atualiza a Quantidade");
                    final EditText edText = new EditText(getActivity());
                    edText.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                    edText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    edText.onEditorAction(EditorInfo.IME_ACTION_PREVIOUS);
                    builder.setView(edText);
                    builder.setPositiveButton("OK", null);
                    builder.setNeutralButton("Eliminar", null);
                    builder.setNegativeButton("Cancelar", null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener()
                    {
                        @Override
                        public void onDismiss(DialogInterface dialog)
                        {
                            if (snackbar != null) snackbar.dismiss();
                        }
                    });
                    final AlertDialog ad = builder.create();
                    ad.setOnShowListener(new DialogInterface.OnShowListener()
                    {
                        @Override
                        public void onShow(DialogInterface dialog)
                        {
                            // para mostrar logo o teclado
                            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(edText, 0);

                            ad.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    new Thread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            final float quantidade;
                                            String errMsg = "número inválido";
                                            try
                                            {
                                                quantidade = Float.parseFloat(edText.getText().toString());
                                                // atualizar a base de dados local e a remota
                                                Error err = update(alcoolArr.get(position), quantidade);
                                                switch(err)
                                                {
                                                    case net:
                                                        errMsg = "liga-te à net para atualizar";
                                                        throw new Exception();
                                                    case updateError:
                                                        errMsg = "erro na atualização";
                                                        throw new Exception();
                                                }
                                                //se teve sucesso
                                                ad.dismiss();
                                                // aqui podia fazer eu o update em vez de eliminar e meter tudo
                                                retrieve();
                                            } catch (Exception ex)
                                            {
                                                ex.printStackTrace();
                                                //esconder o teclado
                                                if(getActivity().getCurrentFocus()!=null)
                                                {
                                                    Log.d(className, "vai esconder teclado");
                                                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                                                    inputMethodManager.hideSoftInputFromWindow(edText.getWindowToken(), 0);
                                                }

                                                snackbar = Snackbar.make(fab, errMsg, Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }
                                    }).start();

                                }
                            });

                            ad.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View v)
                                {
                                    new Thread(new Runnable()
                                    {
                                        @Override
                                        public void run()
                                        {
                                            Log.d(className, "vai eliminar");
                                            String errMsg = "número inválido";
                                            //eliminar da bd local e da bd remota
                                            //tentar primeira na remota, para ver se tem net
                                            try
                                            {
                                                Error err = remove(alcoolArr.get(position));
                                                switch(err)
                                                {
                                                    case net:
                                                        errMsg = "liga-te à net para atualizar";
                                                        throw new Exception();
                                                    case deleteError:
                                                        errMsg = "erro na eliminação";
                                                        throw new Exception();
                                                }
                                                //se teve sucesso
                                                ad.dismiss();

                                                rv.post(new Runnable()
                                                {
                                                    @Override
                                                    public void run()
                                                    {
                                                        Log.d(className, "Adapter - notifyDataSetChanged");
                                                        alcoolArr.remove(position);
                                                        AlcoolAdapter.this.notifyDataSetChanged();
                                                    }
                                                });
                                            }

                                            catch (Exception ex)
                                            {
                                                ex.printStackTrace();
                                                //esconder o teclado
                                                if(getActivity().getCurrentFocus()!=null)
                                                {
                                                    Log.d(className, "vai esconder teclado");
                                                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
                                                    inputMethodManager.hideSoftInputFromWindow(edText.getWindowToken(), 0);
                                                }

                                                snackbar = Snackbar.make(fab, errMsg, Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }
                                    }).start();

                                }
                            });
                        }
                    });
                    ad.show();
                }
            });
        }

        /**
         * vai buscar as informações a ambas as bases de dados novamente e atualiza a lista
         */
        boolean retrieve()
        {
            //ir buscar as cenas à base de dados e preencher a ArrayList
            //primeiro ir buscar à bd local e se estiver ligado à net ir buscar à remota e atualizar
            // a arrayList e a bd local
            //mostrar um aviso se não conseguir buscar info da remota

            Log.d(className, "Adapter - retrieve chamado");

            if(!checkInternetConnectivity())
            {
                if(Inventario_versao_tap_swipe.viewPager.getCurrentItem() == positionInViewPager)
                {
                    snackbar = Snackbar.make(fab, "liga-te à net para atualizar", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    srl.post(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            Log.d(className, "Adapter - refreshing false");
                            srl.setRefreshing(false);
                        }
                    });
                }

                return false;
            }

            srl.post(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.d(className, "Adapter - refreshing true");
                    srl.setRefreshing(true);
                }
            });


            AlcoolRemoteDB.retrieveAll();

            Log.d(className, "Adapter - vai adquirir lock");
            Log.d(className, "Adapter - "+Thread.currentThread().toString());
            synchronized (lock)
            {
                Log.d(className, "Adapter - wait "+wait);
                while(wait)
                {
                    try
                    {
                        lock.wait();
                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                Log.d(className, "Adapter - saiu do wait");
            }

            wait = true;


            Log.d(className, "Adapter - vai chamar retrieveAll local");
            alcoolArr = AlcoolLocalDB.retrieveAll();
            Log.d(className, String.format("Adapter - %d", alcoolArr.size()));

            rv.post(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.d(className, "Adapter - notifyDataSetChanged");
                    AlcoolAdapter.this.notifyDataSetChanged();
                }
            });

            final long time = System.currentTimeMillis();

            dataUltimaAtu.post(new Runnable()
            {
                @Override
                public void run()
                {
                    dataUltimaAtu.setText("Última Atualização : " + new Date(time).toString());
                }
            });


            sharedPref.edit().putLong(alcoolDateName, time).apply();

            if(Inventario_versao_tap_swipe.viewPager.getCurrentItem() == positionInViewPager)
            {
                Log.d(className, "retrieve - entrou no if para mostrar snackbar");
                snackbar = Snackbar.make(fab, "atualizado com sucesso", Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            srl.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Log.d(className, "Adapter - refreshing false");
                    srl.setRefreshing(false);
                }
            }, 800);

            return true;
        }


        Error update(ItemInventario item, float quantidade)
        {
            if(!checkInternetConnectivity())
            {
                return Error.net;
            }

            //falta tb aqui atualizar na base de dados remota
            if(!AlcoolLocalDB.update(item.nome, quantidade)) return Error.updateError;
            AlcoolRemoteDB.update(item, quantidade, new DatabaseReference.CompletionListener()
            {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                {
                    snackbar = Snackbar.make(fab, "atualizado com sucesso", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });


            return Error.noError;
        }

        /**
         * para remover um item tanto da bd local como da bd remota
         * deve sempre ser chamada numa thread non-UI
         * @param item
         * @return
         */
        Error remove(ItemInventario item)
        {
            if(!checkInternetConnectivity())
            {
                return Error.net;
            }

            //falta tb aqui atualizar na base de dados remota
            if(!AlcoolLocalDB.delete(item.nome)) return Error.deleteError;
            AlcoolRemoteDB.delete(item, new DatabaseReference.CompletionListener()
            {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                {
                    snackbar = Snackbar.make(fab, "eliminado com sucesso", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            });


            return Error.noError;
        }

        /**
         * para inserir um item tanto da bd local como da bd remota
         * deve sempre ser chamada numa thread non-UI
         * @param item
         * @return
         */
        Error insert(ItemInventario item)
        {
            if(!checkInternetConnectivity())
            {
                return Error.net;
            }

            AlcoolRemoteDB.insert(item, new DatabaseReference.CompletionListener()
            {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                {

                }
            });
            if(!AlcoolLocalDB.insert(item.nome, item.quantidade)) return Error.insertError;


            return Error.noError;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public int getItemCount()
        {
            return alcoolArr.size();
        }

        boolean checkInternetConnectivity()
    {
        activeNetwork = conManager.getActiveNetworkInfo();

        return (activeNetwork != null && activeNetwork.isConnected());
    }

    }

    enum Error
    {
        net, updateError, deleteError, insertError, noError
    }

}
