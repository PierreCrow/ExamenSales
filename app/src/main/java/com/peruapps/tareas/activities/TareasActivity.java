package com.peruapps.tareas.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.peruapps.tareas.DialogFragments.BuscarTarea;
import com.peruapps.tareas.DialogFragments.DetalleTarea;
import com.peruapps.tareas.adapter.ListaTareas;
import com.peruapps.tareas.utils.Utiles;
import com.peruapps.tareas.datasource.AppDataBase;

import com.peruapps.tareas.R;
import com.peruapps.tareas.entities.Tarea;

import java.util.ArrayList;
import java.util.List;


public class TareasActivity extends AppCompatActivity implements ListaTareas.OnItemClickListener,
        BuscarTarea.CierraDialog_BuscarTarea  {



    private ListaTareas adapter;
    private  ListaTareas.OnItemClickListener  mlistener;
    RecyclerView recyclerviewtareas;

    FloatingActionButton btn_agregarTarea,btn_buscartarea;
    Button filtross;

    ArrayList<Tarea> misTareas,misTareasFiltradas;

    AppDataBase BBDD;




    public ProgressDialog dialog;

    @Override
    public void onItemClicked(View v) {

        try{
            DetalleTarea df= new DetalleTarea();
            df.show(getSupportFragmentManager(), "Dialog");

        }
        catch (Exception ex)
        {

        }
    }

    @Override
    public void onClose_BuscaTarea(DialogInterface dialog, Boolean terminadas,String fecha, Boolean busco) {


        filtross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filtross.setVisibility(View.GONE);
                adapter= new ListaTareas(mlistener,getApplicationContext(),misTareas);
                recyclerviewtareas.setAdapter(adapter);
            }
        });



        if(busco==true)
        {
            if(misTareas==null || misTareas.size()==0)
            {
                Toast.makeText(getApplicationContext(), "No hay tareas que buscar", Toast.LENGTH_SHORT).show();
                filtross.setVisibility(View.GONE);
            }
            else
            {

                if(terminadas==true && !fecha.equals(""))
                {
                    misTareasFiltradas = new ArrayList<>();

                    for(int i=0;i<misTareas.size();i++)
                    {
                        Boolean terminadassss= misTareas.get(i).getTerminada();

                        if(terminadassss==true && fecha.equals(misTareas.get(i).getFecha()))
                        {
                            misTareasFiltradas.add(misTareas.get(i));
                        }
                    }

                    filtross.setVisibility(View.VISIBLE);
                    filtross.setText("TERMINADAS - "+fecha);
                    adapter= new ListaTareas(mlistener,getApplicationContext(),misTareasFiltradas);
                    recyclerviewtareas.setAdapter(adapter);
                }
                else
                {


                    if(terminadas==false && !fecha.equals("")) {
                        misTareasFiltradas = new ArrayList<>();

                        for (int i = 0; i < misTareas.size(); i++) {
                            Boolean terminadassss = misTareas.get(i).getTerminada();

                            if (terminadassss == false && fecha.equals(misTareas.get(i).getFecha())) {
                                misTareasFiltradas.add(misTareas.get(i));
                            }
                        }

                        filtross.setVisibility(View.VISIBLE);
                        filtross.setText("PENDIENTES - "+fecha);
                        adapter = new ListaTareas(mlistener, getApplicationContext(), misTareasFiltradas);
                        recyclerviewtareas.setAdapter(adapter);
                    }
                    else
                    {
                        if(terminadas==true && fecha.equals(""))
                        {
                            misTareasFiltradas = new ArrayList<>();

                            for(int i=0;i<misTareas.size();i++)
                            {
                                Boolean terminadassss= misTareas.get(i).getTerminada();

                                if(terminadassss==true)
                                {
                                    misTareasFiltradas.add(misTareas.get(i));
                                }
                            }

                            filtross.setVisibility(View.VISIBLE);
                            filtross.setText("TERMINADAS");
                            adapter= new ListaTareas(mlistener,getApplicationContext(),misTareasFiltradas);
                            recyclerviewtareas.setAdapter(adapter);
                        }
                        else
                        {
                            if(terminadas==false && fecha.equals(""))
                            {
                                misTareasFiltradas = new ArrayList<>();

                                for(int i=0;i<misTareas.size();i++)
                                {
                                    Boolean terminadassss= misTareas.get(i).getTerminada();

                                    if(terminadassss==false)
                                    {
                                        misTareasFiltradas.add(misTareas.get(i));
                                    }
                                }

                                filtross.setVisibility(View.VISIBLE);
                                filtross.setText("PENDIENTES");
                                adapter= new ListaTareas(mlistener,getApplicationContext(),misTareasFiltradas);
                                recyclerviewtareas.setAdapter(adapter);
                            }
                        }
                    }

                }
            }
        }




    }


    private class cargaTareas extends AsyncTask<Void, Void, ArrayList<Tarea>> {

        @Override
        protected void onPreExecute() {
      //      dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //    dialog.setCanceledOnTouchOutside(false);
          //  dialog.setMessage("Cargamdo Tareas");
            //dialog.show();
        }

        @Override
        protected ArrayList<Tarea> doInBackground(Void... params) {

            List<Tarea> tareass;
            ArrayList<Tarea> listatareas=null;
            try {

                tareass=BBDD.tareasDAO().GetAll();

                if(tareass==null || tareass.size()==0)
                {
                }
                else
                {
                    listatareas=new ArrayList<>();

                    for(Integer i=0;i< tareass.size();i++)
                    {
                        listatareas.add(tareass.get(i));
                    }
                }






            }
            catch (Exception e) {
            }

            return listatareas;
        }

        @Override
        protected void onPostExecute(ArrayList<Tarea> result) {
            super.onPostExecute(result);

            if (dialog.isShowing()) {
                dialog.dismiss();
            }


            if(result==null || result.size()==0)
            {

            }
            else
            {
                misTareas =result;

                adapter= new ListaTareas(mlistener,getApplicationContext(), misTareas);
                recyclerviewtareas.setAdapter(adapter);
            }

        }
    }










    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.tareas);



        BBDD= Utiles.Inicia_BBDD_LOCAL(getApplicationContext());

        Toolbar mitoolbar = (Toolbar) findViewById(R.id.toolbar_ttt);
        setSupportActionBar(mitoolbar);


        mlistener=this;


        filtross=(Button)findViewById(R.id.tv_filtros_tareas);
        btn_agregarTarea =(FloatingActionButton)findViewById(R.id.btn_Agregar_tareas);
        btn_buscartarea =(FloatingActionButton)findViewById(R.id.btn_Buscar_tareas);
        recyclerviewtareas =(RecyclerView)findViewById(R.id.rv_tareas);
        recyclerviewtareas.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dialog = new ProgressDialog(getApplicationContext());

        cargaTareas task= new cargaTareas();
        task.execute();







        btn_buscartarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    BuscarTarea df= new BuscarTarea();
                    df.show(getSupportFragmentManager(), "Dialog");

                }
                catch (Exception ex)
                {

                }


            }
        });

        btn_agregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{

                    Intent intento = new Intent(getApplicationContext(), AgregarTareaActivity.class);
                    startActivity(intento);
                }
                catch (Exception ex)
                {

                }



            }
        });




    }






}



