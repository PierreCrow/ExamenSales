package com.peruapps.tareas.DialogFragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FechaDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{



    //Interface created for communicating this dialog fragment events to called fragment
    public interface DatePickerDialogFragmentEvents{
        void onDateSelected(String date);
    }

    DatePickerDialogFragmentEvents dpdfe;

    public void setDatePickerDialogFragmentEvents(DatePickerDialogFragmentEvents dpdfe){
        this.dpdfe = dpdfe;
    }





    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, yy, mm, dd);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);



        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = sdf.format(c.getTime());
        dpdfe.onDateSelected(formattedDate);

        SharedPreferences preferencias=getContext().getSharedPreferences("FechaHoraBBDD", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("FechaBBDD", formattedDate );
        editor.commit();


    }






}
