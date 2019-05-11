package com.peruapps.tareas.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.peruapps.tareas.DialogFragments.FechaDialog;
import com.peruapps.tareas.R;
import com.peruapps.tareas.utils.Utiles;
import com.peruapps.tareas.datasource.AppDataBase;
import com.peruapps.tareas.entities.Tarea;

import java.util.ArrayList;

public class ActualizarTareaActivity extends AppCompatActivity  implements  FechaDialog.DatePickerDialogFragmentEvents {



    EditText et_nombre,et_detalles;
    Button btn_actualizar,btn_fecha;
    Switch swi_terminartarea;

    TextView tv_fecha;

    String mNombre, mDetalles,mFechaRegistro;


    Integer mIDtarea;
    String nombreTarea,detalleTarea,fechaTarea;
    Boolean terminada;


    AppDataBase BBDD;






    void Viewsreference()
    {
        et_nombre=(EditText)findViewById(R.id.et_nombretarea_ActualizarTarea);
        et_detalles=(EditText)findViewById(R.id.et_detalles_ActualizarTarea);
        tv_fecha=(TextView)findViewById(R.id.tv_fecha_ActualizarTarea) ;
        swi_terminartarea=(Switch)findViewById(R.id.swi_terminadatarea);
        btn_fecha=(Button)findViewById(R.id.btn_fecha_Actualizar);

        btn_actualizar =(Button)findViewById(R.id.btn_actualizar_ActualizarTarea);


        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FechaDialog picker = new FechaDialog();
                picker.setDatePickerDialogFragmentEvents(ActualizarTareaActivity.this);
                picker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNombre=et_nombre.getText().toString();
                mDetalles =et_detalles.getText().toString();
                mFechaRegistro=tv_fecha.getText().toString();



                if(mNombre.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Ingrese nombre de tarea", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(mDetalles.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Ingrese detalle de tarea", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {


                        // nuevoTarea= new Tarea(mNombre,mDedatlle,mCodigo);



                        if(swi_terminartarea.isChecked())
                        {terminada=true;}
                        else
                        {terminada=false;}

                        Actualiza_Tarea taski= new Actualiza_Tarea();
                        taski.execute();






                    }
                }




            }
        });


        seteaObjetos();
    }


    void seteaObjetos()
    {


        SharedPreferences prefe=getSharedPreferences("TareaSeleccionada", Context.MODE_PRIVATE);
        mIDtarea =Integer.parseInt(prefe.getString("id_tarea",""));
         nombreTarea=(prefe.getString("nombreTarea",""));
         detalleTarea=(prefe.getString("detalleTarea",""));
         fechaTarea=(prefe.getString("fechaTarea",""));
       //  terminada=Boolean.parseBoolean(prefe.getString("terminada",""));
         String tareaterminada=prefe.getString("terminada","");

        et_nombre.setText(nombreTarea);
        et_detalles.setText(detalleTarea);
        tv_fecha.setText(fechaTarea);

        if(tareaterminada.equals("TERMINADA"))
        {swi_terminartarea.setChecked(true);}
        else
        {swi_terminartarea.setChecked(false);}


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actualizartarea);

        BBDD= Utiles.Inicia_BBDD_LOCAL(getApplicationContext());

        Toolbar  mitoolbar = (Toolbar) findViewById(R.id.toolbar_actualizar);
        setSupportActionBar(mitoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Viewsreference();











    }

    @Override
    public void onDateSelected(String date) {

        String selectedDate = date;
        tv_fecha.setText(selectedDate);
    }


    private class Actualiza_Tarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {



            try {

                BBDD.tareasDAO().UpdateTarea(mIDtarea,mNombre,mDetalles,mFechaRegistro,terminada);

            }
            catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);



            Intent intent = new Intent(getApplicationContext(), TareasActivity.class);
            startActivity(intent);

        }
    }



}
