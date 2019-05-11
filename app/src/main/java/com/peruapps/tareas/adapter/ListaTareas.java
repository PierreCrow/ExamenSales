package com.peruapps.tareas.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peruapps.tareas.R;
import com.peruapps.tareas.entities.Tarea;

import java.util.ArrayList;
import java.util.List;


//ADAPTADOR PARA LA LISTA USANDO RECYCLERVIEW
public class ListaTareas extends RecyclerView.Adapter<ListaTareas.ListaAsistenciasViewHolder> {


    private List<Tarea> items =new ArrayList<>();



    public OnItemClickListener mlistener;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClicked(View v);
    }

    public void add(Tarea item){
        items.add(item);
        notifyItemInserted(items.size()-1);
    }



    @Override
    public ListaAsistenciasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlistatareas, parent, false);
        ListaAsistenciasViewHolder rvMainAdapterViewHolder = new ListaAsistenciasViewHolder(view);

        return  rvMainAdapterViewHolder;
    }

    @Override
    public void onBindViewHolder(final ListaAsistenciasViewHolder holder, int position) {
        Tarea tareaaas = items.get(position);

        holder.nombreTarea.setText(tareaaas.getNombre());
        holder.id_tarea.setText(tareaaas.getId_tarea().toString());
        holder.detalleTarea.setText(tareaaas.getDetalles().toString());
        holder.fechaTarea.setText(tareaaas.getFecha().toString());

        if(tareaaas.getTerminada()==true)
        {
            holder.terminada.setText("TERMINADA");
            holder.terminada.setTextColor(ContextCompat.getColor(mContext, R.color.DarkBlue));
        }
        else
        {
            holder.terminada.setText("PENDIENTE");
            holder.terminada.setTextColor(ContextCompat.getColor(mContext, R.color.DarkRed));
        }



    }

    @Override
    public int getItemCount() {

        return items.size();
    }

      class ListaAsistenciasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombreTarea,id_tarea,detalleTarea,fechaTarea,terminada;


        public ListaAsistenciasViewHolder(View v){
            super(v);
            nombreTarea=(TextView) v.findViewById(R.id.tv_nombrealmacen_ItemAlmacen);
            id_tarea=(TextView) v.findViewById(R.id.idtarea);
            detalleTarea=(TextView) v.findViewById(R.id.detalletarea);
            fechaTarea=(TextView) v.findViewById(R.id.fechatarea);
            terminada=(TextView) v.findViewById(R.id.terminadatareaa);


            v.setOnClickListener(this);
        }


         @Override
         public void onClick(View v) {


             SharedPreferences preferencias=v.getContext().getSharedPreferences("TareaSeleccionada",Context.MODE_PRIVATE);
             SharedPreferences.Editor editor=preferencias.edit();
             editor.putString("id_tarea", id_tarea.getText().toString() );
             editor.putString("detalleTarea", detalleTarea.getText().toString() );
             editor.putString("fechaTarea", fechaTarea.getText().toString() );
             editor.putString("nombreTarea", nombreTarea.getText().toString() );
             editor.putString("terminada", terminada.getText().toString() );
             editor.commit();

             mlistener.onItemClicked(v);


         }

    }


    public ListaTareas(OnItemClickListener listener, Context context, ArrayList<Tarea> item){
        this.items = item;
        this.mlistener = listener;
        this.mContext=context;

    }



}


