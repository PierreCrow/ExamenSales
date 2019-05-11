package com.peruapps.tareas.DialogFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.peruapps.tareas.R;
import com.peruapps.tareas.activities.ActualizarTareaActivity;
import com.peruapps.tareas.activities.TareasActivity;
import com.peruapps.tareas.utils.Utiles;
import com.peruapps.tareas.datasource.AppDataBase;
import com.peruapps.tareas.entities.Tarea;


public class DetalleTarea extends DialogFragment {


    TextView tv_nombreTarea, tv_fecha, tv_detalleTarea,tv_estado;


    Integer mIDALMACEN;

    Button btnEditar,btnEliminar;



    AppDataBase BBDD;





    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {

            //numero de pixeles que tendra de ancho
            // int width = 700;
            int width=ViewGroup.LayoutParams.MATCH_PARENT;

            //la altura se ajustara al contenido
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;

            //se lo asigno a mi dialogfragment
            dialog.getWindow().setLayout(width, height);

            //con esto hago que sea invicible
            //en este caso no lo hara xq le de un fondo al linearlayout
            // dialog.getWindow().getAttributes().alpha = 0.9f;
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


    @Override
    public void onDismiss(DialogInterface dialog) {


    }


    @Override
    public void onCancel (DialogInterface dialog)
    {
        dismiss();
    }



    private class Elimina_Tarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {



            try {

                BBDD.tareasDAO().DeleteById(mIDALMACEN);

            }
            catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);


            dismiss();

            Intent intent = new Intent(getActivity(), TareasActivity.class);
            startActivity(intent);

        }
    }



    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {

      View view = getActivity().getLayoutInflater().inflate(R.layout.detalletarea, new LinearLayout(getActivity()), false);

        BBDD= Utiles.Inicia_BBDD_LOCAL(getActivity());

        tv_nombreTarea =(TextView)view.findViewById(R.id.tv_NombreTarea_DetalleTarea);
        tv_fecha =(TextView)view.findViewById(R.id.tv_fechaTarea_DetalleTarea);
        tv_detalleTarea =(TextView)view.findViewById(R.id.tv_DetalleTarea_DetalleTarea);
        tv_estado =(TextView)view.findViewById(R.id.tv_EstadoTarea_DetalleTarea);


        btnEditar=(Button)view.findViewById(R.id.btn_editar_DetalleTarea);
        btnEliminar=(Button)view.findViewById(R.id.btn_eliminar_DetalleTarea);

        SharedPreferences prefe=getActivity().getSharedPreferences("TareaSeleccionada", Context.MODE_PRIVATE);
        mIDALMACEN=Integer.parseInt(prefe.getString("id_tarea",""));
        String nombreTarea=(prefe.getString("nombreTarea",""));
        String detalleTarea=(prefe.getString("detalleTarea",""));
        String fechaTarea=(prefe.getString("fechaTarea",""));
        String tareaterminada=prefe.getString("terminada","");


        tv_detalleTarea.setText(detalleTarea);
        tv_nombreTarea.setText(nombreTarea);
        tv_fecha.setText(fechaTarea);
        tv_estado.setText(tareaterminada);






        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
                Intent intent = new Intent(getActivity(), ActualizarTareaActivity.class);
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Muestradialog(getActivity());
               // Elimina_Tarea task= new Elimina_Tarea();
               // task.execute();

            }
        });







        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);



       builder.setContentView(view);
        return  builder;

    }

    void Muestradialog(Context ctx)
    {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:




                        Elimina_Tarea task= new Elimina_Tarea();
                        task.execute();


                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage("¿Esta seguro que desea eliminar la tarea").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }







}

