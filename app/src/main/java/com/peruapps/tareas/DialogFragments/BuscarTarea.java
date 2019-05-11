package com.peruapps.tareas.DialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.peruapps.tareas.R;
import com.peruapps.tareas.utils.Utiles;
import com.peruapps.tareas.datasource.AppDataBase;


public class BuscarTarea extends DialogFragment
        implements   FechaDialog.DatePickerDialogFragmentEvents{




    Spinner spi_Estadotarea;


    TextView tvfecha;
    AppDataBase BBDD;

    Boolean busco;
    Button btn_buscar,btn_fecha;

    String mEstadoTarea;





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





    //evento cuando el dialog se cierra
    @Override
    public void onDismiss(DialogInterface dialog) {



        Activity aaa=getActivity();


        Boolean terminadas;



        if(mEstadoTarea!=null)
        {

            if( mEstadoTarea.equals("TERMINADAS"))
            {
                terminadas=true;
            }
            else {
                terminadas=false;
            }

            if(aaa instanceof CierraDialog_BuscarTarea)
            {  ((CierraDialog_BuscarTarea)aaa).onClose_BuscaTarea(dialog, terminadas,tvfecha.getText().toString(),busco);}

        }


    }


    @Override
    public void onCancel (DialogInterface dialog)
    {
        dismiss();
    }

    @Override
    public void onDateSelected(String date) {
       String FECHA = date.replace("-","/");

        tvfecha.setText(FECHA);


    }








    //evento cuando se crea el dialog
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {

      View view = getActivity().getLayoutInflater().inflate(R.layout.buscartarea, new LinearLayout(getActivity()), false);


        BBDD= Utiles.Inicia_BBDD_LOCAL(getActivity());


        spi_Estadotarea=(Spinner) view.findViewById(R.id.spi_Estado_tarea);
        btn_buscar=(Button) view.findViewById(R.id.btn_buscar_BuscarTarea);
        btn_fecha=(Button) view.findViewById(R.id.btn_ecogefecha_BuscarTarea);
        tvfecha=(TextView)view.findViewById(R.id.tv_fecha_BuscarTarea) ;


        busco=false;

        Utiles.SeteaSpinner_EstadoTarea(spi_Estadotarea,getActivity());


        btn_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FechaDialog picker = new FechaDialog();
                picker.setDatePickerDialogFragmentEvents(BuscarTarea.this);
                picker.show(getFragmentManager(), "Date Picker");
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   nroDoc=et_rucCliente.getText().toString();


                mEstadoTarea =spi_Estadotarea.getSelectedItem().toString();

                if(mEstadoTarea.equals("Seleccione"))
                {
                    Toast.makeText(getActivity(), "Escoja un estado", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dismiss();
                    busco=true;
                }



            }
        });



        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);



       builder.setContentView(view);
        return  builder;

    }

    public interface CierraDialog_BuscarTarea
    {
        public void onClose_BuscaTarea(DialogInterface dialog, Boolean terminadas,String fecha, Boolean busco);
    }







}

