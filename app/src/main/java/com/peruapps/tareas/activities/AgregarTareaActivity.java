package com.peruapps.tareas.activities;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.peruapps.tareas.DialogFragments.FechaDialog;
import com.peruapps.tareas.R;
import com.peruapps.tareas.utils.Utiles;
import com.peruapps.tareas.datasource.AppDataBase;
import com.peruapps.tareas.entities.Tarea;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AgregarTareaActivity extends AppCompatActivity
        implements  FechaDialog.DatePickerDialogFragmentEvents{



    EditText et_nombre,et_detalle;
    Button btn_agregar;
    TextView fecha;
    Button btn_fecha;
    AppDataBase BBDD;

    Boolean Terminada;






    String mNombre, mDedatlle;

    Tarea nuevoTarea;






    void Viewsreference()
    {
        et_nombre=(EditText)findViewById(R.id.et_nombre);
        et_detalle=(EditText)findViewById(R.id.et_detalle);
        fecha=(TextView)findViewById(R.id.tv_fecha);
        btn_fecha=(Button)findViewById(R.id.btn_fecha);



        btn_agregar=(Button)findViewById(R.id.btn_agregar_tarea);


        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FechaDialog picker = new FechaDialog();
                picker.setDatePickerDialogFragmentEvents(AgregarTareaActivity.this);
                picker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNombre=et_nombre.getText().toString();
                mDedatlle =et_detalle.getText().toString();






                if(mNombre.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Ingrese nombre", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    if(mDedatlle.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "Ingrese detalles", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {


                        nuevoTarea = new Tarea(mNombre, mDedatlle,fecha.getText().toString(),Terminada);


                        Inserta_tarea taski= new Inserta_tarea();
                        taski.execute();






                    }
                }




            }
        });



    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agregartarea);

        BBDD= Utiles.Inicia_BBDD_LOCAL(getApplicationContext());

        Terminada=false;

        Viewsreference();

        Toolbar  mitoolbar = (Toolbar) findViewById(R.id.toolbar_agregartarea);
        setSupportActionBar(mitoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);



        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = mdformat.format(calendar.getTime());
        fecha.setText(strDate);


    }

    @Override
    public void onDateSelected(String date) {

        String selectedDate = date;
        fecha.setText(selectedDate);

    }



    private class Inserta_tarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {



            try {

                BBDD.tareasDAO().InsertOnlyOne(nuevoTarea);

            }
            catch (Exception e) {
                String aa=e.getMessage();
                String bb;
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
